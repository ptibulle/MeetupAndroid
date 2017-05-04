/**
 * 
 */

package fr.ptibulle.cordova;

import android.app.Service;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.LOG;

import fr.ptibulle.meetupservice2.MeetupServiceInterface;

/**
 * 
 */
public class MeetupService2 extends CordovaPlugin {

	private CallbackContext mContext;
	private MeetupServiceReceiver meetupServiceReceiver;
	private MeetupServiceInterface mService;
 
 private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
			LOG.d("MeetupService2", "onService Connected : MeetupService2 Interface");
            mService = MeetupServiceInterface.Stub.asInterface(service);			
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
			LOG.d("MeetupService2", "onService Disconnected : MeetupService2 Interface");
            mService = null;
        }
    };

	@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public void onDestroy() {
        LOG.d("MeetupService2", "onDestroy");
        // Déconnexion du service
        if(null != mService) {
            LOG.d("MeetupService2", "unbindService : DataPush Interface");
            this.cordova.getActivity().unbindService(conn);
        }

        super.onDestroy();
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
       
        mContext = callbackContext;

		if (action.equalsIgnoreCase("BINDSERVICE")) {
			LOG.d("MeetupService2", "BINDSERVICE");
			if (mService != null) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
			} else {
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("fr.ptibulle.meetupservice2", "fr.ptibulle.meetupservice2.MeetupService"));
				intent.setAction("fr.ptibulle.meetupservice2.ACTION_BIND");
				this.cordova.getActivity().bindService(intent, conn, Service.BIND_AUTO_CREATE);
			}
			return true;
        } else if (action.equalsIgnoreCase("UNBINDSERVICE")) {
			LOG.d("MeetupService2", "UNBINDSERVICE");
			if(null != mService) {
		    	this.cordova.getActivity().unbindService(conn);
			}
            return true;
        } else if (action.equalsIgnoreCase("STARTTIMER")) {
			 if (mService == null){
				callbackContext.error("Service not bound");					
				return true;
        	}
			LOG.d("MeetupService2", "STARTTIMER");
			try {
				mService.startTimer();
				meetupServiceReceiver = new MeetupServiceReceiver(callbackContext);
				this.cordova.getActivity().registerReceiver(meetupServiceReceiver, new IntentFilter("fr.ptibulle.meetupservice2.Broadcast"));
			} catch (RemoteException re) {
				callbackContext.error("RemoteException : " + re.getMessage());
			} finally {
				return true;
			}
        } else if (action.equalsIgnoreCase("STOPTIMER")) {
			if (mService == null){
				callbackContext.error("Service not bound");					
				return true;
        	}
			LOG.d("MeetupService2", "STOPTIMER");
			try {
				mService.stopTimer();
				if (meetupServiceReceiver != null) {
					this.cordova.getActivity().unregisterReceiver(meetupServiceReceiver);
					meetupServiceReceiver = null;
				}
			} catch (RemoteException re) {
				callbackContext.error("RemoteException : " + re.getMessage());
			} finally {
				return true;
			}
        } 

        return false;
    }

	private class MeetupServiceReceiver extends BroadcastReceiver {

		CallbackContext callbackContext;

		MeetupServiceReceiver(CallbackContext callbackContext) {
			super();
			this.callbackContext = callbackContext;
		}

		@Override
		public void onReceive(Context context, Intent pIntent) {
			Log.i("MeetupServiceReceiver", "onReceive");
			if (callbackContext != null) {
				try{
					PluginResult res;
					if (pIntent.hasExtra("error")) {
						JSONObject result = new JSONObject();
						result.put("erreur", pIntent.getStringExtra("error"));

						res = new PluginResult(PluginResult.Status.ERROR, result);
					} else {
						JSONObject result = new JSONObject();
						if (pIntent.hasExtra("Message") ){
							result.put("Message", pIntent.getStringExtra("Message"));
						} else {
							result.put("Message", "Defaut");
						}
						
						res = new PluginResult(PluginResult.Status.OK, result);
					}
					// On conserve le callback actif
					res.setKeepCallback(true);

					callbackContext.sendPluginResult(res);
				}catch(Exception e){
					Log.e("MeetupService1", e.getMessage(), e);
				}
			}
		}
	}
}

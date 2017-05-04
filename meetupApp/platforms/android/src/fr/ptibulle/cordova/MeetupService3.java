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

import fr.ptibulle.meetupservice3.MeetupServiceInterface;
import fr.ptibulle.meetupservice3.MeetupServiceCallbackInterface;

/**
 * 
 */
public class MeetupService3 extends CordovaPlugin {

	private MeetupServiceCallbackInterface mCallback;
	private MeetupServiceInterface mService;
 
 private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
			LOG.d("MeetupService3", "onService Connected : MeetupService3 Interface");
            mService = MeetupServiceInterface.Stub.asInterface(service);			
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
			LOG.d("MeetupService3", "onService Disconnected : MeetupService3 Interface");
            mService = null;
        }
    };

	@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public void onDestroy() {
        LOG.d("MeetupService3", "onDestroy");
        // Déconnexion du service
        if(null != mService) {
            LOG.d("MeetupService3", "unbindService : MeetupService3 Interface");
            this.cordova.getActivity().unbindService(conn);
        }

        super.onDestroy();
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {

		if (action.equalsIgnoreCase("BINDSERVICE")) {
			LOG.d("MeetupService3", "BINDSERVICE");
			if (mService != null) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
			} else {
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("fr.ptibulle.meetupservice3", "fr.ptibulle.meetupservice3.MeetupService"));
				intent.setAction("fr.ptibulle.meetupservice3.ACTION_BIND");
				this.cordova.getActivity().bindService(intent, conn, Service.BIND_AUTO_CREATE);
			}
			return true;
        } else if (action.equalsIgnoreCase("UNBINDSERVICE")) {
			LOG.d("MeetupService3", "UNBINDSERVICE");
			if(null != mService) {
		    	this.cordova.getActivity().unbindService(conn);
			}
            return true;
        } else if (action.equalsIgnoreCase("STARTTIMER")) {
			 if (mService == null){
				callbackContext.error("Service not bound");					
				return true;
        	}
			LOG.d("MeetupService3", "STARTTIMER");
			try {
				mService.startTimer();
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
			LOG.d("MeetupService3", "STOPTIMER");
			try {
				mService.stopTimer();
			} catch (RemoteException re) {
				callbackContext.error("RemoteException : " + re.getMessage());
			} finally {
				return true;
			}
        } else if (action.equalsIgnoreCase("REGISTER")) {
			 if (mService == null){
				callbackContext.error("Service not bound");					
				return true;
        	}
			LOG.d("MeetupService3", "REGISTER");
			if (mCallback != null) {
				// Deja enregistre
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
			} else {
				try {
					mCallback = new InnerCallBack(callbackContext);
					mService.register(mCallback);
				} catch (RemoteException re) {
					callbackContext.error("RemoteException : " + re.getMessage());
				} 
			}
			return true;
        } else if (action.equalsIgnoreCase("UNREGISTER")) {
			 if (mService == null){
				callbackContext.error("Service not bound");					
				return true;
        	}
			LOG.d("MeetupService3", "UNREGISTER");
			if (mCallback == null) {
				// Pas de callback enregistre
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
			} else {
				try {
					mService.unregister(mCallback);
				} catch (RemoteException re) {
					callbackContext.error("RemoteException : " + re.getMessage());
				} 
			}
			return true;
        }

        return false;
    }

    static class InnerCallBack extends MeetupServiceCallbackInterface.Stub{

    	private CallbackContext callbackContext;

    	public InnerCallBack(CallbackContext callbackContext){
    		super();
    		this.callbackContext = callbackContext;
    	}

    	@Override
    	public void notify(String message){
    		if (message != null){
    			try{
					JSONObject result = new JSONObject();
					result.put("Message", message);

    				PluginResult res = new PluginResult(PluginResult.Status.OK,result);
    				res.setKeepCallback(true);
    				callbackContext.sendPluginResult(res);
    			}catch(Exception e){
    				Log.e("MeetupService3", e.getMessage(), e);
    			}
    		}
    	}

    }
}

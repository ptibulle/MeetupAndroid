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

/**
 * 
 */
public class MeetupService1 extends CordovaPlugin {

	private CallbackContext mContext;
	private MeetupServiceReceiver meetupServiceReceiver;

 
	@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

	
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        PluginResult result = new PluginResult(PluginResult.Status.INVALID_ACTION);
		if (action.equalsIgnoreCase("STARTTIMER")) {
			LOG.d("MeetupService1", "STARTTIMER");

			meetupServiceReceiver = new MeetupServiceReceiver(callbackContext);
			this.cordova.getActivity().registerReceiver(meetupServiceReceiver, new IntentFilter("fr.ptibulle.meetupservice1.Broadcast"));
			
			try {
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("fr.ptibulle.meetupservice1", "fr.ptibulle.meetupservice1.MeetupService"));
                intent.setAction("fr.ptibulle.meetupservice1.START_TIMER");

				ComponentName componentName = this.cordova.getActivity().startService(intent);

				if (componentName == null) {
					this.cordova.getActivity().unregisterReceiver(meetupServiceReceiver);
					result = new PluginResult(PluginResult.Status.ERROR, "No Service MeetupService1");
				} else {
					result = null;
				}
			} catch (SecurityException e) {
				this.cordova.getActivity().unregisterReceiver(meetupServiceReceiver);
				result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
			}
		} else if (action.equalsIgnoreCase("STOPTIMER")) {
			LOG.d("MeetupService1", "STOPTIMER");

			if (meetupServiceReceiver != null) {
				this.cordova.getActivity().unregisterReceiver(meetupServiceReceiver);
				meetupServiceReceiver = null;
			}

			try {
 				Intent intent = new Intent();
				intent.setComponent(new ComponentName("fr.ptibulle.meetupservice1", "fr.ptibulle.meetupservice1.MeetupService"));
                intent.setAction("fr.ptibulle.meetupservice1.STOP_TIMER");

				ComponentName componentName = this.cordova.getActivity().startService(intent);

				if (componentName == null) {
					result = new PluginResult(PluginResult.Status.ERROR, "No Service MeetupService1");
				} else {
					result = new PluginResult(PluginResult.Status.OK);
				}
			} catch (SecurityException e) {
				result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
			}
		}

		if (result != null) {
			callbackContext.sendPluginResult(result);
		}
        return true;
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

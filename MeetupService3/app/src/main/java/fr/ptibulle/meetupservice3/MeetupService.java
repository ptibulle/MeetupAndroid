package fr.ptibulle.meetupservice3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.os.RemoteCallbackList;

import java.util.Timer;
import java.util.TimerTask;

public class MeetupService extends Service {

    private static final String TAG = "MeetupService3";

    public static final RemoteCallbackList<MeetupServiceCallbackInterface> m_callbacks = new RemoteCallbackList<>();


    Timer timer;
    MyTimerTask myTimerTask;

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.d(TAG, "broadcastResult will start");
            //  only one broadcast can be active at a time => Synchronize it !
            synchronized (MeetupService.m_callbacks) {
                final int callbacks = MeetupService.m_callbacks.beginBroadcast();
                try {
                    for (int i = 0; i < callbacks; i++) {
                        try {
                            MeetupService.m_callbacks.getBroadcastItem(i).notify("Callback Service 3");

                        } catch (RemoteException re) {
                            Log.e(TAG, re.getLocalizedMessage(), re);
                        }
                    }
                } finally {
                    MeetupService.m_callbacks.finishBroadcast();
                }
            }
        }
    }

    public MeetupService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MeetupServiceInterface.Stub() {
            @Override
            public void startTimer() throws RemoteException {
                MeetupService.this.startTimer();
            }
            @Override
            public void stopTimer() throws RemoteException {
                MeetupService.this.stopTimer();
            }
            @Override
            public void register(MeetupServiceCallbackInterface callback) throws RemoteException {
                Log.d(TAG, "register callback");
                if (callback != null){
                    synchronized (MeetupService.m_callbacks){
                        MeetupService.m_callbacks.register(callback);
                    }
                }
            }

            @Override
            public void unregister(MeetupServiceCallbackInterface callback) throws RemoteException {
                synchronized (MeetupService.m_callbacks){
                    MeetupService.m_callbacks.unregister(callback);
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        stopTimer();

        super.onDestroy();
    }

    public void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        // On lance la tache dans 2 secondes puis toutes les 5 secondes
        timer.schedule(myTimerTask, 2000, 5000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}

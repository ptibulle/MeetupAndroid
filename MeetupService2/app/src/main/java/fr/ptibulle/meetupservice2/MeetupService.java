package fr.ptibulle.meetupservice2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MeetupService extends Service {

    private static final String TAG = "MeetupService2";

    Timer timer;
    MyTimerTask myTimerTask;

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "Broadcast fr.ptibulle.meetupservice2.Broadcast");

            Intent intent = new Intent("fr.ptibulle.meetupservice2.Broadcast");
            intent.putExtra("Message","Broadcast Service 2");
            MeetupService.this.sendBroadcast(intent);
        }
    }

    public MeetupService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MeetupServiceInterface.Stub() {
            public void startTimer() throws RemoteException {
                MeetupService.this.startTimer();
            }
            public void stopTimer() throws RemoteException {
                MeetupService.this.stopTimer();
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

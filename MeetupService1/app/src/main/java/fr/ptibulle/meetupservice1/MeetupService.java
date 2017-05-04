package fr.ptibulle.meetupservice1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MeetupService extends Service {

    private static final String TAG = "MeetupService1";

    private static final String START_ACTION = "fr.ptibulle.meetupservice1.START_TIMER";

    private static final String STOP_ACTION = "fr.ptibulle.meetupservice1.STOP_TIMER";


    Timer timer;
    MyTimerTask myTimerTask;

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "Broadcast fr.ptibulle.meetupservice1.Broadcast");

            Intent intent = new Intent("fr.ptibulle.meetupservice1.Broadcast");
            intent.putExtra("Message","Broadcast Service 1");
            MeetupService.this.sendBroadcast(intent);
        }
    }

    public MeetupService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Received start id " + startId + ": " + intent);
        if (intent != null) {
            final String action = intent.getAction();
            if (START_ACTION.equals(action)) {
                startTimer();
            } else if (STOP_ACTION.equals(action)) {
                stopTimer();
            }
        }
        return START_NOT_STICKY;
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

package pnrs.rtrk.myapplication;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherService extends Service {
    private static final String LOG_TAG = "ExampleService";
    private static final long PERIOD = 3000L;
    private RunnableExample mRunnable;

    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String KEY = "&APPID=8a8b70915fd021fff2707ceaef3dceb1&units=metric";
    public String GET_INFO, city,temp;
    private HTTPHelper httpHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        httpHelper = new HTTPHelper();
        mRunnable = new RunnableExample();
        mRunnable.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mRunnable.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        city = intent.getStringExtra("town");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private class RunnableExample implements Runnable {
        private Handler mHandler;
        private boolean mRun = false;

        public RunnableExample() {
            mHandler = new Handler(getMainLooper());
        }

        public void start() {
            mRun = true;
            mHandler.postDelayed(this, PERIOD);
        }

        public void stop() {
            mRun = false;
            mHandler.removeCallbacks(this);
        }

        @Override
        public void run() {
            if (!mRun) {
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GET_INFO = BASE_URL + city + KEY;
                        Log.d("Link je: ", GET_INFO);
                        JSONObject jsonObject = httpHelper.getJSONObjectFromURL(GET_INFO);

                        JSONObject temperature = jsonObject.getJSONObject("main");
                        temp = temperature.get("temp").toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            NotificationCompat.Builder b = new NotificationCompat.Builder(WeatherService.this);
            b.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_umbrella)
                    .setTicker("Vremenska prognoza")
                    .setContentTitle("Temperatura je ažurirana")
                    .setContentText(temp + " °C")
                    .setContentInfo("INFO");

            NotificationManager nm = (NotificationManager) WeatherService.this.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(1, b.build());

            ContentResolver resolver = getContentResolver();

            //dodati kod

            Log.d(LOG_TAG, "Hello from Runnable " + city);
            mHandler.postDelayed(this, PERIOD);
        }
    }
}

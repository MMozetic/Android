package pnrs.rtrk.myapplication;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    private static final long PERIOD = 10000L;
    private RunnableExample mRunnable;

    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String KEY = "&APPID=8a8b70915fd021fff2707ceaef3dceb1&units=metric";
    public String GET_INFO, city, temp;
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

                        ContentResolver resolver = getContentResolver();

                        Cursor cursor = resolver.query(WeatherProvider.CONTENT_URI,null,"Name=?",new String[]{city},"Date ASC");
                        cursor.moveToLast();
                        ContentValues values = new ContentValues();

                        values.put(WeatherDbHelper.COLUMN_DATE, cursor.getString(cursor.getColumnIndex("Date")));
                        values.put(WeatherDbHelper.COLUMN_NAME, city);
                        values.put(WeatherDbHelper.COLUMN_TEMPERATURE, Double.parseDouble(temp));
                        values.put(WeatherDbHelper.COLUMN_PREASSURE, Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
                        values.put(WeatherDbHelper.COLUMN_HUMIDITY, Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
                        values.put(WeatherDbHelper.COLUMN_SUNRISE, cursor.getString(cursor.getColumnIndex("Sunrise")));
                        values.put(WeatherDbHelper.COLUMN_SUNSET, cursor.getString(cursor.getColumnIndex("Sunset")));
                        values.put(WeatherDbHelper.COLUMN_WIND_SPEED, Double.toString(cursor.getDouble(cursor.getColumnIndex("WindSpeed"))));
                        values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION, cursor.getString(cursor.getColumnIndex("WindDirection")));
                        values.put(WeatherDbHelper.COLUMN_IMAGE_URL, cursor.getString(cursor.getColumnIndex("ImageUrl")));
                        values.put(WeatherDbHelper.COLUMN_DAY, cursor.getString(cursor.getColumnIndex("Day")));

                        resolver.update(WeatherProvider.CONTENT_URI, values, "Name=? AND Date=?", new String[]{city, cursor.getString(cursor.getColumnIndex("Date"))});
                        cursor.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            for(long i = 0; i < 10000000; i++);

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

            Log.d(LOG_TAG, "Hello from Runnable " + city);
            mHandler.postDelayed(this, PERIOD);
        }
    }
}

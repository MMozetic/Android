package pnrs.rtrk.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weather.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_TEMPERATURE = "Temperature";
    public static final String COLUMN_PREASSURE = "Preassure";
    public static final String COLUMN_HUMIDITY = "Humidity";
    public static final String COLUMN_SUNRISE = "Sunrise";
    public static final String COLUMN_SUNSET = "Sunset";
    public static final String COLUMN_WIND_SPEED = "WindSpeed";
    public static final String COLUMN_WIND_DIRECTION = "WindDirection";

    public WeatherDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DATE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TEMPERATURE + " REAL, " +
                COLUMN_PREASSURE + " INTEGER, " +
                COLUMN_HUMIDITY + " INTEGER, " +
                COLUMN_SUNRISE + " TEXT, " +
                COLUMN_SUNSET + " TEXT, " +
                COLUMN_WIND_SPEED + " REAL, " +
                COLUMN_WIND_DIRECTION + " TEXT);"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

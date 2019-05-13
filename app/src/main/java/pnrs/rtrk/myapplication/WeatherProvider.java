package pnrs.rtrk.myapplication;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class WeatherProvider extends ContentProvider {

    public static final String AUTHORITY = "pnrs.rtrk.myapplication";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI,WeatherDbHelper.TABLE_NAME);

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/vnd.pnrs.rtrk.myapplication.weather";
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/vnd.pnrs.rtrk.myapplication.weather";

    private static final int WEATHER = 1;
    private static final int WEATHER_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY,WeatherDbHelper.TABLE_NAME,WEATHER);
        sUriMatcher.addURI(AUTHORITY,WeatherDbHelper.TABLE_NAME + "/#",WEATHER_ID);
    }

    private WeatherDbHelper mHelper = null;

    public WeatherProvider() {
    }

    private int delete(String selection, String[] selectionArgs) {
        int deleted = 0;

        SQLiteDatabase db = mHelper.getWritableDatabase();
        deleted = db.delete(WeatherDbHelper.TABLE_NAME,selection,selectionArgs);
        mHelper.close();

        return deleted;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted = 0;

        switch (sUriMatcher.match(uri)){
            case WEATHER:
                deleted = delete(selection,selectionArgs);
                break;
            case WEATHER_ID:
                deleted = delete(WeatherDbHelper.COLUMN_WIND_DIRECTION + "=?",new String[]{uri.getLastPathSegment()});
                break;
            default:
        }

        if(deleted>0){
            ContentResolver resolver = getContext().getContentResolver();
            resolver.notifyChange(uri,null);
        }

        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        String type = null;

        switch (sUriMatcher.match(uri)){
            case WEATHER:
                type = CONTENT_TYPE;
                break;
            case WEATHER_ID:
                type = CONTENT_ITEM_TYPE;
                break;
            default:
        }

        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        long id = db.insert(WeatherDbHelper.TABLE_NAME,null,values);
        mHelper.close();

        if(id != -1){
            ContentResolver resolver = getContext().getContentResolver();
            if(resolver == null){
                return null;
            }

            resolver.notifyChange(uri,null);
        }

        return Uri.withAppendedPath(uri,Long.toString(id));
    }

    @Override
    public boolean onCreate() {
        mHelper = new WeatherDbHelper(getContext());
        return true;
    }

    private Cursor query(String[] projection, String selection,
                         String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db.query(WeatherDbHelper.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        switch (sUriMatcher.match(uri)){
            case WEATHER:
                cursor = query(projection,selection,selectionArgs,sortOrder);
                break;
            case WEATHER_ID:
                cursor = query(projection, "_id=?",new String[]{uri.getLastPathSegment()},null);
                break;
            default:
        }

        return cursor;
    }

    private int update(ContentValues values, String selection,
                       String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int updated = db.update(WeatherDbHelper.TABLE_NAME,values,selection,selectionArgs);
        mHelper.close();

        return updated;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updated = 0;

        switch (sUriMatcher.match(uri)){
            case WEATHER:
                updated = update(values,selection,selectionArgs);
                break;
            case WEATHER_ID:
                updated = update(values,"_id=?",new String[]{uri.getLastPathSegment()});
                break;
            default:
        }

        if(updated>0){
            ContentResolver resolver = getContext().getContentResolver();
            resolver.notifyChange(uri,null);
        }

        return updated;
    }
}


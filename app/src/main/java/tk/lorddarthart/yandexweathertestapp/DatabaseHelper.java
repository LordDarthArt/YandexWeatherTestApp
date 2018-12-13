package tk.lorddarthart.yandexweathertestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DATABASE_NAME = "tk.lorddarthart.yandexweathertestapp.db";
    public static int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE_CITIES = "cities";
    public static final String CITY_ID = "id";
    public static final String CITY_NAME = "cityname";
    public static final String CITY_COUNTRY = "country";
    public static final String CITY_LONGITUDE = "longitude";
    public static final String CITY_LATITUDE = "latitude";

    public static final String DATABASE_CREATE_WEATHER_SCRIPT = "create table "
            + DATABASE_TABLE_CITIES
            + " (" + CITY_ID + " integer not null primary key autoincrement, "
            + CITY_NAME + " text not null, "
            + CITY_COUNTRY + " text not null, "
            + CITY_LONGITUDE + " text not null, "
            + CITY_LATITUDE + " text not null, "
            + "UNIQUE(" + CITY_NAME + ") ON CONFLICT REPLACE);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_WEATHER_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void addCity(SQLiteDatabase mSqLiteDatabase, String cityName, String cityCountry, String cityLatitude, String cityLongitude) {

        ContentValues newValues = new ContentValues();
        newValues.put(DatabaseHelper.CITY_NAME, cityName);
        newValues.put(DatabaseHelper.CITY_COUNTRY, cityCountry);
        newValues.put(DatabaseHelper.CITY_LONGITUDE, cityLongitude);
        newValues.put(DatabaseHelper.CITY_LATITUDE, cityLatitude);

        mSqLiteDatabase.insertWithOnConflict(DatabaseHelper.DATABASE_TABLE_CITIES, null, newValues, SQLiteDatabase.CONFLICT_REPLACE);
    }
}

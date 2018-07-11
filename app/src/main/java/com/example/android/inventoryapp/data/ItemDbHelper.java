package com.example.android.inventoryapp.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventoryapp.data.ItemContract.SneakerEntry;

/**
 * Created by User on 6/18/2018.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    private static final String DATABASE_NAME = "store.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_SNEAKERS_TABLE = "CREATE TABLE " + SneakerEntry.TABLE_NAME + "("
                + SneakerEntry.COLUMN_SNEAKER_NAME + " TEXT NOT NULL, "
                + SneakerEntry.COLUMN_SNEAKER_PRICE + " INTEGER, "
                + SneakerEntry.COLUMN_SNEAKER_QUANTITY +  " INTEGER, "
                + SneakerEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + SneakerEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SneakerEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_SNEAKERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

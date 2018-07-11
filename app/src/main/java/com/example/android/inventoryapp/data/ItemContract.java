package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 6/18/2018.
 */

public class ItemContract {
    private ItemContract() {};
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SNEAKERS= "sneakers";

    public static final class SneakerEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SNEAKERS);


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SNEAKERS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SNEAKERS;

        /** Name of database table for sneakers */
        public final static String TABLE_NAME = "sneakers";

        /**
         * Name of the sneaker
         *
         * Type: TEXT
         */
        public final static String COLUMN_SNEAKER_NAME = "name";

        /**
         * Price of the sneaker
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SNEAKER_PRICE = "price";

        /**
         * Quantity of one kind of sneaker
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SNEAKER_QUANTITY = "quantity";

        /**
         * Name of the supplier
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";

        /**
         * Phone number of the supplier
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";


        /**
         * Phone number of the supplier
         *
         * Type: TEXT
         */
        public final static String COLUMN_ID = "_id";

    }
}

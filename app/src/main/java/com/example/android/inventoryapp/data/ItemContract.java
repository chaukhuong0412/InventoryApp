package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by User on 6/18/2018.
 */

public class ItemContract {
    private ItemContract() {};

    public static final class SneakerEntry implements BaseColumns {

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

    }
}

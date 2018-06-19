package com.example.android.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.inventoryapp.data.ItemContract.SneakerEntry;
import com.example.android.inventoryapp.data.ItemDbHelper;

public class MainActivity extends AppCompatActivity {

    private ItemDbHelper itemDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addItemButton = findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
            }
        });
        itemDbHelper = new ItemDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = itemDbHelper.getReadableDatabase();
        String[] projection = {SneakerEntry.COLUMN_SNEAKER_NAME, SneakerEntry.COLUMN_SNEAKER_PRICE, SneakerEntry.COLUMN_SNEAKER_QUANTITY, SneakerEntry.COLUMN_SUPPLIER_NAME};
        Cursor cursor = db.query(SneakerEntry.TABLE_NAME, projection, null, null, null, null, null);
        TextView displayView = findViewById(R.id.text_view_item);
        try {
            displayView.append(SneakerEntry.COLUMN_SNEAKER_NAME + " - " +
                    SneakerEntry.COLUMN_SNEAKER_PRICE + " - " +
                    SneakerEntry.COLUMN_SNEAKER_QUANTITY + " - " +
                    SneakerEntry.COLUMN_SUPPLIER_NAME);

            int nameSneakerColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_NAME);
            int priceColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_QUANTITY);
            int nameSupplierColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SUPPLIER_NAME);

            while (cursor.moveToNext()) {
                String currentSneakerName = cursor.getString(nameSneakerColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(nameSupplierColumnIndex);
                displayView.append(("\n" + currentSneakerName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName));
            }
        } finally {
            cursor.close();
        }

    }
}

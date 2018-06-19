package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ItemDbHelper;
import com.example.android.inventoryapp.data.ItemContract.SneakerEntry;
import com.example.android.inventoryapp.MainActivity;

/**
 * Created by User on 6/18/2018.
 */

public class ItemActivity extends  AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneNumberEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        mNameEditText = findViewById(R.id.edit_item_name);
        mPriceEditText = findViewById(R.id.edit_item_price);
        mQuantityEditText = findViewById(R.id.edit_item_quantity);
        mSupplierNameEditText = findViewById(R.id.edit_item_supplier_name);
        mSupplierPhoneNumberEditText = findViewById(R.id.edit_item_supplier_phone_number);
        addButton = findViewById(R.id.add_item_confirm_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem();
                finish();
            }
        });

    }

    private void insertItem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String supplierPhoneNumberString = mSupplierPhoneNumberEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);
        int quantity = Integer.parseInt(quantityString);

        // Create database helper
        ItemDbHelper mDbHelper = new ItemDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SneakerEntry.COLUMN_SNEAKER_NAME, nameString);
        values.put(SneakerEntry.COLUMN_SNEAKER_PRICE, price);
        values.put(SneakerEntry.COLUMN_SNEAKER_QUANTITY, quantity);
        values.put(SneakerEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(SneakerEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumberString);
        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(SneakerEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        }
    }
}

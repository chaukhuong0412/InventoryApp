package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

import static com.example.android.inventoryapp.data.ItemContract.SneakerEntry.CONTENT_URI;

/**
 * Created by User on 6/18/2018.
 */

public class ItemActivity extends  AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_ITEM_LOADER = 0;
    private Uri mCurrentItemUri;

    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneNumberEditText;
    private Button addButton;
    private Button saveButton;
    private Button deleteButton;
    private Button increaseButton;
    private Button decreaseButton;
    private Button orderButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        mCurrentItemUri = intent.getData();

        if (mCurrentItemUri == null)
            setTitle("Add item");
        else
            setTitle("Edit item");


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

        saveButton = findViewById(R.id.save_item_confirm_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
                finish();
            }
        });

        deleteButton = findViewById(R.id.delete_item_confirm_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
                finish();
            }
        });

        orderButton = findViewById(R.id.order_item_confirm_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phoneNumber = "" + mSupplierPhoneNumberEditText.getText();
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        increaseButton = findViewById(R.id.increase_button);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantityString = mQuantityEditText.getText().toString().trim();
                int newQuantity = Integer.parseInt(quantityString) + 1;
                String newQuantityString = "" + newQuantity;
                mQuantityEditText.setText(newQuantityString);
            }
        });

        decreaseButton = findViewById(R.id.decrease_button);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = 0;
                String quantityString = mQuantityEditText.getText().toString().trim();
                try {
                    quantity = Integer.parseInt(quantityString);
                } catch (NumberFormatException e) {

                }
                if (quantity == 0)
                    return;
                else {
                    int newQuantity = quantity - 1;
                    String newQuantityString = "" + newQuantity;
                    mQuantityEditText.setText(newQuantityString);
                }
            }
        });

        getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
    }

    private void insertItem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String supplierPhoneNumberString = mSupplierPhoneNumberEditText.getText().toString().trim();
        int price = 0;
        try {
            price = Integer.parseInt(priceString);
        } catch (NumberFormatException e) {

        }

        int quantity = 1;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {

        }

        ContentValues values = new ContentValues();
        values.put(SneakerEntry.COLUMN_SNEAKER_NAME, nameString);
        values.put(SneakerEntry.COLUMN_SNEAKER_PRICE, price);
        values.put(SneakerEntry.COLUMN_SNEAKER_QUANTITY, quantity);
        values.put(SneakerEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(SneakerEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumberString);

        Uri newUri = getContentResolver().insert(CONTENT_URI, values);


        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mCurrentItemUri == null) {
            return null;
        }

        String[] projection = {
                SneakerEntry._ID,
                SneakerEntry.COLUMN_SNEAKER_NAME,
                SneakerEntry.COLUMN_SNEAKER_PRICE,
                SneakerEntry.COLUMN_SNEAKER_QUANTITY,
                SneakerEntry.COLUMN_SUPPLIER_NAME,
                SneakerEntry.COLUMN_SUPPLIER_PHONE_NUMBER};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                mCurrentItemUri,
                projection,
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_NAME);
            int priceColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneNumberColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            String itemPriceString = "" + price;
            int quantity = cursor.getInt(quantityColumnIndex);
            String quantityString = "" + quantity;
            String supplierName = cursor.getString(supplierNameColumnIndex);
            String supplierPhoneNumber = cursor.getString(supplierPhoneNumberColumnIndex);

            mNameEditText.setText(name);
            mPriceEditText.setText(itemPriceString);
            mQuantityEditText.setText(quantityString);
            mSupplierNameEditText.setText(supplierName);
            mSupplierPhoneNumberEditText.setText(supplierPhoneNumber);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mPriceEditText.setText("");
        mQuantityEditText.setText("");
        mSupplierNameEditText.setText("");
        mSupplierPhoneNumberEditText.setText("");
    }

    private void saveItem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String supplierPhoneNumberString = mSupplierPhoneNumberEditText.getText().toString().trim();
        int price = 0;
        try {
            price = Integer.parseInt(priceString);
        } catch (NumberFormatException e) {

        }
        int quantity = 1;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {

        }

        ContentValues values = new ContentValues();
        values.put(SneakerEntry.COLUMN_SNEAKER_NAME, nameString);
        values.put(SneakerEntry.COLUMN_SNEAKER_PRICE, price);
        values.put(SneakerEntry.COLUMN_SNEAKER_QUANTITY, quantity);
        values.put(SneakerEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(SneakerEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumberString);

        if (mCurrentItemUri == null) {
            Uri newUri = getContentResolver().insert(SneakerEntry.CONTENT_URI, values);

            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "Error",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Success",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(mCurrentItemUri, values, null, null);

            if (rowsAffected == 0) {
                Toast.makeText(this, "Error",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Success",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteItem() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentItemUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentItemUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, "Error",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, "Successs",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }
}



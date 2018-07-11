package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ItemContract.SneakerEntry;


public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return view;
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);


        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_NAME);
        int pricedColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(SneakerEntry.COLUMN_SNEAKER_QUANTITY);

        // Read the pet attributes from the Cursor for the current pet
        String itemName = cursor.getString(nameColumnIndex);
        int itemPrice = cursor.getInt(pricedColumnIndex);
        String itemPriceString = "Price:" + itemPrice + "$";
        final int itemQuantity = cursor.getInt(quantityColumnIndex);
        String itemQuantityString = "Quantity:" + itemQuantity;

        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(itemName);
        priceTextView.setText(itemPriceString);
        quantityTextView.setText(itemQuantityString);
        Button yourButton = view.findViewById(R.id.sale_button);
        yourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemQuantity == 0)
                    return;
                else {
                    int newQuantity = itemQuantity - 1;
                    ContentValues values = new ContentValues();
                    values.put(SneakerEntry.COLUMN_SNEAKER_QUANTITY, newQuantity);
                }
            }
        });
    }
}
package com.example.malihakhizer.foodorderingapp.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.malihakhizer.foodorderingapp.Models.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderDbHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "FoodOrderingDB.db";
    private static final int DB_VER = 1;


    public OrderDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public ArrayList<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductID", "ProductName", "Quantity", "Price", "Discount", "PictureUrl", "Description"};
        String sqlTable = "OrderDetails";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final ArrayList<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new Order(
                        c.getString(c.getColumnIndex("ProductID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("PictureUrl")),
                        c.getString(c.getColumnIndex("Description"))
                ));
            } while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetails(ProductID,ProductName,Quantity,Price,Discount,PictureUrl,Description) VALUES ('%s','%s','%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount(),
                order.getPictureUrl(),
                order.getDescription());

        db.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetails;");
        db.execSQL(query);
    }

}

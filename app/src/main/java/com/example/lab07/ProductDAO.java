package com.example.lab07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    DbHelper dbHelper;
    public ProductDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public List<Product> GetAll()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Product> listProduct = new ArrayList<>();
        String query = "SELECT * FROM product";
        Cursor c =  db.rawQuery(query, null);
        while (c.moveToNext())
        {
            Product temp = new Product();
            temp.setId(c.getInt(0));
            temp.setName(c.getString(1));
            temp.setImage(c.getString(2));
            temp.setPrice(c.getFloat(3));
            listProduct.add(temp);
        }
        return listProduct;
    }
    public void Insert(Product p) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", p.getName());
        values.put("image", p.getImage());
        values.put("price", p.getPrice());
        db.insert("product", null, values);

    }
    public void Delete(int productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("product", "id=?", new String[] { String.valueOf(productId) });

    }
}

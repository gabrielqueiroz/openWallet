package com.gqueiroz.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String dbName = "openWallet";
    private static final int dbVersion = 1;

    private static final String tableName = "items";

    private static final String itemId = "itemId";
    private static final String itemName = "itemName";
    private static final String itemValue = "itemValue";
    private static final String itemImage = "itemImage";


    private final Item item1 = new Item(1, "Carteira", 99.90, "Carteira");
    private final Item item2 = new Item(2, "Banco", 99.90, "Banco");
    private final Item item3 = new Item(3, "Cartao de Credito", 99.90, "Cartao de Credito");
    private final Item[] defaultItems = new Item[]{item1, item2, item3};

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sampleDB) {
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(itemId INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, itemValue REAL, itemImage TEXT);");

        for (Item item : defaultItems)
            sampleDB.execSQL("INSERT INTO " + tableName + " Values ('" + String.valueOf(item.getId()) + "', " +
                    "'" + item.getName() + "', '" + String.valueOf(item.getValue()) + "', '" + item.getImage() + "');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sampleDB, int oldVersion, int newVersion) {
        sampleDB.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(sampleDB);
    }

    public Item getItemByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(tableName, new String[]{itemId, itemName, itemValue, itemImage}, itemName + "=?", new String[]{name}, null, null, null, null);

        if (c != null)
            c.moveToFirst();

        Item itemQuery = new Item(Integer.parseInt(c.getString(0)), c.getString(1), Double.parseDouble(c.getString(2)), c.getString(3));

        return itemQuery;
    }


    public List<Item> getAllItems() {
        List<Item> results = new ArrayList<>();
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        Cursor c = sampleDB.rawQuery("SELECT itemName, itemValue FROM " + tableName, null);

        if (c != null && c.moveToFirst()) {
            do {
                Item item = new Item();
                item.setName(c.getString(c.getColumnIndex("itemName")));
                item.setValue(c.getDouble(c.getColumnIndex("itemValue")));
                results.add(item);
            } while (c.moveToNext());
                c.close();
        }

        return results;
    }
}

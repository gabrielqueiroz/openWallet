package com.gqueiroz.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String dbName = "openWallet";
    private static final int dbVersion = 2;

    private static final String tableItem = "itens";

    private static final String itemId = "itemId";
    private static final String itemNome = "itemName";
    private static final String itemValue = "itemValue";
    private static final String itemImage = "itemImage";

    private static final String tableReferencias = "referencias";

    private static final String referenciasId = "referenciasId";
    private static final String referenciasNome = "referenciasName";
    private static final String referenciasItem = "referenciaItem";

    private static final String tableHistorico = "historico";

    private static final String historicoId = "historicoId";
    private static final String historicoTipo = "historicoTipo";
    private static final String historicoReferencia = "historicoReferencia";
    private static final String historicoItem = "historicoItem";

    private final Item item1 = new Item(1, "Carteira", 99.90, "Carteira");
    private final Item item2 = new Item(2, "Banco", 99.90, "Banco");
    private final Item item3 = new Item(3, "Cartao de Credito", 99.90, "Cartao de Credito");
    private final Item[] defaultItems = new Item[]{item1, item2, item3};

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sampleDB) {
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableItem + "(itemId INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, itemValue REAL, itemImage TEXT);");

        for (Item item : defaultItems)
            sampleDB.execSQL("INSERT INTO " + tableItem + " Values ('" + String.valueOf(item.getId()) + "', " +
                    "'" + item.getName() + "', '" + String.valueOf(item.getValue()) + "', '" + item.getImage() + "');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sampleDB, int oldVersion, int newVersion) {
        sampleDB.execSQL("DROP TABLE IF EXISTS " + tableItem);
        onCreate(sampleDB);
    }

    public Item getItemByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(tableItem, new String[]{itemId, itemNome, itemValue, itemImage}, itemNome + "=?", new String[]{name}, null, null, null, null);

        if (c != null)
            c.moveToFirst();

        Item itemQuery = new Item(Integer.parseInt(c.getString(0)), c.getString(1), Double.parseDouble(c.getString(2)), c.getString(3));

        return itemQuery;
    }


    public List<Item> getAllItems() {
        List<Item> results = new ArrayList<>();
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        Cursor c = sampleDB.rawQuery("SELECT itemName, itemValue FROM " + tableItem, null);

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

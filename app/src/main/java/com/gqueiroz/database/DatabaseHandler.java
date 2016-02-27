package com.gqueiroz.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gqueiroz.openwallet.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String dbName = "openWallet";
    private static final int dbVersion = 3;

    private static final String tableItem = "itens";

    private static final String itemId = "itemId";
    private static final String itemNome = "itemName";
    private static final String itemValue = "itemValue";
    private static final String itemImage = "itemImage";
    private static final String itemCor = "itemCor";

    private static final String tableReferencias = "referencias";

    private static final String referenciasId = "referenciasId";
    private static final String referenciasNome = "referenciasName";
    private static final String referenciasItem = "referenciaItem";

    private static final String tableHistorico = "historico";

    private static final String historicoId = "historicoId";
    private static final String historicoTipo = "historicoTipo";
    private static final String historicoReferencia = "historicoReferencia";
    private static final String historicoItem = "historicoItem";

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sampleDB) {
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableItem + "(itemId INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, itemValue REAL, itemImage TEXT, itemCor TEXT);");

        String icone1 = String.valueOf(R.drawable.ic_account_balance_white_48dp);
        String icone2 = String.valueOf(R.drawable.ic_shopping_cart_white_48dp);
        String icone3 = String.valueOf(R.drawable.ic_card_travel_white_48dp);

        String color1 = String.valueOf(R.color.colorPrimary);
        String color2 = String.valueOf(R.color.colorPrimaryDark1);
        String color3 = String.valueOf(R.color.colorPrimaryDark2);

        Item item1 = new Item(1, "Banco", 99.90, icone1, color1);
        Item item2 = new Item(2, "Supermercado", 99.90, icone2, color2);
        Item item3 = new Item(3, "Cartao de Crédito", 99.90, icone3, color3);
        Item[] defaultItems = new Item[]{item1, item2, item3};

        for (Item item : defaultItems)
            sampleDB.execSQL("INSERT INTO " + tableItem + " Values ('" + String.valueOf(item.getId()) + "', " +
                    "'" + item.getName() + "', '" + String.valueOf(item.getValue()) + "', '" + item.getImage() + "', '" + String.valueOf(item.getColor()) + "');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sampleDB, int oldVersion, int newVersion) {
        sampleDB.execSQL("DROP TABLE IF EXISTS " + tableItem);
        onCreate(sampleDB);
    }

    public void insertIntoItem(Item item) {
        SQLiteDatabase sampleDB = this.getReadableDatabase();
        sampleDB.execSQL("INSERT INTO " + tableItem + " Values ('" + String.valueOf(item.getId()) +
                "', " + item.getName() + "', '" + String.valueOf(item.getValue()) +
                "', '" + item.getImage() + "', '" + String.valueOf(item.getColor()) + "');");
    }

    public Item getItemByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableItem, new String[]{itemId, itemNome, itemValue, itemImage, itemCor}, itemNome + "=?", new String[]{name}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item itemQuery = new Item(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3), cursor.getString(4));

        return itemQuery;
    }

    public List<Item> getAllItems() {
        List<Item> results = new ArrayList<>();
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        Cursor cursor = sampleDB.rawQuery("SELECT itemName, itemValue, itemImage, itemCor FROM " + tableItem, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setName(cursor.getString(cursor.getColumnIndex("itemName")));
                item.setValue(cursor.getDouble(cursor.getColumnIndex("itemValue")));
                item.setImage(cursor.getString(cursor.getColumnIndex("itemImage")));
                item.setColor(cursor.getString(cursor.getColumnIndex("itemCor")));
                results.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return results;
    }
}

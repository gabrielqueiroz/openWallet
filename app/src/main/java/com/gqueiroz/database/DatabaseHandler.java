package com.gqueiroz.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gqueiroz.openwallet.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String dbName = "openWallet";
    private static final int dbVersion = 6;

    private static final String tableItem = "items";

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
    private static final String historicoValor = "historicoValor";
    private static final String historicoSaldo = "historicoSaldo";
    private static final String historicoData = "historicoData";

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sampleDB) {
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableItem + "(itemId INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, itemValue REAL, itemImage TEXT, itemCor TEXT);");

        String icone1 = String.valueOf(R.drawable.ic_account_balance_white_48dp);
        String icone2 = String.valueOf(R.drawable.ic_shopping_cart_white_48dp);
        String icone3 = String.valueOf(R.drawable.ic_credit_card_white_48dp);

        String color1 = String.valueOf(R.color.azul);
        String color2 = String.valueOf(R.color.vermelho);
        String color3 = String.valueOf(R.color.verde);

        Item item1 = new Item("Banco", 99.90, icone1, color1);
        Item item2 = new Item("Supermercado", 99.90, icone2, color2);
        Item item3 = new Item("Cartao de Crédito", 99.90, icone3, color3);
        Item[] defaultItems = new Item[]{item1, item2, item3};

        for (Item item : defaultItems)
            sampleDB.execSQL("INSERT INTO " + tableItem + " (itemName, itemValue, itemImage, itemCor) VALUES ('" + item.getName() + "', '" + String.valueOf(item.getValue()) + "', '" + item.getImage() + "', '" + String.valueOf(item.getColor()) + "');");

        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableHistorico + "(referenciasId INTEGER PRIMARY KEY AUTOINCREMENT, historicoTipo TEXT, historicoReferencia TEXT, historicoItem TEXT, historicoValor REAL, historicoSaldo REAL, historicoSaldo DATETIME);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sampleDB, int oldVersion, int newVersion) {
        sampleDB.execSQL("DROP TABLE IF EXISTS " + tableItem);
        onCreate(sampleDB);
    }

    public void insertIntoItem(Item item) {
        SQLiteDatabase sampleDB = this.getReadableDatabase();
        sampleDB.execSQL("INSERT INTO " + tableItem + " (itemName, itemValue, itemImage, itemCor) VALUES ('" + item.getName() + "" +
                "', '" + String.valueOf(item.getValue()) + "', '" + item.getImage() + "', '" +
                String.valueOf(item.getColor()) + "');");
    }

    public void deleteFromItem(int itemId) {
        SQLiteDatabase sampleDB = this.getReadableDatabase();
        sampleDB.execSQL("DELETE FROM " + tableItem + " WHERE itemId = " + String.valueOf(itemId));
    }

    public void updateItemById(int itemId, double newValue) {
        SQLiteDatabase sampleDB = this.getReadableDatabase();
        String value = new DecimalFormat("##.##").format(newValue);
        sampleDB.execSQL("UPDATE " + tableItem + " SET itemValue = " + value + " WHERE itemId = " + String.valueOf(itemId));
    }

    public Item getItemById(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableItem, new String[]{this.itemId, itemNome, itemValue, itemImage, itemCor}, itemId + "=?", new String[]{}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item itemQuery = new Item(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3), cursor.getString(4));

        return itemQuery;
    }

    public List<Item> getAllItems() {
        List<Item> results = new ArrayList<>();
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        Cursor cursor = sampleDB.rawQuery("SELECT itemId, itemName, itemValue, itemImage, itemCor FROM " + tableItem, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex("itemId")));
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

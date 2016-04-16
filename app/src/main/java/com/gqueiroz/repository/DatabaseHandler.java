package com.gqueiroz.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gqueiroz.openwallet.R;

import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String dbName = "openWallet";
    private static final int dbVersion = 9;

    private static final String tableItem = "items";

    private static final String itemId = "itemId";
    private static final String itemName = "itemName";
    private static final String itemValue = "itemValue";
    private static final String itemImage = "itemImage";
    private static final String itemCor = "itemCor";
    private static final String itemCorDark = "itemCorDark";

    private static final String tableReferencias = "referencias";

    private static final String referenciasId = "referenciasId";
    private static final String referenciasNome = "referenciasName";
    private static final String referenciasItem = "referenciaItem";

    private static final String tableHistorico = "historico";

    private static final String historicoId = "historicoId";
    private static final String historicoTipo = "historicoTipo";
    private static final String historicoItem = "historicoItem";
    private static final String historicoReferencia = "historicoReferencia";
    private static final String historicoValor = "historicoValor";
    private static final String historicoSaldo = "historicoSaldo";
    private static final String historicoData = "historicoData";

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sampleDB) {
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableItem + "(itemId INTEGER PRIMARY KEY AUTOINCREMENT, itemName TEXT, itemValue REAL, itemImage TEXT, itemCor TEXT, itemCorDark TEXT);");

        String icone1 = String.valueOf(R.drawable.ic_account_balance_white_48dp);
        String icone2 = String.valueOf(R.drawable.ic_shopping_cart_white_48dp);
        String icone3 = String.valueOf(R.drawable.ic_credit_card_white_48dp);

        String colorAzul = String.valueOf(R.color.azul);
        String colorAzulDark = String.valueOf(R.color.azulDark);
        String colorVermelho = String.valueOf(R.color.vermelho);
        String colorVermelhoDark = String.valueOf(R.color.vermelhoDark);
        String colorVerde = String.valueOf(R.color.verde);
        String colorVerdeDark = String.valueOf(R.color.verdeDark);

        Item item1 = new Item("Banco", 100.00, icone1, colorAzul, colorAzulDark);
        Item item2 = new Item("Supermercado", 100.00, icone2, colorVermelho, colorVermelhoDark);
        Item item3 = new Item("Cartão de Crédito", 100.00, icone3, colorVerde, colorVerdeDark);
        Item[] defaultItems = new Item[]{item1, item2, item3};

        for (Item item : defaultItems)
            sampleDB.execSQL("INSERT INTO " + tableItem + " (itemName, itemValue, itemImage, itemCor, itemCorDark) VALUES ('" + item.getName() + "', '" + String.valueOf(item.getValue()) + "', '" + item.getImage() + "', '" + String.valueOf(item.getColor()) + "', '" + String.valueOf(item.getColorDark()) + "');");

        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableHistorico + "(historicoId INTEGER PRIMARY KEY AUTOINCREMENT, historicoItem INTEGER, historicoTipo TEXT, historicoReferencia TEXT, historicoValor REAL, historicoSaldo REAL, historicoData DATETIME, FOREIGN KEY (historicoItem) REFERENCES items (itemId));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sampleDB, int oldVersion, int newVersion) {
        sampleDB.execSQL("DROP TABLE IF EXISTS " + tableItem);
        onCreate(sampleDB);
    }

    public void insertIntoItem(Item item) {
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(itemName, item.getName());
        contentValues.put(itemValue, item.getValue());
        contentValues.put(itemImage, item.getImage());
        contentValues.put(itemCor, item.getColor());
        contentValues.put(itemCorDark, item.getColorDark());
        sampleDB.insertOrThrow(tableItem, null, contentValues);
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

    public void insertIntoHistoric(boolean credito, double balance, double value, int item, String referencia) {
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        String hBalance = new DecimalFormat("##.##").format(balance);
        String hValue = new DecimalFormat("##.##").format(value);
        String tipo;

        if (credito)
            tipo = "C";
        else
            tipo = "D";

        ContentValues contentValues = new ContentValues();
        contentValues.put(historicoItem, item);
        contentValues.put(historicoTipo, tipo);
        contentValues.put(historicoReferencia, referencia);
        contentValues.put(historicoValor, hValue);
        contentValues.put(historicoSaldo, hBalance);
        contentValues.put(historicoData, date);

        sampleDB.insertOrThrow(tableHistorico, null, contentValues);
    }

    public Item getItemById(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tableItem, new String[]{DatabaseHandler.itemId, itemName, itemValue, itemImage, itemCor, itemCorDark}, itemId + "=?", new String[]{}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item itemQuery = new Item(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return itemQuery;
    }

    public List<Item> getAllItems() {
        List<Item> results = new ArrayList<>();
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        Cursor cursor = sampleDB.rawQuery("SELECT * FROM " + tableItem, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(itemId)));
                item.setName(cursor.getString(cursor.getColumnIndex(itemName)));
                item.setValue(cursor.getDouble(cursor.getColumnIndex(itemValue)));
                item.setImage(cursor.getString(cursor.getColumnIndex(itemImage)));
                item.setColor(cursor.getString(cursor.getColumnIndex(itemCor)));
                item.setColorDark(cursor.getString(cursor.getColumnIndex(itemCorDark)));
                results.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return results;
    }

    public List<History> getAllHistory(int itemId){
        List<History> results = new ArrayList<>();
        SQLiteDatabase sampleDB = this.getReadableDatabase();

        Cursor cursor = sampleDB.rawQuery("SELECT * FROM "+tableHistorico+" WHERE historicoItem = "+itemId+" ORDER BY historicoId DESC",null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setId(cursor.getInt(cursor.getColumnIndex(historicoId)));
                history.setBalance(cursor.getDouble(cursor.getColumnIndex(historicoSaldo)));
                history.setReferencia(cursor.getString(cursor.getColumnIndex(historicoReferencia)));
                history.setData(new java.sql.Date(cursor.getLong(cursor.getColumnIndex(historicoData))));
                history.setTipo(cursor.getString(cursor.getColumnIndex(historicoTipo)));
                history.setValor(cursor.getDouble(cursor.getColumnIndex(historicoValor)));
                results.add(history);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return results;
    }
}

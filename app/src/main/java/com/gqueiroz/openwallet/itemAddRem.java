package com.gqueiroz.openwallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gqueiroz.database.DatabaseHandler;
import com.gqueiroz.database.Item;

import java.util.Arrays;

public class ItemAddRem extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText itemValor;
    private Spinner spinner;

    private FloatingActionButton itemAddRem;
    private TextView adicionaReferencia;
    private TextView removerReferencia;

    private TextView add1;
    private TextView add5;
    private TextView add10;
    private TextView add20;

    private TextView rem1;
    private TextView rem5;
    private TextView rem10;
    private TextView rem20;

    private boolean credito = false;
    private Item item = new Item();
    private String[] referencias = new String[]{"Supermercado", "Farmacia", "Shopping", "Restaurante", "Outros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add_rem);

        findItemsByID();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String extra = extras.getString("extra");
            item = new Gson().fromJson(extra, item.getClass());
            credito = extras.getBoolean("credito");
        }

        setSupportActionBar(toolbar);
        if(credito)
            getSupportActionBar().setTitle("Credito em "+item.getName());
        else
            getSupportActionBar().setTitle("Debito em "+item.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Arrays.sort(referencias);

        ArrayAdapter<String> referenciasArray = new ArrayAdapter<>(ItemAddRem.this, android.R.layout.simple_spinner_item, referencias);
        referenciasArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(referenciasArray);

        adicionaReferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaReferencia();
            }
        });

        itemAddRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validaValorItem(itemValor))
                    return;

                itemAddRem(item.getId(), item.getValue(), credito);
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        changeColor(credito);
    }

    public void findItemsByID() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.referencias);
        itemValor = (EditText) findViewById(R.id.itemValor);

        adicionaReferencia = (TextView) findViewById(R.id.adicionaReferencia);
        removerReferencia = (TextView) findViewById(R.id.removeReferencia);
        itemAddRem = (FloatingActionButton) findViewById(R.id.itemAddRem);

        add1 = (TextView) findViewById(R.id.add1);
        add5 = (TextView) findViewById(R.id.add5);
        add10 = (TextView) findViewById(R.id.add10);
        add20 = (TextView) findViewById(R.id.add20);

        add1.setOnClickListener(this);
        add5.setOnClickListener(this);
        add10.setOnClickListener(this);
        add20.setOnClickListener(this);

        rem1 = (TextView) findViewById(R.id.rem1);
        rem5 = (TextView) findViewById(R.id.rem5);
        rem10 = (TextView) findViewById(R.id.rem10);
        rem20 = (TextView) findViewById(R.id.rem20);

        rem1.setOnClickListener(this);
        rem5.setOnClickListener(this);
        rem10.setOnClickListener(this);
        rem20.setOnClickListener(this);
    }

    public void onClick(View v) {
        String auxText = itemValor.getText().toString();
        double aux = Double.parseDouble(auxText);

        switch (v.getId()) {
            case R.id.add1:
                aux = aux + 1;
                break;
            case R.id.add5:
                aux = aux + 5;
                break;
            case R.id.add10:
                aux = aux + 10;
                break;
            case R.id.add20:
                aux = aux + 20;
                break;
            case R.id.rem1:
                if ((aux - 1) >= 0)
                    aux = aux - 1;
                else
                    aux = 0;
                break;
            case R.id.rem5:
                if ((aux - 5) >= 0)
                    aux = aux - 5;
                else
                    aux = 0;
                break;
            case R.id.rem10:
                if ((aux - 10) >= 0)
                    aux = aux - 10;
                else
                    aux = 0;
                break;
            case R.id.rem20:
                if ((aux - 20) >= 0)
                    aux = aux - 20;
                else
                    aux = 0;
                break;
        }

        itemValor.setText(String.valueOf(aux));
    }

    public void adicionaReferencia() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Insira uma nova referência");
        alertBuilder.setCancelable(true);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(75, 0, 75, 0);

        final EditText referencia = new EditText(this);

        layout.addView(referencia, params);
        alertBuilder.setView(layout);

        alertBuilder.setPositiveButton(
                "Adicionar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alertBuilder.setNegativeButton(
                "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void changeColor(boolean credito){
        if(credito){
            toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verde));
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeDark));
            add1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verde));
            add5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeDark));
            add10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeGrad));
            add20.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeGradDark));
            rem1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verde));
            rem5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeDark));
            rem10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeGrad));
            rem20.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeGradDark));
            adicionaReferencia.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verde));
            removerReferencia.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.verdeDark));
            itemAddRem.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.verdeGrad)));
        } else {
            toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelho));
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoDark));
            add1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelho));
            add5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoDark));
            add10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoGrad));
            add20.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoGradDark));
            rem1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelho));
            rem5.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoDark));
            rem10.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoGrad));
            rem20.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoGradDark));
            adicionaReferencia.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelho));
            removerReferencia.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoDark));
            itemAddRem.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.vermelhoGrad)));
        }
    }

    public void itemAddRem(int id, double balance, boolean credito) {
        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        Double value = Double.valueOf(itemValor.getText().toString());
        Double newBalance;

        if (credito)
            newBalance = balance + value;
        else
            newBalance = balance - value;

        databaseHandler.updateItemById(id, newBalance);
        databaseHandler.insertIntoHistoric(credito, newBalance, value, id, spinner.getSelectedItem().toString());
    }

    public boolean validaValorItem(EditText itemValor){
        if (Double.valueOf(itemValor.getText().toString())<=0) {
            itemValor.setError("Insira um valor para a operação");
            itemValor.setFocusable(true);
            return false;
        }
        return true;
    }
}
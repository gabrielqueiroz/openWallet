package com.gqueiroz.openwallet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

public class ItemAddRem extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText itemValor;
    private Spinner spinner;

    private TextView add1;
    private TextView add5;
    private TextView add10;
    private TextView add20;
    private TextView rem1;
    private TextView rem5;
    private TextView rem10;
    private TextView rem20;

    private String[] referencias = new String[]{"Super-Mercado", "Farmacia", "Shopping", "Restaurante", "Outros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add_rem);

        findItemsByID();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Arrays.sort(referencias);

        ArrayAdapter<String> referenciasArray = new ArrayAdapter<>(ItemAddRem.this, android.R.layout.simple_spinner_item, referencias);
        referenciasArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(referenciasArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Escreva uma nova referência");
        builder.setCancelable(true);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton(
                "Inserir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public void findItemsByID() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.referencias);
        itemValor = (EditText) findViewById(R.id.itemValor);

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
}

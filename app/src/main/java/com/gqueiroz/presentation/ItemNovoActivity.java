package com.gqueiroz.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gqueiroz.repository.DatabaseHandler;
import com.gqueiroz.repository.Item;

public class ItemNovoActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton inserirItem;

    private EditText itemNome;
    private EditText itemValor;

    private LinearLayout backgroundNewItem;

    private ImageView itemImagem;

    private Toolbar toolbar;

    private int color;
    private int colorDark;

    private SelectIconDialog selectIconDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_novo);

        findViewsById();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inserirItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNomeItemValid() || !isValorItemValid())
                    return;
                DatabaseHandler databaseHandler = new DatabaseHandler(v.getContext());
                databaseHandler.insertIntoItem(novoItem());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        backgroundNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIconDialog = new SelectIconDialog(ItemNovoActivity.this, color, colorDark, itemImagem);
                selectIconDialog.show();
            }
        });

    }

    public void onClick(View v) {
        clickAddRem(v);
        changeColor(v);
    }

    public void clickAddRem(View v) {
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

    public void changeColor(View v) {

        switch (v.getId()) {
            case R.id.colorVermelho:
                color = R.color.vermelho;
                colorDark = R.color.vermelhoDark;
                break;
            case R.id.colorVerde:
                color = R.color.verde;
                colorDark = R.color.verdeDark;
                break;
            case R.id.colorAmarelo:
                color = R.color.amarelo;
                colorDark = R.color.amareloDark;
                break;
            case R.id.colorRoxo:
                color = R.color.roxo;
                colorDark = R.color.roxoDark;
                break;
            case R.id.colorAzul:
                color = R.color.azul;
                colorDark = R.color.azulDark;
                break;
            case R.id.colorLaranja:
                color = R.color.laranja;
                colorDark = R.color.laranjaDark;
                break;
            case R.id.colorRosa:
                color = R.color.rosa;
                colorDark = R.color.rosaDark;
                break;
            case R.id.colorCiano:
                color = R.color.ciano;
                colorDark = R.color.cianoDark;
                break;
            case R.id.colorIndigo:
                color = R.color.indigo;
                colorDark = R.color.indigoDark;
                break;
            case R.id.colorMarrom:
                color = R.color.marrom;
                colorDark = R.color.marromDark;
                break;
        }

        backgroundNewItem.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), color));
    }

    public Item novoItem() {
        Item item = new Item();

        item.setName(itemNome.getText().toString());
        item.setValue(Double.valueOf(itemValor.getText().toString()));
        item.setColor(String.valueOf(color));
        item.setColorDark(String.valueOf(colorDark));
        item.setImage(itemImagem.getTag().toString());

        return item;
    }

    public void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        itemNome = (EditText) findViewById(R.id.itemNome);
        itemValor = (EditText) findViewById(R.id.itemValor);

        inserirItem = (FloatingActionButton) findViewById(R.id.novoItem);

        TextView add1 = (TextView) findViewById(R.id.add1);
        TextView add5 = (TextView) findViewById(R.id.add5);
        TextView add10 = (TextView) findViewById(R.id.add10);
        TextView add20 = (TextView) findViewById(R.id.add20);
        TextView rem1 = (TextView) findViewById(R.id.rem1);
        TextView rem5 = (TextView) findViewById(R.id.rem5);
        TextView rem10 = (TextView) findViewById(R.id.rem10);
        TextView rem20 = (TextView) findViewById(R.id.rem20);

        add1.setOnClickListener(this);
        add5.setOnClickListener(this);
        add10.setOnClickListener(this);
        add20.setOnClickListener(this);
        rem1.setOnClickListener(this);
        rem5.setOnClickListener(this);
        rem10.setOnClickListener(this);
        rem20.setOnClickListener(this);

        backgroundNewItem = (LinearLayout) findViewById(R.id.backgroundNewItem);

        Button colorVermelho = (Button) findViewById(R.id.colorVermelho);
        Button colorVerde = (Button) findViewById(R.id.colorVerde);
        Button colorAmarelo = (Button) findViewById(R.id.colorAmarelo);
        Button colorRoxo = (Button) findViewById(R.id.colorRoxo);
        Button colorAzul = (Button) findViewById(R.id.colorAzul);
        Button colorLaranja = (Button) findViewById(R.id.colorLaranja);
        Button colorRosa = (Button) findViewById(R.id.colorRosa);
        Button colorCiano = (Button) findViewById(R.id.colorCiano);
        Button colorIndigo = (Button) findViewById(R.id.colorIndigo);
        Button colorMarrom = (Button) findViewById(R.id.colorMarrom);

        colorVermelho.setOnClickListener(this);
        colorVerde.setOnClickListener(this);
        colorAmarelo.setOnClickListener(this);
        colorRoxo.setOnClickListener(this);
        colorAzul.setOnClickListener(this);
        colorLaranja.setOnClickListener(this);
        colorRosa.setOnClickListener(this);
        colorCiano.setOnClickListener(this);
        colorIndigo.setOnClickListener(this);
        colorMarrom.setOnClickListener(this);

        itemImagem = (ImageView) findViewById(R.id.itemImagem);

        color = R.color.azul;
        colorDark = R.color.azulDark;

        backgroundNewItem.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), color));
    }

    public boolean isNomeItemValid() {
        if (TextUtils.isEmpty(itemNome.getText())) {
            itemNome.setError("Insira um nome para o item");
            itemNome.setFocusable(true);
            return false;
        }
        return true;
    }

    public boolean isValorItemValid(){
        if (TextUtils.isEmpty(itemValor.getText())) {
            itemValor.setError("Valor não pode ser branco");
            itemValor.setFocusable(true);
            return false;
        }
        return true;
    }
}

package com.gqueiroz.openwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gqueiroz.database.DatabaseHandler;
import com.gqueiroz.database.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemNovo extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton inserirItem;

    private EditText itemNome;

    private ImageView icon1;
    private ImageView icon2;
    private ImageView icon3;
    private ImageView icon4;
    private ImageView icon5;

    private EditText itemValor;

    private TextView add1;
    private TextView add5;
    private TextView add10;
    private TextView add20;

    private TextView rem1;
    private TextView rem5;
    private TextView rem10;
    private TextView rem20;

    private SeekBar seekBarIcon;
    private SeekBar seekBarColor;

    private List<Integer> colors;
    private List<Integer> colorsDark;
    private List<Integer> icons;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_novo);

        findViewsById();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeIcon(3);
        seekBarIcon.setMax(icons.size() - 1);
        seekBarIcon.setProgress(7);

        seekBarIcon.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeIcon(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        changeColor(4);
        seekBarColor.setMax(colors.size() - 1);
        seekBarColor.setProgress(4);

        seekBarColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void changeIcon(int progress) {
        icons = new ArrayList<>();
        icons.add(R.drawable.ic_shopping_cart_white_48dp);
        icons.add(R.drawable.ic_card_travel_white_48dp);
        icons.add(R.drawable.ic_card_giftcard_white_48dp);
        icons.add(R.drawable.ic_beach_access_white_48dp);
        icons.add(R.drawable.ic_motorcycle_white_48dp);
        icons.add(R.drawable.ic_weekend_white_48dp);
        icons.add(R.drawable.ic_credit_card_white_48dp);
        icons.add(R.drawable.ic_account_balance_white_48dp);
        icons.add(R.drawable.ic_favorite_white_48dp);
        icons.add(R.drawable.ic_videogame_asset_white_48dp);
        icons.add(R.drawable.ic_fingerprint_white_48dp);
        icons.add(R.drawable.ic_pets_white_48dp);
        icons.add(R.drawable.ic_vpn_key_white_48dp);
        icons.add(R.drawable.ic_face_white_48dp);
        icons.add(R.drawable.ic_help_white_48dp);

        if (progress - 2 < 0)
            icon1.setVisibility(View.INVISIBLE);
        else {
            icon1.setVisibility(View.VISIBLE);
            icon1.setImageDrawable(ContextCompat.getDrawable(this, icons.get(progress - 2)));
        }

        if (progress - 1 < 0)
            icon2.setVisibility(View.INVISIBLE);
        else {
            icon2.setVisibility(View.VISIBLE);
            icon2.setImageDrawable(ContextCompat.getDrawable(this, icons.get(progress - 1)));
        }

        icon3.setImageDrawable(ContextCompat.getDrawable(this, icons.get(progress)));

        if (progress + 1 > icons.size() - 1)
            icon4.setVisibility(View.INVISIBLE);
        else {
            icon4.setVisibility(View.VISIBLE);
            icon4.setImageDrawable(ContextCompat.getDrawable(this, icons.get(progress + 1)));
        }

        if (progress + 2 > icons.size() - 1)
            icon5.setVisibility(View.INVISIBLE);
        else {
            icon5.setVisibility(View.VISIBLE);
            icon5.setImageDrawable(ContextCompat.getDrawable(this, icons.get(progress + 2)));
        }
    }

    public void changeColor(int progress) {
        colors = new ArrayList<>();
        colorsDark = new ArrayList<>();

        colors.add(R.color.vermelho);
        colorsDark.add(R.color.vermelhoDark);
        colors.add(R.color.verde);
        colorsDark.add(R.color.verdeDark);
        colors.add(R.color.amarelo);
        colorsDark.add(R.color.amareloDark);
        colors.add(R.color.roxo);
        colorsDark.add(R.color.roxoDark);
        colors.add(R.color.colorPrimary);
        colorsDark.add(R.color.colorPrimaryDark);
        colors.add(R.color.laranja);
        colorsDark.add(R.color.laranjaDark);
        colors.add(R.color.rosa);
        colorsDark.add(R.color.rosaDark);
        colors.add(R.color.ciano);
        colorsDark.add(R.color.cianoDark);
        colors.add(R.color.indigo);
        colorsDark.add(R.color.indigoDark);

        icon1.setBackgroundColor(ContextCompat.getColor(this, colors.get(progress)));
        icon2.setBackgroundColor(ContextCompat.getColor(this, colors.get(progress)));
        icon3.setBackgroundColor(ContextCompat.getColor(this, colors.get(progress)));
        icon4.setBackgroundColor(ContextCompat.getColor(this, colors.get(progress)));
        icon5.setBackgroundColor(ContextCompat.getColor(this, colors.get(progress)));
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
            case R.id.novoItem:
                if (!validaNomeItem(itemNome))
                    break;
                DatabaseHandler databaseHandler = new DatabaseHandler(this);
                databaseHandler.insertIntoItem(novoItem());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
        }

        itemValor.setText(String.valueOf(aux));
    }

    public Item novoItem() {
        Item item = new Item();

        int icone = seekBarIcon.getProgress();
        int cor = seekBarColor.getProgress();

        item.setName(itemNome.getText().toString());
        item.setValue(Double.valueOf(itemValor.getText().toString()));
        item.setImage(icons.get(icone).toString());
        item.setColor(colors.get(cor).toString());
        item.setColorDark(colorsDark.get(cor).toString());

        return item;
    }

    public void findViewsById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        itemNome = (EditText) findViewById(R.id.itemNome);

        seekBarIcon = (SeekBar) findViewById(R.id.seekBarIcon);
        seekBarColor = (SeekBar) findViewById(R.id.seekBarColor);

        icon1 = (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);
        icon3 = (ImageView) findViewById(R.id.icon3);
        icon4 = (ImageView) findViewById(R.id.icon4);
        icon5 = (ImageView) findViewById(R.id.icon5);

        itemValor = (EditText) findViewById(R.id.itemValor);

        inserirItem = (FloatingActionButton) findViewById(R.id.novoItem);

        add1 = (TextView) findViewById(R.id.add1);
        add5 = (TextView) findViewById(R.id.add5);
        add10 = (TextView) findViewById(R.id.add10);
        add20 = (TextView) findViewById(R.id.add20);

        rem1 = (TextView) findViewById(R.id.rem1);
        rem5 = (TextView) findViewById(R.id.rem5);
        rem10 = (TextView) findViewById(R.id.rem10);
        rem20 = (TextView) findViewById(R.id.rem20);

        inserirItem.setOnClickListener(this);

        add1.setOnClickListener(this);
        add5.setOnClickListener(this);
        add10.setOnClickListener(this);
        add20.setOnClickListener(this);

        rem1.setOnClickListener(this);
        rem5.setOnClickListener(this);
        rem10.setOnClickListener(this);
        rem20.setOnClickListener(this);
    }

    public boolean validaNomeItem(EditText itemNome) {
        if (TextUtils.isEmpty(itemNome.getText())) {
            itemNome.setError("Insira um nome para o item");
            itemNome.setFocusable(true);
            return false;
        }
        return true;
    }
}

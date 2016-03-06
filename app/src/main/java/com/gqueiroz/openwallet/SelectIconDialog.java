package com.gqueiroz.openwallet;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by gabrielqueiroz on 3/3/16.
 */
public class SelectIconDialog extends Dialog implements View.OnClickListener{

    private Activity c;
    private Button voltar;
    private int color;
    private int colorDark;
    private ImageView backgroundItem;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;
    private ImageView image10;
    private ImageView image11;
    private ImageView image12;
    private ImageView image13;
    private ImageView image14;
    private ImageView image15;
    

    private LinearLayout selectItemPrimary;

    public SelectIconDialog(Activity a, int color, int colorDark, ImageView backgroundItem) {
        super(a);
        this.c = a;
        this.color = color;
        this.colorDark = colorDark;
        this.backgroundItem = backgroundItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_select_icon);

        findViewsById();

        selectItemPrimary.setBackgroundColor(color);
        voltar.setBackgroundColor(colorDark);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

    }

    public void closeDialog() {
        this.dismiss();
    }

    public void findViewsById(){
        selectItemPrimary = (LinearLayout) findViewById(R.id.selectItemPrimary);
        voltar = (Button) findViewById(R.id.selectItemVoltar);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        image6 = (ImageView) findViewById(R.id.image6);
        image7 = (ImageView) findViewById(R.id.image7);
        image8 = (ImageView) findViewById(R.id.image8);
        image9 = (ImageView) findViewById(R.id.image9);
        image10 = (ImageView) findViewById(R.id.image10);
        image11 = (ImageView) findViewById(R.id.image11);
        image12 = (ImageView) findViewById(R.id.image12);
        image13 = (ImageView) findViewById(R.id.image13);
        image14 = (ImageView) findViewById(R.id.image14);
        image15 = (ImageView) findViewById(R.id.image15);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        image7.setOnClickListener(this);
        image8.setOnClickListener(this);
        image9.setOnClickListener(this);
        image10.setOnClickListener(this);
        image11.setOnClickListener(this);
        image12.setOnClickListener(this);
        image13.setOnClickListener(this);
        image14.setOnClickListener(this);
        image15.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.image1:
                backgroundItem.setImageDrawable(image1.getDrawable());
                backgroundItem.setTag(R.drawable.ic_beach_access_white_48dp);
                closeDialog();
                break;
            case R.id.image2:
                backgroundItem.setImageDrawable(image2.getDrawable());
                backgroundItem.setTag(R.drawable.ic_motorcycle_white_48dp);
                closeDialog();
                break;
            case R.id.image3:
                backgroundItem.setImageDrawable(image3.getDrawable());
                backgroundItem.setTag(R.drawable.ic_weekend_white_48dp);
                closeDialog();
                break;
            case R.id.image4:
                backgroundItem.setImageDrawable(image4.getDrawable());
                backgroundItem.setTag(R.drawable.ic_favorite_white_48dp);
                closeDialog();
                break;
            case R.id.image5:
                backgroundItem.setImageDrawable(image5.getDrawable());
                backgroundItem.setTag(R.drawable.ic_videogame_asset_white_48dp);
                closeDialog();
                break;
            case R.id.image6:
                backgroundItem.setImageDrawable(image6.getDrawable());
                backgroundItem.setTag(R.drawable.ic_pets_white_48dp);
                closeDialog();
                break;
            case R.id.image7:
                backgroundItem.setImageDrawable(image7.getDrawable());
                backgroundItem.setTag(R.drawable.ic_fingerprint_white_48dp);
                closeDialog();
                break;
            case R.id.image8:
                backgroundItem.setImageDrawable(image8.getDrawable());
                backgroundItem.setTag(R.drawable.ic_vpn_key_white_48dp);
                closeDialog();
                break;
            case R.id.image9:
                backgroundItem.setImageDrawable(image9.getDrawable());
                backgroundItem.setTag(R.drawable.ic_face_white_48dp);
                closeDialog();
                break;
            case R.id.image10:
                backgroundItem.setImageDrawable(image10.getDrawable());
                backgroundItem.setTag(R.drawable.ic_shopping_cart_white_48dp);
                closeDialog();
                break;
            case R.id.image11:
                backgroundItem.setImageDrawable(image11.getDrawable());
                backgroundItem.setTag(R.drawable.ic_flight_takeoff_white_48dp);
                closeDialog();
                break;
            case R.id.image12:
                backgroundItem.setImageDrawable(image12.getDrawable());
                backgroundItem.setTag(R.drawable.ic_card_giftcard_white_48dp);
                closeDialog();
                break;
            case R.id.image13:
                backgroundItem.setImageDrawable(image13.getDrawable());
                backgroundItem.setTag(R.drawable.ic_credit_card_white_48dp);
                closeDialog();
                break;
            case R.id.image14:
                backgroundItem.setImageDrawable(image14.getDrawable());
                backgroundItem.setTag(R.drawable.ic_account_balance_white_48dp);
                closeDialog();
                break;
            case R.id.image15:
                backgroundItem.setImageDrawable(image15.getDrawable());
                backgroundItem.setTag(R.drawable.ic_card_travel_white_48dp);
                closeDialog();
                break;
        }
    }
}

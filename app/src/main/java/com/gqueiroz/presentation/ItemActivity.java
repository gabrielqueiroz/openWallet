package com.gqueiroz.presentation;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.gqueiroz.adapters.HistoryAdapter;
import com.gqueiroz.repository.DatabaseHandler;
import com.gqueiroz.repository.History;
import com.gqueiroz.repository.Item;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity {

    @Bind(R.id.recyclerViewHistory)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.multipleActions)
    FloatingActionsMenu multipleActions;

    @Bind(R.id.actionCredit)
    FloatingActionButton actionCredit;

    @Bind(R.id.actionDebit)
    FloatingActionButton actionDebit;

    @Bind(R.id.itemBackground)
    GridLayout itemBackground;

    @Bind(R.id.backgroundItemValue)
    CardView backgroundItemValue;

    @Bind(R.id.backgroundMonthSelected)
    CardView backgroundMonthSelected;

    @Bind(R.id.itemIcon)
    ImageView itemIcon;

    @Bind(R.id.totalValue)
    TextView totalValue;

    private Item item = new Item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String extra = extras.getString("extra");
            item = new Gson().fromJson(extra, item.getClass());
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(item.getName());

        changeColor();

        totalValue.setText(Html.fromHtml("<b>= R$</b>" + item.getValue()));

        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        List<History> history = databaseHandler.getAllHistory(item.getId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ItemActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        HistoryAdapter historyAdapter = new HistoryAdapter(history, getApplicationContext());
        recyclerView.setAdapter(historyAdapter);

        actionCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRemActivity.class);
                String extra = new Gson().toJson(item);
                i.putExtra("extra",extra);
                i.putExtra("credito", true);
                v.getContext().startActivity(i);
            }
        });

        actionDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRemActivity.class);
                String extra = new Gson().toJson(item);
                i.putExtra("extra",extra);
                i.putExtra("credito", false);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_ajuda) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeColor(){
        int colorPrimary = ContextCompat.getColor(getApplicationContext(),
                Integer.parseInt(item.getColor()));
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(),
                Integer.parseInt(item.getColorDark()));

        toolbar.setBackgroundColor(colorPrimary);
        getWindow().setStatusBarColor(colorPrimaryDark);

        itemBackground.setBackgroundColor(colorPrimary);
        backgroundItemValue.setCardBackgroundColor(colorPrimaryDark);
        backgroundMonthSelected.setCardBackgroundColor(colorPrimaryDark);

        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                Integer.parseInt(item.getImage())));
    }

}

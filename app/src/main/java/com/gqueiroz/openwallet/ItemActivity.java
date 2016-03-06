package com.gqueiroz.openwallet;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.gqueiroz.adapters.HistoryAdapter;
import com.gqueiroz.database.DatabaseHandler;
import com.gqueiroz.database.History;
import com.gqueiroz.database.Item;

import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HistoryAdapter historyAdapter;

    private Toolbar toolbar;

    private FloatingActionsMenu multipleActions;
    private FloatingActionButton actionCredit;
    private FloatingActionButton actionDebit;

    private LinearLayout emptyHistory;
    private LinearLayout backgroundItem;
    private LinearLayout backgroundHistory;
    private ImageView itemImagem;
    private TextView itemNome;
    private TextView itemValor;

    private Item item = new Item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        findItemsByID();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String extra = extras.getString("extra");
            item = new Gson().fromJson(extra, item.getClass());
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(item.getName());

        changeColor();

        itemNome.setText(item.getName());
        itemValor.setText("R$ "+item.getValue());

        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        List<History> history = databaseHandler.getAllHistory(item.getId());

        linearLayoutManager = new LinearLayoutManager(ItemActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        historyAdapter = new HistoryAdapter(history, getApplicationContext());
        recyclerView.setAdapter(historyAdapter);

        if(history.isEmpty()){
            emptyHistory.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        actionCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRem.class);
                String extra = new Gson().toJson(item);
                i.putExtra("extra",extra);
                i.putExtra("credito", true);
                v.getContext().startActivity(i);
            }
        });

        actionDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRem.class);
                String extra = new Gson().toJson(item);
                i.putExtra("extra",extra);
                i.putExtra("credito", false);
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_item, menu);
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
        //Change Toolbar, StatusBarColor and FloatingActionButton
        toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Integer.parseInt(item.getColor())));
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), Integer.parseInt(item.getColorDark())));
        //Change Item Color
        backgroundItem.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Integer.parseInt(item.getColor())));
        backgroundHistory.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Integer.parseInt(item.getColor())));
        itemImagem.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), Integer.parseInt(item.getImage())));
    }

    public void findItemsByID() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistory);
        emptyHistory = (LinearLayout) findViewById(R.id.emptyHistory);
        multipleActions = (FloatingActionsMenu) findViewById(R.id.multipleActions);
        actionCredit = (FloatingActionButton) findViewById(R.id.actionCredit);
        actionDebit = (FloatingActionButton) findViewById(R.id.actionDebit);
        backgroundItem = (LinearLayout) findViewById(R.id.backgroundItem);
        backgroundHistory = (LinearLayout) findViewById(R.id.backgroundHistory);
        itemImagem = (ImageView) findViewById(R.id.itemImagem);
        itemNome = (TextView) findViewById(R.id.itemNome);
        itemValor = (TextView) findViewById(R.id.itemValor);
    }
}

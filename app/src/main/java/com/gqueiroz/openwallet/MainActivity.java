package com.gqueiroz.openwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gqueiroz.adapters.ItemAdapter;
import com.gqueiroz.repository.DatabaseHandler;
import com.gqueiroz.repository.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemAdapter itemAdapter;

    private Toolbar toolbar;
    private FloatingActionButton novoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findItemsByID();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Open Wallet");

        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        List<Item> items = databaseHandler.getAllItems();

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        itemAdapter = new ItemAdapter(items, getApplicationContext());
        recyclerView.setAdapter(itemAdapter);

        novoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ItemNovoActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    public void findItemsByID(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewItems);
        novoItem = (FloatingActionButton) findViewById(R.id.novoItem);
    }
}

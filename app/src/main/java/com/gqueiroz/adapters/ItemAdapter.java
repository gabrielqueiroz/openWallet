package com.gqueiroz.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gqueiroz.database.Item;
import com.gqueiroz.openwallet.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    public List<Item> itemList;

    public ItemAdapter(List<Item> itemList){
        this.itemList = itemList;
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_item, viewGroup, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, int position) {
        final Item item = itemList.get(position);
        itemViewHolder.itemNome.setText(item.getName());
        itemViewHolder.itemValor.setText("R$ "+String.valueOf(item.getValue()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView itemNome;
        protected TextView itemValor;
        protected ImageView itemImagem;
        protected CardView card;

        public ItemViewHolder(View v) {
            super(v);
            itemNome = (TextView) v.findViewById(R.id.itemNome);
            itemValor = (TextView) v.findViewById(R.id.itemValor);
            itemImagem = (ImageView) v.findViewById(R.id.itemImagem);
            card = (CardView) v.findViewById(R.id.cardItem);
        }
    }
}

package com.gqueiroz.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gqueiroz.database.Item;
import com.gqueiroz.openwallet.R;
import com.gqueiroz.openwallet.ItemAddRem;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    public List<Item> itemList;

    public ItemAdapter(List<Item> itemList) {
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
        itemViewHolder.itemValor.setText("R$ " + String.valueOf(item.getValue()));

        itemViewHolder.itemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRem.class);
                v.getContext().startActivity(i);
            }
        });

        itemViewHolder.itemRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRem.class);
                v.getContext().startActivity(i);
            }
        });

        itemViewHolder.itemImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemViewHolder.backgroundAdd.setVisibility(View.GONE);
                itemViewHolder.backgroundRem.setVisibility(View.GONE);
                itemViewHolder.backgroundDel.setVisibility(View.GONE);
            }
        });

        itemViewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemViewHolder.backgroundDel.getVisibility() == View.VISIBLE || itemViewHolder.backgroundAdd.getVisibility() == View.VISIBLE) {
                    itemViewHolder.backgroundAdd.setVisibility(View.GONE);
                    itemViewHolder.backgroundRem.setVisibility(View.GONE);
                    itemViewHolder.backgroundDel.setVisibility(View.GONE);
                } else {
                    itemViewHolder.backgroundAdd.setVisibility(View.VISIBLE);
                    itemViewHolder.backgroundRem.setVisibility(View.VISIBLE);
                    itemViewHolder.backgroundDel.setVisibility(View.GONE);
                }
            }
        });

        itemViewHolder.itemCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemViewHolder.backgroundAdd.setVisibility(View.GONE);
                itemViewHolder.backgroundRem.setVisibility(View.GONE);
                itemViewHolder.backgroundDel.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView itemNome;
        protected TextView itemValor;

        protected ImageView itemAdd;
        protected ImageView itemRem;
        protected ImageView itemDel;
        protected ImageView itemImagem;

        protected LinearLayout backgroundAdd;
        protected LinearLayout backgroundRem;
        protected LinearLayout backgroundDel;
        protected LinearLayout backgroundItem;

        protected CardView itemCard;

        public ItemViewHolder(View v) {
            super(v);
            itemNome = (TextView) v.findViewById(R.id.itemNome);
            itemValor = (TextView) v.findViewById(R.id.itemValor);
            itemImagem = (ImageView) v.findViewById(R.id.itemImagem);
            itemAdd = (ImageView) v.findViewById(R.id.itemAdd);
            itemRem = (ImageView) v.findViewById(R.id.itemRem);
            itemDel = (ImageView) v.findViewById(R.id.itemDel);
            itemCard = (CardView) v.findViewById(R.id.itemCard);
            backgroundAdd = (LinearLayout) v.findViewById(R.id.backgroundAdd);
            backgroundRem = (LinearLayout) v.findViewById(R.id.backgroundRem);
            backgroundDel = (LinearLayout) v.findViewById(R.id.backgroundDel);
            backgroundItem = (LinearLayout) v.findViewById(R.id.backgroundItem);
        }
    }
}

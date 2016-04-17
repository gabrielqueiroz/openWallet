package com.gqueiroz.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gqueiroz.repository.DatabaseHandler;
import com.gqueiroz.repository.Item;
import com.gqueiroz.openwallet.ItemActivity;
import com.gqueiroz.openwallet.R;
import com.gqueiroz.openwallet.ItemAddRemActivity;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context context;
    private List<Item> itemList;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
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
        itemViewHolder.itemValor.setText(Html.fromHtml(String.format("<b>R$</b> %s", String.valueOf(item.getValue()))));
        itemViewHolder.itemImagem.setImageDrawable(ContextCompat.getDrawable(context, Integer.parseInt(item.getImage())));
        itemViewHolder.backgroundItem.setBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(item.getColor())));
        itemViewHolder.itemCard.setCardBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(item.getColor())));

        itemViewHolder.backgroundAdd.setBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(item.getColorDark())));
        itemViewHolder.backgroundAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRemActivity.class);
                String extra = new Gson().toJson(item);
                i.putExtra("extra", extra);
                i.putExtra("credito", true);
                v.getContext().startActivity(i);
            }
        });

        itemViewHolder.backgroundRem.setBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(item.getColorDark())));
        itemViewHolder.backgroundRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ItemAddRemActivity.class);
                String extra = new Gson().toJson(item);
                i.putExtra("extra", extra);
                i.putExtra("credito", false);
                v.getContext().startActivity(i);
            }
        });

        itemViewHolder.backgroundDel.setBackgroundColor(ContextCompat.getColor(context, Integer.parseInt(item.getColorDark())));
        itemViewHolder.backgroundDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                alertBuilder.setMessage(Html.fromHtml("Tem certeza que deseja <b>excluir " + item.getName() + "</b>? Todo o histórico de transaçōes será excluído também."));
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton(
                        "Sim, excluir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                                databaseHandler.deleteFromItem(item.getId());
                                itemViewHolder.backgroundDel.setVisibility(View.GONE);
                                dataSetChanged();
                            }
                        });

                alertBuilder.setNegativeButton(
                        "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                itemViewHolder.backgroundDel.setVisibility(View.GONE);
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });

        itemViewHolder.itemImagem.setOnClickListener(new View.OnClickListener() {
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

        itemViewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemViewHolder.backgroundDel.getVisibility() == View.VISIBLE || itemViewHolder.backgroundAdd.getVisibility() == View.VISIBLE) {
                    itemViewHolder.backgroundAdd.setVisibility(View.GONE);
                    itemViewHolder.backgroundRem.setVisibility(View.GONE);
                    itemViewHolder.backgroundDel.setVisibility(View.GONE);
                } else {
                    Intent i = new Intent(v.getContext(), ItemActivity.class);
                    String extra = new Gson().toJson(item);
                    i.putExtra("extra", extra);
                    v.getContext().startActivity(i);
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

    @UiThread
    private void dataSetChanged() {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        List<Item> newList = databaseHandler.getAllItems();

        itemList.clear();
        itemList.addAll(newList);
        this.notifyDataSetChanged();
    }


}

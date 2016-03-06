package com.gqueiroz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gqueiroz.openwallet.R;
import com.gqueiroz.database.History;

import java.util.List;

/**
 * Created by gabrielqueiroz on 2/28/16.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<History> historyList;

    public HistoryAdapter(List<History> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View historyView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_history, viewGroup, false);

        return new HistoryViewHolder(historyView);
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder historyViewHolder, int position) {
        final History history = historyList.get(position);
        historyViewHolder.detalhes.setText("R$ "+String.valueOf(history.getValor())+" com "+history.getReferencia());
        if(history.getTipo().equals("C"))
            historyViewHolder.credit.setVisibility(View.VISIBLE);
        else
            historyViewHolder.debit.setVisibility(View.VISIBLE);

        historyViewHolder.data.setText(history.getData().toString());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        protected ImageView credit;
        protected ImageView debit;
        protected TextView detalhes;
        protected TextView data;

        public HistoryViewHolder(View v) {
            super(v);
            credit = (ImageView) v.findViewById(R.id.credit);
            debit = (ImageView) v.findViewById(R.id.debit);
            detalhes = (TextView) v.findViewById(R.id.detalhes);
            data = (TextView) v.findViewById(R.id.data);
        }
    }
}

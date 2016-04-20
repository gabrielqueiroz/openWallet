package com.gqueiroz.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gqueiroz.presentation.R;
import com.gqueiroz.repository.History;

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
        historyViewHolder.historyValue.setText(Html.fromHtml("<b>R$</b> " + history.getValor()));
        historyViewHolder.historyDetails.setText(Html.fromHtml("com <b>" + history.getReferencia() + "</b> em <b>" + history.getData().toString()+"</b>"));

        if (history.getTipo().equals("C")) {
            historyViewHolder.backgroundHistory.setBackgroundColor
                    (ContextCompat.getColor(context, R.color.verde));
        } else {
            historyViewHolder.backgroundHistory.setBackgroundColor
                    (ContextCompat.getColor(context, R.color.vermelho));
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        protected CardView backgroundHistory;
        protected TextView historyValue;
        protected TextView historyDetails;

        public HistoryViewHolder(View v) {
            super(v);
            backgroundHistory = (CardView) v.findViewById(R.id.backgroundHistory);
            historyValue = (TextView) v.findViewById(R.id.historyValue);
            historyDetails = (TextView) v.findViewById(R.id.historyDetails);
        }
    }
}

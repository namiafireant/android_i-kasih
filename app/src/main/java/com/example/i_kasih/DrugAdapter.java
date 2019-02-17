package com.example.i_kasih;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.DrugViewHolder> {
    List<Drug> drugs;

    public DrugAdapter(List<Drug> drugs) { this.drugs = drugs; }
    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_drug, viewGroup, false);
        DrugViewHolder DrugViewHolder = new DrugViewHolder(v);
        return DrugViewHolder;
    }
    @Override
    public void onBindViewHolder(DrugViewHolder DrugViewHolder, int i) {
        DrugViewHolder.drugName.setText(drugs.get(i).getNama());
        DrugViewHolder.drugHarga.setText(drugs.get(i).getHarga());
        DrugViewHolder.drugKode.setText(drugs.get(i).getKode());
    }
    @Override
    public int getItemCount() {
        return drugs.size();
    }
    public Drug getItem(int position) {
        return drugs.get(position);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class DrugViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView drugName;
        TextView drugHarga;
        TextView drugKode;
        DrugViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            drugName = (TextView) itemView.findViewById(R.id.textViewRowNama);
            drugHarga = (TextView) itemView.findViewById(R.id.textViewRowHarga);
            drugKode = (TextView) itemView.findViewById(R.id.textViewRowKode);
        }
    }
}
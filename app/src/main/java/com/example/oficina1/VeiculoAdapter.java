package com.example.oficina1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.Veiculo;
import java.util.List;

public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoAdapter.VeiculoViewHolder> {

    private List<Veiculo> veiculos;

    public interface OnVeiculoClickListener {
        void onVeiculoClick(Veiculo veiculo);
    }

    private OnVeiculoClickListener listener;

    public VeiculoAdapter(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public VeiculoAdapter(List<Veiculo> veiculos, OnVeiculoClickListener listener) {
        this.veiculos = veiculos;
        this.listener = listener;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VeiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_veiculo, parent, false);
        return new VeiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiculoViewHolder holder, int position) {
        Veiculo veiculo = veiculos.get(position);
        holder.txtModelo.setText(veiculo.modelo);
        holder.txtPlaca.setText(veiculo.placa);
        String anoCor = veiculo.ano + " • " + veiculo.cor;
        holder.txtAnoCor.setText(anoCor);
        holder.txtStatus.setText(veiculo.status);

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onVeiculoClick(veiculo));
        }
    }

    @Override
    public int getItemCount() {
        return veiculos != null ? veiculos.size() : 0;
    }

    static class VeiculoViewHolder extends RecyclerView.ViewHolder {
        TextView txtModelo, txtPlaca, txtAnoCor, txtStatus;

        public VeiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtModelo = itemView.findViewById(R.id.txtItemModelo);
            txtPlaca = itemView.findViewById(R.id.txtItemPlaca);
            txtAnoCor = itemView.findViewById(R.id.txtItemAnoCor);
            txtStatus = itemView.findViewById(R.id.txtItemStatus);
        }
    }
}
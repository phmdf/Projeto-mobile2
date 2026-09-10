package com.example.oficina1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.Orcamento;
import java.util.List;

public class OrcamentoAdapter extends RecyclerView.Adapter<OrcamentoAdapter.OrcamentoViewHolder> {

    private List<Orcamento> orcamentos;

    public interface OnOrcamentoClickListener {
        void onOrcamentoClick(Orcamento orcamento);
    }

    private OnOrcamentoClickListener listener;

    public OrcamentoAdapter(List<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
    }

    public OrcamentoAdapter(List<Orcamento> orcamentos, OnOrcamentoClickListener listener) {
        this.orcamentos = orcamentos;
        this.listener = listener;
    }

    public void setOrcamentos(List<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrcamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orcamento, parent, false);
        return new OrcamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrcamentoViewHolder holder, int position) {
        Orcamento orcamento = orcamentos.get(position);
        holder.txtVeiculo.setText("Veículo ID: " + orcamento.veiculoId);
        holder.txtStatus.setText(orcamento.status);
        holder.txtDetalhes.setText(orcamento.detalhes);
        holder.txtData.setText(orcamento.dataCriacao);
        holder.txtValor.setText(String.format("R$ %.2f", orcamento.valorTotal));

        // Mudar cor do status dinamicamente
        int color;
        switch (orcamento.status) {
            case "Pronto":
            case "Entregue":
                color = 0xFF10B981; // Verde
                break;
            case "Em Manutenção":
                color = 0xFF3B82F6; // Azul
                break;
            case "Recusado":
                color = 0xFFEF4444; // Vermelho
                break;
            default:
                color = 0xFFF59E0B; // Amarelo/Laranja (Pendente/Aguardando)
                break;
        }
        holder.txtStatus.getBackground().setTint(color);

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onOrcamentoClick(orcamento));
        }
    }

    @Override
    public int getItemCount() {
        return orcamentos != null ? orcamentos.size() : 0;
    }

    static class OrcamentoViewHolder extends RecyclerView.ViewHolder {
        TextView txtVeiculo, txtStatus, txtDetalhes, txtData, txtValor;

        public OrcamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVeiculo = itemView.findViewById(R.id.txtItemOrcamentoVeiculo);
            txtStatus = itemView.findViewById(R.id.txtItemOrcamentoStatus);
            txtDetalhes = itemView.findViewById(R.id.txtItemOrcamentoDetalhes);
            txtData = itemView.findViewById(R.id.txtItemOrcamentoData);
            txtValor = itemView.findViewById(R.id.txtItemOrcamentoValor);
        }
    }
}
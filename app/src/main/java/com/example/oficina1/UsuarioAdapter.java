package com.example.oficina1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.oficina1.database.Usuario;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;
    private OnUsuarioClickListener listener;

    public interface OnUsuarioClickListener {
        void onUsuarioClick(Usuario usuario);
    }

    public UsuarioAdapter(List<Usuario> usuarios, OnUsuarioClickListener listener) {
        this.usuarios = usuarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.txtNome.setText(usuario.nome);
        holder.txtEmail.setText(usuario.email);
        holder.txtCargo.setText(usuario.cargo);
        holder.itemView.setOnClickListener(v -> listener.onUsuarioClick(usuario));
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtEmail, txtCargo;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtItemUsuarioNome);
            txtEmail = itemView.findViewById(R.id.txtItemUsuarioEmail);
            txtCargo = itemView.findViewById(R.id.txtItemUsuarioCargo);
        }
    }
}
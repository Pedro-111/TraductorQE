package com.traductornmt.app.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.traductornmt.app.R;
import com.traductornmt.app.models.diccionario.Entrada;

import java.util.List;

public class EntradaAdapter extends RecyclerView.Adapter<EntradaAdapter.EntradaViewHolder> {

    private List<Entrada> entradas;

    public EntradaAdapter(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    @NonNull
    @Override
    public EntradaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entrada, parent, false);
        return new EntradaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntradaViewHolder holder, int position) {
        Entrada entrada = entradas.get(position);
        holder.bind(entrada);
    }

    @Override
    public int getItemCount() {
        return entradas.size();
    }

    public void updateData(List<Entrada> nuevasEntradas) {
        this.entradas = nuevasEntradas;
        notifyDataSetChanged();
    }

    static class EntradaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPalabra;
        private TextView tvCategoria;
        private TextView tvOrigen;
        private TextView tvDefinicion;
        private TextView tvEjemplo;
        private TextView tvReferencias;

        public EntradaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPalabra = itemView.findViewById(R.id.tv_palabra);
            tvCategoria = itemView.findViewById(R.id.tv_categoria);
            tvOrigen = itemView.findViewById(R.id.tv_origen);
            tvDefinicion = itemView.findViewById(R.id.tv_definicion);
            tvEjemplo = itemView.findViewById(R.id.tv_ejemplo);
            tvReferencias = itemView.findViewById(R.id.tv_referencias);
        }

        public void bind(Entrada entrada) {
            tvPalabra.setText(entrada.getPalabra());

            // Mostrar categoría si existe
            if (entrada.getCategoria() != null && !entrada.getCategoria().isEmpty()) {
                tvCategoria.setText(entrada.getCategoria());
                tvCategoria.setVisibility(View.VISIBLE);
            } else {
                tvCategoria.setVisibility(View.GONE);
            }

            // Mostrar origen si existe
            if (entrada.getOrigen() != null && !entrada.getOrigen().isEmpty()) {
                tvOrigen.setText("(" + entrada.getOrigen() + ")");
                tvOrigen.setVisibility(View.VISIBLE);
            } else {
                tvOrigen.setVisibility(View.GONE);
            }

            tvDefinicion.setText(entrada.getDefinicion());

            // Mostrar ejemplo si existe
            if (entrada.getEjemplo() != null && !entrada.getEjemplo().isEmpty()) {
                tvEjemplo.setText(entrada.getEjemplo());
                tvEjemplo.setVisibility(View.VISIBLE);
            } else {
                tvEjemplo.setVisibility(View.GONE);
            }

            // Mostrar referencias si existen
            if (entrada.getReferencias() != null && !entrada.getReferencias().isEmpty()) {
                tvReferencias.setText("Cf. " + entrada.getReferencias());
                tvReferencias.setVisibility(View.VISIBLE);
            } else {
                tvReferencias.setVisibility(View.GONE);
            }
        }
    }
}
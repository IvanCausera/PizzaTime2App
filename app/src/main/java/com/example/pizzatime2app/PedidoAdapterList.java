package com.example.pizzatime2app;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class PedidoAdapterList extends RecyclerView.Adapter<PedidoAdapterList.ListViewHolder> {

    public ArrayList<Pedido> listPizzas;
    public ArrayList<Pedido> listBebidas = null;
    public View.OnClickListener onClickListener;

    public PedidoAdapterList(ArrayList<Pedido> list) {
        this.listPizzas = list;
    }
    public PedidoAdapterList(ArrayList<Pedido> listPizzas, ArrayList<Pedido> listBebidas) {
        this.listPizzas = listPizzas; this.listBebidas=listBebidas;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_element, parent, false);
        view.setOnClickListener(this.onClickListener);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Pedido pedidoPizza = listPizzas.get(position);
        holder.name.setText(pedidoPizza.getName());
        holder.price.setText(pedidoPizza.getQuantity() + " -- " + pedidoPizza.getPrice());

        if (listBebidas.get(position) != null){
            Pedido pedidoBebida = listBebidas.get(position);
            holder.nameBebida.setVisibility(View.VISIBLE);
            holder.nameBebida.setText(pedidoBebida.getName());
            holder.priceBebida.setVisibility(View.VISIBLE);
            holder.priceBebida.setText(pedidoBebida.getQuantity() + " -- " + pedidoBebida.getPrice());
        }
    }
    @Override
    public int getItemCount() {
        if (listPizzas == null) return 0;
        else return listPizzas.size();
    }

    public void removeItem(int position){
        listPizzas.remove(position);
        listBebidas.remove(position);
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView price;

        public TextView nameBebida;
        public TextView priceBebida;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName_listE);
            price = itemView.findViewById(R.id.txtPrice_listE);
            nameBebida = itemView.findViewById(R.id.txtBebidaName_listE);
            priceBebida = itemView.findViewById(R.id.txtBebidaPrice_listE);
        }
    }
}

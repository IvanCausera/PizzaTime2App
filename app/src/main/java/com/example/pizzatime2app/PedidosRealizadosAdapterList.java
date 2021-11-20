package com.example.pizzatime2app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzatime2app.modelo.PedidoItem;
import com.example.pizzatime2app.modelo.PedidosRealizados;
import com.example.pizzatime2app.modelo.PedidosUsuario;
import com.example.pizzatime2app.modelo.PizzaBebida;

import java.util.ArrayList;

public class PedidosRealizadosAdapterList extends RecyclerView.Adapter<PedidosRealizadosAdapterList.ListViewHolder> {

    private Context context;
    public ArrayList<PedidosRealizados> list;

    public PedidosRealizadosAdapterList(ArrayList<PedidosRealizados> list){ this.list = list; }

    public PedidosRealizadosAdapterList(ArrayList<PedidosRealizados> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_pedidosusuarios, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        PedidosRealizados pedidoRealizado = list.get(position);
        String textPizza = "";
        String textBebida = "";
        holder.id.setText( context.getString(R.string.pedido) + ": " +  (pedidoRealizado.getId()+1));
        for (PedidoItem item: pedidoRealizado.getPizzas()) {
            textPizza += item.getNombre() + ", " + item.getCantidad() + "\n";
        }

        if (!pedidoRealizado.getBebidas().isEmpty()){
            holder.bebidas.setVisibility(View.VISIBLE);
            for (PedidoItem item: pedidoRealizado.getBebidas()) {
                textBebida += item.getNombre() + ", " + item.getCantidad() + "\n";
            }
        }

        holder.pizzas.setText(textPizza);
        holder.bebidas.setText(textBebida);
        holder.precioTotal.setText(context.getString(R.string.precio_total) + ": " +  pedidoRealizado.getPrecioTotal());

    }
    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else return list.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView pizzas;
        public TextView bebidas;
        public TextView precioTotal;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtNumero_listUsu);
            pizzas = itemView.findViewById(R.id.txtPizza_listUsu);
            bebidas = itemView.findViewById(R.id.txtBebida_listUsu);
            precioTotal = itemView.findViewById(R.id.txtPrecio_listUsu);
        }
    }
}

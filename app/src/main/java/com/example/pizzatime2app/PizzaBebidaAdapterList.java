package com.example.pizzatime2app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzatime2app.modelo.PizzaBebida;

import java.util.ArrayList;

public class PizzaBebidaAdapterList extends RecyclerView.Adapter<PizzaBebidaAdapterList.ListViewHolder> {

    private Context context;
    public ArrayList<PizzaBebida> list;
    public View.OnClickListener onClickListener;

    public PizzaBebidaAdapterList(Context context){
        this.context =  context;
        list = new ArrayList<>();
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
        PizzaBebida pedidoPizza = list.get(position);
        holder.name.setText(pedidoPizza.getNombre());
        holder.price.setText(String.valueOf(pedidoPizza.getPrecio()));
    }
    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else return list.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void changeList(ArrayList<PizzaBebida> list){
        this.list = null;
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addToList(ArrayList<PizzaBebida> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addToList(PizzaBebida pizzaBebida){
        this.list.add(pizzaBebida);
        notifyDataSetChanged();
    }

    public void updateItem(int pos, PizzaBebida pizzaBebida){
        this.list.set(pos, pizzaBebida);
        notifyItemChanged(pos);
    }

    public void deleteItem(int pos){
        this.list.remove(pos);
        notifyDataSetChanged();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView price;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName_listE);
            price = itemView.findViewById(R.id.txtName_listE);
        }
    }
}

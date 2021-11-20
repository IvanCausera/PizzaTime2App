package com.example.pizzatime2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pizzatime2app.modelo.PedidosUsuario;

public class HistoryActivity extends AppCompatActivity {

    private PedidosUsuario pedidosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){ //From OrderSummary
            Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);
            pedidosUsuario = bundle.getParcelable(MainActivity.HISTORY);
        }

        RecyclerView recHistory = findViewById(R.id.recViewHistory);
        recHistory.setHasFixedSize(true);

        LinearLayoutManager linearLayoutRecPizzas = new LinearLayoutManager(HistoryActivity.this);
        linearLayoutRecPizzas.setOrientation(linearLayoutRecPizzas.VERTICAL);
        recHistory.setLayoutManager(linearLayoutRecPizzas);

        PedidosRealizadosAdapterList adapterPizza = new PedidosRealizadosAdapterList(pedidosUsuario.getPedidoRealizado(), this);

        recHistory.setAdapter(adapterPizza);


    }
}
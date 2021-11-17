package com.example.pizzatime2app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderSummary extends BaseActivity {

    private ArrayList<Pedido> pizzasOrder;
    private ArrayList<Pedido> bebidasOrder;
    private Client client;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){

            Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);
            client = bundle.getParcelable(MainActivity.CLIENT);
            pizzasOrder = bundle.getParcelableArrayList(MainActivity.PIZZA);
            bebidasOrder = bundle.getParcelableArrayList(MainActivity.BEBIDA);

            RecyclerView recViewSummary = findViewById(R.id.recViewPaymentSummary);

            TextView txtCost = findViewById(R.id.txtCoste_ActOrder);
            Button btnAccept = findViewById(R.id.btnAccept_ActOrder);

            recViewSummary.setHasFixedSize(true);
            recViewSummary.addItemDecoration(new DividerItemDecoration(OrderSummary.this, DividerItemDecoration.VERTICAL));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderSummary.this);
            linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            recViewSummary.setLayoutManager(linearLayoutManager);

            PedidoAdapterList pedidoAdapterList = new PedidoAdapterList(pizzasOrder, bebidasOrder);
            recViewSummary.setAdapter(pedidoAdapterList);
            pedidoAdapterList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recViewSummary.getChildAdapterPosition(view);
                    Pedido pizza = pizzasOrder.get(position);
                    Pedido bebida = bebidasOrder.get(position);

                    pedidoAdapterList.removeItem(position);

                    double reduceCost = (pizza.getQuantity() * pizza.getPrice());
                    if (bebida != null) reduceCost += (bebida.getQuantity() * bebida.getPrice());
                    client.setCost(client.getCost() - reduceCost);

                    txtCost.setText(getString(R.string.coste) + ": " + client.getCost());
                }
            });

            txtCost.setText(getString(R.string.coste) + ": " + client.getCost());

            /**
             * Starts next activity.
             * Send data to the next activity
             */
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pizzasOrder.size() == 0) Toast.makeText(OrderSummary.this, getString(R.string.error_necesario_un_pedido), Toast.LENGTH_LONG).show();
                    else{
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(MainActivity.CLIENT, client);
                        bundle.putParcelableArrayList(MainActivity.PIZZA, pizzasOrder);
                        bundle.putParcelableArrayList(MainActivity.BEBIDA, bebidasOrder);

                        Intent intentPayment = new Intent(OrderSummary.this, PaymentForm.class);
                        intentPayment.putExtra(Intent.EXTRA_TEXT, bundle);
                        startActivity(intentPayment);
                    }
                }
            });
        }

        Button btnBuyMore = findViewById(R.id.btnCancel_ActOrder);

        /**
         * Starts the previous activity
         */
        btnBuyMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.CLIENT, client);
                bundle.putParcelableArrayList(MainActivity.PIZZA, pizzasOrder);
                bundle.putParcelableArrayList(MainActivity.BEBIDA, bebidasOrder);

                Intent intent = new Intent(OrderSummary.this, MainActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, bundle);
                startActivity(intent);
            }
        });

    } //End OnCreate
}
package com.example.pizzatime2app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentSummary extends BaseActivity {
    static protected final String ACCEPT = "Accept";
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
        setContentView(R.layout.activity_payment_summary);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);
            client = bundle.getParcelable(MainActivity.CLIENT);
            pizzasOrder = bundle.getParcelableArrayList(MainActivity.PIZZA);
            bebidasOrder = bundle.getParcelableArrayList(MainActivity.BEBIDA);

            TextView txtNombre = findViewById(R.id.txtNombre_ActPaymentSummary);
            TextView txtCard = findViewById(R.id.txtCard);
            TextView txtCoste = findViewById(R.id.txtCoste_ActPaymentSummary);


            Button btnAccept = findViewById(R.id.btnAccept_ActPaymentSummary);
            Button btnEnviar = findViewById(R.id.btnEnviar);

            txtNombre.setText(getString(R.string.nombre) + ": " + client.getName());
            txtCard.setText(getString(R.string.tarjeta) + ": " + client.getCardType() + " - " + client.getCard());

            txtCoste.setText(getString(R.string.coste) + ": " + client.getCost() + "€");

            RecyclerView recViewPaymentSummary = findViewById(R.id.recViewPaymentSummary);

            recViewPaymentSummary.setHasFixedSize(true);
            recViewPaymentSummary.addItemDecoration(new DividerItemDecoration(PaymentSummary.this, DividerItemDecoration.VERTICAL));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PaymentSummary.this);
            linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            recViewPaymentSummary.setLayoutManager(linearLayoutManager);

            PedidoAdapterList pedidoAdapterList = new PedidoAdapterList(pizzasOrder, bebidasOrder);
            recViewPaymentSummary.setAdapter(pedidoAdapterList);

            /**
             * Start next activity.
             * Send data to the next activity.
             */
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPaymentSummary = new Intent(PaymentSummary.this, MainActivity.class);
                    intentPaymentSummary.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.pago_realizado));
                    startActivity(intentPaymentSummary);
                }
            });

            /**
             * If it has an app to send an email, it sends an email with all the information.
             */
            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = txtNombre.getText().toString() + "\n" +
                            txtCard.getText().toString() + "\n" +
                            txtCoste.getText().toString() + "\n";

                    for (int i = 0; i < pizzasOrder.size(); i++){
                        message += pizzasOrder.get(i).getName() + " -- " + pizzasOrder.get(i).getQuantity() + "\n";
                        if (bebidasOrder.get(i) != null) message += "\t" + bebidasOrder.get(i).getName() + " -- " + bebidasOrder.get(i).getQuantity() + "\n";
                    }
                    Log.i("AAA", message);
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
                }
            });

        }
    }
}
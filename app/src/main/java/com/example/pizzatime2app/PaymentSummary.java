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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzatime2app.conexion.ApiPizzaTime;
import com.example.pizzatime2app.conexion.ClientPizzaTime;
import com.example.pizzatime2app.modelo.PedidoItem;
import com.example.pizzatime2app.modelo.PedidosRealizados;
import com.example.pizzatime2app.modelo.PedidosUsuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentSummary extends BaseActivity {
    static protected final String ACCEPT = "Accept";
    private ArrayList<Pedido> pizzasOrder;
    private ArrayList<Pedido> bebidasOrder;
    private Client client;

    private Retrofit retrofit;

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

            PedidoAdapterList pizzaBebidaAdapterList = new PedidoAdapterList(pizzasOrder, bebidasOrder);
            recViewPaymentSummary.setAdapter(pizzaBebidaAdapterList);

            /**
             * Start next activity.
             * Send data to the next activity.
             */
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertarPedidoUsuario(client.getPedidoUsuario());
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
                    insertarPedidoUsuario(client.getPedidoUsuario());
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

        retrofit = ClientPizzaTime.getCliente();

    } // End onCreate

    private void insertarPedidoUsuario(PedidosUsuario pu){
        ApiPizzaTime api = retrofit.create(ApiPizzaTime.class);

        ArrayList<PedidoItem> pizzas = new ArrayList<>();
        for (Pedido pedido:
             pizzasOrder) {
            pizzas.add(pedido.toPedidoItem());
        }

        ArrayList<PedidoItem> bebidas = new ArrayList<>();
        for (Pedido bebida:
                bebidasOrder) {
            if (bebida != null) bebidas.add(bebida.toPedidoItem());
        }

        int lastid = -1;
        ArrayList<PedidosRealizados> pedidosRealizados = pu.getPedidoRealizado();
        if (!pedidosRealizados.isEmpty())
            lastid = pedidosRealizados.get(pedidosRealizados.size() - 1).getId();


        pu.addPedido(new PedidosRealizados((lastid+1), pizzas, bebidas, client.getCost()));

        Call<PedidosUsuario> callPedido = api.putPedidosUsuario(pu);

        callPedido.enqueue(new Callback<PedidosUsuario>() {
            @Override
            public void onResponse(Call<PedidosUsuario> call, Response<PedidosUsuario> response) {
                if (!response.isSuccessful()){

                    Toast.makeText(getApplicationContext(), (response.code() + ": " + response.message()), Toast.LENGTH_LONG).show();
                    Log.e("CallPayment", response.code() + ": " + response.message());
                }
            }
            @Override
            public void onFailure(Call<PedidosUsuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo en la respuesta", Toast.LENGTH_LONG).show();
                Log.e("CallPayment", t.getMessage());
            }
        });
    }
}
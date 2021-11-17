package com.example.pizzatime2app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzatime2app.conexion.ApiPizzaTime;
import com.example.pizzatime2app.conexion.ClientPizzaTime;
import com.example.pizzatime2app.modelo.PizzaBebida;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends com.example.pizzatime2app.BaseActivity {
    static protected  final String VERSION = "1.0";
    static protected final String CLIENT = "Client";
    static protected final String PIZZA = "Pizza";
    static protected final String BEBIDA = "Bebida";

    private int qPizza = 0;
    private int qBebida = 0;
    private double cost = 0;
    private boolean pizza = false;
    private boolean bebida = false;
    private ArrayList<PizzaBebida> pizzasOrder = new ArrayList<>();
    private ArrayList<PizzaBebida> bebidasOrder = new ArrayList<>();
    private PizzaBebida pedidoPizza;
    private PizzaBebida pedidoBebida;
    private Client client;
    private double pricePizza = 0;
    private double priceBebida = 0;

    private Retrofit retrofit;
    private ArrayList<PizzaBebida> pizzas = new ArrayList<>();
    private ArrayList<PizzaBebida> bebidas = new ArrayList<>();
    private ApiPizzaTime api;

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
        setContentView(R.layout.activity_main);

        EditText editTNombre = findViewById(R.id.editTNombre);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){ //From OrderSummary
            Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);
            client = bundle.getParcelable(MainActivity.CLIENT);
            pizzasOrder = bundle.getParcelableArrayList(MainActivity.PIZZA);
            bebidasOrder = bundle.getParcelableArrayList(MainActivity.BEBIDA);
            editTNombre.setText(client.getName());
        }
        if (intent != null && intent.hasExtra(Intent.EXTRA_SUBJECT)){//From PaymentSummary
            Toast.makeText(MainActivity.this, intent.getStringExtra(Intent.EXTRA_SUBJECT), Toast.LENGTH_SHORT).show();
        }

        RecyclerView recPizzas = findViewById(R.id.recViewPizzas);
        RecyclerView recBebidas = findViewById(R.id.recViewBebidas);

        TextView txtPizza = findViewById(R.id.txtPizza);
        TextView txtBebida = findViewById(R.id.txtBebida);

        TextView txtName = findViewById(R.id.txtNameListE);
        TextView txtPrice = findViewById(R.id.txtPriceListE);
        Button btnCancelarPizza = findViewById(R.id.btnCancelPizza);
        Button btnMenosPizza = findViewById(R.id.btnMenosPizza);
        TextView txtCantidadPizza = findViewById(R.id.txtCantidadPizza);
        Button btnMasPizza = findViewById(R.id.btnMasPizza);

        TextView txtNameBebida = findViewById(R.id.txtNameBebidaListE);
        TextView txtPriceBebida = findViewById(R.id.txtPriceBebidaListE);
        Button btnCancelBebida = findViewById(R.id.btnCancelBebida);
        Button btnMenosBebida = findViewById(R.id.btnMenosBebida);
        TextView txtCantidadBebida = findViewById(R.id.txtCantidadBebida);
        Button btnMasBebida = findViewById(R.id.btnMasBebida);

        Button btnAccept = findViewById(R.id.btnAceept_ActMain);
        TextView txtCoste = findViewById(R.id.txtCoste_ActMain);
        txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");

        recPizzas.setHasFixedSize(true);
        recBebidas.setHasFixedSize(true);


        recPizzas.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        recBebidas.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        PedidoAdapterList adapterPizza = new PedidoAdapterList(this);

        adapterPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidoPizza = pizzas.get(recPizzas.getChildAdapterPosition(view));
                pricePizza = pedidoPizza.getPrecio();
                pizza = true;
                recPizzas.setVisibility(View.GONE);
                txtPizza.setVisibility(View.GONE);

                txtName.setVisibility(View.VISIBLE);
                txtPrice.setVisibility(View.VISIBLE);
                btnCancelarPizza.setVisibility(View.VISIBLE);
                btnMenosPizza.setVisibility(View.VISIBLE);
                txtCantidadPizza.setVisibility(View.VISIBLE);
                btnMasPizza.setVisibility(View.VISIBLE);

                txtName.setText(pedidoPizza.getNombre());
                txtPrice.setText(String.valueOf(pedidoPizza.getPrecio()));

                qPizza = 1;
                txtCantidadPizza.setText(String.valueOf(qPizza));
                cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                cost = Math.round(cost * 100) / 100.0;
                txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");

            }
        });

        PedidoAdapterList adapterBebida = new PedidoAdapterList(this);
        adapterBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidoBebida = bebidas.get(recBebidas.getChildAdapterPosition(view));
                priceBebida = pedidoBebida.getPrecio();
                bebida = true;
                recBebidas.setVisibility(View.GONE);
                txtBebida.setVisibility(View.GONE);

                txtNameBebida.setVisibility(View.VISIBLE);
                txtPriceBebida.setVisibility(View.VISIBLE);
                btnCancelBebida.setVisibility(View.VISIBLE);
                btnMenosBebida.setVisibility(View.VISIBLE);
                txtCantidadBebida.setVisibility(View.VISIBLE);
                btnMasBebida.setVisibility(View.VISIBLE);

                txtNameBebida.setText(pedidoBebida.getNombre());
                txtPriceBebida.setText(String.valueOf(pedidoBebida.getPrecio()));

                qBebida = 1;
                txtCantidadBebida.setText(String.valueOf(qBebida));
                cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                cost = Math.round(cost * 100) / 100.0;
                txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");
            }
        });

        recPizzas.setAdapter(adapterPizza);
        recBebidas.setAdapter(adapterBebida);

        btnCancelarPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pizza = false;
                recPizzas.setVisibility(View.VISIBLE);
                txtPizza.setVisibility(View.VISIBLE);

                txtName.setVisibility(View.GONE);
                txtPrice.setVisibility(View.GONE);
                btnCancelarPizza.setVisibility(View.GONE);
                btnMenosPizza.setVisibility(View.GONE);
                txtCantidadPizza.setVisibility(View.GONE);
                btnMasPizza.setVisibility(View.GONE);

                qPizza = 0;
                cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                cost = Math.round(cost * 100) / 100.0;
                txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");
            }
        });

        btnCancelBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bebida = false;
                recBebidas.setVisibility(View.VISIBLE);
                txtBebida.setVisibility(View.VISIBLE);

                txtNameBebida.setVisibility(View.GONE);
                txtPriceBebida.setVisibility(View.GONE);
                btnCancelBebida.setVisibility(View.GONE);
                btnMenosBebida.setVisibility(View.GONE);
                txtCantidadBebida.setVisibility(View.GONE);
                btnMasBebida.setVisibility(View.GONE);

                qBebida = 0;
                cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                cost = Math.round(cost * 100) / 100.0;
                txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");
            }
        });

        // OnCLickListeners to add or remove portions of pizza
        btnMasPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qPizza++;
                txtCantidadPizza.setText(String.valueOf(qPizza));
                cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                cost = Math.round(cost * 100) / 100.0;
                txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");
            }
        });
        btnMenosPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qPizza > 1){
                    qPizza --;
                    txtCantidadPizza.setText(String.valueOf(qPizza));
                    cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                    cost = Math.round(cost * 100) / 100.0;
                    txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");
                }
            }
        });

        // OnCLickListeners to add or remove drinks
        btnMasBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qBebida ++;
                txtCantidadBebida.setText(String.valueOf(qBebida));
                cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                cost = Math.round(cost * 100) / 100.0;
                txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");
            }
        });
        btnMenosBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qBebida > 0){
                    qBebida--;
                    txtCantidadBebida.setText(String.valueOf(qBebida));
                    cost = (qPizza * pricePizza)+(qBebida * priceBebida);
                    cost = Math.round(cost * 100) / 100.0;
                    txtCoste.setText(getString(R.string.coste) +": "+ cost + "€");
                }
            }
        });


        // Send data to the next activity
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTNombre.getText().toString();
                boolean error = false;

                if (name.isEmpty()){
                    error = true;
                    editTNombre.setError(getString(R.string.error_establecer_nombre));
                }
                if (qPizza == 0){
                    error = true;
                    Toast.makeText(MainActivity.this, getString(R.string.error_selecionar_pizza), Toast.LENGTH_LONG).show();
                }

                if (!error){
                    if (qBebida == 0) pedidoBebida = null;
                    else pedidoBebida.setQuantity(qBebida);
                    pedidoPizza.setQuantity(qPizza);

                    pizzasOrder.add(pedidoPizza);
                    bebidasOrder.add(pedidoBebida);

                    double clientCost = 0;
                    if (client != null) clientCost = client.getCost();

                    client = new Client(name, (clientCost + cost));
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CLIENT, client);
                    bundle.putParcelableArrayList(PIZZA, pizzasOrder);
                    bundle.putParcelableArrayList(BEBIDA, bebidasOrder);

                    Intent intent = new Intent(MainActivity.this, OrderSummary.class);
                    intent.putExtra(Intent.EXTRA_TEXT, bundle);
                    startActivity(intent);
                }
            }
        });

        retrofit = ClientPizzaTime.getCliente();


    } // End onCreate

    private void getDatos(){
        api = retrofit.create(ApiPizzaTime.class);
        Call<ArrayList<PizzaBebida>> respuesta = api.getPizzaBebida();
        respuesta.enqueue(new Callback<ArrayList<PizzaBebida>>() {
            @Override
            public void onResponse(Call<ArrayList<PizzaBebida>> call, Response<ArrayList<PizzaBebida>> response) {
                ArrayList<PizzaBebida> listPizzaBebida = response.body();
                for (int i = 0; i < listPizzaBebida.size(); i++){
                    PizzaBebida pb = listPizzaBebida.get(i);
                    if (pb.getTipo() == 0) pizzas.add(pb);
                    else bebidas.add(pb);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PizzaBebida>> call, Throwable t) {

            }
        });
    }

}
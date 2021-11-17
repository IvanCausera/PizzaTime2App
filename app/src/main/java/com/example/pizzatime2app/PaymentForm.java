package com.example.pizzatime2app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.Calendar;

public class PaymentForm extends com.example.pizzatime2app.BaseActivity {

    private static String cardSelected = "";
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
        setContentView(R.layout.activity_payment_form);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            Bundle bundle = intent.getBundleExtra(Intent.EXTRA_TEXT);
            client = bundle.getParcelable(MainActivity.CLIENT);
            pizzasOrder = bundle.getParcelableArrayList(MainActivity.PIZZA);
            bebidasOrder = bundle.getParcelableArrayList(MainActivity.BEBIDA);

            EditText etMonth = findViewById(R.id.editTMonth);
            EditText etYear = findViewById(R.id.editTYear);
            EditText etCard = findViewById(R.id.editTCardNum);

            TextView txtNombre = findViewById(R.id.txtNombre);
            TextView txtCost = findViewById(R.id.txtCoste_ActPaymentForm);
            Button btnAccept = findViewById(R.id.btnAccept_ActPaymentForm);
            ImageView imgCard = findViewById(R.id.imageCard);
            Spinner spinner = findViewById(R.id.spinnerCard);

            txtNombre.setText(getString(R.string.nombre)+": " + client.getName());
            txtCost.setText(getString(R.string.coste)+ ": " + client.getCost());

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tarjetas, android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            /**
             * Save the Item selected and change the image related.
             */
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    cardSelected = adapterView.getSelectedItem().toString();
                    switch (adapterView.getSelectedItemPosition()){
                        case 0: imgCard.setImageDrawable(AppCompatResources.getDrawable(PaymentForm.this, R.drawable.visa_logo));break;
                        case 1: imgCard.setImageDrawable(AppCompatResources.getDrawable(PaymentForm.this, R.drawable.mastercard_logo)); break;
                        case 2: imgCard.setImageDrawable(AppCompatResources.getDrawable(PaymentForm.this, R.drawable.euro6000_logo)); break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            /**
             * Start next activity.
             * Send data to the next activity.
             * Check that required fields are selected.
             */
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean error = false;

                    if (etCard.getText().toString().isEmpty()){
                        etCard.requestFocus();
                        etCard.setError(getString(R.string.error_sin_num_card));
                        error = true;
                    }
                    if (etMonth.getText().toString().isEmpty()){
                        etMonth.requestFocus();
                        etMonth.setError(getString(R.string.error_sin_fecha_caducidad));
                        error = true;
                    }
                    if (etYear.getText().toString().isEmpty()){
                        etYear.requestFocus();
                        etYear.setError(getString(R.string.error_sin_fecha_caducidad));
                    } else {
                        int month = Integer.parseInt(etMonth.getText().toString());
                        int year = Integer.parseInt(etYear.getText().toString());

                        Calendar calExpirationDate = Calendar.getInstance();
                        calExpirationDate.set(year, month, 1);
                        Calendar calActual = Calendar.getInstance();

                        if (!Client.isValidDate(1, month, year)){
                            etMonth.requestFocus();
                            etMonth.setError(getString(R.string.error_fecha_incorrecta));
                            etYear.setError(getString(R.string.error_fecha_incorrecta));
                        } else {
                            if (Client.isExpired(calExpirationDate, calActual)){
                                etMonth.requestFocus();
                                etMonth.setError(getString(R.string.error_card_caducada));
                                etYear.setError(getString(R.string.error_card_caducada));
                                error = true;
                            }

                            if (!error){
                                client.setCard(cardSelected, etCard.getText().toString(), (month + "/" + year));
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(MainActivity.CLIENT, client);
                                bundle.putParcelableArrayList(MainActivity.PIZZA, pizzasOrder);
                                bundle.putParcelableArrayList(MainActivity.BEBIDA, bebidasOrder);
                                Intent intentPaymentSummary = new Intent(PaymentForm.this, PaymentSummary.class);
                                intentPaymentSummary.putExtra(Intent.EXTRA_TEXT, bundle);
                                startActivity(intentPaymentSummary);
                            }
                        }
                    }
                }
            });
        }
    } // End onCreate
}
package com.example.pizzatime2app.conexion;

import com.example.pizzatime2app.modelo.PedidoItem;
import com.example.pizzatime2app.modelo.PedidosRealizados;
import com.example.pizzatime2app.modelo.PedidosUsuario;
import com.example.pizzatime2app.modelo.PizzaBebida;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiPizzaTime {
    @GET("pizzaBebida")
    Call<ArrayList<PizzaBebida>> getPizzaBebida();

    @GET("pedidosUsuario")
    Call<PedidosUsuario> getPedidosUsuario();

    @PUT("pizzaBebida/{id}")
    Call<PizzaBebida> putPizzaBebida(
            @Path("id") int id,
            @Body PizzaBebida pizzaBebida
    );

    @PUT("pedidosUsuario")
    Call<PedidosUsuario> putPedidosUsuario(
            @Body PedidosUsuario pedidosUsuario
    );
}

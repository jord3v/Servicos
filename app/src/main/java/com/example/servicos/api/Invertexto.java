package com.example.servicos.api;
import com.example.servicos.model.Address;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Invertexto {
    @GET("v1/cep/{zipcode}")
    Call<Address> getAddress(
            @Path("zipcode") String zipcode,
            @Query("token") String token
    );


}

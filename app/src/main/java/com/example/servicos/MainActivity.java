package com.example.servicos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicos.api.Invertexto;
import com.example.servicos.model.Address;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Variables
        EditText inputCep = findViewById(R.id.inputCep);
        Button btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroCep = inputCep.getText().toString();
                search(numeroCep);
            }
        });


    }

    private void search(String zipcode){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Invertexto invertexto = retrofit.create(Invertexto.class);
        Call<Address> call = invertexto.getAddress(
                zipcode, Constants.TOKEN
        );

        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if(response.isSuccessful()){
                    Address address = response.body();
                    TextView textInfo = findViewById(R.id.textInfo);
                    textInfo.setText(address.formatter());
                }else{
                    Toast.makeText(MainActivity.this,
                            "Erro ao buscar informações, verifique o CEP",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable throwable) {
                Toast.makeText(MainActivity.this,
                        "Verifique sua conexão com a internet",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
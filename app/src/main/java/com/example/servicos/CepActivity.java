package com.example.servicos;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicos.api.Invertexto;
import com.example.servicos.model.Address;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        // Adiciona o botão de voltar na ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Create Variables
        EditText inputCep = findViewById(R.id.inputCep);
        Button btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        contentLayout = findViewById(R.id.contentLayout);

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            finish();
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numeroCep = inputCep.getText().toString();
                search(numeroCep);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Trata o clique no botão de voltar
        if (item.getItemId() == android.R.id.home) {
            finish(); // Fecha a activity atual e volta para a anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            contentLayout.setVisibility(View.VISIBLE);
        }
    }

    private void search(String zipcode) {
        showLoading(true); // Mostra o loading

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
                showLoading(false); // Esconde o loading
                if (response.isSuccessful()) {
                    Address address = response.body();
                    TextView textInfo = findViewById(R.id.textInfo);
                    textInfo.setText(address.formatter());
                } else {
                    Toast.makeText(CepActivity.this,
                            "Erro ao buscar informações, verifique o CEP",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable throwable) {
                showLoading(false); // Esconde o loading
                Toast.makeText(CepActivity.this,
                        "Verifique sua conexão com a internet",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

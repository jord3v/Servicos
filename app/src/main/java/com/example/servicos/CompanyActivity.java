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
import com.example.servicos.model.Company;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private LinearLayout contentLayout;
    private EditText inputCnpj;
    private Button btnSearchCnpj;
    private TextView textInfoCnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Consultar CNPJ");
        }

        progressBar = findViewById(R.id.progressBarCnpj);
        contentLayout = findViewById(R.id.contentLayout);
        inputCnpj = findViewById(R.id.inputCnpj);
        btnSearchCnpj = findViewById(R.id.btnSearchCnpj);
        textInfoCnpj = findViewById(R.id.textInfoCnpj);

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v -> {
            finish();
        });

        btnSearchCnpj.setOnClickListener(v -> {
            String cnpj = inputCnpj.getText().toString();
            if (!cnpj.isEmpty()) {
                searchCompany(cnpj);
            } else {
                Toast.makeText(CompanyActivity.this, "Por favor, insira um CNPJ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        contentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void searchCompany(String cnpj) {
        showLoading(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Invertexto invertexto = retrofit.create(Invertexto.class);
        Call<Company> call = invertexto.getCompany(cnpj, Constants.TOKEN);

        call.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    Company companyData = response.body();
                    textInfoCnpj.setText(companyData.formatter());
                } else {
                    Toast.makeText(CompanyActivity.this, "Erro ao buscar CNPJ. Verifique o número.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                showLoading(false);
                Toast.makeText(CompanyActivity.this, "Falha na conexão. Verifique sua internet.", Toast.LENGTH_LONG).show();
            }
        });
    }
}

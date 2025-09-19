package com.example.servicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoToCep = findViewById(R.id.btnGoToCep);
        Button btnGoToCnpj = findViewById(R.id.btnGoToCnpj);

        btnGoToCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CepActivity.class);
                startActivity(intent);
            }
        });

        btnGoToCnpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompanyActivity.class);
                startActivity(intent);
            }
        });
    }
}
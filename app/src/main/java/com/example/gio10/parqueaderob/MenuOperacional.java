package com.example.gio10.parqueaderob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuOperacional extends AppCompatActivity {
    Button ingresar, listar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_operacional);

        ingresar = findViewById(R.id.btnIngresarV);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOperacional.this,Insertar.class);
                startActivity(i);
            }
        });

        listar = findViewById(R.id.btnListarTodosV);
        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuOperacional.this,ListarTodos.class);
                startActivity(i);
            }
        });
    }
}

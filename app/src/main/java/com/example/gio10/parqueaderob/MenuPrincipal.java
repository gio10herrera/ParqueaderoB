package com.example.gio10.parqueaderob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity {
    Button about, appParqueadero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        about = findViewById(R.id.btnAcercaDe);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipal.this,AcercaDe.class);
                startActivity(i);
            }
        });

        appParqueadero = findViewById(R.id.btnAplicacion);
        appParqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MenuPrincipal.this,MenuOperacional.class);
                startActivity(it);
            }
        });
    }
}

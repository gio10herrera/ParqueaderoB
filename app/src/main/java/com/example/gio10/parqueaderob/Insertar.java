package com.example.gio10.parqueaderob;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gio10.parqueaderob.model.DaoVehiculo;
import com.example.gio10.parqueaderob.model.VehiculoDbHelper;

public class Insertar extends AppCompatActivity {
    Spinner horas, minutos;
    Button guardar;
    EditText placa;
    VehiculoDbHelper basedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        horas = findViewById(R.id.spinnerHora);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayHoras, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        horas.setAdapter(adapter);

        minutos = findViewById(R.id.spinnerMinutos);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMinutos = ArrayAdapter.createFromResource(this,
                R.array.arrayMinutos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMinutos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        minutos.setAdapter(adapterMinutos);

        guardar = findViewById(R.id.btnIngresarV2);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
                limpiarEditText();
                finish();
            }
        });
    }

    public void limpiarEditText(){
        placa.setText("");
    }

    public void guardar(){
        abrir();
        try {
            SQLiteDatabase sqldata = basedatos.getWritableDatabase();
            ContentValues values = new ContentValues();
            placa = (EditText) findViewById(R.id.txtLaPlaca);


            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA, placa.getText().toString());
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_HORAENTRADA, Integer.parseInt(horas.getSelectedItem().toString()));
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_MINENTRADA, Integer.parseInt(minutos.getSelectedItem().toString()));
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_HORASALIDA, -1);
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_MINSALIDA, -1);
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_TOTALPAGAR, 0);
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_ISOUT, 0);
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_CADENTRADA, horas.getSelectedItem().toString() + ":" + minutos.getSelectedItem().toString() + " horas");
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_CADSALIDA, "PARQUEADO");

            long newRowId = sqldata.insert(DaoVehiculo.VehiculoEntry.TABLE_NAME, null, values);
            Toast.makeText(this,"INSERCIÔN EXITOSA", Toast.LENGTH_LONG).show();
            close();
        }
        catch (Exception e){
            Toast.makeText(this,"Error al insertar Datos", Toast.LENGTH_LONG).show();
        }
    }

    public void abrir(){
        try {
            basedatos = new VehiculoDbHelper(this);
        }
        catch(Exception e){
            Toast.makeText(this,"Error al crear base datos", Toast.LENGTH_LONG).show();
        }
    }

    public void close(){
        try {
            basedatos.close();
        }
        catch(Exception e){
            Toast.makeText(this,"Error al cerrar base datos", Toast.LENGTH_LONG).show();
        }
    }
}

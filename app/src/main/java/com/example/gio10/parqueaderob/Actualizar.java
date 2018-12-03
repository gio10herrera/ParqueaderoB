package com.example.gio10.parqueaderob;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.gio10.parqueaderob.model.Vehiculo;
import com.example.gio10.parqueaderob.model.VehiculoDbHelper;

public class Actualizar extends AppCompatActivity {
    VehiculoDbHelper basedatos;
    EditText thePlaca;
    Button updte;
    Spinner hours,minuts;
    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        hours = findViewById(R.id.spinnerHora2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayHoras, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        hours.setAdapter(adapter);

        minuts = findViewById(R.id.spinnerMinutos2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMinutos = ArrayAdapter.createFromResource(this,
                R.array.arrayMinutos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMinutos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        minuts.setAdapter(adapterMinutos);

        thePlaca = findViewById(R.id.txtLaPlaca2);

        updte = findViewById(R.id.btnActualizarV2);
        updte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = getVehiculoData();
                actualizar();
                finish();

            }
        });
    }

    public void actualizar(){
        abrir();
        cur.moveToFirst();
        if(cur.getCount()==0){
            Toast.makeText(this,"Esta placa no esta registrada en la base de datos", Toast.LENGTH_LONG).show();
        }
        else{

            try {
                SQLiteDatabase sqldata = basedatos.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_HORAENTRADA, Integer.parseInt(hours.getSelectedItem().toString()));
                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_MINENTRADA, Integer.parseInt(minuts.getSelectedItem().toString()));
                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_ISOUT, 0);
                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_HORASALIDA, -1);
                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_MINSALIDA, -1);
                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_TOTALPAGAR, 0);
                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_CADENTRADA, hours.getSelectedItem().toString() + ":" + minuts.getSelectedItem().toString() + " horas");
                values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_CADSALIDA, "PARQUEADO");


                sqldata.update(DaoVehiculo.VehiculoEntry.TABLE_NAME,values,DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA+"=?",new String[]{thePlaca.getText().toString()});
                Toast.makeText(this,"ACTUALIZACION EXITOSA", Toast.LENGTH_LONG).show();

            }
            catch (Exception e){
                Toast.makeText(this,"Error al actualizar Datos", Toast.LENGTH_LONG).show();
            }

        }
        close();
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

    public Cursor getVehiculoData(){
        String sqlString = "SELECT * FROM " + DaoVehiculo.VehiculoEntry.TABLE_NAME + " WHERE " +
                DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA + "='" + thePlaca.getText().toString() + "'";
        abrir();
        SQLiteDatabase sqldata = basedatos.getReadableDatabase();
        Cursor c = sqldata.rawQuery(sqlString, null);
        return c;
    }
}

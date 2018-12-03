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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gio10.parqueaderob.model.DaoVehiculo;
import com.example.gio10.parqueaderob.model.Vehiculo;
import com.example.gio10.parqueaderob.model.VehiculoDbHelper;

import java.util.ArrayList;

public class Salida extends AppCompatActivity {
    Spinner lasHoras, losMinutos;
    VehiculoDbHelper basedatos;
    EditText plac;
    Cursor cur;
    Button salid;
    Vehiculo v;
    TextView pagar;
    //SimpleCursorAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida);

        plac = findViewById(R.id.txtLaPlacaVehic);

        lasHoras = findViewById(R.id.spinnerHoraSalida);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayHoras, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        lasHoras.setAdapter(adapter);

        losMinutos = findViewById(R.id.spinnerMinutosSalida);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterMinutos = ArrayAdapter.createFromResource(this,
                R.array.arrayMinutos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterMinutos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        losMinutos.setAdapter(adapterMinutos);

        pagar = findViewById(R.id.txtVTotPagar);

        salid = findViewById(R.id.btnSalidaVehic);
        salid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = GetVehiculoData();
                llenar(cur);

                close();
            }
        });
    }

    public void llenar(Cursor c){
        String plac;
        int hEnt,minEnt,hSal,minSal,totPagar,out;

        if(c.getCount()==0){
            Toast.makeText(this,"Esta placa no esta registrada en la base de datos", Toast.LENGTH_LONG).show();
        }
        else{
            c.moveToFirst();
            plac = c.getString(1);
            hEnt = Integer.parseInt(c.getString(2));
            minEnt = Integer.parseInt(c.getString(3));
            hSal = Integer.parseInt(c.getString(4));
            minSal = Integer.parseInt(c.getString(5));
            totPagar = Integer.parseInt(c.getString(6));
            out = Integer.parseInt(c.getString(7));
            v = new Vehiculo(plac,hEnt,minEnt,hSal,minSal,totPagar,out);
            if (v.getIsOut()==0){
                int h = Integer.parseInt(lasHoras.getSelectedItem().toString());
                if(v.getHoraEntrada() < h){
                    v.setIsOut(1);
                    v.setHoraSalida(h);
                    v.setMinSalida(Integer.parseInt(losMinutos.getSelectedItem().toString()));
                    calcularTotalPagar();
                    pagar.setText("$ " + v.getTotalPagar());
                    actualizar();
                }
                else{
                    if(v.getHoraEntrada() == h){
                        v.setTotalPagar(3000);
                        actualizar();
                    }
                    else{
                        Toast.makeText(this,"la hora de salida debe ser mayor que la hora de entrada, la hora de entrada es: " + v.getHoraEntrada(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                Toast.makeText(this,"el vehiculo esta registrado pero no esta dentro del parqueadero", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void actualizar(){
        try {
            SQLiteDatabase sqldata = basedatos.getWritableDatabase();
            ContentValues values = new ContentValues();


            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_HORASALIDA, v.getHoraSalida());
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_MINSALIDA, v.getMinSalida());
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_TOTALPAGAR, v.getTotalPagar());
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_ISOUT, v.getIsOut());
            values.put(DaoVehiculo.VehiculoEntry.COLUMN_NAME_CADSALIDA, String.valueOf(v.getHoraSalida()) + ":" + String.valueOf(v.getMinSalida()) + " horas");


            sqldata.update(DaoVehiculo.VehiculoEntry.TABLE_NAME,values,DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA+"=?",new String[]{String.valueOf(plac.getText().toString())});
            Toast.makeText(this,"ACTUALIZACION EXITOSA", Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            Toast.makeText(this,"Error al actualizar Datos", Toast.LENGTH_LONG).show();
        }

    }



    public void calcularTotalPagar(){
        int difHoras = v.getHoraSalida()-v.getHoraEntrada();
        int tMinHoras = difHoras*60;
        int difMin = v.getMinSalida()-v.getMinEntrada();
        int totalMinutos = tMinHoras+difMin;
        double horasACobrar = totalMinutos/60;
        horasACobrar = Math.ceil(horasACobrar);
        int lasHorasACobrar = Double.valueOf(horasACobrar).intValue();
        int totalPgar = lasHorasACobrar*3000;
        v.setTotalPagar(totalPgar);
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

    public Cursor GetVehiculoData()
    {
        String sqlString = "SELECT * FROM " + DaoVehiculo.VehiculoEntry.TABLE_NAME + " WHERE " +
                DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA + "='" + plac.getText().toString() + "'";
        abrir();
        SQLiteDatabase sqldata = basedatos.getReadableDatabase();
        Cursor c = sqldata.rawQuery(sqlString, null);
        return c;
    }
}

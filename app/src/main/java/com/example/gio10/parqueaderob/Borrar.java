package com.example.gio10.parqueaderob;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gio10.parqueaderob.model.DaoVehiculo;
import com.example.gio10.parqueaderob.model.VehiculoDbHelper;

public class Borrar extends AppCompatActivity {
    Button deleteV;
    VehiculoDbHelper basedatos;
    EditText plak;
    Cursor cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        plak = findViewById(R.id.txtLaPlacaVehicular);
        deleteV = findViewById(R.id.btnEliminarVe);
        deleteV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur = getVehiculoData();
                eliminar();
                finish();
            }
        });
    }

    public void eliminar(){
        cur.moveToFirst();
        if(cur.getCount()==0){
            Toast.makeText(this,"Esta placa no esta registrada en la base de datos", Toast.LENGTH_LONG).show();
        }
        else{
            try {
                SQLiteDatabase sqldata = basedatos.getWritableDatabase();
                sqldata.delete(DaoVehiculo.VehiculoEntry.TABLE_NAME,DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA+"=?",new String[]{cur.getString(1)});
                Toast.makeText(this,"ELIMINACIÓN EXITOSA", Toast.LENGTH_LONG).show();

            }
            catch (Exception e){
                Toast.makeText(this,"Error al eliminar Datos " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            close();
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

    public Cursor getVehiculoData(){
        String sqlString = "SELECT * FROM " + DaoVehiculo.VehiculoEntry.TABLE_NAME + " WHERE " +
                DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA + "='" + plak.getText().toString() + "'";
        abrir();
        SQLiteDatabase sqldata = basedatos.getReadableDatabase();
        Cursor c = sqldata.rawQuery(sqlString, null);
        return c;
    }
}

package com.example.gio10.parqueaderob;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.gio10.parqueaderob.model.DaoVehiculo;
import com.example.gio10.parqueaderob.model.Vehiculo;
import com.example.gio10.parqueaderob.model.VehiculoDbHelper;

import java.util.ArrayList;

public class ListarTodos extends AppCompatActivity {

    VehiculoDbHelper basedatos;
    ListView listado;
    ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    Cursor cur;
    SimpleCursorAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        listado = (ListView) findViewById(R.id.listaVehiculo);
        cur = GetAllData();
        llenar(cur);
        String from [] = new String[]{DaoVehiculo.VehiculoEntry.COLUMN_NAME_PLACA,DaoVehiculo.VehiculoEntry.COLUMN_NAME_CADENTRADA,DaoVehiculo.VehiculoEntry.COLUMN_NAME_HORASALIDA,DaoVehiculo.VehiculoEntry.COLUMN_NAME_TOTALPAGAR};
        int to [] = new int[] {R.id.txtLaPlaca,R.id.txtLaEntrada,R.id.txtLaSalida,R.id.txtElSaldo};
        try {
            adaptador = new SimpleCursorAdapter(this,R.layout.lista_detalle_vehiculo,cur,from,to,0);
        }
        catch (Exception e){
            Toast.makeText(ListarTodos.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        listado.setAdapter(adaptador);
        close();

        /*listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Estudiante e = estudiantes.get(i);
                Intent it = new Intent(ListaEstudiantes.this,DatosEstudiante.class);
                it.putExtra("estudiante",e);
                it.putExtra("i",i);
                startActivity(it);
                finish();
                //String cadena = p.getNombre() + " "  + p.getCapital() + " " + p.getContinente();
                //Toast.makeText(ListaPaises.this,"Item seleccionado = " + i + "  " + cadena, Toast.LENGTH_LONG).show();
            }
        });*/
    }

    public void llenar(Cursor c){
        String plac;
        int hEnt,minEnt,hSal,minSal,totPagar,out;

        while(c.moveToNext()){
            plac = c.getString(1);
            hEnt = Integer.parseInt(c.getString(2));
            minEnt = Integer.parseInt(c.getString(3));
            hSal = Integer.parseInt(c.getString(4));
            minSal = Integer.parseInt(c.getString(5));
            totPagar = Integer.parseInt(c.getString(6));
            out = Integer.parseInt(c.getString(7));

            Vehiculo v = new Vehiculo(plac,hEnt,minEnt,hSal,minSal,totPagar,out);
            vehiculos.add(v);

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

    public Cursor GetAllData()
    {
        abrir();
        SQLiteDatabase sqldata = basedatos.getReadableDatabase();
        Cursor c = sqldata.rawQuery("SELECT * FROM " + DaoVehiculo.VehiculoEntry.TABLE_NAME, null);

        return c;
    }
}

package com.example.gio10.parqueaderob.model;

import android.provider.BaseColumns;

public final class DaoVehiculo {
    private DaoVehiculo(){

    }

    public static class VehiculoEntry implements BaseColumns {
        public static final String TABLE_NAME = "Vehiculos";
        public static final String COLUMN_NAME_PLACA = "placa";
        public static final String COLUMN_NAME_HORAENTRADA = "horaentrada";
        public static final String COLUMN_NAME_MINENTRADA = "minentrada";
        public static final String COLUMN_NAME_HORASALIDA = "horasalida";
        public static final String COLUMN_NAME_MINSALIDA = "minsalida";
        public static final String COLUMN_NAME_TOTALPAGAR = "totalpagar";
        public static final String COLUMN_NAME_ISOUT = "isout";
        public static final String COLUMN_NAME_CADENTRADA = "cadentrada";
        public static final String COLUMN_NAME_CADSALIDA = "cadsalida";
        public static final String COLUMN_NAME_AVISO = "aviso";

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VehiculoEntry.TABLE_NAME + " (" +
                    VehiculoEntry._ID + " INTEGER PRIMARY KEY," +
                    VehiculoEntry.COLUMN_NAME_PLACA + " TEXT NOT NULL," +
                    VehiculoEntry.COLUMN_NAME_HORAENTRADA + " INTEGER," +
                    VehiculoEntry.COLUMN_NAME_MINENTRADA + " INTEGER," +
                    VehiculoEntry.COLUMN_NAME_HORASALIDA + " INTEGER," +
                    VehiculoEntry.COLUMN_NAME_MINSALIDA + " INTEGER," +
                    VehiculoEntry.COLUMN_NAME_TOTALPAGAR + " INTEGER," +
                    VehiculoEntry.COLUMN_NAME_ISOUT + " INTEGER," +
                    VehiculoEntry.COLUMN_NAME_CADENTRADA + " TEXT," +
                    VehiculoEntry.COLUMN_NAME_CADSALIDA + " TEXT," +
                    "CONSTRAINT name_unique UNIQUE (" + VehiculoEntry.COLUMN_NAME_PLACA + "))";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VehiculoEntry.TABLE_NAME;
}

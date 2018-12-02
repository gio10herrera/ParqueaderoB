package com.example.gio10.parqueaderob.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehiculo implements Parcelable{
    private String placa;
    private int horaEntrada;
    private int minEntrada;
    private int horaSalida;
    private int minSalida;
    private int totalPagar;
    private int isOut; //1 significa que el vehiculo no esta dentro del parqueadero, 0 significa lo contrario

    public Vehiculo(String placa, int horaEntrada, int minEntrada, int isOut) {
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.minEntrada = minEntrada;
        this.isOut = isOut;
        horaSalida=0;
        minSalida=0;
        totalPagar=0;
    }

    public Vehiculo(String placa, int horaEntrada, int minEntrada, int horaSalida, int minSalida, int totalPagar, int isOut) {
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.minEntrada = minEntrada;
        this.horaSalida = horaSalida;
        this.minSalida = minSalida;
        this.totalPagar = totalPagar;
        this.isOut = isOut;
    }

    public int getIsOut() {
        return isOut;
    }

    public void setIsOut(int isOut) {
        this.isOut = isOut;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getMinEntrada() {
        return minEntrada;
    }

    public void setMinEntrada(int minEntrada) {
        this.minEntrada = minEntrada;
    }

    public int getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(int horaSalida) {
        this.horaSalida = horaSalida;
    }

    public int getMinSalida() {
        return minSalida;
    }

    public void setMinSalida(int minSalida) {
        this.minSalida = minSalida;
    }

    public int getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(int totalPagar) {
        this.totalPagar = totalPagar;
    }

    private Vehiculo(Parcel in){
        placa = in.readString();
        horaEntrada = in.readInt();
        minEntrada = in.readInt();
        horaSalida = in.readInt();
        minSalida = in.readInt();
        totalPagar = in.readInt();
        isOut = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placa);
        dest.writeInt(horaEntrada);
        dest.writeInt(minEntrada);
        dest.writeInt(horaSalida);
        dest.writeInt(minSalida);
        dest.writeInt(totalPagar);
        dest.writeInt(isOut);
    }

    public static final Parcelable.Creator<Vehiculo> CREATOR
            = new Parcelable.Creator<Vehiculo>() {
        public Vehiculo createFromParcel(Parcel in) {
            return new Vehiculo(in);
        }

        public Vehiculo[] newArray(int size) {
            return new Vehiculo[size];
        }
    };
}

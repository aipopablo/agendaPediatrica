package models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 16/11/2017.
 */

public class Vacuna {
    private int id;
    private String nombreVacuna;
    private Date fechaAplicacion;
    private String aplicada;

    public Vacuna(int id, String nombreVacuna, Date fechaAplicacion, String aplicada) {
        this.id = id;
        this.nombreVacuna = nombreVacuna;
        this.fechaAplicacion = fechaAplicacion;
        this.aplicada = aplicada;
    }

    public Vacuna(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public String getAplicada() {
        return aplicada;
    }

    public String getAplicadaExtendida(){
        if (this.aplicada.equals("N")){
            return "Pendiente de Aplicar";
        }else{
            return "Aplicada";
        }
    }

    public void setAplicada(String aplicada) {
        this.aplicada = aplicada;
    }

    public String getFechaAplicacionString(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(this.fechaAplicacion);
    }

    public void setFechaAplicacion(String fechaAp) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date fecha = null;

        try {
            fecha = df.parse(fechaAp);
            this.fechaAplicacion = fecha;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

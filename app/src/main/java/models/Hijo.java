package models;

import android.graphics.drawable.Drawable;

import com.agendapediatrica.pablo.agendapediatricanuevo.R;

/**
 * Created by Admin on 5/11/2017.
 */

public class Hijo {
    private int id;
    private String nombre;
    private int edad;
    private String sexo;

    public Hijo(int id, String nombre, int edad, String sexo) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
    }

    public Hijo(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getSexoExtendido(){
        if (this.sexo.equals("M")){
            return "Masculino";
        }else{
            return "Femenino";
        }
    }

    /*
    public int getDrawImage(){
        if (this.sexo.equals("M")){
            return R.drawable.hombre;
        }else{
            return R.drawable.mujer;
        }
    }
    */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Hijo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}

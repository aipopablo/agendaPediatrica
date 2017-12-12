package models;

/**
 * Created by Admin on 30/10/2017.
 */

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String emailUsuario;

    public Usuario(int idUsuario, String nombreUsuario, String emailUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
    }

    public Usuario() {

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Nombre:'" + nombreUsuario + '\'' + "Correo:'" + emailUsuario + '\'' + "ID:'" + idUsuario + '\'' +
                '}';
    }
}
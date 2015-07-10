package co.com.gdgcali.labfirebase.model;

/**
 * Created by jggomezt on 03/06/2015.
 */
public class Mensaje {

    private String autor;
    private String mensaje;
    private String fecha;

    public Mensaje(String autor, String mensaje, String fecha) {
        this.autor = autor;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }


    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

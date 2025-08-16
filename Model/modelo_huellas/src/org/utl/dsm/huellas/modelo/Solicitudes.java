
package org.utl.dsm.huellas.modelo;

public class Solicitudes {
    private int idSolicitud;
    private String fecha;
    private int estatus;
    private String motivo;
    private String telefono;
    private String correo;
    private String direccion;
    private int idAnimal;
    private int idAdoptante;
private String nombreAnimal;
private String nombreAdoptante;

    public Solicitudes() {
    }

    public Solicitudes(int idSolicitud, String fecha, int estatus, String motivo, String telefono, String correo, String direccion, int idAnimal, int idAdoptante) {
        this.idSolicitud = idSolicitud;
        this.fecha = fecha;
        this.estatus = estatus;
        this.motivo = motivo;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.idAnimal = idAnimal;
        this.idAdoptante = idAdoptante;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    public String getNombreAdoptante() {
        return nombreAdoptante;
    }

    public void setNombreAdoptante(String nombreAdoptante) {
        this.nombreAdoptante = nombreAdoptante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdAdoptante() {
        return idAdoptante;
    }

    public void setIdAdoptante(int idAdoptante) {
        this.idAdoptante = idAdoptante;
    }
    
    
}

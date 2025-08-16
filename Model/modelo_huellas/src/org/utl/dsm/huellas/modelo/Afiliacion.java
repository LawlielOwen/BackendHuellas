
package org.utl.dsm.huellas.modelo;


public class Afiliacion  extends Persona{
     private int idAfiliado;
    private String mascotasAfiliado;
    private String tipoAfiliado;
    private String frecuenciaAfiliado;
    private String disponibilidadAfiliado;
    private String deseoContactoAfiliado;
    private String medioContactoAfiliado;
    private String comentariosAfiliado;
    private int idCentro;
    




    // Constructor vacío como buen novato
    public Afiliacion() {}

    public Afiliacion(int idAfiliado, String mascotasAfiliado, String tipoAfiliado, String frecuenciaAfiliado, String disponibilidadAfiliado, String deseoContactoAfiliado, String medioContactoAfiliado, String comentariosAfiliado, int idCentro) {
        this.idAfiliado = idAfiliado;
        this.mascotasAfiliado = mascotasAfiliado;
        this.tipoAfiliado = tipoAfiliado;
        this.frecuenciaAfiliado = frecuenciaAfiliado;
        this.disponibilidadAfiliado = disponibilidadAfiliado;
        this.deseoContactoAfiliado = deseoContactoAfiliado;
        this.medioContactoAfiliado = medioContactoAfiliado;
        this.comentariosAfiliado = comentariosAfiliado;
        this.idCentro = idCentro;
    }

    public Afiliacion(int idAfiliado, String mascotasAfiliado, String tipoAfiliado, String frecuenciaAfiliado, String disponibilidadAfiliado, String deseoContactoAfiliado, String medioContactoAfiliado, String comentariosAfiliado, int idCentro, String nombre, String app, String apm, String fechaNacimiento, String correo, String contraseña, String foto, String telefono, String genero, int estatus) {
        super(nombre, app, apm, fechaNacimiento, correo, contraseña, foto, telefono, genero, estatus);
        this.idAfiliado = idAfiliado;
        this.mascotasAfiliado = mascotasAfiliado;
        this.tipoAfiliado = tipoAfiliado;
        this.frecuenciaAfiliado = frecuenciaAfiliado;
        this.disponibilidadAfiliado = disponibilidadAfiliado;
        this.deseoContactoAfiliado = deseoContactoAfiliado;
        this.medioContactoAfiliado = medioContactoAfiliado;
        this.comentariosAfiliado = comentariosAfiliado;
        this.idCentro = idCentro;
    }

 
 
    public int getIdAfiliado() {
        return idAfiliado;
    }

    public void setIdAfiliado(int idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    public String getMascotasAfiliado() {
        return mascotasAfiliado;
    }

    public void setMascotasAfiliado(String mascotasAfiliado) {
        this.mascotasAfiliado = mascotasAfiliado;
    }

    public String getTipoAfiliado() {
        return tipoAfiliado;
    }

    public void setTipoAfiliado(String tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    public String getFrecuenciaAfiliado() {
        return frecuenciaAfiliado;
    }

    public void setFrecuenciaAfiliado(String frecuenciaAfiliado) {
        this.frecuenciaAfiliado = frecuenciaAfiliado;
    }

    public String getDisponibilidadAfiliado() {
        return disponibilidadAfiliado;
    }

    public void setDisponibilidadAfiliado(String disponibilidadAfiliado) {
        this.disponibilidadAfiliado = disponibilidadAfiliado;
    }

    public String getDeseoContactoAfiliado() {
        return deseoContactoAfiliado;
    }

    public void setDeseoContactoAfiliado(String deseoContactoAfiliado) {
        this.deseoContactoAfiliado = deseoContactoAfiliado;
    }

    public String getMedioContactoAfiliado() {
        return medioContactoAfiliado;
    }

    public void setMedioContactoAfiliado(String medioContactoAfiliado) {
        this.medioContactoAfiliado = medioContactoAfiliado;
    }

    public String getComentariosAfiliado() {
        return comentariosAfiliado;
    }

    public void setComentariosAfiliado(String comentariosAfiliado) {
        this.comentariosAfiliado = comentariosAfiliado;
    }

    public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

}

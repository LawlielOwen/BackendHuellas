
package org.utl.dsm.huellas.modelo;

public class Centros {
    private int idCentro;
    private String nombreCentro;
    private String direccionCentro;
    private String telefonoCentro;
    private String correoCentro;
    private String horarioAperturaCentro;
    private String horarioCierreCentro;
    private int estatus;

    public Centros() {
    }

    public Centros(int idCentro, String nombreCentro, String direccionCentro, String telefonoCentro, String correoCentro, String horarioAperturaCentro, String horarioCierreCentro, int estatus) {
        this.idCentro = idCentro;
        this.nombreCentro = nombreCentro;
        this.direccionCentro = direccionCentro;
        this.telefonoCentro = telefonoCentro;
        this.correoCentro = correoCentro;
        this.horarioAperturaCentro = horarioAperturaCentro;
        this.horarioCierreCentro = horarioCierreCentro;
        this.estatus = estatus;
    }

    public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getDireccionCentro() {
        return direccionCentro;
    }

    public void setDireccionCentro(String direccionCentro) {
        this.direccionCentro = direccionCentro;
    }

    public String getTelefonoCentro() {
        return telefonoCentro;
    }

    public void setTelefonoCentro(String telefonoCentro) {
        this.telefonoCentro = telefonoCentro;
    }

    public String getCorreoCentro() {
        return correoCentro;
    }

    public void setCorreoCentro(String correoCentro) {
        this.correoCentro = correoCentro;
    }

    public String getHorarioAperturaCentro() {
        return horarioAperturaCentro;
    }

    public void setHorarioAperturaCentro(String horarioAperturaCentro) {
        this.horarioAperturaCentro = horarioAperturaCentro;
    }

    public String getHorarioCierreCentro() {
        return horarioCierreCentro;
    }

    public void setHorarioCierreCentro(String horarioCierreCentro) {
        this.horarioCierreCentro = horarioCierreCentro;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
    
}

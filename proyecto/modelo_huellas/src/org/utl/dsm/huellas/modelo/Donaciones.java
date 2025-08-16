package org.utl.dsm.huellas.modelo;

import java.time.LocalDate;
import java.util.Date;

public class Donaciones {
    private int idDonacion;
    private String nombreDonante;
    private double montoDonacion;
    private String centroBeneficiado;
    private String centroDonacion;
    private Date fechaDonacion;
   private String banco_pago;
   private String tipoTarjeta;
    private int id_adoptante;
    private int idCentro;
private int ultimosDigitos;
private int conteoDonaciones;
private double totalDonacion;
    // Constructor
    public Donaciones() { }

    public int getIdDonacion() {
        return idDonacion;
    }

    public void setIdDonacion(int idDonacion) {
        this.idDonacion = idDonacion;
    }

    public String getNombreDonante() {
        return nombreDonante;
    }

    public void setNombreDonante(String nombreDonante) {
        this.nombreDonante = nombreDonante;
    }

    public double getMontoDonacion() {
        return montoDonacion;
    }

    public void setMontoDonacion(double montoDonacion) {
        this.montoDonacion = montoDonacion;
    }

    public String getCentroBeneficiado() {
        return centroBeneficiado;
    }

    public void setCentroBeneficiado(String centroBeneficiado) {
        this.centroBeneficiado = centroBeneficiado;
    }

    public String getCentroDonacion() {
        return centroDonacion;
    }

    public void setCentroDonacion(String centroDonacion) {
        this.centroDonacion = centroDonacion;
    }

    public String getBanco_pago() {
        return banco_pago;
    }

    public void setBanco_pago(String banco_pago) {
        this.banco_pago = banco_pago;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public int getId_adoptante() {
        return id_adoptante;
    }

    public void setId_adoptante(int id_adoptante) {
        this.id_adoptante = id_adoptante;
    }

    public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }

    public int getUltimosDigitos() {
        return ultimosDigitos;
    }

    public void setUltimosDigitos(int ultimosDigitos) {
        this.ultimosDigitos = ultimosDigitos;
    }

    public Date getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(Date fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public int getConteoDonaciones() {
        return conteoDonaciones;
    }

    public void setConteoDonaciones(int conteoDonaciones) {
        this.conteoDonaciones = conteoDonaciones;
    }

    public double getTotalDonacion() {
        return totalDonacion;
    }

    public void setTotalDonacion(double totalDonacion) {
        this.totalDonacion = totalDonacion;
    }
    
    

} 
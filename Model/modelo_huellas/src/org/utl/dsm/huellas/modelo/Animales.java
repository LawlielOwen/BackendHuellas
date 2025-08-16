package org.utl.dsm.huellas.modelo;

public class Animales {

    private int idAnimal;
    private String nombreAnimal;
    private String genero;
    private String edad;
    private Double peso;
    private String especie;
    private String descripcion;
    private String raza;
    private String tamano;
    private String caracter;
    private int estatus;
    private String estatusTexto;
    private String codigoAnimal;
    private String foto;
    private int idCentro;

    public Animales(int idAnimal, String nombreAnimal, String genero, String edad, Double peso, String especie, String descripcion, String raza, String tamano, String caracter, int estatus, String estatusTexto, String codigoAnimal, String foto, int idCentro) {
        this.idAnimal = idAnimal;
        this.nombreAnimal = nombreAnimal;
        this.genero = genero;
        this.edad = edad;
        this.peso = peso;
        this.especie = especie;
        this.descripcion = descripcion;
        this.raza = raza;
        this.tamano = tamano;
        this.caracter = caracter;
        this.estatus = estatus;
        this.estatusTexto = estatusTexto;
        this.codigoAnimal = codigoAnimal;
        this.foto = foto;
        this.idCentro = idCentro;
    }


    

    public Animales() {
    }

    public String getEstatusTexto() {
        return estatusTexto;
    }

    public void setEstatusTexto(String estatusTexto) {
        this.estatusTexto = estatusTexto;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getCodigoAnimal() {
        return codigoAnimal;
    }

    public void setCodigoAnimal(String codigoAnimal) {
        this.codigoAnimal = codigoAnimal;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(int idCentro) {
        this.idCentro = idCentro;
    }
    
    
}

package org.utl.dsm.huellas.modelo;

public class EmpleadoLogin {
    private String correo;
    private String contraseña;

    // Constructor vacío requerido para deserialización
    public EmpleadoLogin() {
    }

    public EmpleadoLogin(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "EmpleadoLogin{" +
                "correo='" + correo + '\'' +
                ", contraseña=''}";
    }
}
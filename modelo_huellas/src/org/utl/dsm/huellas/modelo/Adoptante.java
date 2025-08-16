
package org.utl.dsm.huellas.modelo;

public class Adoptante extends Persona {
    private int idAdoptante;

    public Adoptante(int idAdoptante) {
        this.idAdoptante = idAdoptante;
    }

    public Adoptante(int idAdoptante, String nombre, String app, String apm, String fechaNacimiento, String correo, String contraseña, String foto, String telefono, String genero, int estatus) {
        super(nombre, app, apm, fechaNacimiento, correo, contraseña, foto, telefono, genero, estatus);
        this.idAdoptante = idAdoptante;
    }

    public Adoptante() {
    }

    public int getIdAdoptante() {
        return idAdoptante;
    }

    public void setIdAdoptante(int idAdoptante) {
        this.idAdoptante = idAdoptante;
    }
    
    
}

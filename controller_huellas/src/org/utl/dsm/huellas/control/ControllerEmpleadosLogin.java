package org.utl.dsm.huellas.control;

import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import org.utl.dsm.huellas.modelo.Empleado;
import org.utl.dsm.huellas.modelo.EmpleadoLogin;
import org.utl.dsm.huellas.db.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

public class ControllerEmpleadosLogin {

    public ControllerEmpleadosLogin() {
    }

    public Empleado validarLogin(EmpleadoLogin login) throws Exception {
        Empleado empleado = null;

        String sql = "SELECT p.id_persona, p.nombre_persona, p.app_persona, p.apm_persona, p.nacimiento_persona, "
                + "p.genero_persona, p.correo_persona, p.contraseña_persona, p.foto_persona, p.telefono_persona, p.estatus_persona, "
                + "e.id_empleado, e.direccion_empleado, e.codigoPostal_empleado, e.codigo_empleado, e.rol_empleado, e.id_centro "
                + "FROM tbl_empleados e "
                + "INNER JOIN tbl_personas p ON e.id_persona = p.id_persona "
                + "WHERE p.correo_persona = ? AND p.contraseña_persona = ? AND p.estatus_persona = 1";

        ConexionMySQL connMySQL = new ConexionMySQL();
        try ( Connection conn = connMySQL.open();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login.getCorreo());
            ps.setString(2, login.getContraseña());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                empleado = new Empleado();

                // Datos Persona
                empleado.setIdPersona(rs.getInt("id_persona"));
                empleado.setNombre(rs.getString("nombre_persona"));
                empleado.setApp(rs.getString("app_persona"));
                empleado.setApm(rs.getString("apm_persona"));
                empleado.setFechaNacimiento(rs.getString("nacimiento_persona"));
                empleado.setGenero(rs.getString("genero_persona"));
                empleado.setCorreo(rs.getString("correo_persona"));
                empleado.setContraseña(rs.getString("contraseña_persona"));

                String base64 = rs.getString("foto_persona");
                if (base64 != null && !base64.isEmpty()) {
                    try {

                        if (!base64.startsWith("data:image")) {
                            byte[] imageBytes = Base64.getDecoder().decode(base64);
                            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                            String mimeType = URLConnection.guessContentTypeFromStream(bais);
                            if (mimeType == null) {
                                mimeType = "image/jpeg";
                            }
                            empleado.setFoto("data:" + mimeType + ";base64," + base64);
                        } else {
                            empleado.setFoto(base64);
                        }
                    } catch (Exception e) {

                        empleado.setFoto("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");
                    }
                } else {

                    empleado.setFoto("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=");
                }
                empleado.setTelefono(rs.getString("telefono_persona"));
                empleado.setEstatus(rs.getInt("estatus_persona"));

                // Datos Empleado
                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setDireccion(rs.getString("direccion_empleado"));
                empleado.setCP(rs.getInt("codigoPostal_empleado"));
                empleado.setCodigo(rs.getString("codigo_empleado"));
                empleado.setRol(rs.getString("rol_empleado"));
                empleado.setCentro(rs.getInt("id_centro"));
            }
            rs.close();
        } catch (Exception ex) {
            throw new Exception("Error en validarLogin: " + ex.getMessage());
        } finally {
            connMySQL.close();
        }

        return empleado;
    }
}

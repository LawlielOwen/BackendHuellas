package org.utl.dsm.huellas.control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Empleado;

public class ControllerEmpleados {

    private Empleado fill(ResultSet rs) throws Exception {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("id_empleado"));
        e.setIdPersona(rs.getInt("id_persona"));
        e.setNombre(rs.getString("nombre_persona"));
        e.setApp(rs.getString("app_persona"));
        e.setApm(rs.getString("apm_persona"));
        e.setFechaNacimiento(rs.getString("nacimiento_persona"));
        e.setGenero(rs.getString("genero_persona"));
        e.setCorreo(rs.getString("correo_persona"));
        e.setContraseña(rs.getString("contraseña_persona"));
        
        // Procesamiento de la imagen en base64 similar a animalesControl
        String base64 = rs.getString("foto_persona");
        if (base64 != null && !base64.isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            String mimeType = URLConnection.guessContentTypeFromStream(bais);
            if (mimeType == null) {
                mimeType = "image/jpeg";
            }
            String fotoConTipo = "data:" + mimeType + ";base64," + base64;
            e.setFoto(fotoConTipo);
        }
        
        e.setTelefono(rs.getString("telefono_persona"));
        e.setDireccion(rs.getString("direccion_empleado"));
        e.setCP(rs.getInt("codigoPostal_empleado"));
        e.setCodigo(rs.getString("codigo_empleado"));
        e.setRol(rs.getString("rol_empleado"));
        e.setCentro(rs.getInt("id_centro"));
        e.setEstatus(rs.getInt("estatus_persona"));
        return e;
    }

    public List<Empleado> listarEmpleados(int estatus) throws Exception {
        String sql = estatus != -1 ? "CALL listarEmpleadosPorEstatus(?)" : "CALL listarTodosEmpleados()";
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        CallableStatement cstmt = con.prepareCall(sql);
        
        if (estatus != -1) {
            cstmt.setInt(1, estatus);
        }
        
        ResultSet rs = cstmt.executeQuery();
        List<Empleado> empleados = new ArrayList<>();
        
        while (rs.next()) {
            empleados.add(fill(rs));
        }
        
        rs.close();
        cstmt.close();
        conn.close();
        return empleados;
    }

    public void insertarEmpleado(Empleado e) throws Exception {
        validarEmpleado(e);
        
        // Procesar la imagen como en animalesControl
        String fotoBase64 = e.getFoto();
        if (fotoBase64 != null && fotoBase64.contains(",")) {
            fotoBase64 = fotoBase64.split(",")[1];
        }
        
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        
        try {
            con.setAutoCommit(false);
            
            // Insertar persona
            CallableStatement cstmtPersona = con.prepareCall("{CALL insertarPersona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cstmtPersona.setString(1, e.getNombre());
            cstmtPersona.setString(2, e.getApp());
            cstmtPersona.setString(3, e.getApm());
            cstmtPersona.setString(4, e.getFechaNacimiento());
            cstmtPersona.setString(5, e.getGenero());
            cstmtPersona.setString(6, e.getCorreo());
            cstmtPersona.setString(7, e.getContraseña());
            cstmtPersona.setString(8, fotoBase64);
            cstmtPersona.setString(9, e.getTelefono());
            cstmtPersona.registerOutParameter(10, Types.INTEGER);
            cstmtPersona.executeUpdate();
            
            int idPersona = cstmtPersona.getInt(10);
            e.setIdPersona(idPersona);
            cstmtPersona.close();
            
            // Generar código empleado
            String codigo = generarCodigoEmpleado(e, con);
            e.setCodigo(codigo);
            
            // Insertar empleado
            CallableStatement cstmtEmpleado = con.prepareCall("{CALL insertarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            cstmtEmpleado.setString(1, e.getDireccion());
            cstmtEmpleado.setInt(2, e.getCP());
            cstmtEmpleado.setString(3, codigo);
            cstmtEmpleado.setString(4, e.getRol());
            cstmtEmpleado.setInt(5, e.getCentro());
            cstmtEmpleado.setInt(6, idPersona);
            cstmtEmpleado.registerOutParameter(7, Types.INTEGER);
            cstmtEmpleado.executeUpdate();
            
            int idEmpleado = cstmtEmpleado.getInt(7);
            e.setIdEmpleado(idEmpleado);
            cstmtEmpleado.close();
            
            con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    private String generarCodigoEmpleado(Empleado e, Connection con) throws Exception {
        // Primera letra del nombre
        String nombreInicial = e.getNombre() != null && !e.getNombre().isEmpty() ? 
            e.getNombre().substring(0, 1).toUpperCase() : "X";
        
        // Dos primeras letras del apellido paterno
        String apPaternoIniciales = e.getApp() != null && e.getApp().length() >= 2 ? 
            e.getApp().substring(0, 2).toUpperCase() : 
            (e.getApp() != null ? e.getApp().toUpperCase() : "XX");
        
        // Primera letra del apellido materno
        String apMaternoInicial = e.getApm() != null && !e.getApm().isEmpty() ? 
            e.getApm().substring(0, 1).toUpperCase() : "X";
        
        // Dos últimos dígitos del año de nacimiento
        String anioNacimiento = "00";
        if (e.getFechaNacimiento() != null && e.getFechaNacimiento().length() >= 4) {
            anioNacimiento = e.getFechaNacimiento().substring(2, 4);
        }
        
        // Número consecutivo
        CallableStatement cstmt = con.prepareCall("{CALL contarEmpleados()}");
        ResultSet rs = cstmt.executeQuery();
        int consecutivo = 1;
        if (rs.next()) {
            consecutivo = rs.getInt(1) + 1;
        }
        rs.close();
        cstmt.close();
        
        // Formato: GMAG021 (9 caracteres)
        return nombreInicial + apPaternoIniciales + apMaternoInicial + anioNacimiento + String.format("%02d", consecutivo);
    }

    public void actualizarEmpleado(Empleado e) throws Exception {
        validarEmpleado(e);
        
        // Procesar la imagen
        String fotoBase64 = e.getFoto();
        if (fotoBase64 != null && fotoBase64.contains(",")) {
            fotoBase64 = fotoBase64.split(",")[1];
        }
        
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        
        try {
            con.setAutoCommit(false);
            
            // Actualizar persona
            CallableStatement cstmtPersona = con.prepareCall("{CALL actualizarPersona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            cstmtPersona.setString(1, e.getNombre());
            cstmtPersona.setString(2, e.getApp());
            cstmtPersona.setString(3, e.getApm());
            cstmtPersona.setString(4, e.getFechaNacimiento());
            cstmtPersona.setString(5, e.getGenero());
            cstmtPersona.setString(6, e.getCorreo());
            cstmtPersona.setString(7, e.getContraseña());
            cstmtPersona.setString(8, fotoBase64);
            cstmtPersona.setString(9, e.getTelefono());
            cstmtPersona.setInt(10, e.getIdPersona());
            cstmtPersona.executeUpdate();
            cstmtPersona.close();
            
            // Actualizar empleado
            CallableStatement cstmtEmpleado = con.prepareCall("{CALL actualizarEmpleado(?, ?, ?, ?, ?, ?)}");
            cstmtEmpleado.setString(1, e.getDireccion());
            cstmtEmpleado.setInt(2, e.getCP());
            cstmtEmpleado.setString(3, e.getRol());
            cstmtEmpleado.setInt(4, e.getCentro());
            cstmtEmpleado.setInt(5, e.getIdPersona());
            cstmtEmpleado.setInt(6, e.getIdEmpleado());
            cstmtEmpleado.executeUpdate();
            cstmtEmpleado.close();
            
            con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    public void eliminarEmpleadoLogico(int idPersona) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        
        CallableStatement cstmt = con.prepareCall("{CALL desactivarEmpleado(?)}");
        cstmt.setInt(1, idPersona);
        cstmt.executeUpdate();
        
        cstmt.close();
        con.close();
    }

    public List<Empleado> buscarEmpleados(String filtro) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        
        CallableStatement cstmt = con.prepareCall("{CALL buscarEmpleados(?)}");
        cstmt.setString(1, "%" + filtro + "%");
        
        ResultSet rs = cstmt.executeQuery();
        List<Empleado> empleados = new ArrayList<>();
        
        while (rs.next()) {
            empleados.add(fill(rs));
        }
        
        rs.close();
        cstmt.close();
        con.close();
        
        return empleados;
    }

    private void validarEmpleado(Empleado e) throws Exception {
        if (e == null) throw new Exception("Empleado no puede ser null");
        if (e.getNombre() == null || e.getNombre().trim().isEmpty()) 
            throw new Exception("Nombre es requerido");
        if (e.getApp() == null || e.getApp().trim().isEmpty()) 
            throw new Exception("Apellido paterno es requerido");
        if (e.getApm() == null || e.getApm().trim().isEmpty()) 
            throw new Exception("Apellido materno es requerido");
        if (e.getFechaNacimiento() == null || e.getFechaNacimiento().trim().isEmpty()) 
            throw new Exception("Fecha de nacimiento es requerida");
        if (e.getGenero() == null || e.getGenero().trim().isEmpty()) 
            throw new Exception("Género es requerido");
        if (e.getCorreo() == null || e.getCorreo().trim().isEmpty()) 
            throw new Exception("Correo es requerido");
        if (e.getContraseña() == null || e.getContraseña().trim().isEmpty()) 
            throw new Exception("Contraseña es requerida");
        if (e.getDireccion() == null || e.getDireccion().trim().isEmpty()) 
            throw new Exception("Dirección es requerida");
        if (e.getRol() == null || e.getRol().trim().isEmpty()) 
            throw new Exception("Rol es requerido");
    }
}
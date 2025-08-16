package org.utl.dsm.huellas.control;

import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Solicitudes;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class solicitudesControl {
    
    private Solicitudes fill(ResultSet rs) throws Exception {
        Solicitudes s = new Solicitudes();
        s.setIdSolicitud(rs.getInt("id_solicitud"));
        s.setFecha(rs.getString("fecha_solicitud"));
        s.setMotivo(rs.getString("motivo_solicitud"));
        s.setTelefono(rs.getString("telefono_contacto"));
        s.setCorreo(rs.getString("correo_solicitud"));
        s.setDireccion(rs.getString("direccion_contacto"));
        s.setIdAnimal(rs.getInt("id_animal"));
        s.setIdAdoptante(rs.getInt("id_adoptante"));
        s.setEstatus(rs.getInt("estatus_solicitud"));
        s.setNombreAnimal(rs.getString("nombre_animal"));
        s.setNombreAdoptante(rs.getString("nombre_adoptante"));
        return s;
    }
    
    public List<Solicitudes> getAll(String filtro) throws Exception {
        String sql = "select * from verSoli";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        List<Solicitudes> soli = new ArrayList<>();
        while (rs.next()) {
            soli.add(fill(rs));
        }
        rs.close();
        cstmt.close();
        conn.close();
        return soli;
    }
    
    public List<Solicitudes> getSolicitudes(String filtro) throws Exception {
        String sql = "select * from vertodassolicitudes ORDER BY id_solicitud DESC";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        List<Solicitudes> soli = new ArrayList<>();
        while (rs.next()) {
            soli.add(fill(rs));
        }
        rs.close();
        cstmt.close();
        conn.close();
        return soli;
    }
    
    public void insertarSoli(Solicitudes s) throws Exception {
        CorreoController c = new CorreoController();
        int idGenerado = -1;
        ConexionMySQL conexion = new ConexionMySQL();
        Connection conn = conexion.open();
        
        String call = "{CALL enviarSoli(?, ?, ?,?,?,?,?)}";
        CallableStatement cstmt = conn.prepareCall(call);
        
        cstmt.setString(1, s.getMotivo());
        cstmt.setString(2, s.getTelefono());
        cstmt.setString(3, s.getCorreo());
        cstmt.setString(4, s.getDireccion());
        cstmt.setInt(5, s.getIdAnimal());
        cstmt.setInt(6, s.getIdAdoptante());
        cstmt.registerOutParameter(7, Types.INTEGER);
        
        cstmt.executeUpdate();
        idGenerado = cstmt.getInt(7);
        s.setIdSolicitud(idGenerado);
        String nombreA = obtenerNombreAnimal(s.getIdAnimal(), conn);
        String nombreAdoptant = obtenerNombreAdoptante(s.getIdAdoptante(), conn);
        c.enviarCorreoPendiente(s.getCorreo(), nombreA, nombreAdoptant);
        cstmt.close();
        conn.close();
        
    }
    
    public void aceptarSolicitud(Solicitudes a) throws Exception {
        CorreoController c = new CorreoController();
        String sql = "{CALL  aceptarSoli(?,?)}";
        
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        
        cstmt.setInt(1, a.getIdSolicitud());
        cstmt.setInt(2, a.getIdAnimal());
        cstmt.executeUpdate();
        String nombreA = obtenerNombreAnimal(a.getIdAnimal(), conns);
        Solicitudes infoSoli = obtenerInfoSolicitud(a.getIdSolicitud());
        String nombreAdoptante = infoSoli.getNombreAdoptante();
        String correoAdop = obtenerCorreoAdoptante(a.getIdSolicitud(), conns);
        c.enviarCorreoAceptado(correoAdop, nombreA, nombreAdoptante);
        cstmt.close();
        conn.close();
        
    }
    
    public void rechazarSolicitud(Solicitudes a) throws Exception {
        String sql = "{CALL  rechazarSoli(?)}";
        
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        
        cstmt.setInt(1, a.getIdSolicitud());
        cstmt.executeUpdate();
        Solicitudes infoSoli = obtenerInfoSolicitud(a.getIdSolicitud());
        String nombreAnimal = infoSoli.getNombreAnimal();
        String nombreAdoptante = infoSoli.getNombreAdoptante();
        String correoAdop = obtenerCorreoAdoptante(a.getIdSolicitud(), conns);
        CorreoController c = new CorreoController();
        c.enviarCorreoRechazado(correoAdop, nombreAnimal, nombreAdoptante);
        cstmt.close();
        conn.close();
        
    }
    
    public void eliminarSoli(Solicitudes a) throws Exception {
        String sql = "{CALL  eliminarSoli(?)}";
        
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        
        cstmt.setInt(1, a.getIdSolicitud());
        cstmt.executeUpdate();
        cstmt.close();
        conn.close();
    }
    
    public int contarDisponibles() throws Exception {
        String sql = "{CALL contarSoli()}";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        
        int disponibles = 0;
        if (rs.next()) {
            disponibles = rs.getInt("totalDisponibles");
        }
        
        rs.close();
        cstmt.close();
        conn.close();
        
        return disponibles;
    }
    
    public int contarAceptar() throws Exception {
        String sql = "{CALL contarAceptadas()}";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        
        int disponibles = 0;
        if (rs.next()) {
            disponibles = rs.getInt("totalDisponibles");
        }
        
        rs.close();
        cstmt.close();
        conn.close();
        
        return disponibles;
    }
    
    public int contarRechazar() throws Exception {
        String sql = "{CALL contarRechazo()}";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        
        int disponibles = 0;
        if (rs.next()) {
            disponibles = rs.getInt("totalDisponibles");
        }
        
        rs.close();
        cstmt.close();
        conn.close();
        
        return disponibles;
    }
    
    public List<Solicitudes> filtrarPorEstatus(int estatus) throws Exception {
        List<Solicitudes> lista = new ArrayList<>();
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        
        String sql = "CALL filtroSolicitud(?)";
        CallableStatement stmt = con.prepareCall(sql);
        stmt.setInt(1, estatus);
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            lista.add(fill(rs));
        }
        
        rs.close();
        stmt.close();
        con.close();
        
        return lista;
    }
    
    public List<Solicitudes> buscarSoli(String nombre) throws Exception {
        List<Solicitudes> lista = new ArrayList<>();
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall("{CALL buscarSolicitud(?)}");
        
        cstmt.setString(1, nombre);
        ResultSet rs = cstmt.executeQuery();
        
        while (rs.next()) {
            lista.add(fill(rs));
        }
        
        rs.close();
        cstmt.close();
        conn.close();
        
        return lista;
    }
    
    private String obtenerNombreAnimal(int idAnimal, Connection conn) throws Exception {
        String nombreAnimal = "";
        String sql = "SELECT nombre_animal FROM tbl_animales WHERE id_animal = ?";
        
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idAnimal);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                nombreAnimal = rs.getString("nombre_animal");
            }
            rs.close();
        }
        
        return nombreAnimal;
    }
    
    private String obtenerNombreAdoptante(int idAdoptante, Connection conn) throws Exception {
        String nombreAdoptante = "";
        String sql = "{CALL obtenerNombreAdoptante(?)}";
        
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idAdoptante);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                nombreAdoptante = rs.getString("nombre_adoptante");
            }
            rs.close();
        }
        
        return nombreAdoptante;
    }
    
    private String obtenerCorreoAdoptante(int idAdoptante, Connection conn) throws Exception {
        String nombreAdoptante = "";
        String sql = "{CALL obtenerCorreoAdoptante(?)}";
        
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idAdoptante);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                nombreAdoptante = rs.getString("correo_solicitud");
            }
            rs.close();
        }
        
        return nombreAdoptante;
    }
    
    public Solicitudes obtenerInfoSolicitud(int idSolicitud) throws Exception {
        Solicitudes s = new Solicitudes();
        
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();
        
        String sql = "{CALL getInfoSolicitud(?, ?, ?, ?)}";
        CallableStatement cstmt = con.prepareCall(sql);
        
        cstmt.setInt(1, idSolicitud);
        
        cstmt.registerOutParameter(2, Types.VARCHAR);
        cstmt.registerOutParameter(3, Types.VARCHAR);
        cstmt.registerOutParameter(4, Types.VARCHAR);
        
        cstmt.execute();
        
        s.setNombreAnimal(cstmt.getString(2));
        s.setNombreAdoptante(cstmt.getString(3));
        s.setCorreo(cstmt.getString(4));
        s.setIdSolicitud(idSolicitud);
        
        cstmt.close();
        conn.close();
        
        return s;
    }
    
}

package org.utl.dsm.huellas.control;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Centros;

public class centroControl {

    private Centros fill(ResultSet rs) throws Exception {
        Centros c = new Centros();
        c.setIdCentro(rs.getInt("id_centro"));
        c.setNombreCentro(rs.getString("nombre_centro"));
        c.setDireccionCentro(rs.getString("direccion_centro"));
        c.setTelefonoCentro(rs.getString("telefono_centro"));
        c.setCorreoCentro(rs.getString("correo_centro"));
        c.setHorarioAperturaCentro(rs.getString("horarioApertura_centro"));
        c.setHorarioCierreCentro(rs.getString("horarioCierre_centro"));
        c.setEstatus(rs.getInt("estatus_centro"));
        return c;
    }
public List<Centros> getNombresCentros() throws Exception {
    String sql = "SELECT id_centro, nombre_centro FROM tbl_centros WHERE estatus_centro = 1";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    PreparedStatement stmt = conns.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    List<Centros> listaCentros = new ArrayList<>();
 
    while (rs.next()) {
        Centros c = new Centros();
        c.setIdCentro(rs.getInt("id_centro"));
        c.setNombreCentro(rs.getString("nombre_centro"));
        listaCentros.add(c);
    }
 
    rs.close();
    stmt.close();
    conn.close();
    return listaCentros;
}
    public List<Centros> getAll(String filtro) throws Exception {
        String sql = "SELECT * FROM tbl_centros";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        PreparedStatement ps = conns.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Centros> centro = new ArrayList<>();
        while (rs.next()) {
            centro.add(fill(rs));
        }
        rs.close();
        ps.close();
        conns.close();
        return centro;
    }

    public void insertarCentro(Centros c) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String query = "{CALL insertarCentro(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement stmt = con.prepareCall(query);
        stmt.setString(1, c.getNombreCentro());
        stmt.setString(2, c.getDireccionCentro());
        stmt.setString(3, c.getTelefonoCentro());
        stmt.setString(4, c.getCorreoCentro());
        stmt.setString(5, c.getHorarioAperturaCentro());
        stmt.setString(6, c.getHorarioCierreCentro());
        stmt.setInt(7, c.getEstatus());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public void actualizarCentro(Centros c) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String query = "{CALL actualizarCentro(?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement stmt = con.prepareCall(query);
        stmt.setInt(1, c.getIdCentro());
        stmt.setString(2, c.getNombreCentro());
        stmt.setString(3, c.getDireccionCentro());
        stmt.setString(4, c.getTelefonoCentro());
        stmt.setString(5, c.getCorreoCentro());
        stmt.setString(6, c.getHorarioAperturaCentro());
        stmt.setString(7, c.getHorarioCierreCentro());
        stmt.setInt(8, c.getEstatus());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public void eliminarCentro(int idCentro) throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String query = "{CALL eliminarCentro(?)}";
        CallableStatement stmt = con.prepareCall(query);
        stmt.setInt(1, idCentro);

        stmt.execute();
        stmt.close();
        con.close();
    }

    public int contarCentrosActivos() throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String query = "{CALL contarCentrosActivos()}";
        CallableStatement stmt = con.prepareCall(query);
        ResultSet rs = stmt.executeQuery();

        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total_activos");
        }

        rs.close();
        stmt.close();
        con.close();

        return total;
    }

    public int contarCentrosInactivos() throws Exception {
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String query = "{CALL contarCentrosInactivos()}";
        CallableStatement stmt = con.prepareCall(query);
        ResultSet rs = stmt.executeQuery();

        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total_inactivos");
        }

        rs.close();
        stmt.close();
        con.close();

        return total;
    }
}

  
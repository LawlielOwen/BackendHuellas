package org.utl.dsm.huellas.control;

import org.utl.dsm.huellas.modelo.Donaciones;
import org.utl.dsm.huellas.db.ConexionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ControllerDonaciones {

   public int generarD(Donaciones obj) throws Exception {
    ConexionMySQL connMysql = new ConexionMySQL();
    Connection conn = connMysql.open();

    CallableStatement cstmt = conn.prepareCall(
        "{call registrar_donacion_pago(?, ?, ?, ?, ?, ?, ?, ?)}"
    );

    cstmt.setString(1, obj.getNombreDonante());
    cstmt.setDouble(2, obj.getMontoDonacion());
    cstmt.setString(3, obj.getCentroDonacion());
    cstmt.setNull(4, java.sql.Types.INTEGER);
    cstmt.setInt(5, obj.getIdCentro());
    cstmt.setString(6, obj.getBanco_pago());
    cstmt.setString(7,"Debito" );
    cstmt.setInt(8, obj.getUltimosDigitos());

    cstmt.executeUpdate();

    CallableStatement cstmt2 = conn.prepareCall("SELECT LAST_INSERT_ID()");
    var rs = cstmt2.executeQuery();
    int idDonacion = 0;
    if (rs.next()) {
        idDonacion = rs.getInt(1);
        obj.setIdDonacion(idDonacion);
    }

    rs.close();
    cstmt.close();
    cstmt2.close();
    connMysql.close();

    return idDonacion;
}
   
   //controller por parte del empleado 
   
         public List<Donaciones> getDonaciones() throws Exception {
    String sql = "SELECT nombre_donacion,"
            + "monto_donacion,fecha_donacion,"
            + "centroBeneficiado_donacion "
            + " FROM tbl_donaciones order by id_donacion desc";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    PreparedStatement stmt = conns.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    List<Donaciones> listaCentros = new ArrayList<>();

    while (rs.next()) {
        Donaciones c = new Donaciones();
        c.setNombreDonante(rs.getString("nombre_donacion"));
        c.setMontoDonacion(rs.getDouble("monto_donacion"));
        c.setCentroDonacion(rs.getString("centroBeneficiado_donacion"));
       c.setFechaDonacion(rs.getDate("fecha_donacion"));
        listaCentros.add(c);
    }

    rs.close();
    stmt.close();
    conn.close();
    return listaCentros;
}

               public List<Donaciones> getCount() throws Exception {
    String sql = "SELECT count(id_donacion) as donaciones"
            + " FROM tbl_donaciones where id_donacion >=0";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    PreparedStatement stmt = conns.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    List<Donaciones> listaCentros = new ArrayList<>();

    while (rs.next()) {
        Donaciones c = new Donaciones();
        c.setConteoDonaciones(rs.getInt("donaciones"));
     
        listaCentros.add(c);
    }

    rs.close();
    stmt.close();
    conn.close();
    return listaCentros;
}
               
       public List<Donaciones> getTotal() throws Exception {
    String sql = "SELECT sum(monto_donacion) as montoDonacion"
            + " FROM tbl_donaciones";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    PreparedStatement stmt = conns.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();
    List<Donaciones> listaCentros = new ArrayList<>();

    while (rs.next()) {
        Donaciones c = new Donaciones();
        c.setTotalDonacion(rs.getDouble("montoDonacion"));
     
        listaCentros.add(c);
    }

    rs.close();
    stmt.close();
    conn.close();
    return listaCentros;
}

       //prueba con call,para inteliji
       public int contarDisponibles() throws Exception {
    String sql = "{CALL sp_conteoDonaciones()}";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();

    CallableStatement cstmt = conns.prepareCall(sql);
    ResultSet rs = cstmt.executeQuery();

    int disponibles = 0;
    if (rs.next()) {
        disponibles = rs.getInt("donaciones");
    }

    rs.close();
    cstmt.close();
    conn.close();

    return disponibles;
}
      
         public int sumaDonaciones() throws Exception {
    String sql = "{CALL sp_sumaDonaciones()}";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();

    CallableStatement cstmt = conns.prepareCall(sql);
    ResultSet rs = cstmt.executeQuery();

    int disponibles = 0;
    if (rs.next()) {
        disponibles = rs.getInt("montoDonacion");
    }

    rs.close();
    cstmt.close();
    conn.close();

    return disponibles;
}
      
        public List<Donaciones> todasDonacioneses() throws Exception {
    String sql ="{CALL sp_buscarDonaciones()}";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    CallableStatement cstmt = conns.prepareCall(sql);
    ResultSet rs = cstmt.executeQuery();
    List<Donaciones> listaCentros = new ArrayList<>();

    while (rs.next()) {
        Donaciones c = new Donaciones();
        c.setNombreDonante(rs.getString("nombre_donacion"));
        c.setMontoDonacion(rs.getDouble("monto_donacion"));
        c.setCentroDonacion(rs.getString("centroBeneficiado_donacion"));
       c.setFechaDonacion(rs.getDate("fecha_donacion"));
        listaCentros.add(c);
    }

    rs.close();
    cstmt.close();
    conn.close();
    return listaCentros;
}
     
   
}
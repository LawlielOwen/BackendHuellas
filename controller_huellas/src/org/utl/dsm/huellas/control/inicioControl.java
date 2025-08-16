
package org.utl.dsm.huellas.control;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Animales;
public class inicioControl {
        private Animales fill(ResultSet rs) throws Exception {
        Animales a = new Animales();
        a.setIdAnimal(rs.getInt("id_animal"));
        a.setNombreAnimal(rs.getString("nombre_animal"));
        a.setGenero(rs.getString("genero_animal"));
        a.setEdad(rs.getString("edad_animal"));
        a.setPeso(rs.getDouble("peso_animal"));
        a.setEspecie(rs.getString("especie_animal"));
        a.setDescripcion(rs.getString("descripcion_animal"));
        a.setRaza(rs.getString("raza_animal"));
        a.setTamano(rs.getString("tamaño_animal"));
        int estatusInt = rs.getInt("estatus_animal");
        a.setEstatus(estatusInt);
        a.setEstatusTexto(tomarEstatus(estatusInt));
        a.setCodigoAnimal(rs.getString("codigo_animal"));
a.setCaracter(rs.getString("caracter_animal"));
        String base64 = rs.getString("foto_animal");

        byte[] imageBytes = Base64.getDecoder().decode(base64);
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        String mimeType = URLConnection.guessContentTypeFromStream(bais);
        if (mimeType == null) {
            mimeType = "image/jpeg";
        }
        String fotoConTipo = "data:" + mimeType + ";base64," + base64;
        a.setFoto(fotoConTipo);
        a.setIdCentro(rs.getInt("id_centro"));
        return a;
    }

    private String tomarEstatus(int Estatus) {
        if (Estatus == 1) {
            return "En adopcion";
        } else {
            return "Adoptado";
        }

    }
  
       public List<Animales> busquedaAnimal(String nombre) throws Exception {
    List<Animales> lista = new ArrayList<>();
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    CallableStatement cstmt = conns.prepareCall("{CALL busquedaGeneral(?)}");

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
          public List<Animales> busquedaPerros(String nombre) throws Exception {
    List<Animales> lista = new ArrayList<>();
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    CallableStatement cstmt = conns.prepareCall("{CALL busquedaPerros(?)}");

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
             public List<Animales> busquedaGatos(String nombre) throws Exception {
    List<Animales> lista = new ArrayList<>();
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    CallableStatement cstmt = conns.prepareCall("{CALL busquedaGatos(?)}");

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
             public List<Animales> filtrarPerros(Animales a) throws Exception {
        List<Animales> lista = new ArrayList<>();
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String sql = "CALL filtrarPerros(?,?,?)";
        CallableStatement stmt = con.prepareCall(sql);
        stmt.setString(1, a.getTamano());
         stmt.setString(2, a.getGenero());
         stmt.setString(3, a.getCaracter());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        stmt.close();
        con.close();

        return lista;
    }
              public List<Animales> filtrarTodos(Animales a) throws Exception {
        List<Animales> lista = new ArrayList<>();
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String sql = "CALL filtrarAnimalesTodos(?,?,?,?)";
        CallableStatement stmt = con.prepareCall(sql);
        stmt.setString(1, a.getEspecie());
        stmt.setString(2, a.getEdad());
         stmt.setString(3, a.getGenero());
         stmt.setString(4, a.getCaracter());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        stmt.close();
        con.close();

        return lista;
    }
                   public List<Animales> filtrarGatos(Animales a) throws Exception {
        List<Animales> lista = new ArrayList<>();
        ConexionMySQL conn = new ConexionMySQL();
        Connection con = conn.open();

        String sql = "CALL filtrarGatos(?,?,?)";
        CallableStatement stmt = con.prepareCall(sql);
        stmt.setString(1, a.getEdad());
         stmt.setString(2, a.getGenero());
            stmt.setString(3, a.getCaracter());
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        stmt.close();
        con.close();

        return lista;
    }
                   public List<Animales> getAll(String filtro) throws Exception {
        String sql = "select * from verEnadopcion";
        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);
        ResultSet rs = cstmt.executeQuery();
        List<Animales> animales = new ArrayList<>();
        while (rs.next()) {
            animales.add(fill(rs));
        }
        rs.close();
        cstmt.close();
        conn.close();
        return animales;
    }
}

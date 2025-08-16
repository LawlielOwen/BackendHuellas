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

public class animalesControl {

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
        a.setCaracter(rs.getString("caracter_animal"));
        int estatusInt = rs.getInt("estatus_animal");
        a.setEstatus(estatusInt);
        a.setEstatusTexto(tomarEstatus(estatusInt));
        a.setCodigoAnimal(rs.getString("codigo_animal"));

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

    public List<Animales> getAll(String filtro) throws Exception {
        String sql = "select * from verAnimales";
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
        public List<Animales> getPerros(String filtro) throws Exception {
        String sql = "select * from verperros";
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
           public List<Animales> getGerros(String filtro) throws Exception {
        String sql = "select * from vergatos";
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

   public List<Animales> filtrarAnimales(Animales a) throws Exception {
    List<Animales> lista = new ArrayList<>();
    ConexionMySQL conn = new ConexionMySQL();
    Connection con = conn.open();

    String sql = "CALL filtrarAnimales(?, ?)";
    CallableStatement stmt = con.prepareCall(sql);
    stmt.setString(1, a.getEspecie());
    stmt.setInt(2, a.getEstatus());

    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
        lista.add(fill(rs));
    }

    rs.close();
    stmt.close();
    con.close();

    return lista;
}
    public void insertarAnimal(Animales a) throws Exception {

        String codigoAnimal = generarCodigo(a.getNombreAnimal(), a.getRaza());

        String fotoBase64 = a.getFoto();
        if (fotoBase64.contains(",")) {
            fotoBase64 = fotoBase64.split(",")[1];
        }
        ConexionMySQL conexion = new ConexionMySQL();
        Connection conn = conexion.open();

        String call = "{CALL agregarA(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";
        CallableStatement cstmt = conn.prepareCall(call);

        cstmt.setString(1, a.getNombreAnimal());
        cstmt.setString(2, a.getGenero());
        cstmt.setString(3, a.getEdad());
        cstmt.setDouble(4, a.getPeso());
        cstmt.setString(5, a.getEspecie());
        cstmt.setString(6, a.getDescripcion());
        cstmt.setString(7, a.getRaza());
        cstmt.setString(8, a.getTamano());
        cstmt.setString(9, codigoAnimal);
        cstmt.setString(10, fotoBase64);
        cstmt.setInt(11, a.getIdCentro());
        cstmt.setString(12, a.getCaracter());
        cstmt.executeUpdate();

        cstmt.close();
        conn.close();
    }

    private String generarCodigo(String nombre, String raza) throws Exception {
        String nombrePart = nombre.length() >= 3 ? nombre.substring(0, 3).toUpperCase() : nombre.toUpperCase();
        String razaPart = raza.length() >= 2 ? raza.substring(0, 2).toUpperCase() : raza.toUpperCase();
        String prefijo = nombrePart + razaPart;

        String sql = "SELECT COUNT(*) FROM tbl_animales WHERE LEFT(codigo_animal, 5) = ?";
        ConexionMySQL conexion = new ConexionMySQL();
        Connection conn = conexion.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, prefijo);
        ResultSet rs = pstmt.executeQuery();

        int conteo = 0;
        if (rs.next()) {
            conteo = rs.getInt(1);
        }

        rs.close();
        pstmt.close();
        conn.close();

        String sufijo = String.format("%02d", conteo);

        return prefijo + sufijo;
    }

    public void modificarAanimal(Animales a) throws Exception {
        String sql = "{CALL  modAnimal(?, ?, ?, ?, ?,?,?,?,?,?,?)}";

        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);

        cstmt.setInt(1, a.getIdAnimal());
        cstmt.setString(2, a.getNombreAnimal());
        cstmt.setString(3, a.getGenero());
        cstmt.setString(4, a.getEdad());
        cstmt.setDouble(5, a.getPeso());
        cstmt.setString(6, a.getEspecie());
        cstmt.setString(7, a.getDescripcion());
        cstmt.setString(8, a.getRaza());
        cstmt.setString(9, a.getTamano());
        cstmt.setInt(10, a.getIdCentro());
                cstmt.setString(11, a.getCaracter());
        cstmt.executeUpdate();
        cstmt.close();
        conn.close();
    }
    public void eliminarAanimal(Animales a) throws Exception {
        String sql = "{CALL  eliminarA(?)}";

        ConexionMySQL conn = new ConexionMySQL();
        Connection conns = conn.open();
        CallableStatement cstmt = conns.prepareCall(sql);

        cstmt.setInt(1, a.getIdAnimal());
        cstmt.executeUpdate();
        cstmt.close();
        conn.close();
    }
       public List<Animales> buscarAnimal(String nombre) throws Exception {
    List<Animales> lista = new ArrayList<>();
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();
    CallableStatement cstmt = conns.prepareCall("{CALL buscarA(?)}");

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
public int contarDisponibles() throws Exception {
    String sql = "{CALL contarDisponibles()}";
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

public int contarAdoptados() throws Exception {
    String sql = "{CALL contarAdoptados()}";
    ConexionMySQL conn = new ConexionMySQL();
    Connection conns = conn.open();

    CallableStatement cstmt = conns.prepareCall(sql);
    ResultSet rs = cstmt.executeQuery();

    int adoptados = 0;
    if (rs.next()) {
        adoptados = rs.getInt("totalAdoptados");
    }

    rs.close();
    cstmt.close();
    conn.close();

    return adoptados;
}

}

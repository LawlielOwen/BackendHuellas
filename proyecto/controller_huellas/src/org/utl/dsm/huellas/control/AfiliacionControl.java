
package org.utl.dsm.huellas.control;

import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Afiliacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AfiliacionControl {
    
     // GET ALL
    public List<Afiliacion> getAllAfiliados() throws Exception {
        List<Afiliacion> lista = new ArrayList<>();
        String query = "{CALL getAllAfiliados()}";
        try (
            Connection conn = new ConexionMySQL().open();
            CallableStatement cs = conn.prepareCall(query)
        ) {
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Afiliacion a = new Afiliacion();
                a.setIdAfiliado(rs.getInt("id_afiliado"));
                a.setMascotasAfiliado(rs.getString("mascotas_afiliado"));
                a.setTipoAfiliado(rs.getString("tipo_afiliado"));
                a.setFrecuenciaAfiliado(rs.getString("frecuencia_afiliado"));
                a.setDisponibilidadAfiliado(rs.getString("disponibilidad_afiliado"));
                a.setDeseoContactoAfiliado(rs.getString("deseoContacto_afliado"));
                a.setMedioContactoAfiliado(rs.getString("medioContacto_afilado"));
                a.setComentariosAfiliado(rs.getString("comentarios_afiliado"));
                a.setIdCentro(rs.getInt("id_centro"));
                a.setIdPersona(rs.getInt("id_persona"));

                // Nuevos campos del join
                a.setNombre(rs.getString("nombre_persona"));
                a.setCorreo(rs.getString("correo_persona"));
                a.setTelefono(rs.getString("telefono_persona"));
a.setIdPersona(rs.getInt("id_persona"));
                lista.add(a);
            }
        }
        return lista;
    }

    // ELIMINAR
    public void eliminarAfiliado(int idAfiliado) throws Exception {
        String query = "{CALL deleteAfiliado(?)}";
        try (
            Connection conn = new ConexionMySQL().open();
            CallableStatement cs = conn.prepareCall(query)
        ) {
            cs.setInt(1, idAfiliado);
            cs.execute();
        }
    }

    // MODIFICAR
    public void modificarAfiliadoPersona(Afiliacion a) throws Exception {
        String query = "{CALL modificarAfiliadoPersona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement cs = conn.prepareCall(query);

        cs.setInt(1, a.getIdPersona());
        cs.setString(2, a.getNombre());
        cs.setString(3, a.getCorreo());
        cs.setString(4, a.getTelefono());
        cs.setInt(5, a.getIdAfiliado());
        cs.setString(6, a.getMascotasAfiliado());
        cs.setString(7, a.getTipoAfiliado());
        cs.setString(8, a.getFrecuenciaAfiliado());
        cs.setString(9, a.getDisponibilidadAfiliado());
        cs.setString(10, a.getDeseoContactoAfiliado());
        cs.setString(11, a.getMedioContactoAfiliado());
        cs.setString(12, a.getComentariosAfiliado());
        cs.setInt(13, a.getIdCentro());

        cs.execute();
        cs.close();
        conn.close();
    }

    // AGREGAR (adentro de la clase, no afuera)
    public void agregarAfiliado(Afiliacion a) throws Exception {
        String sql = "{CALL agregarAfiliadoPersona(?,?,?,?,?,?,?,?,?,?,?)}";
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement cs = conn.prepareCall(sql);

      cs.setString(1, a.getNombre());
        cs.setString(2, a.getCorreo());
        cs.setString(3, a.getTelefono());
        cs.setString(4, a.getMascotasAfiliado());
        cs.setString(5, a.getTipoAfiliado());
        cs.setString(6, a.getFrecuenciaAfiliado());
        cs.setString(7, a.getDisponibilidadAfiliado());
        cs.setString(8, a.getDeseoContactoAfiliado());
        cs.setString(9, a.getMedioContactoAfiliado());
        cs.setString(10, a.getComentariosAfiliado());
        cs.setInt(11, a.getIdCentro());

        cs.execute();
        cs.close();
        conn.close();
    }
    
   public List<Afiliacion> buscarAfiliadosPorNombreOCorreo(String texto) throws Exception {
    List<Afiliacion> lista = new ArrayList<>();
    String sql = "{CALL buscarAfiliadosPorNombreOCorreo(?)}";
    try (
        Connection conn = new ConexionMySQL().open();
        CallableStatement cs = conn.prepareCall(sql)
    ) {
        cs.setString(1, texto);
        ResultSet rs = cs.executeQuery();
        while (rs.next()) {
            Afiliacion a = new Afiliacion();
            a.setIdAfiliado(rs.getInt("id_afiliado"));
            a.setMascotasAfiliado(rs.getString("mascotas_afiliado"));
            a.setTipoAfiliado(rs.getString("tipo_afiliado"));
            a.setFrecuenciaAfiliado(rs.getString("frecuencia_afiliado"));
            a.setDisponibilidadAfiliado(rs.getString("disponibilidad_afiliado"));
            a.setDeseoContactoAfiliado(rs.getString("deseoContacto_afliado"));
            a.setMedioContactoAfiliado(rs.getString("medioContacto_afilado"));
            a.setComentariosAfiliado(rs.getString("comentarios_afiliado"));
            a.setIdCentro(rs.getInt("id_centro"));
            a.setIdPersona(rs.getInt("id_persona"));
            a.setNombre(rs.getString("nombre_persona"));
            a.setCorreo(rs.getString("correo_persona"));
            a.setTelefono(rs.getString("telefono_persona"));
            lista.add(a);
        }
    }
    return lista;
}


}
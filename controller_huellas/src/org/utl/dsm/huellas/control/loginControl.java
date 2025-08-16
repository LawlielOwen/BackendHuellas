
package org.utl.dsm.huellas.control;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.huellas.db.ConexionMySQL;
import org.utl.dsm.huellas.modelo.Empleado;
public class loginControl {
    public int logInEmpleado(Empleado e) throws Exception {

        String sql = "{call loginEmpleado(?,?,?)}";
        int usuarioEncontrado = 0;
        ConexionMySQL connMySQL = new ConexionMySQL();
        Connection conn = connMySQL.open();
        CallableStatement cstmt = conn.prepareCall(sql);
        cstmt.setString(1, e.getCorreo());
        cstmt.setString(2, e.getContraseña());

        cstmt.registerOutParameter(3, Types.INTEGER);
        cstmt.executeUpdate();
        usuarioEncontrado = cstmt.getInt(3);
        e.setIdEmpleado(usuarioEncontrado);
        int idUsuario = e.getIdEmpleado();

        cstmt.close();
        connMySQL.close();
        System.out.println(idUsuario);
        return idUsuario;

    }
}

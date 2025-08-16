package org.utl.dsm.huellas.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionMySQL {
    Connection conn;

    public Connection open() {
        // Leemos los datos desde variables de entorno
      String user = System.getenv("DB_USER");
String password = System.getenv("DB_PASSWORD");
String url = System.getenv("DB_URL");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Excepcion encontrada");
            }
        }
    }
}

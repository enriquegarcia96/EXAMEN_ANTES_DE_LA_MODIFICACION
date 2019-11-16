package com.GARCIA.libs;
import java.sql.*;

public class Conexion {



    private static Connection con;

    private Conexion() {}

    public static Connection abrirConexion() {
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/sistema_bancario?useTimezone=true&serverTimezone=UTC",
                        "root", "Dragon97");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return con;
    }


}

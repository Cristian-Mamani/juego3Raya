package com.edu.pe.config;

import java.sql.*;

public class Conexion {

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/BD_JUEGO";
            String usr = "root";
            String psw = "";
            con = DriverManager.getConnection(url, usr, psw);
            System.out.println("Conexion exitosa!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return con;
    }
    
    public static void main(String[] args) {
        Conexion.getConexion();
    }
}

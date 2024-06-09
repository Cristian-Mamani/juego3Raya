package com.edu.pe.modelo.dao;

import com.edu.pe.config.Conexion;
import com.edu.pe.modelo.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ResultDAO {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;

    public int Insertar(Result obj) {
        int result = 0;
        try {
            conn = Conexion.getConexion();
            String sql = "INSERT INTO resultados(nombre_partida,nombre_jugador1,nombre_jugador2,ganador,punto,estado) "
                    + " VALUES(?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getNombrePartida());
            ps.setString(2, obj.getNombreJugador1());
            ps.setString(3, obj.getNombreJugador2());
            ps.setString(4, obj.getNombreGanador());
            ps.setInt(5, obj.getPuntuacion());
            ps.setString(6, obj.getEstado());
            result = ps.executeUpdate();

            if (result > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex) {

            }
        }
        return result;
    }

    public int Actualizar(Result obj) {
        int result = 0;
        try {
            conn = Conexion.getConexion();
            String sql = "UPDATE resultados SET ganador = ?, punto=?,estado=?"
                    + " WHERE id_resultado = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getNombreGanador());
            ps.setInt(2, obj.getPuntuacion());
            ps.setString(3, obj.getEstado());
            ps.setInt(4, obj.getId());
            result = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {

            }
        }
        return result;
    }

    public ArrayList<Result> Listar() {
        ArrayList<Result> lista = new ArrayList<>();

        try {
            conn = Conexion.getConexion();
            String sql = "select * from resultados";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Result obj = new Result();
                obj.setId(rs.getInt("id_resultado"));
                obj.setNombrePartida(rs.getString("nombre_partida"));
                obj.setNombreJugador1(rs.getString("nombre_jugador1"));
                obj.setNombreJugador2(rs.getString("nombre_jugador2"));
                obj.setNombreGanador(rs.getString("ganador"));
                obj.setPuntuacion(rs.getInt("punto"));
                obj.setEstado(rs.getString("estado"));
                lista.add(obj);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex) {

            }
        }

        return lista;
    }

    public int MaximaPartida() {
        int nro = 0;

        try {
            conn = Conexion.getConexion();
            String sql = "SELECT IFNULL(MAX(id_resultado),0) + 1 FROM resultados";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                nro = rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex) {

            }
        }

        return nro;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aghsk
 */
public class GestorDeConexiones {

    Connection conexion = null;
    
    //Metodo para conectarte a la BBDD
    public void conection() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521/CENTROESTUDIOS";
            String user = "root";
            String password = "4002";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("FUNCIONA");
        } catch (SQLException ex) {
            System.out.println("ERROR:direccion no valido o usuario/clave");
        } catch (ClassNotFoundException ex1) {
            Logger.getLogger(GestorDeConexiones.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    //Metodo para desconectarte de la BBDD
    public void cerrarConexion() {
        try {
            conexion.close();
            System.out.println("Desconectado de Peliculas");
        } catch (SQLException ex) {
            System.out.println("Error en la desconexión");
        }
    }

    //MOSTRAR TABLAS
    public ResultSet mostrarTabla(String query) {
        Statement sta;
        ResultSet rs = null;
        try {
            sta = conexion.createStatement();
            rs = sta.executeQuery(query);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return rs;
    }

    //METODO PARA DE ALTA EN LA BBDD
    //INSERTAR CRITICA
    public void insertarCritica(String id_Critica, String cod_critico, String critica_Nombre, String texto_Critica, String puntuacion_Critica) {
        Statement sta;
        try {
            conexion.setAutoCommit(false);

            sta = conexion.createStatement();
            sta.executeUpdate("INSERT INTO Critica VALUES('" + id_Critica
                    + "',(SELECT REF(c) FROM Critico c WHERE c.cod_Critico_Critica='"
                    + cod_critico + "'),'"
                    + critica_Nombre + "','"
                    + texto_Critica + "','"
                    + puntuacion_Critica + "')");
            sta.close();

            conexion.commit();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error al insertar Crítica");
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex1) {
                    System.out.println(ex1.toString());
                    System.out.println("Error al insertar crítica.");
                }
            }
        }
    }

    //INSERTAR CRITICO
    public void insertarCritico(String id_Critico, String nombre_Critico, String apodo_Critico, String cod_Critico_Critica) {
        Statement sta;
        try {
            sta = conexion.createStatement();
            sta.executeUpdate("INSERT INTO Critico(id_Critico, nombre_Critico, apodo_Critico, cod_Critico_Critica) VALUES\n"
                    + "( '" + id_Critico + "', '" + nombre_Critico + "', '" + apodo_Critico + "', '" + cod_Critico_Critica + "' )");
            sta.close();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    //METODO PARA DAR DE BAJA EN LA BBDD
    //ELIMINAR CRITICA 
    public void eliminarCritica(String id_Critica) {

        Statement sta;
        try {
            conexion.setAutoCommit(false);
            sta = conexion.createStatement();

            sta.executeUpdate("DELETE FROM Critica WHERE id_Critica ='" + id_Critica + "'");

            sta.close();
            conexion.commit();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error al eliminar Critica.");
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex1) {
                    System.out.println(ex1.toString());
                    System.out.println("Error al eliminar critica.");
                }
            }
        }
    }

    //ELIMINAR CRITICO HACE FALTA ELIMINAR CRITICAS ANTES POR FOREIGN KEY
    public void eliminarCritico(String cod_Critico_Critica) {
        Statement sta;
        try {

            sta = conexion.createStatement();

            //sta.executeUpdate("DELETE FROM critica WHERE cod_critico = '" + cod_Critico_Critica + "';");
            sta.executeUpdate("DELETE FROM Critico WHERE cod_Critico_Critica ='" + cod_Critico_Critica + "'");

            sta.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error al eliminar Critico.");
        }
    }

    //PREPARED STATEMENT
    //Peliculas de un Director
    public String cosulta_PeliculasDirector(String director_Pelicula) {
        String query = "SELECT * FROM pelicula WHERE director_Pelicula= ?";
        String consulta = "";
        try {
            PreparedStatement pst = conexion.prepareStatement(query);
            pst.setString(1, director_Pelicula);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id_Pelicula")
                        + ", Titulo: " + rs.getString("nombre_Pelicula")
                        + ", Genero: " + rs.getString("genero_Pelicula")
                        + ", Director: " + rs.getString("director_Pelicula")
                        + ", Duracion: " + rs.getString("duracion_Pelicula")
                        + ", Estudio: " + rs.getString("estudio"));
                consulta = "ID: " + rs.getString("id_Pelicula")
                        + ", Titulo: " + rs.getString("nombre_Pelicula")
                        + ", Genero: " + rs.getString("genero_Pelicula")
                        + ", Director: " + rs.getString("director_Pelicula")
                        + ", Duracion: " + rs.getString("duracion_Pelicula")
                        + ", Estudio: " + rs.getString("estudio");
            }

            rs.close();
            pst.close();
            return consulta;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error en el Prepared Statement.");
            return "ERROR";
        }
    }

    //BUSQUEDA CRITICAS DE UN CRITICO
    public String cosulta_CriticasCritico(String cod_critico) {
        String query = "SELECT * FROM critica WHERE id_Critica= ?";
        String consulta = "";
        try {
            PreparedStatement pst = conexion.prepareStatement(query);
            pst.setString(1, cod_critico);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id_Critica")
                        + ", Cod_Critico: " + rs.getString("cod_critico")
                        + ", Pelicula: " + rs.getString("critica_Nombre")
                        + ", Critica: " + rs.getString("texto_Critica")
                        + ", Puntuación: " + rs.getString("puntuacion_Critica"));
                consulta = "ID: " + rs.getString("id_Critica")
                        + ", Cod_Critico: " + rs.getString("cod_critico")
                        + ", Pelicula: " + rs.getString("critica_Nombre")
                        + ", Critica: " + rs.getString("texto_Critica")
                        + ", Puntuación: " + rs.getString("puntuacion_Critica");
            }

            rs.close();
            pst.close();
            return consulta;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error en el Prepared Statement.");
            return "ERROR";
        }
    }
    //BUSQUEDA POR GENERO

    public String cosulta_Genero(String genero_Pelicula) {
        String query = "SELECT * FROM Pelicula WHERE genero_Pelicula= ?";
        String consulta = "";
        try {
            PreparedStatement pst = conexion.prepareStatement(query);
            pst.setString(1, genero_Pelicula);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id_Pelicula")
                        + ", Titulo: " + rs.getString("nombre_Pelicula")
                        + ", Genero: " + rs.getString("genero_Pelicula")
                        + ", Director: " + rs.getString("director_Pelicula")
                        + ", Duracion: " + rs.getString("duracion_Pelicula")
                        + ", Estudio: " + rs.getString("estudio"));
                consulta = "ID: " + rs.getString("id_Pelicula")
                        + ", Titulo: " + rs.getString("nombre_Pelicula")
                        + ", Genero: " + rs.getString("genero_Pelicula")
                        + ", Director: " + rs.getString("director_Pelicula")
                        + ", Duracion: " + rs.getString("duracion_Pelicula")
                        + ", Estudio: " + rs.getString("estudio");
            }

            rs.close();
            pst.close();
            return consulta;

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error en el Prepared Statement.");
            return "ERROR";
        }
    }

    //EDITAR DATOS DE LA BBDD
    //CRITICO, SOLO SE PUEDE CAMBIAR EL NOMBRE
    public void editarCritico(String id_Critico, String nombre_Critico, String apodoCritico) {
        Statement sta;
        try {
            sta = conexion.createStatement();
            String comanda = "UPDATE Critico SET nombre_critico ='" + nombre_Critico + "' , apodo_critico='" + apodoCritico
                    + "' WHERE id_critico='" + id_Critico + "'";
            sta.executeUpdate(comanda);

            System.out.println(sta);
            sta.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("Error al actualizar critico.");
        }
    }

    //EDITAR CRITICAS (NOMBRE, CRITICA Y PUNTUACION)
    public void editarCritica(String id, String critica_Nombre, String texto_Critica, String puntuacion_Critica) {
        Statement sta;
        try {

            conexion.setAutoCommit(false);
            sta = conexion.createStatement();

            sta.executeUpdate("UPDATE critica SET critica_Nombre = '" + critica_Nombre
                    + "', texto_Critica= '" + texto_Critica + "', puntuacion_Critica= '"
                    + puntuacion_Critica + "' WHERE id_Critica = " + id + ";");

            sta.close();

            conexion.commit();

        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("Error al actualizar critica.");
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex1) {
                    System.out.println(ex1.toString());
                    System.out.println("Error al actualizar critica.");
                }
            }
        }
    }
}

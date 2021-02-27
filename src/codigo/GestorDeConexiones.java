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

//    public GestorDeConexiones() {
//
//        try {
//            String urll = "jdbc:oracle:thin:@localhost:1521/CENTROESTUDIOS";
//            String user = "root";
//            String password = "4002";
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            conexion = DriverManager.getConnection(urll, user, password);
//        } catch (SQLException ex) {
//            System.out.println("ERROR:direccion no valido o usuario/clave");
//        } catch (ClassNotFoundException ex1) {
//            Logger.getLogger(GestorDeConexiones.class.getName()).log(Level.SEVERE, null, ex1);
//        }
//    }

    //Metodo para conectarte a la BBDD
    public void conection() {
        try {
            String urll = "jdbc:oracle:thin:@localhost:1521/CENTROESTUDIOS";
            String user = "root";
            String password = "4002";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection(urll, user, password);
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
//    //INSERTAR PELICULA
//    public void insertarPelicula(String id_Pelicula, String nombre_Pelicula, String genero_Pelicula, String director_Pelicula, String duracion_Pelicula) {
//        Statement sta;
//        try {
//            conexion.setAutoCommit(false);
//
//            sta = conexion.createStatement();
//            sta.executeUpdate("INSERT INTO pelicula(id_Pelicula, nombre_Pelicula, genero_Pelicula, director_Pelicula,duracion_Pelicula)"
//                    + " VALUES(" + id_Pelicula + ", '" + nombre_Pelicula + "', '" + genero_Pelicula + "', '" + director_Pelicula + "', '" + duracion_Pelicula + "');");
//            sta.close();
//
//            conexion.commit();
//
//        } catch (SQLException ex) {
//            System.out.println(ex.toString());
//            System.out.println("Error al insertar Película");
//            if (conexion != null) {
//                try {
//                    conexion.rollback();
//                } catch (SQLException ex1) {
//                    System.out.println(ex1.toString());
//                    System.out.println("Error al insertar película.");
//                }
//            }
//        }
//    }
    //INSERTAR CRITICA
    public void insertarCritica(String id_Critica, String cod_critico, String critica_Nombre, String texto_Critica, String puntuacion_Critica) {
        Statement sta;
        try {
            conexion.setAutoCommit(false);

            sta = conexion.createStatement();
            sta.executeUpdate("INSERT INTO critica(id_Critica, cod_critico,critica_Nombre, texto_Critica, puntuacion_Critica)"
                    + " VALUES(" + id_Critica + ", '" + cod_critico + "', '" + critica_Nombre + "', '" + texto_Critica + "', '" + puntuacion_Critica + "');");
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
    public void insertarCritico(String id_Critico, String nombre_Critico, String cod_Critico_Critica) {
        Statement sta;
        try {
            conexion.setAutoCommit(false);

            sta = conexion.createStatement();
            sta.executeUpdate("INSERT INTO critico(id_Critico, nombre_Critico, cod_Critico_Critica)"
                    + " VALUES(" + id_Critico + ", '" + nombre_Critico + "', '" + cod_Critico_Critica + "');");
            sta.close();
            conexion.commit();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error al insertar Critico");
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex1) {
                    System.out.println(ex1.toString());
                    System.out.println("Error al insertar crítico.");
                }
            }
        }
    }

    //METODO PARA DAR DE BAJA EN LA BBDD
    //ELIMINAR PELICULA
//    public void eliminarPelicula(String id_Pelicula) {
//        Statement sta;
//        try {
//            conexion.setAutoCommit(false);
//            sta = conexion.createStatement();
//            sta.executeUpdate("DELETE FROM pelicula WHERE id_Pelicula = " + id_Pelicula + ";");
//            sta.close();
//            conexion.commit();
//        } catch (SQLException ex) {
//            System.out.println(ex.toString());
//            System.out.println("Error al eliminar película.");
//            if (conexion != null) {
//                try {
//                    conexion.rollback();
//                } catch (SQLException ex1) {
//                    System.out.println(ex1.toString());
//                    System.out.println("Error al eliminar pelicula.");
//                }
//            }
//        }
//    }
    //ELIMINAR CRITICA 
    public void eliminarCritica(String id_Critica) {

        Statement sta;
        try {
            conexion.setAutoCommit(false);
            sta = conexion.createStatement();

            sta.executeUpdate("DELETE FROM critica WHERE id_Critica = " + id_Critica + ";");

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

            conexion.setAutoCommit(false);
            sta = conexion.createStatement();

            sta.executeUpdate("DELETE FROM critica WHERE cod_critico = '" + cod_Critico_Critica + "';");
            sta.executeUpdate("DELETE FROM critico WHERE cod_Critico_Critica = '" + cod_Critico_Critica + "';");

            sta.close();
            conexion.commit();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            System.out.println("Error al eliminar Critico.");
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex1) {
                    System.out.println(ex1.toString());
                    System.out.println("Error al eliminar critico.");
                }
            }
        }
    }

    //PREPARED STATEMENT
    //Peliculas de un Director
    public String cosulta_PeliculasDirector(String director_Pelicula) {
        String query = "SELECT * FROM pelicula WHERE director_Pelicula= ?;";
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
                        + ", Duracion: " + rs.getString("duracion_Pelicula"));
                consulta = "ID: " + rs.getString("id_Pelicula")
                        + ", Titulo: " + rs.getString("nombre_Pelicula")
                        + ", Genero: " + rs.getString("genero_Pelicula")
                        + ", Director: " + rs.getString("director_Pelicula")
                        + ", Duracion: " + rs.getString("duracion_Pelicula");
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
        String query = "SELECT * FROM critica WHERE cod_critico= ?;";
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
        String query = "SELECT * FROM pelicula WHERE genero_Pelicula= ?;";
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
                        + ", Duracion: " + rs.getString("duracion_Pelicula"));
                consulta = "ID: " + rs.getString("id_Pelicula")
                        + ", Titulo: " + rs.getString("nombre_Pelicula")
                        + ", Genero: " + rs.getString("genero_Pelicula")
                        + ", Director: " + rs.getString("director_Pelicula")
                        + ", Duracion: " + rs.getString("duracion_Pelicula");
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
    public void editarCritico(String id_Critico, String nombre_Critico) {
        Statement sta;
        try {

            conexion.setAutoCommit(false);
            sta = conexion.createStatement();

            sta.executeUpdate("UPDATE critico SET nombre_Critico = '" + nombre_Critico
                    + "' WHERE id_Critico =" + id_Critico + ";");

            sta.close();

            conexion.commit();

        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("Error al actualizar critico.");
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex1) {
                    System.out.println(ex1.toString());
                    System.out.println("Error al actualizar critico.");
                }
            }
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

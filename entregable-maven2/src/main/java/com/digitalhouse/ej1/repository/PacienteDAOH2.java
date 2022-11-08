package com.digitalhouse.ej1.repository;

import com.digitalhouse.ej1.model.Paciente;
import com.mysql.cj.jdbc.DatabaseMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class PacienteDAOH2 implements PacienteDAO{


    private final static String host="sql10.freemysqlhosting.net";
    private final static String port="3306";
    private final static String user= "sql10550430";
    private final static String password= "fSsWLMNj16";
    private final static String database= user;

    private final static String DB_TABLE= "PACIENTES";

    private final static String CREATE= "CREATE TABLE IF NOT EXISTS PACIENTES (ID INT AUTO_INCREMENT PRIMARY KEY,NOMBRE VARCHAR(30),APELLIDO VARCHAR(30),DOMICILIO VARCHAR (200),DNI VARCHAR(15) UNIQUE,ALTAFECHA DATE)";

    private final static  String DB_INIT="INIT=RUNSCRIPT FROM 'create.sql';";


    //private final static String DB_URL = "jdbc:h2:~/db_consultorio;INIT=RUNSCRIPT FROM 'create.sql';";
    private final static String DB_URL =  "jdbc:mysql://"+host+":"+port+"/"+database;
    private final static String DB_USER= user;

    private final static String DB_PASS = password;
   private final static String INSERT = "INSERT INTO PACIENTES(NOMBRE,APELLIDO,DOMICILIO,DNI,ALTAFECHA) VALUES(?,?,?,?,?)";
    private final static String SELECT = "SELECT * FROM PACIENTES where ID = ?";

    private final static String DELETE = "DELETE FROM PACIENTES where ID = ?";

    private final static String UPDATE = "UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, DOMICILIO=?, DNI=?,ALTAFECHA=?  where ID =?";
    private final static String SELECT_ALL = "SELECT * FROM PACIENTES";

    private final static String DELETE_ALL = "DELETE FROM PACIENTES";
    private final static Logger logger = LogManager.getLogger(PacienteDAOH2.class);



    @Override
    public int guardar(Paciente p) {
        Connection connection = null;
        try {
            connection = getConnection();

            var insert = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, p.nombre());
            insert.setString(2, p.apellido());
            insert.setString(3, p.domicilio());
            insert.setString(4, p.DNI());
            insert.setDate(5, p.altafecha());

            insert.execute();
            logger.info("Agregando Paciente");

            ResultSet rs = insert.getGeneratedKeys();
            if(rs.next()) {
                int lastId = rs.getInt(1);
                connection.close();
                return lastId;

            }


        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
        return 0;}

    @Override
    public Paciente buscar(int id){

        try {
            var connection = getConnection();
            var buscar = connection.prepareStatement(SELECT);
            buscar.setInt(1, id);
            var result = buscar.executeQuery();
            if (result.next()) {
                boolean flag = true;
                var nombre = result.getString(2);
                var apellido = result.getString(3);
                var domicilio = result.getString(4);
                var DNI = result.getString(5);
                var altafecha = result.getDate(6);

                logger.info("Paciente encontrado");
                Paciente paci = new Paciente(nombre, apellido, domicilio,DNI,altafecha);
                connection.close();
                return paci;}

        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
        return null;

    }


    @Override
    public void update(Paciente p,int id) {
        Connection connection = null;
        try {
            connection = getConnection();
            var update = connection.prepareStatement(UPDATE);
            update.setString(1, p.nombre());
            update.setString(2, p.apellido());
            update.setString(3, p.domicilio());
            update.setString(4, p.DNI());
            update.setDate(5, p.altafecha());

            update.setInt(6, 1);
            update.executeUpdate();
            connection.close();

            logger.info("Registro Paciente actualizado");

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void borrar(int id) {
        Connection connection = null;
        try {
            connection = getConnection();
            var delete = connection.prepareStatement(DELETE);
            delete.setInt(1, id);
            delete.executeUpdate();
            connection.close();
            logger.info("Registro PAciente borrado");
        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
    }
    @Override
    public ArrayList<Paciente> buscarTodos() {
        try {
            var connection = getConnection();
            Statement st = connection.createStatement();
            var res = st.executeQuery(SELECT_ALL);
            ArrayList<Paciente> pacientes = new ArrayList<>(0);
            while (res.next()) {
                pacientes.add(new Paciente(res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getDate(6)));
            }
            if (pacientes.size() == 0) return null;
            logger.info("consulta sobre todos los pacientes realizada");
            connection.close();
            return pacientes;
        } catch (SQLException e) {
            logger.error(e.getMessage());


        }return null;
    }


    @Override
    public void borrarTodos() {
        Connection connection = null;
        try {
            connection = getConnection();
            var deleteAll = connection.prepareStatement(DELETE_ALL);
            deleteAll.execute();
            connection.close();
        }
        catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection= DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        com.mysql.cj.jdbc.DatabaseMetaData databaseMetaData = (com.mysql.cj.jdbc.DatabaseMetaData) connection.getMetaData();
        if (!tableExists(connection)){connection.createStatement().executeUpdate(CREATE);}
        return connection;


    }
    boolean tableExists(Connection connection) throws SQLException {

        com.mysql.cj.jdbc.DatabaseMetaData meta = (DatabaseMetaData) connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, DB_TABLE, new String[] {"TABLE"});
        return resultSet.next();}



}

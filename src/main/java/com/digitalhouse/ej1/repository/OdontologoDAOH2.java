package com.digitalhouse.ej1.repository;

import com.digitalhouse.ej1.model.Odontologo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;



public class OdontologoDAOH2  implements OdontologoDAO {

    private final static String DB_URL = "jdbc:h2:~/db_consultorio;INIT=RUNSCRIPT FROM 'create.sql';";
    private final static String INSERT = "INSERT INTO ODONTOLOGOS(NOMBRE,APELLIDO,MATRICULA) VALUES(?,?,?)";
    private final static String SELECT = "SELECT * FROM ODONTOLOGOS where ID = ?";

    private final static String DELETE = "DELETE FROM ODONTOLOGOS where ID = ?";

    private final static String UPDATE = "UPDATE ODONTOLOGOS SET NOMBRE=?, APELLIDO=?, MATRICULA=? where ID =?";
    private final static String SELECT_ALL = "SELECT *  FROM ODONTOLOGOS";

    private final static String DELETE_ALL = "DELETE FROM ODONTOLOGOS";
    private final static Logger logger = LogManager.getLogger(OdontologoDAOH2.class);



    @Override
    public int guardar(Odontologo o) {
        Connection connection = null;
        try {
            connection = getConnection();

            var insert = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, o.nombre());
            insert.setString(2, o.apellido());
            insert.setString(3, o.matricula());
            insert.execute();
            logger.info("Agregando Odontologo");

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
    public Odontologo buscar(int id){

        try {
            var connection = getConnection();
            var buscar = connection.prepareStatement(SELECT);
            buscar.setInt(1, id);
            var result = buscar.executeQuery();
            if (result.next()) {
                boolean flag = true;
                var nombre = result.getString(2);
                System.out.println(nombre);
                var apellido = result.getString(3);
                var matricula = result.getString(4);
                logger.info("Odontologo encontrado");
                Odontologo odol = new Odontologo(nombre, apellido, matricula);
                connection.close();
                return odol;}

        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
        return null;

        }


    @Override
    public void update(Odontologo o,int id) {
        Connection connection = null;
        try {
            connection = getConnection();
            var update = connection.prepareStatement(UPDATE);
            update.setString(1, o.nombre());
            update.setString(2, o.apellido());
            update.setString(3, o.matricula());
            update.setInt(4, 1);
            update.executeUpdate();
            connection.close();

            logger.info("Registro actualizado");

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
            logger.info("Registro borrado");
        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
    }
    @Override
    public ArrayList<Odontologo>  buscarTodos() {
        try {
            var connection = getConnection();
            Statement st = connection.createStatement();
            var res = st.executeQuery(SELECT_ALL);
            ArrayList<Odontologo> odontologos = new ArrayList<>(0);
            while (res.next()) {
                odontologos.add(new Odontologo(res.getString(2), res.getString(3), res.getString(4)));
            }
            if (odontologos.size() == 0) return null;
            logger.info("consulta sobre todos los registros realizada");
            connection.close();
            return odontologos;
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
        return DriverManager.getConnection(DB_URL);
    }

}

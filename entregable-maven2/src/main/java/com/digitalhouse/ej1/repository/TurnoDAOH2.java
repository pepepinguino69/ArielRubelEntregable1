package com.digitalhouse.ej1.repository;



import com.digitalhouse.ej1.model.Turno;
import com.mysql.cj.jdbc.DatabaseMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;



public class TurnoDAOH2 implements TurnoDAO {


    private final static String host="sql10.freemysqlhosting.net";
    private final static String port="3306";
    private final static String user= "sql10530656";
    private final static String password= "VVbEIRHtxJ";
    private final static String database= "sql10530656";

    private final static  String DB_INIT="INIT=RUNSCRIPT FROM 'create.sql';";


    //private final static String DB_URL = "jdbc:h2:~/db_consultorio;INIT=RUNSCRIPT FROM 'create.sql';";
    private final static String DB_URL =  "jdbc:mysql://"+host+":"+port+"/"+database;
    private final static String DB_USER= user;
    private final static String DB_TABLE= "TURNOS";

    private final static String DB_PASS = password;
    private final static String CREATE =  "create table TURNOS  (id int primary key auto_increment,DNI VARCHAR(20), MATRICULA VARCHAR(20),COMTURNO DATE, FINTURNO DATE)";

    private final static String INSERT = "INSERT INTO TURNOS(DNI, MATRICULA,COMTURNO, FINTURNO) VALUES(?,?,?,?)";
    private final static String SELECT = "SELECT * FROM TURNOS where ID = ?";

    private final static String SELECT_ALL = "SELECT * FROM TURNOS;";

    private final static String DELETE = "DELETE FROM TURNOS where ID = ?";

    private final static String UPDATE = "UPDATE TURNOS SET DNI=?, MATRICULA=?, COMTURNO=?, FINTURNO=? where ID =?";

    private final static String AVAILABLE = "SELECT * FROM TURNOS where matricula = ? AND comturno=>? and finturno<=?";

    private final static String DELETE_ALL = "DELETE FROM TURNOS";
    private final static Logger logger = LogManager.getLogger(TurnoDAOH2.class);


    @Override
    public int guardar(Turno t) {
        try {
            Connection connection = getConnection();

            var insert = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, t.DNI());
            insert.setString(2, t.matricula());
            insert.setDate(3, t.comTurno());
            insert.setDate(4, t.finTurno());

            insert.execute();
            logger.info("Agregando Turno");

            ResultSet rs = insert.getGeneratedKeys();
            if (rs.next()) {
                int lastId = rs.getInt(1);
                connection.close();
                return lastId;

            }


        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
        return 0;
    }

    @Override
    public Turno buscar(int id) {

        try {
            var connection = getConnection();
            var buscar = connection.prepareStatement(SELECT);
            buscar.setInt(1, id);
            var result = buscar.executeQuery();
            if (result.next()) {
                var DNI = result.getString(2);
                var matricula = result.getString(3);
                var comTurno = result.getDate(4);
                var finTurno = result.getDate(5);

                logger.info("Turno encontrado");
                Turno turn = new Turno(DNI, matricula, comTurno, finTurno);
                connection.close();
                return turn;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
        return null;

    }


    @Override
    public void update(Turno t, int id) {

        try {
            Connection connection = getConnection();
            var update = connection.prepareStatement(UPDATE);
            update.setString(1, t.DNI());
            update.setString(2, t.matricula());
            update.setDate(3, t.comTurno());
            update.setDate(4, t.finTurno());
            update.setInt(5, 1);
            update.executeUpdate();
            connection.close();

            logger.info("Registro Turno actualizado");

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void borrar(int id) {

        try {
            Connection connection = getConnection();
            var delete = connection.prepareStatement(DELETE);
            delete.setInt(1, id);
            delete.executeUpdate();
            connection.close();
            logger.info("Registro Turno borrado");
        } catch (SQLException e) {
            logger.error(e.getMessage());

        }
    }

    @Override
    public ArrayList<Turno> buscarTodos() {
        try {
            var connection = getConnection();
            Statement st = connection.createStatement();
            var res = st.executeQuery(SELECT_ALL);
            ArrayList<Turno> turnos = new ArrayList<>(0);
            while (res.next()) {
                turnos.add(new Turno(res.getString(2), res.getString(3), res.getDate(4), res.getDate(5)));
            }
            if (turnos.size() == 0) return null;
            logger.info("consulta sobre todos los turnos realizada");
            connection.close();
            return turnos;
        } catch (SQLException e) {
            logger.error(e.getMessage());


        }
        return null;
    }


    @Override
    public void borrarTodos() {

        try {
            Connection connection = getConnection();
            var deleteAll = connection.prepareStatement(DELETE_ALL);
            deleteAll.execute();
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


    private Connection getConnection() throws SQLException {
        Connection connection= DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        DatabaseMetaData databaseMetaData = (DatabaseMetaData) connection.getMetaData();

        if (!tableExists(connection)){connection.createStatement().executeUpdate(CREATE);}
        return connection;


    }
    boolean tableExists(Connection connection) throws SQLException {

        DatabaseMetaData meta = (DatabaseMetaData) connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, DB_TABLE, new String[] {"TABLE"});
        return resultSet.next();}

    @Override
    public boolean checkAvailability(Turno t) {
        try {
            var connection = getConnection();
            var check = connection.prepareStatement(AVAILABLE);
            check.setString(1, t.matricula());
            check.setDate(2, t.comTurno());
            check.setDate(3, t.finTurno());
            var result = check.executeQuery();
            connection.close();
            if (result.next()) {
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }return true;
    }



}

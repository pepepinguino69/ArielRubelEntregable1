import com.digitalhouse.ej1.model.Odontologo;
import com.digitalhouse.ej1.repository.OdontologoDAOH2;
import com.digitalhouse.ej1.service.OdontologoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OdontologoServiceTest {


    @Test
    @DisplayName("WHEN a odontologo object is created THEN it can be found and positively compared using the newly generated Id")
    public void test1() throws SQLException {
        //GIVEN


        var nombre = "Thomas";
        var apellido = "Cook";
        Random rand = new Random();
        var matricula = String.valueOf(rand.nextInt(100000));
        Odontologo odontologo = new Odontologo(nombre, apellido, matricula);

        //WHEN
        var service = new OdontologoService();
        var actual = service.guardar(odontologo);

        //THEN
        assertEquals(service.buscar(actual), odontologo);


    }
        @Test
        @DisplayName("WHEN a odontologo object is updated THEN it has the right params")
        public void test2() throws SQLException {
            //GIVEN
            var nombre = "Juan";
            var apellido="Cook";
            Random rand = new Random();
            var matricula=String.valueOf(rand.nextInt(100000));
            Odontologo odontologo= new Odontologo(nombre,apellido,matricula);
            int id2Update=1;
            //WHEN
            var service = new OdontologoService(new OdontologoDAOH2());
            service.update(odontologo,id2Update);
            var actual = service.buscar(id2Update);

            //THEN
            var dbRecord = service.buscar(id2Update) ;
            assertEquals(actual, dbRecord);


        }

    @Test
    @DisplayName("WHEN a odontologo object is deleted THEN it can not  be found in the Database")
    public void test3() throws SQLException {
        //GIVEN
        var nombre = "Thomas";
        var apellido = "Cook";
        Random rand = new Random();
        var matricula = String.valueOf(rand.nextInt(100000));
        Odontologo odontologo = new Odontologo(nombre, apellido, matricula);

        //WHEN
        var service = new OdontologoService();
        var actual = service.guardar(odontologo);
        service.borrar(actual);

        //THEN
        assertNull(service.buscar(actual));

    }
    @Test
    @DisplayName("WHEN a random  number of odontologo objects are added to a clean database the returned rows of buscarTodos equals the number of added records")
    public void test4() {
        //GIVEN
        var service = new OdontologoService();
        service.borrarTodos();
        Random rand1 = new Random();
        int testRecords=rand1.nextInt(5)+3;
        for (int i = 0; i < testRecords; i++) {
            var nombre = "Thomas";
            var apellido = "Cook";
            Random rand = new Random();
            var matricula = String.valueOf(rand.nextInt(100000));
            Odontologo odontologo = new Odontologo(nombre, apellido, matricula);
            var service2 = new OdontologoService(new OdontologoDAOH2());
            service2.guardar(odontologo);}



        //WHEN
        var service3 = new OdontologoService();
        int encontrados = service3.buscartTodos().size();


        //THEN

        assertEquals(encontrados, testRecords);


    }
}




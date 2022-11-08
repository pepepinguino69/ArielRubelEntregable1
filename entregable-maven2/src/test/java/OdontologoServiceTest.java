import com.digitalhouse.ej1.model.Odontologo;
import com.digitalhouse.ej1.model.Paciente;
import com.digitalhouse.ej1.model.Turno;
import com.digitalhouse.ej1.service.OdontologoService;
import com.digitalhouse.ej1.service.PacienteService;
import com.digitalhouse.ej1.service.TurnoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Date;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OdontologoServiceTest {



    @Test
    @DisplayName("WHEN a odontologo object is created THEN it can be found and positively compared using the newly generated Id")
    public void test1() {
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
    public void test2() {
        //GIVEN
        var nombre = "Juan";
        var apellido = "Cook";
        Random rand = new Random();
        var matricula = String.valueOf(rand.nextInt(100000));
        Odontologo odontologo = new Odontologo(nombre, apellido, matricula);
        int id2Update = 1;
        //WHEN
        var service = new OdontologoService();
        service.update(odontologo, id2Update);
        var actual = service.buscar(id2Update);

        //THEN
        var dbRecord = service.buscar(id2Update);
        assertEquals(actual, dbRecord);


    }

    @Test
    @DisplayName("WHEN a odontologo object is deleted THEN it can not  be found in the Database")
    public void test3() {
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
        int testRecords = rand1.nextInt(5) + 3;
        for (int i = 0; i < testRecords; i++) {
            var nombre = "Thomas";
            var apellido = "Cook";
            Random rand = new Random();
            var matricula = String.valueOf(rand.nextInt(100000));
            Odontologo odontologo = new Odontologo(nombre, apellido, matricula);
            var service2 = new OdontologoService();
            service2.guardar(odontologo);
        }


        //WHEN
        var service3 = new OdontologoService();
        int encontrados = service3.buscartTodos().size();


        //THEN

        assertEquals(encontrados, testRecords);


    }

    @Test
    @DisplayName("WHEN a Paciente object is created THEN it can be found and positively compared using the newly generated Id")
    public void test5() {
        //GIVEN


        var nombre = "Pepe";
        var apellido = "Bastos";
        var domicilio = "Libertador 2444";
        Date ahora = new Date(1665273605);
        Random rand = new Random();
        var dni = String.valueOf(rand.nextInt(10000000));
        Paciente paciente = new Paciente(nombre, apellido, domicilio, dni, ahora);

        //WHEN
        var service = new PacienteService();
        var actual = service.guardar(paciente);

        //THEN
        assertEquals(service.buscar(actual), paciente);


    }

    @Test
    @DisplayName("WHEN a paciente object is updated THEN it has the right params")
    public void test6() {
        //GIVEN
        var nombre = "Pepe";
        var apellido = "Espadas";
        var domicilio = "Libertador 2444";
        Date ahora = new Date(1665273605);
        Random rand = new Random();
        var dni = String.valueOf(rand.nextInt(10000000));

        Paciente paciente = new Paciente(nombre, apellido, domicilio, dni, ahora);
        int id2Update = 2;
        //WHEN
        var service = new PacienteService();
        service.update(paciente, id2Update);
        var actual = service.buscar(id2Update);

        //THEN
        var dbRecord = service.buscar(id2Update);
        assertEquals(actual, dbRecord);


    }

    @Test
    @DisplayName("WHEN a paciente object is deleted THEN it can not  be found in the Database")
    public void test7() {
        //GIVEN
        var nombre = "Pepe";
        var apellido = "Borra";
        var domicilio = "Libertador 2444";
        Date ahora = new Date(1665273605);
        Random rand = new Random();
        var dni = String.valueOf(rand.nextInt(10000000));
        Paciente paciente = new Paciente(nombre, apellido, domicilio, dni, ahora);

        //WHEN
        var service = new PacienteService();
        var actual = service.guardar(paciente);
        service.borrar(actual);

        //THEN
        assertNull(service.buscar(actual));

    }

    @Test
    @DisplayName("WHEN a random  number of paciente objects are added to a clean database the returned rows of buscarTodos equals the number of added records")
    public void test8() {
        //GIVEN
        var service = new PacienteService();
        service.borrarTodos();
        Random rand1 = new Random();
        int testRecords = rand1.nextInt(5) + 3;
        for (int i = 0; i < testRecords; i++) {
            var nombre = "Juan";
            var apellido = "Pelotas";
            var domicilio = "Libertador 2444";
            Date ahora = new Date(1665273605);
            Random rand = new Random();
            var dni = String.valueOf(rand.nextInt(10000000));
            Paciente paciente = new Paciente(nombre, apellido, domicilio, dni, ahora);
            var service2 = new PacienteService();
            service2.guardar(paciente);
        }


        //WHEN
        var service3 = new PacienteService();
        int encontrados = service3.buscartTodos().size();


        //THEN

        assertEquals(encontrados, testRecords);


    }
    @Test
    @DisplayName("When a turno object is added recorded info checks with sent info ")
    public void test9() {
        //GIVEN
        var nombre = "Thomas";
        var apellido = "Cook";
        Random rand = new Random();
        var matricula = String.valueOf(rand.nextInt(100000));
        Odontologo odontologo = new Odontologo(nombre, apellido, matricula);
        var service = new OdontologoService();
        service.guardar(odontologo);
        var nombrePac = "Pepe";
        var apellidoPac = "Bastos";
        var domicilioPac = "Libertador 2444";
        java.sql.Date ahora = new Date((long) (52940*365.25*24*3600));
        java.sql.Date fin = new Date((long) (52940*365.85*24*3600));
        java.sql.Date com = new Date((long) (52940*365.85*24*3600-3600000));

        Random randDNI = new Random();
        var dni = String.valueOf(randDNI.nextInt(10000000));
        Paciente paciente = new Paciente(nombrePac, apellidoPac, domicilioPac, dni, ahora);
        var servicePac = new PacienteService();
        servicePac.guardar(paciente);
        Turno turno = new Turno(dni, matricula, com, fin);
        var serviceTurno = new TurnoService();
        var actual = serviceTurno.guardar(turno);
        System.out.println(com.toString());
        assertEquals(serviceTurno.buscar(actual), turno);

    }



}

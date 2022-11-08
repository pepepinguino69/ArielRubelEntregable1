package com.digitalhouse.ej1;
import com.digitalhouse.ej1.model.Odontologo;
import com.digitalhouse.ej1.repository.OdontologoDAOH2;
import com.digitalhouse.ej1.service.OdontologoService;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class Main {
    private final static String log4jConfigFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "log4j2.xml";

    public static void main(String[] args) throws IOException, SQLException {
        startLogger();
        Random rand = new Random();
        String mat = String.valueOf(rand.nextInt(100000));
        var serviceO = new OdontologoService(new OdontologoDAOH2());
        var odontologo = new Odontologo( "Nombre", "Apellido", mat);
      var newOdontologo = new Odontologo( "Nombre", "Apellido", "Matricula");
       serviceO.guardar(odontologo);


        int id=1;
        serviceO.update(newOdontologo,id);
        System.out.println(serviceO.buscartTodos());





    }


    private static void startLogger() throws IOException {
        var source = new ConfigurationSource(new FileInputStream(log4jConfigFile));
        Configurator.initialize(null, source);
    }
}

package com.digitalhouse.ej1.service;
import com.digitalhouse.ej1.model.Turno;
import com.digitalhouse.ej1.repository.TurnoDAO;
import com.digitalhouse.ej1.repository.TurnoDAOH2;
import java.util.ArrayList;





public class TurnoService {

    private final com.digitalhouse.ej1.repository.TurnoDAO TurnoDAO;

    public TurnoService() {
        this.TurnoDAO = new TurnoDAOH2();
    }

    public int guardar(Turno t) {
        return this.TurnoDAO.guardar(t);

    }


    public Turno buscar(int id) {

        return this.TurnoDAO.buscar(id);
    }

    public void borrar(int id) {
        this.TurnoDAO.borrar(id);
    }

    public void update(Turno t, int id) {
        this.TurnoDAO.update(t, id);
    }

    public ArrayList<Turno> buscartTodos() {
        return this.TurnoDAO.buscarTodos();
    }

    public void borrarTodos() {
        this.TurnoDAO.borrarTodos();
    }
}















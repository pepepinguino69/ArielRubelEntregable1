package com.digitalhouse.ej1.service;

import com.digitalhouse.ej1.model.Odontologo;
import com.digitalhouse.ej1.repository.OdontologoDAO;
import com.digitalhouse.ej1.repository.OdontologoDAOH2;
import lombok.AllArgsConstructor;



import java.util.ArrayList;


@AllArgsConstructor
public class OdontologoService {

    private final OdontologoDAO odontologoDAO;

    public OdontologoService(){this.odontologoDAO = new OdontologoDAOH2();}
    public int guardar(Odontologo o){
        return this.odontologoDAO.guardar(o);

    }



    public  Odontologo buscar(int id) {

        return this.odontologoDAO.buscar(id);
    }

    public void borrar(int id) {
           this.odontologoDAO.borrar(id);
    }
    public void update(Odontologo o,int id) {
        this.odontologoDAO.update(o, id);
    }
    public ArrayList<Odontologo> buscartTodos(){
        return this.odontologoDAO.buscarTodos();
    }
    public void borrarTodos() {
        this.odontologoDAO.borrarTodos();}
}

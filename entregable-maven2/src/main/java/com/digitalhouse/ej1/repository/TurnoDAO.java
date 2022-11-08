package com.digitalhouse.ej1.repository;

import com.digitalhouse.ej1.model.Turno;

import java.util.ArrayList;


public interface TurnoDAO {
    int guardar(Turno t);

    Turno buscar(int id);

    void update(Turno t, int id);

    ArrayList<Turno> buscarTodos();

    void borrar(int id);

    void borrarTodos();

    boolean checkAvailability(Turno t);
}









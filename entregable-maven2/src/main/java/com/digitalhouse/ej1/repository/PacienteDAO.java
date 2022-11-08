package com.digitalhouse.ej1.repository;

import com.digitalhouse.ej1.model.Paciente;

import java.util.ArrayList;

public interface PacienteDAO {
    int guardar(Paciente p);

    Paciente buscar(int id);

    void update(Paciente p, int id);

    ArrayList<Paciente> buscarTodos();

    void borrar(int id);

    void borrarTodos();
}








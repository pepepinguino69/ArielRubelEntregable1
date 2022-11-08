package com.digitalhouse.ej1.service;


import lombok.AllArgsConstructor;

import java.util.ArrayList;


import com.digitalhouse.ej1.model.Paciente;
import com.digitalhouse.ej1.repository.PacienteDAO;
import com.digitalhouse.ej1.repository.PacienteDAOH2;

import java.util.ArrayList;


    public class PacienteService {

        private final PacienteDAO PacienteDAO;

        public PacienteService() {
            this.PacienteDAO = new PacienteDAOH2();
        }

        public int guardar(Paciente o) {
            return this.PacienteDAO.guardar(o);

        }


        public Paciente buscar(int id) {

            return this.PacienteDAO.buscar(id);
        }

        public void borrar(int id) {
            this.PacienteDAO.borrar(id);
        }

        public void update(Paciente o, int id) {
            this.PacienteDAO.update(o, id);
        }

        public ArrayList<Paciente> buscartTodos() {
            return this.PacienteDAO.buscarTodos();
        }

        public void borrarTodos() {
            this.PacienteDAO.borrarTodos();
        }
    }
















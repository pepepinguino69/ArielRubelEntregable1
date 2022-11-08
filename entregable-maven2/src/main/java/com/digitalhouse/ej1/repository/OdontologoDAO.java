package com.digitalhouse.ej1.repository;

import com.digitalhouse.ej1.model.Odontologo;


import java.util.ArrayList;

public interface OdontologoDAO {
    int  guardar(Odontologo o)  ;
    Odontologo buscar(int id) ;

   void  update(Odontologo o,int id) ;
    ArrayList<Odontologo> buscarTodos() ;
    void borrar(int id);
    void borrarTodos();






}

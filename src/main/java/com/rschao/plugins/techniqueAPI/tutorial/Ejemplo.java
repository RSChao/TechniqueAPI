package com.rschao.plugins.techniqueAPI.tutorial;

import java.util.ArrayList;
import java.util.List;

public class Ejemplo {
    //Tangamandapio
    /*Tangamandapio
    * RSChao
    * Minecraft
    * Showdown SMP*/
    int numero_entero_1 = 10; //numero sin decimales (1, 100, -4, etc)
    boolean VoF = false; // true or false
    String texto = "Hola, mundo";
    double decimal = -0.5;

    //arrays
    String[] array = {"a", "b", "c", "d"};

    //Listas
    List<String> lista = new ArrayList<>();

    void insertarNombre(List<String> list, String nombre){
        list.add(nombre);
    }

    public void funcion(){
        insertarNombre(lista, "RS.Chao");
        insertarNombre(lista, "Delta");
    }
}

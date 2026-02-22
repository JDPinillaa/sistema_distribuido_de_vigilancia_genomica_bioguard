package org.JuanDiego.models;

import enums.Gender;


/**
 *Modelo que representa a un paciente
 *@author Juan Diego
 *@since 20260219
 *@version 1.1
 */
public class Patient {
    //////Atributos

    /**
     * Representa el numero de identificacion del paciente
     */
    private String id;

    /**
     * Representa el nombre del paciente
     */
    private String name;

    /**
     * Representa el apellido del paciente
     */
    private String surmname;

    /**
     * Representa la edad del paciente
     */
    private int age;

    /**
     * Representa el correo electronico del paciente
     */
    private String email;

    /**
     * Representa el genero del paciente con el Enum Gender
     */
    private Gender gender;
    private String ciudad;
    private String pais;

    ////// Metodo constructor
    public Patient(String id, String name, String surmname, int age, String email, Gender gender, String ciudad, String pais) {
        this.id = id;
        this.name = name;
        this.surmname = surmname;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    ////// Metodos de acceso

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurmname() {
        return surmname;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGenero() {
        return gender;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }
}
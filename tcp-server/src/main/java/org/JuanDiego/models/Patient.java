package org.JuanDiego.models;

import org.JuanDiego.enums.Gender;


/**
 *Modelo que representa a un paciente
 *@author Juan Diego
 *@since 20260219
 *@version 1.2
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
    private String surname;

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

    /**
     * Representa la ciudad de residencia del paciente
     */
    private String city;

    /**
     * Representa el país de residencia del paciente
     */
    private String country;

    ////// Metodo constructor
    public Patient(String id, String name, String surname, int age, String email, Gender gender, String city, String country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.city = city;
        this.country = country;
    }

    ////// Metodos de acceso

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    /**
     * Representa la informacion del paciente en formato de archivo .csv
     */
    public String toCSV(){
        return String.join(",", id, name, surname,String.valueOf(age), email, String.valueOf(gender.getAbbreviation()), city, country  );
    }
}

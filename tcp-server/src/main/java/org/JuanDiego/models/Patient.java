package org.JuanDiego.models;

public class Patient {
    private String id;
    private String name;
    private String surmname;
    private int age;
    private String email;
    private String genero;
    private String ciudad;
    private String pais;

    public Patient(String id, String name, String surmname, int age, String email, String genero, String ciudad, String pais) {
        this.id = id;
        this.name = name;
        this.surmname = surmname;
        this.age = age;
        this.email = email;
        this.genero = genero;
        this.ciudad = ciudad;
        this.pais = pais;
    }

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

    public String getGenero() {
        return genero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }
}

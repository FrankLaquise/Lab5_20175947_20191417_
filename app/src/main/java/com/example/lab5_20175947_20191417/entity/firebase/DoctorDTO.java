package com.example.lab5_20175947_20191417.entity.firebase;

public class DoctorDTO {
    private String title;
    private String first;
    private String last;
    private String country;
    private String state;
    private String city;
    private String age;
    private String email;
    private String cell;
    private String foto;
    private String nat;
    public DoctorDTO() {
    }
    public DoctorDTO(String title, String first, String last, String country, String state, String city, String age, String email, String cell, String foto, String nat) {
        this.title = title;
        this.first = first;
        this.last = last;
        this.country = country;
        this.state = state;
        this.city = city;
        this.age = age;
        this.email = email;
        this.cell = cell;
        this.foto = foto;
        this.nat = nat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }
}

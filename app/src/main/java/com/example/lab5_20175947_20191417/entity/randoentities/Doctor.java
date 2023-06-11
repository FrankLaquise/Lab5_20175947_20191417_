package com.example.lab5_20175947_20191417.entity.randoentities;

public class Doctor {
    private Name name;
    private LocationR location;
    private String email;
    private Dob dob;
    private String cell;
    private Picture picture;
    private String nat;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public LocationR getLocation() {
        return location;
    }

    public void setLocation(LocationR location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }
}

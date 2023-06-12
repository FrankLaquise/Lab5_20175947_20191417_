package com.example.lab5_20175947_20191417;

public class HelperClass {

    private String nombre;
    private String contraseña;
    private String correo;
    private String telefono;

    public HelperClass() {
        // Constructor vacío requerido por Firebase
    }

    public HelperClass(String nombre, String contraseña, String correo, String telefono) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}

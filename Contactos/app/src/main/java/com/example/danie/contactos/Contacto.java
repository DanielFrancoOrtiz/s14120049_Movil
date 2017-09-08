package com.example.danie.contactos;

import java.io.Serializable;

/**
 * Created by danie on 06/09/2017.
 */

public class Contacto implements Serializable{
    private String usuario;
    private String numero;
    private String email;
    private String twiter;
    private String fecha;

    public Contacto(String usuario, String numero, String email, String twiter, String fecha){
        this.usuario = usuario;
        this.numero = numero;
        this.email = email;
        this.twiter = twiter;
        this.fecha = fecha;
    }
    public String getUsuario(){
        return usuario;
    }
    public String getNumero(){
        return numero;
    }
    public String getEmail(){
        return email;
    }
    public String getTwiter(){
        return twiter;
    }
    public String getFechaNacimiento(){
        return fecha;
    }

}

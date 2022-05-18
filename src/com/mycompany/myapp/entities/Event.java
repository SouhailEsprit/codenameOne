/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author wassim
 */
public class Event {
   private int id;
    private String nom;
    private String description;
    private String  date_debut;
    private String  date_Fin ;
    private String lieu ;
    private String image ; 
 private String nombre_participants ; 
    public Event (){}

    public String getNombre_participants() {
        return nombre_participants;
    }

    public void setNombre_participants(String nombre_participants) {
        this.nombre_participants = nombre_participants;
    }

    public Event(String nombre_participants) {
        this.nombre_participants = nombre_participants;
    }

    public Event(String nom, String description, String date_debut, String date_Fin, String lieu, String image) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_Fin = date_Fin;
        this.lieu = lieu;
        this.image = image;
    }
    public Event(int id, String nom, String description, String date_debut, String date_Fin, String lieu, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_Fin = date_Fin;
        this.lieu = lieu;
        this.image = image;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_Fin() {
        return date_Fin;
    }

    public void setDate_Fin(String date_Fin) {
        this.date_Fin = date_Fin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}

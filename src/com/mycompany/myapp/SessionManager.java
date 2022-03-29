/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.io.Preferences;

/**
 *
 * @author louay
 */
public class SessionManager {

    public static Preferences pref;

    private static int id;
    private static String firstname;
    private static String lastname;
    private static String email;
    private static String picture;
    private static String password;
    private static String role;
    private static int phonenumber;
    private static String countrycode;

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return pref.get("id", id);
    }

    public static void setId(int id) {
        pref.set("id", id);//nsajl id user connecté  w na3tiha identifiant "id";
    }

    public static String getFirstName() {
        return pref.get("first_name", firstname);
    }

    public static void setFirstName(String firstname) {
        pref.set("first_name", firstname);
    }

    public static String getRoles() {
        return pref.get("role", role);
    }

    public static void setRole(String role) {
        pref.set("role", role);
    }

    public static String getLastName() {
        return pref.get("last_name", lastname);
    }

    public static void setLastName(String lastname) {
        pref.set("last_name", lastname);
    }

    public static String getCountryCode() {
        return pref.get("countrycode", countrycode);
    }

    public static void setCountrCode(String countrycode) {
        pref.set("countrycode", countrycode);
    }

    public static int getPhoneNumber() {
        return pref.get("phonenumber", phonenumber);
    }

    public static void setPhoneNumber(String phonenumber) {
        pref.set("phonenumber", phonenumber);
    }

    public static String getEmail() {
        return pref.get("email", email);
    }

    public static void setEmail(String email) {
        pref.set("email", email);
    }

    public static String getPassowrd() {
        return pref.get("passowrd", password);
    }

    public static void setPassowrd(String passowrd) {
        pref.set("passowrd", passowrd);
    }

    public static String getPicture() {
        return pref.get("picture", picture);
    }

    public static void setPicture(String picture) {
        pref.set("picture", picture);
    }
}

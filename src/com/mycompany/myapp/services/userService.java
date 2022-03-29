/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.SessionManager;
import com.mycompany.myapp.utils.Statics;
import java.util.Map;

/**
 *
 * @author louay
 */
public class userService {

    public static userService instance = null;
    String json;
    private boolean resultat;

    private ConnectionRequest req;

    public static userService getInstance() {
        if (instance == null) {
            instance = new userService();
        }
        return instance;
    }

    public userService() {
        req = new ConnectionRequest();
    }

    public boolean SignUp(TextField firstname, TextField lastname, TextField email, TextField password, TextField confirmPassword, TextField countrycode, TextField phonenumber, TextField picture, ComboBox roles, Resources res) {

        String url = Statics.BASE_URL + "mobile/user/register?email="
                + email.getText().toString()
                + "&roles=" + roles.getSelectedItem().toString()
                + "&password=" + password.getText().toString()
                + "&firstname=" + firstname.getText().toString()
                + "&lastname=" + lastname.getText().toString()
                + "&countrycode=" + countrycode.getText().toString()
                + "&phonenumber=" + phonenumber.getText().toString()
                + "&picture=" + picture.getText().toString();

        req.setUrl(url);

        if (email.getText().equals(" ")) {
            Dialog.show("Error", "field can't be empty", "OK", null);
        } else {
            resultat = req.getResponseCode() == 200;
        }

        req.addResponseListener((e) -> {
            byte[] data = (byte[]) e.getMetaData();
            String responseData = new String(data);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;
    }

    public boolean SignIn(TextField email, TextField password, Resources res) {
        String url = Statics.BASE_URL + "mobile/user/login?email="
                + email.getText().toString()
                + "&password=" + password.getText().toString();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {
                if (json.equals("no user with such email")) {
                    Dialog.show("Error", "no user found", "OK", null);
                } else if (json.equals("incorrect password")) {
                    Dialog.show("Error", "no user found", "OK", null);
                } else {
                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    float id = Float.parseFloat(user.get("id").toString());

                    SessionManager.setId((int) id);
                    SessionManager.setFirstName(user.get("firstname").toString());
                    SessionManager.setLastName(user.get("lastname").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setRole(user.get("roles").toString());
                    SessionManager.setRole(user.get("CountryCode").toString());
                    SessionManager.setRole(user.get("phonenumber").toString());

                    //photo 
                    if (user.get("picture") != null) {
                        SessionManager.setPicture(user.get("profilePicture").toString());
                    }

                    if (user.size() > 0) {
                        resultat = req.getResponseCode() == 200;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;
    }

    public void editUser(int id, String firstName, String lastName, String password, String phoneNumber) {
        String url = Statics.BASE_URL + "mobile/editUser?id=" + id + "&firstname=" + firstName + "&lastname=" + lastName + "&password=" + password + "&phonenumber=" + phoneNumber;
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean deleteProduct(int id) {
        String url = Statics.BASE_URL + "mobile/user/delete?id=" + id;

        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultat = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;
    }
}

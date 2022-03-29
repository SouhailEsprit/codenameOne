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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.productCategory;
import com.mycompany.myapp.entities.reclamation;
import com.mycompany.myapp.utils.Statics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author louay
 */
public class reclamationService {

    public static reclamationService instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private boolean resultat;
    public ArrayList<reclamationService> reclamation;

    public static reclamationService getInstance() {
        if (instance == null) {
            instance = new reclamationService();
        }
        return instance;
    }

    public reclamationService() {
        req = new ConnectionRequest();
    }

    public void add(reclamation reclamation) {
        String url = Statics.BASE_URL + "reclamation/mobile/addReclamation?type=" + reclamation.getType() + "&email=" + reclamation.getEmail() + "&message=" + reclamation.getMessage();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean delete(int id) {
        String url = Statics.BASE_URL + "reclamation/mobile/deleteReclamation?id=" + id;

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

    public void edit(reclamation reclamation) {
        String url = Statics.BASE_URL + "reclamation/mobile/editReclamation?message=" + reclamation.getMessage() + "&id=" + reclamation.getId();
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<reclamation> show() {
        ArrayList<reclamation> result = new ArrayList<>();

        String url = Statics.BASE_URL + "reclamation/mobile/showReclamation";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapProducts = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapProducts.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        reclamation pub = new reclamation();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("Id").toString());

                        String type = obj.get("type").toString();
                        String message = obj.get("message").toString();
                        String email = obj.get("email").toString();

                        String DateConverter = obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10, obj.get("date").toString().lastIndexOf("}"));

                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String dateString = formatter.format(currentTime);

                        pub.setDate(dateString);
                        pub.setId((int) id);
                        pub.setEmail(email);
                        pub.setType(type);
                        pub.setMessage(message);

                        result.add(pub);

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();
                }

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;

    }
}

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
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.typeExercice;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author louay
 */
public class typeExerciceService {

    public static typeExerciceService instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private boolean resultat;
    public ArrayList<typeExercice> category;

    public static typeExerciceService getInstance() {
        if (instance == null) {
            instance = new typeExerciceService();
        }
        return instance;
    }

    public typeExerciceService() {
        req = new ConnectionRequest();
    }

    public void addType(typeExercice category) {
        String url = Statics.BASE_URL + "exercice/mobile/addType?name=" + category.getName();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean deleteType(int id) {
        String url = Statics.BASE_URL + "exercice/mobile/deleteType?id=" + id;

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

    public void editType(typeExercice category) {
        String url = Statics.BASE_URL + "exercice/mobile/editType?name=" + category.getName() + "&id=" + category.getId();
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<typeExercice> showType() {
        ArrayList<typeExercice> result = new ArrayList<>();

        String url = Statics.BASE_URL + "exercice/mobile/showType";
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
                        typeExercice pub = new typeExercice();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());

                        String name = obj.get("nom").toString();

                        pub.setId((int) id);
                        pub.setName(name);
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

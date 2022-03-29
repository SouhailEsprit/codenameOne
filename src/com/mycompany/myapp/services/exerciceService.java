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
import com.mycompany.myapp.entities.exercice;
import com.mycompany.myapp.entities.product;
import com.mycompany.myapp.entities.productCategory;
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
public class exerciceService {

    public static exerciceService instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private boolean resultat;
    public ArrayList<exercice> Publications;

    public static exerciceService getInstance() {
        if (instance == null) {
            instance = new exerciceService();
        }
        return instance;
    }

    public exerciceService() {
        req = new ConnectionRequest();
    }

    public void addExercice(exercice exc, typeExercice type) {
        String url = Statics.BASE_URL + "exercice/mobile/addExercice?name=" + exc.getNom() + "&description=" + exc.getDescription() + "&objectif=" + exc.getObjectif() + "&video=" + exc.getVideo() + "&typeId=" + type.getId();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean deleteProduct(int id) {
        String url = Statics.BASE_URL + "exercice/mobile/deleteExercice?id=" + id;

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

    public void editProdcut(exercice product) {
        String url = Statics.BASE_URL + "exercice/mobile/editExercice?name=" + product.getNom() + "&description=" + product.getDescription() + "&video=" + product.getVideo() + "&objectif=" + product.getObjectif() + "&id=" + product.getId();
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<exercice> showProducts() {
        ArrayList<exercice> result = new ArrayList<>();

        String url = Statics.BASE_URL + "exercice/mobile/showExercice";
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
                        exercice pub = new exercice();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());

                        String name = obj.get("nom").toString();
                        String description = obj.get("description").toString();
                        String video = obj.get("video").toString();
                        String objectif = obj.get("objectif").toString();

                        pub.setId((int) id);
                        pub.setNom(name);
                        pub.setDescription(description);
                        pub.setVideo(video);
                        pub.setObjectif(objectif);

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

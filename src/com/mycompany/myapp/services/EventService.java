/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author wassim
 */
public class EventService {

    public static EventService instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private boolean resultat;
    public ArrayList<Event> event_list;

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public EventService() {
        req = new ConnectionRequest();
    }

    public void addEvent(Event ev) {
        String url = Statics.BASE_URL + "/api/AddEvent?nom=" + ev.getNom() + "&description=" + ev.getDescription() + "&lieu=" + ev.getLieu() + "&nombre_participants=" + ev.getNombre_participants() + "&image=" + ev.getImage();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<Event> showAllEvent() {
        ArrayList<Event> result = new ArrayList<>();

        String url = Statics.BASE_URL + "api/events";
        req.setUrl(url);

        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp;
            jsonp = new JSONParser();

            try {
                Map<String, Object> mapEvents = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapEvents.get("root");

                for (Map<String, Object> obj : listOfMaps) {
                    Event e = new Event();

                    //dima id fi codename one float 5outhouha
                    float id = Float.parseFloat(obj.get("id").toString());

                    String name = obj.get("nom").toString();
                    String image = obj.get("image").toString();
                    String lieu = obj.get("lieu").toString();
                    String date_Fin = obj.get("dateFin").toString();
                    String date_debut = obj.get("dateDebut").toString();
                    String description = obj.get("description").toString();
                    String nb_places = obj.get("nombreParticipants").toString();
                    e.setId((int) id);
                    e.setNombre_participants(nb_places);
                    e.setNom(name);
                    e.setDate_Fin(date_Fin);
                    e.setDate_debut(date_debut);
                    e.setImage(image);
                    e.setDescription(description);
                    e.setLieu(lieu);

                    result.add(e);

                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;

    }

    public void modifierEvent(String idEvent, String nom, String image, String description, String date_debut, String date_fin, String nombre_participants, String lieu) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/api/updateEvent");
        con.addArgument("id", idEvent);
        con.addArgument("nom", nom);
        con.addArgument("image", image);
        con.addArgument("description", description);
        con.addArgument("date_debut", date_debut);
        con.addArgument("date_fin", date_fin);
        con.addArgument("lieu", lieu);
        con.addArgument("nombre_participants", nombre_participants);

        con.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void AddEvent(String nom, String image, String description, String date_debut, String date_fin, int nombre_participants, String lieu) {
        ConnectionRequest con = new ConnectionRequest();

        con.setUrl("http://127.0.0.1:8000/api/addEvent?"
                + "nom=" + nom + "&description=" + description + "&image=" + image
                + "&date_debut=" + date_debut
                + "&date_fin=" + date_fin
                + "&nombre_participants=" + nombre_participants
                + "&lieu=" + lieu);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

            }
        });
        NetworkManager.getInstance().addToQueue(con);
    }

    protected String genString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public String uploadPhoto(String path) {
        String photoName = genString() + ".jpg";
        try {
            MultipartRequest cr = new MultipartRequest();
            cr.setUrl("http://localhost/Calometre-main/savetofile.php");
            cr.setPost(true);
            String mime = "image/jpeg";
            cr.addData("myFile", path, mime);
            cr.setFilename("myFile", photoName);//any unique name you want
            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            cr.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (IOException ex) {
        }
        return photoName;
    }

    public void supprimerEvent(String idEvent) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/api/removeEvent");
        con.addArgument("id", idEvent);
        con.setPost(true);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        con.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}

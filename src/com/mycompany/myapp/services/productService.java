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
import com.mycompany.myapp.entities.product;
import com.mycompany.myapp.entities.productCategory;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author seif
 */
public class productService {

    public static productService instance = null;
    public static boolean resultOk = true;
    private ConnectionRequest req;
    private boolean resultat;
    public ArrayList<product> Publications;

    public static productService getInstance() {
        if (instance == null) {
            instance = new productService();
        }
        return instance;
    }

    public productService() {
        req = new ConnectionRequest();
    }

    public void addProduct(product product, productCategory category) {
        String url = Statics.BASE_URL + "product/mobile/addProduct?name=" + product.getName() + "&description=" + product.getDescription() + "&image=" + product.getImage() + "&quantity=" + product.getQuantity() + "&price=" + product.getPrice() + "&catId=" + category.getId();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean deleteProduct(int id) {
        String url = Statics.BASE_URL + "product/mobile/deleteProduct?id=" + id;

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

    public product detailProduct(int id, product product) {

        String url = Statics.BASE_URL + "product/mobile/detailProduct?id=" + id;
        req.setUrl(url);

        String str = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {

            JSONParser jsonp = new JSONParser();
            try {

                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                product.setId(id);

            } catch (IOException ex) {
                System.out.println("error related to sql :( " + ex.getMessage());
            }

            System.out.println("data === " + str);

        }));

        NetworkManager.getInstance().addToQueueAndWait(req);

        return product;

    }

    public void editProdcut(product product) {
        String url = Statics.BASE_URL + "product/mobile/editProduct?name=" + product.getName() + "&description=" + product.getDescription() + "&image=" + product.getImage() + "&quantity=" + product.getQuantity() + "&price=" + product.getPrice() + "&id=" + product.getId();
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public ArrayList<product> showProducts() {
        ArrayList<product> result = new ArrayList<>();

        String url = Statics.BASE_URL + "product/mobile/showProduct";
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
                        product pub = new product();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());

                        String name = obj.get("name").toString();
                        String description = obj.get("description").toString();
                        String image = obj.get("image").toString();
                        String price = obj.get("price").toString();
                        String quantity = obj.get("quantity").toString();

                        pub.setId((int) id);
                        pub.setName(name);
                        pub.setDescription(description);
                        pub.setImage(image);
                        pub.setPrice(price);
                        pub.setQuantity(quantity);

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

    public ArrayList<product> findProducts(String search) {
        ArrayList<product> result = new ArrayList<>();

        String url = Statics.BASE_URL + "product/mobile/findProduct?name=" + search;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapPublications = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPublications.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        product pub = new product();

                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());

                        String name = obj.get("name").toString();
                        String description = obj.get("description").toString();
                        String image = obj.get("image").toString();
                        String priceString = obj.get("price").toString();
                        String quantityString = obj.get("quantity").toString();

                        pub.setId((int) id);
                        pub.setName(name);
                        pub.setDescription(description);
                        pub.setImage(image);
                        pub.setPrice(priceString);
                        pub.setQuantity(quantityString);

                        //insert data into ArrayList result
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
            cr.setUrl("http://localhost/Calometre-main/saveimageProduct.php");
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

}

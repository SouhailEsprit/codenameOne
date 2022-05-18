/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.product;
import com.mycompany.myapp.services.productService;
import java.io.IOException;

/**
 *
 * @author louay
 */
public class EditProduct extends SideMenuBaseForm {

    String productimage;

    public EditProduct(Resources res, product publication) {
        super(BoxLayout.y());

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Edit product", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);

        TextField titre = new TextField(publication.getName(), "Nom ...");
        titre.getStyle().setBgColor(0x000000);
        titre.getStyle().setFgColor(0x000000);
        titre.getStyle().setBorder(Border.createRoundBorder(50, 50));
        titre.getStyle().setElevation(1);
        titre.getStyle().setPadding(3, 3, 0, 0);
        titre.getStyle().setUnderline(false);

        TextField description = new TextField(publication.getDescription(), "Descrption ...");
        description.getStyle().setBgColor(0x000000);
        description.getStyle().setFgColor(0x000000);
        description.getStyle().setBorder(Border.createRoundBorder(50, 50));
        description.getStyle().setElevation(1);
        description.getStyle().setPadding(3, 3, 0, 0);
        description.getStyle().setUnderline(false);

        TextField price = new TextField(publication.getPrice(), "Price ...");
        price.getStyle().setBgColor(0x000000);
        price.getStyle().setFgColor(0x000000);
        price.getStyle().setBorder(Border.createRoundBorder(50, 50));
        price.getStyle().setElevation(1);
        price.getStyle().setPadding(3, 3, 0, 0);
        price.getStyle().setUnderline(false);

        TextField quantity = new TextField(publication.getQuantity(), "Quantity ...");
        quantity.getStyle().setBgColor(0x000000);
        quantity.getStyle().setFgColor(0x000000);
        quantity.getStyle().setBorder(Border.createRoundBorder(50, 50));
        quantity.getStyle().setElevation(1);
        quantity.getStyle().setPadding(3, 3, 0, 0);
        quantity.getStyle().setUnderline(false);

        Button photobutton = new Button("Upload image");
        photobutton.getStyle().setBgColor(0xffffff);
        photobutton.getStyle().setFgColor(0x0583D2);
        photobutton.getStyle().setBgTransparency(255);
        photobutton.getStyle().setBorder(Border.createRoundBorder(30, 30));
        photobutton.getStyle().setMargin(13, 13, 80, 80);
        photobutton.getStyle().setPadding(3, 3, 0, 0);
        photobutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().openImageGallery(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        String i = (String) ev.getSource();
                        if (i != null) {
                            try {

                                Image img = Image.createImage(i);
                                img = img.scaled(Math.round(Display.getInstance().getDisplayWidth() - 40), Math.round(Display.getInstance().getDisplayHeight() - 40));
                            } catch (IOException ex) {
                            }
                        }

                        productimage = productService.getInstance().uploadPhoto(i);

                    }
                });
            }

        });

        Button addPub = new Button("Edit");
        addPub.getStyle().setBgColor(0xffffff);
        addPub.getStyle().setFgColor(0x0583D2);
        addPub.getStyle().setBgTransparency(255);
        addPub.getStyle().setBorder(Border.createRoundBorder(30, 30));
        addPub.getStyle().setMargin(13, 13, 80, 80);
        addPub.getStyle().setPadding(3, 3, 0, 0);

        Container pub = BoxLayout.encloseY(
                BorderLayout.center(
                        BoxLayout.encloseY(
                                titre, description, price, quantity, photobutton, addPub
                        )
                )
        );
        pub.getStyle().setPadding(70, 70, 40, 40);

        add(pub);

        addPub.addActionListener(l -> {
            if (titre.getText().equals("")) {
                Dialog.show("Error", "Veuillez vérifier les données", "OK", null);
            } else {
                InfiniteProgress ip = new InfiniteProgress();
                final Dialog iDialog = ip.showInfiniteBlocking();

                publication.setName(titre.getText());
                publication.setDescription(description.getText());
                publication.setImage(productimage);
                publication.setPrice(price.getText());
                publication.setQuantity(quantity.getText());

                productService.getInstance().editProdcut(publication);
                iDialog.dispose();
                new ShowProduct(res).show();
                refreshTheme();
            }
        });
        setupSideMenu(res);
    }

    @Override

    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}

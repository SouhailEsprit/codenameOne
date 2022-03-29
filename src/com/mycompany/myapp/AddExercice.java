/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.exercice;
import com.mycompany.myapp.entities.typeExercice;
import com.mycompany.myapp.services.exerciceService;

/**
 *
 * @author louay
 */
public class AddExercice extends SideMenuBaseForm {

    public AddExercice(Resources res, typeExercice publication) {
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
                                new Label("Add exercice", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);

        TextField nom = new TextField("", "Nom...");
        nom.getStyle().setBgColor(0x000000);
        nom.getStyle().setFgColor(0x000000);
        nom.getStyle().setBorder(Border.createRoundBorder(50, 50));
        nom.getStyle().setElevation(1);
        nom.getStyle().setPadding(3, 3, 0, 0);
        nom.getStyle().setUnderline(false);

        TextField description = new TextField("", "Description...");
        description.getStyle().setBgColor(0x000000);
        description.getStyle().setFgColor(0x000000);
        description.getStyle().setBorder(Border.createRoundBorder(50, 50));
        description.getStyle().setElevation(1);
        description.getStyle().setPadding(3, 3, 0, 0);
        description.getStyle().setUnderline(false);

        TextField video = new TextField("", "Video...");
        video.getStyle().setBgColor(0x000000);
        video.getStyle().setFgColor(0x000000);
        video.getStyle().setBorder(Border.createRoundBorder(50, 50));
        video.getStyle().setElevation(1);
        video.getStyle().setPadding(3, 3, 0, 0);
        video.getStyle().setUnderline(false);

        TextField objectif = new TextField("", "Objectif...");
        objectif.getStyle().setBgColor(0x000000);
        objectif.getStyle().setFgColor(0x000000);
        objectif.getStyle().setBorder(Border.createRoundBorder(50, 50));
        objectif.getStyle().setElevation(1);
        objectif.getStyle().setPadding(3, 3, 0, 0);
        objectif.getStyle().setUnderline(false);

        Button addPub = new Button("Add");
        addPub.getStyle().setBgColor(0xffffff);
        addPub.getStyle().setFgColor(0x0583D2);
        addPub.getStyle().setBgTransparency(255);
        addPub.getStyle().setBorder(Border.createRoundBorder(30, 30));
        addPub.getStyle().setMargin(13, 13, 80, 80);
        addPub.getStyle().setPadding(3, 3, 0, 0);

        Container pub = BoxLayout.encloseY(
                BorderLayout.center(
                        BoxLayout.encloseY(
                                nom, description, video, objectif, addPub
                        )
                )
        );
        pub.getStyle().setPadding(70, 70, 40, 40);

        add(pub);

        addPub.addActionListener(l -> {
            if (description.getText().equals("") || nom.getText().equals("") || video.getText().equals("") || objectif.getText().equals("")) {
                Dialog.show("Error", "Veuillez vérifier les données", "OK", null);
            } else {
                InfiniteProgress ip = new InfiniteProgress();;
                final Dialog iDialog = ip.showInfiniteBlocking();
                String categoryId = Integer.toString(publication.getId());
                exercice com = new exercice(nom.getText(), description.getText(), video.getText(), objectif.getText(), categoryId);
                exerciceService.getInstance().addExercice(com, publication);
                iDialog.dispose();
//showprodcuts
                new ShowExercice(res).show();
                /*new (res).show();
                new ShowPub(res).show();*/
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

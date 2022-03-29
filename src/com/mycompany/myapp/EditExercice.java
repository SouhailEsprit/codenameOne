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
import com.mycompany.myapp.entities.productCategory;
import com.mycompany.myapp.services.categoryService;
import com.mycompany.myapp.services.exerciceService;
import com.mycompany.myapp.services.typeExerciceService;

/**
 *
 * @author louay
 */
public class EditExercice extends SideMenuBaseForm {

    public EditExercice(Resources res, exercice publication) {
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
                                new Label("Edit exercice", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);

        TextField titre = new TextField(publication.getNom(), "Nom ...");
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

        TextField objectif = new TextField(publication.getObjectif(), "Objectif ...");
        objectif.getStyle().setBgColor(0x000000);
        objectif.getStyle().setFgColor(0x000000);
        objectif.getStyle().setBorder(Border.createRoundBorder(50, 50));
        objectif.getStyle().setElevation(1);
        objectif.getStyle().setPadding(3, 3, 0, 0);
        objectif.getStyle().setUnderline(false);

        TextField video = new TextField(publication.getVideo(), "Video ...");
        video.getStyle().setBgColor(0x000000);
        video.getStyle().setFgColor(0x000000);
        video.getStyle().setBorder(Border.createRoundBorder(50, 50));
        video.getStyle().setElevation(1);
        video.getStyle().setPadding(3, 3, 0, 0);
        video.getStyle().setUnderline(false);

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
                                titre, description, objectif, video, addPub
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

                publication.setNom(titre.getText());
                publication.setDescription(description.getText());
                publication.setObjectif(objectif.getText());
                publication.setVideo(video.getText());

                exerciceService.getInstance().editProdcut(publication);
                iDialog.dispose();
                new ShowExercice(res).show();
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

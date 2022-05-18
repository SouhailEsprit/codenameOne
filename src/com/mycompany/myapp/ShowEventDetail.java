/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.EventService;
import java.io.IOException;

/**
 *
 * @author louay
 */
public class ShowEventDetail extends SideMenuBaseForm {

    EncodedImage enc;

    public ShowEventDetail(Resources res, Event event) {
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
                                new Label(event.getNom() + " details", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);

        Label titre = new Label("Nom:" + event.getNom());
        titre.getAllStyles().setFgColor(0x000000);

        Label description = new Label("Description:" + event.getDescription());
        description.getAllStyles().setFgColor(0x000000);

        Label dateDebut = new Label("Date_debut:" + event.getDate_debut());
        dateDebut.getAllStyles().setFgColor(0x000000);

        Label dateFin = new Label("Date_Fin:" + event.getDate_Fin());
        dateFin.getAllStyles().setFgColor(0x000000);
        try {
            enc = EncodedImage.create("/giphy.gif");
        } catch (IOException ex) {
        }
        Image image = URLImage.createToStorage(enc, event.getImage(), "http://127.0.0.1/Calometre-main/public/uploads/Event_images/" + event.getImage());

        Container ev = BoxLayout.encloseY(BorderLayout.center(BoxLayout.encloseY(titre, description, dateDebut, dateFin
        )
        )
        );
        ev.getStyle().setPadding(70, 70, 40, 40);

        add(image);
        add(ev);

        setupSideMenu(res);
    }

    ShowEventDetail(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override

    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}

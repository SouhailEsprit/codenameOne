/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
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
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.EventService;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author wassim
 */
public class EventsList extends SideMenuBaseForm {

    EncodedImage enc;

    public EventsList(Resources res) {
        super(BoxLayout.y());

        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);

        Button menuButton = new Button("");
        Button addButton = new Button("add Event");
        addButton.getStyle().setBgColor(0xffffff);
        addButton.getStyle().setFgColor(0xFFA500);
        addButton.getStyle().setBgTransparency(255);
        addButton.getStyle().setBorder(Border.createRoundBorder(30, 30));
        addButton.getStyle().setPadding(1, 1, 1, 1);
        addButton.getStyle().setMargin(2, 2, 2, 2);
        addButton.addActionListener((l) -> {
            //new EditPub(res, pubs).show();
            new AddEvent(res).show();
        });
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Events", "Title")
                        )
                ),
                BorderLayout.east(addButton)
        );

        tb.setTitleComponent(titleCmp);

        ArrayList<Event> events = EventService.getInstance().showAllEvent();

        for (Event e : events) {

            Button showEventDetail = new Button("Show");
            showEventDetail.getStyle().setBgColor(0xffffff);
            showEventDetail.getStyle().setFgColor(0xFFA500);
            showEventDetail.getStyle().setBgTransparency(255);
            showEventDetail.getStyle().setBorder(Border.createRoundBorder(30, 30));
            showEventDetail.getStyle().setPadding(1, 1, 1, 1);
            showEventDetail.getStyle().setMargin(2, 2, 2, 2);
            Button deleteEvent = new Button("Delete");
            deleteEvent.getStyle().setBgColor(0xffffff);
            deleteEvent.getStyle().setFgColor(0xFFA500);
            deleteEvent.getStyle().setBgTransparency(255);
            deleteEvent.getStyle().setBorder(Border.createRoundBorder(30, 30));
            deleteEvent.getStyle().setPadding(1, 1, 1, 1);
            deleteEvent.getStyle().setMargin(2, 2, 2, 2);
            Button updateEvent = new Button("Update");
            updateEvent.getStyle().setBgColor(0xffffff);
            updateEvent.getStyle().setFgColor(0xFFA500);
            updateEvent.getStyle().setBgTransparency(255);
            updateEvent.getStyle().setBorder(Border.createRoundBorder(30, 30));
            updateEvent.getStyle().setPadding(1, 1, 1, 1);
            updateEvent.getStyle().setMargin(2, 2, 2, 2);
            showEventDetail.addActionListener((l) -> {
                //new EditPub(res, pubs).show();
                new ShowEventDetail(res, e).show();
            });
            deleteEvent.addActionListener((l) -> {
                new EventService().getInstance().supprimerEvent(String.valueOf(e.getId()));
                new EventsList(res).show();
            });
            updateEvent.addActionListener((l) -> {
                new EditEvent(res, e).show();
            });
            Label nom = new Label("Nom: " + e.getNom());
            nom.getAllStyles().setFgColor(0xffffff);

            try {
                enc = EncodedImage.create("/giphy.gif");
            } catch (IOException ex) {
            }
            Image image = URLImage.createToStorage(enc, e.getImage(), "http://127.0.0.1/Calometre-main/public/uploads/Event_images/" + e.getImage());

            Label description = new Label("Description: " + e.getDescription());
            description.getAllStyles().setFgColor(0xffffff);

            Label dateDebut = new Label("Start Date : " + e.getDate_debut());
            dateDebut.getAllStyles().setFgColor(0xffffff);

            Label dateFin = new Label("End Date : " + e.getDate_Fin());
            dateFin.getAllStyles().setFgColor(0xffffff);

            Container ev = BoxLayout.encloseY(GridLayout.encloseIn(2, dateDebut, description));
            Container ev1 = BoxLayout.encloseY(GridLayout.encloseIn(3, showEventDetail, deleteEvent, updateEvent));

            Container first = GridLayout.encloseIn(1, ev1);
            Container eve = BoxLayout.encloseY(BorderLayout.centerAbsolute(
                    BoxLayout.encloseY(
                            nom
                    )
            ),//.add(BorderLayout.WEST, pubImage),
                    BoxLayout.encloseY(ev, first)
            );

            eve.getStyle().setFgColor(0xffffff);
            eve.getStyle().setBgColor(0xfdb819);
            eve.getStyle().setBgTransparency(255);
            eve.getStyle().setPadding(7, 7, 7, 7);
            eve.getStyle().setMargin(20, 20, 30, 30);
            eve.getStyle().setBorder(Border.createRoundBorder(50, 50));

            add(image);
            add(eve);
        }
        setupSideMenu(res);
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}

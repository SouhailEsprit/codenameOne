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
import com.mycompany.myapp.entities.Event;

import com.mycompany.myapp.services.EventService;
import java.io.IOException;

/**
 *
 * @author wassim
 */
public class EditEvent extends SideMenuBaseForm {

    String eventimg;

    public EditEvent(Resources res, Event event) {
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
                                new Label("Update Event", "Title")
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
        nom.setText(event.getNom());
        TextField description = new TextField("", "Description...");
        description.getStyle().setBgColor(0x000000);
        description.getStyle().setFgColor(0x000000);
        description.getStyle().setBorder(Border.createRoundBorder(50, 50));
        description.getStyle().setElevation(1);
        description.getStyle().setPadding(3, 3, 0, 0);
        description.getStyle().setUnderline(false);
        description.setText(event.getDescription());

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

                        eventimg = EventService.getInstance().uploadPhoto(i);

                    }
                });
            }

        });

        TextField Description = new TextField("", "Date debut sous format jj/mm/aaaa");
        Description.getStyle().setBgColor(0x000000);
        Description.getStyle().setFgColor(0x000000);
        Description.getStyle().setBorder(Border.createRoundBorder(50, 50));
        Description.getStyle().setElevation(1);
        Description.getStyle().setPadding(3, 3, 0, 0);
        Description.getStyle().setUnderline(false);
        Description.setText(event.getDate_debut());

        TextField datefin = new TextField("", "Date fin sous format jj/mm/aaaa");
        datefin.getStyle().setBgColor(0x000000);
        datefin.getStyle().setFgColor(0x000000);
        datefin.getStyle().setBorder(Border.createRoundBorder(50, 50));
        datefin.getStyle().setElevation(1);
        datefin.getStyle().setPadding(3, 3, 0, 0);
        datefin.getStyle().setUnderline(false);
        datefin.setText(event.getDate_Fin());

        TextField nbplaces = new TextField("", "Nombre de places");
        nbplaces.getStyle().setBgColor(0x000000);
        nbplaces.getStyle().setFgColor(0x000000);
        nbplaces.getStyle().setBorder(Border.createRoundBorder(50, 50));
        nbplaces.getStyle().setElevation(1);
        nbplaces.getStyle().setPadding(3, 3, 0, 0);
        nbplaces.getStyle().setUnderline(false);
        nbplaces.setText(event.getNombre_participants());

        TextField lieu = new TextField("", "Lieu");
        lieu.getStyle().setBgColor(0x000000);
        lieu.getStyle().setFgColor(0x000000);
        lieu.getStyle().setBorder(Border.createRoundBorder(50, 50));
        lieu.getStyle().setElevation(1);
        lieu.getStyle().setPadding(3, 3, 0, 0);
        lieu.getStyle().setUnderline(false);
        lieu.setText(event.getLieu());

        Button addPub = new Button("Update");
        addPub.getStyle().setBgColor(0xffffff);
        addPub.getStyle().setFgColor(0x0583D2);
        addPub.getStyle().setBgTransparency(255);
        addPub.getStyle().setBorder(Border.createRoundBorder(30, 30));
        addPub.getStyle().setMargin(13, 13, 80, 80);
        addPub.getStyle().setPadding(3, 3, 0, 0);

        Container pub = BoxLayout.encloseY(
                BorderLayout.center(
                        BoxLayout.encloseY(
                                nom, description, photobutton, Description, datefin, nbplaces, lieu, addPub
                        )
                )
        );
        pub.getStyle().setPadding(70, 70, 40, 40);

        add(pub);

        addPub.addActionListener(l -> {
            if (description.getText().equals("") || nom.getText().equals("") || Description.getText().equals("")) {
                Dialog.show("Error", "Veuillez vérifier les données", "OK", null);
            } else {
                InfiniteProgress ip = new InfiniteProgress();;
                final Dialog iDialog = ip.showInfiniteBlocking();
                EventService.getInstance().modifierEvent(String.valueOf(event.getId()),nom.getText(), eventimg, description.getText(), Description.getText(), datefin.getText(), nbplaces.getText(), lieu.getText());
                iDialog.dispose();
                new EventsList(res).show();

//showEvents
            }
        });
        setupSideMenu(res);
    }

    @Override

    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

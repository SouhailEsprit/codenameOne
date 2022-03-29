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
import com.mycompany.myapp.entities.productCategory;
import com.mycompany.myapp.entities.reclamation;
import com.mycompany.myapp.services.categoryService;
import com.mycompany.myapp.services.reclamationService;

/**
 *
 * @author louay
 */
public class AddReclamation extends SideMenuBaseForm {

    public AddReclamation(Resources res) {
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
                                new Label("Add reclamation", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);

        TextField type = new TextField("", "Type ...");
        type.getStyle().setBgColor(0x000000);
        type.getStyle().setFgColor(0x000000);
        type.getStyle().setBorder(Border.createRoundBorder(50, 50));
        type.getStyle().setElevation(1);
        type.getStyle().setPadding(3, 3, 0, 0);
        type.getStyle().setUnderline(false);

        TextField email = new TextField("", "Email ...");
        email.getStyle().setBgColor(0x000000);
        email.getStyle().setFgColor(0x000000);
        email.getStyle().setBorder(Border.createRoundBorder(50, 50));
        email.getStyle().setElevation(1);
        email.getStyle().setPadding(3, 3, 0, 0);
        email.getStyle().setUnderline(false);

        TextField message = new TextField("", "Message ...");
        message.getStyle().setBgColor(0x000000);
        message.getStyle().setFgColor(0x000000);
        message.getStyle().setBorder(Border.createRoundBorder(50, 50));
        message.getStyle().setElevation(1);
        message.getStyle().setPadding(3, 3, 0, 0);
        message.getStyle().setUnderline(false);

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
                                type, email, message, addPub
                        )
                )
        );
        pub.getStyle().setPadding(70, 70, 40, 40);

        add(pub);

        addPub.addActionListener(l -> {
            if (type.getText().equals("") || message.getText().equals("") || email.getText().equals("")) {
                Dialog.show("Error", "Veuillez vérifier les données", "OK", null);
            } else {
                InfiniteProgress ip = new InfiniteProgress();
                final Dialog iDialog = ip.showInfiniteBlocking();
                reclamation p = new reclamation(type.getText().toString(), email.getText().toString(), message.getText().toString());
                reclamationService.getInstance().add(p);
                iDialog.dispose();
                new ShowReclamation(res).show();
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

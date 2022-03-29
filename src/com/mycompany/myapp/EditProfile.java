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
import com.mycompany.myapp.entities.product;
import com.mycompany.myapp.services.productService;
import com.mycompany.myapp.services.userService;

/**
 *
 * @author louay
 */
public class EditProfile extends SideMenuBaseForm {

    public EditProfile(Resources res) {
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
                                new Label("Edit profile", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);

        TextField firstname = new TextField(SessionManager.getFirstName(), "name ...");
        firstname.getStyle().setBgColor(0x000000);
        firstname.getStyle().setFgColor(0x000000);
        firstname.getStyle().setBorder(Border.createRoundBorder(50, 50));
        firstname.getStyle().setElevation(1);
        firstname.getStyle().setPadding(3, 3, 0, 0);
        firstname.getStyle().setUnderline(false);

        TextField lastname = new TextField(SessionManager.getLastName(), "last name ...");
        lastname.getStyle().setBgColor(0x000000);
        lastname.getStyle().setFgColor(0x000000);
        lastname.getStyle().setBorder(Border.createRoundBorder(50, 50));
        lastname.getStyle().setElevation(1);
        lastname.getStyle().setPadding(3, 3, 0, 0);
        lastname.getStyle().setUnderline(false);

        TextField password = new TextField("", "password ...", 20, TextField.PASSWORD);
        password.getStyle().setBgColor(0x000000);
        password.getStyle().setFgColor(0x000000);
        password.getStyle().setBorder(Border.createRoundBorder(50, 50));
        password.getStyle().setElevation(1);
        password.getStyle().setPadding(3, 3, 0, 0);
        password.getStyle().setUnderline(false);

        TextField confirmPassword = new TextField("", "Confirm password ...", 20, TextField.PASSWORD);
        confirmPassword.getStyle().setBgColor(0x000000);
        confirmPassword.getStyle().setFgColor(0x000000);
        confirmPassword.getStyle().setBorder(Border.createRoundBorder(50, 50));
        confirmPassword.getStyle().setElevation(1);
        confirmPassword.getStyle().setPadding(3, 3, 0, 0);
        confirmPassword.getStyle().setUnderline(false);

        TextField number = new TextField(String.valueOf(SessionManager.getPhoneNumber()), "Phone number ...");
        number.getStyle().setBgColor(0x000000);
        number.getStyle().setFgColor(0x000000);
        number.getStyle().setBorder(Border.createRoundBorder(50, 50));
        number.getStyle().setElevation(1);
        number.getStyle().setPadding(3, 3, 0, 0);
        number.getStyle().setUnderline(false);

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
                                firstname, lastname, password, confirmPassword, number, addPub
                        )
                )
        );
        pub.getStyle().setPadding(70, 70, 40, 40);

        add(pub);

        addPub.addActionListener(l -> {

            InfiniteProgress ip = new InfiniteProgress();
            final Dialog iDialog = ip.showInfiniteBlocking();
            userService.getInstance().editUser(SessionManager.getId(), firstname.getText().toString(), lastname.getText().toString(), password.getText().toString(), number.getText().toString());
            iDialog.dispose();
            new ProfileForm(res).show();
            refreshTheme();

        }
        );
        setupSideMenu(res);
    }

    @Override

    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

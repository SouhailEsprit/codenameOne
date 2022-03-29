/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.userService;
import java.util.Vector;

/**
 *
 * @author louay
 */
public class CreateAccount extends Form {

    public CreateAccount(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Welcome, ", "WelcomeWhite")
        );

        getTitleArea().setUIID("Container");

        TextField email = new TextField("", "email", 20, TextField.EMAILADDR);
        TextField firstname = new TextField("", "firstname");
        TextField lastname = new TextField("", "lastname");
        TextField phonenumber = new TextField("", "phonenumber", 20, TextField.NUMERIC);
        TextField countrycode = new TextField("", "countrycode", 20, TextField.NUMERIC);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);

        email.getAllStyles().setMargin(LEFT, 0);
        firstname.getAllStyles().setMargin(LEFT, 0);
        lastname.getAllStyles().setMargin(LEFT, 0);
        phonenumber.getAllStyles().setMargin(LEFT, 0);
        countrycode.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);

        Label emailIcon = new Label("", "TextField");
        Label firstnameIcon = new Label("", "TextField");
        Label lastnameIcon = new Label("", "TextField");
        Label phonenumberIcon = new Label("", "TextField");
        Label countrycodeIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");

        emailIcon.getAllStyles().setMargin(RIGHT, 0);
        firstnameIcon.getAllStyles().setMargin(RIGHT, 0);
        lastnameIcon.getAllStyles().setMargin(RIGHT, 0);
        phonenumberIcon.getAllStyles().setMargin(RIGHT, 0);
        countrycodeIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);

        FontImage.setMaterialIcon(emailIcon, FontImage.MATERIAL_MAIL_OUTLINE, 3);
        FontImage.setMaterialIcon(firstnameIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(lastnameIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(phonenumberIcon, FontImage.MATERIAL_PHONE, 3);
        FontImage.setMaterialIcon(countrycodeIcon, FontImage.MATERIAL_CODE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Vector<String> vectorRole;
        vectorRole = new Vector();
        vectorRole.add("Coach");
        vectorRole.add("Client");

        ComboBox<String> roles;
        roles = new ComboBox<>(vectorRole);

        Button loginButton = new Button("Create account");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            userService.getInstance().SignUp(firstname, lastname, email, password, password, countrycode, phonenumber, password, roles, theme);
            if (userService.getInstance().SignUp(firstname, lastname, email, password, password, countrycode, phonenumber, password, roles, theme)) {
                Dialog.show("success", "connected", "OK", null);
                new LoginForm(theme).show();
            }
        });

        Button createNewAccount = new Button("Login");
        createNewAccount.setUIID("CreateNewAccountButton");
        createNewAccount.addActionListener((l) -> {
            new LoginForm(theme).show();
        });

        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(firstname).
                        add(BorderLayout.WEST, firstnameIcon),
                BorderLayout.center(lastname).
                        add(BorderLayout.WEST, lastnameIcon),
                BorderLayout.center(phonenumber).
                        add(BorderLayout.WEST, phonenumberIcon),
                BorderLayout.center(countrycode).
                        add(BorderLayout.WEST, countrycodeIcon),
                BorderLayout.center(roles),
                BorderLayout.center(email).
                        add(BorderLayout.WEST, emailIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                createNewAccount
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}

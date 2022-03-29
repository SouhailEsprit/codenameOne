/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.productCategory;
import com.mycompany.myapp.entities.typeExercice;
import com.mycompany.myapp.services.categoryService;
import com.mycompany.myapp.services.typeExerciceService;
import java.util.ArrayList;

/**
 *
 * @author louay
 */
public class ShowType extends SideMenuBaseForm {

    public ShowType(Resources res) {
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
                                new Label("Type Exercice", "Title")
                        )
                )
        );

        tb.setTitleComponent(titleCmp);

        Button newPost = new Button("Add new Type");

        /* TextField SearchArea = new TextField("", "search...");
        SearchArea.getStyle().setBgColor(0x000000);
        SearchArea.getStyle().setFgColor(0x000000);
        SearchArea.getStyle().setBorder(Border.createRoundBorder(50, 50));
        SearchArea.getStyle().setElevation(1);
        SearchArea.getStyle().setPadding(3, 3, 0, 0);
        SearchArea.getStyle().setUnderline(false);

        Button searchButton = new Button("Search");
        searchButton.addActionListener((l) -> {
            new FindPub(res, SearchArea.getText()).show();
        });*/
        newPost.setAlignment(LEFT);
        newPost.addActionListener((l) -> {
            new AddType(res).show();
        });

        add(new Label("Type Exercice", ""));
        add(newPost);
        /*add(SearchArea);
        add(searchButton);*/

        ArrayList<typeExercice> Publications = typeExerciceService.getInstance().showType();

        for (typeExercice pubs : Publications) {

            /*String imageLink = pubs.getImage();
//show image in codename
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xffff0000), true);
            Image im = URLImage.createToStorage(placeholder, imageLink, imageLink);
            ImageViewer image = new ImageViewer(im.scaled(250, 200));*/
            Button addComment = new Button("Add Exercice");
            addComment.getStyle().setBgColor(0xffffff);
            addComment.getStyle().setFgColor(0x0583D2);
            addComment.getStyle().setBgTransparency(255);
            addComment.getStyle().setBorder(Border.createRoundBorder(30, 30));
            addComment.getStyle().setPadding(1, 1, 1, 1);
            addComment.getStyle().setMargin(2, 2, 2, 2);
            addComment.addActionListener((l) -> {
                //new AddComment(res, pubs).show();
                new AddExercice(res, pubs).show();
            });

            Button editPost = new Button("Edit");
            editPost.getStyle().setBgColor(0xffffff);
            editPost.getStyle().setFgColor(0xFFA500);
            editPost.getStyle().setBgTransparency(255);
            editPost.getStyle().setBorder(Border.createRoundBorder(30, 30));
            editPost.getStyle().setPadding(1, 1, 1, 1);
            editPost.getStyle().setMargin(2, 2, 2, 2);
            editPost.addActionListener((l) -> {
                new EditType(res, pubs).show();
            });

            Button deletePost = new Button("Delete");
            deletePost.getStyle().setBgColor(0xffffff);
            deletePost.getStyle().setFgColor(0xFF0000);
            deletePost.getStyle().setBgTransparency(255);
            deletePost.getStyle().setBorder(Border.createRoundBorder(30, 30));
            deletePost.getStyle().setPadding(1, 1, 1, 1);
            deletePost.getStyle().setMargin(2, 2, 2, 2);
            deletePost.addActionListener((l) -> {
                typeExerciceService.getInstance().deleteType(pubs.getId());
                if (typeExerciceService.getInstance().deleteType(pubs.getId())) {
                    Dialog.show("Success", "type deleted", "OK", null);
                    new ShowType(res).show();
                    refreshTheme();
                }
            });
//String username = user.getUsername();
            Label nom = new Label("Nom: " + pubs.getName());
            nom.getAllStyles().setFgColor(0xffffff);

            Container first = GridLayout.encloseIn(2, addComment, editPost);
            Container second = GridLayout.encloseIn(1, deletePost);
            Container pub = BoxLayout.encloseY(
                    BorderLayout.centerAbsolute(
                            BoxLayout.encloseY(
                                    nom
                            )
                    ),//.add(BorderLayout.WEST, pubImage),
                    BoxLayout.encloseY(first, second)
            );

            pub.getStyle().setFgColor(0xffffff);
            pub.getStyle().setBgColor(0x00FFFF);
            pub.getStyle().setBgTransparency(255);
            pub.getStyle().setPadding(7, 7, 7, 7);
            pub.getStyle().setMargin(20, 20, 30, 30);
            pub.getStyle().setBorder(Border.createRoundBorder(50, 50));

            add(pub);
        }
        setupSideMenu(res);
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}

package application;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class InterestScene {

    private static final String[][] CATS = {
        {"CODING",      "💻", "Coding",            "Software Eng. & CS"},
        {"MACHINERY",   "⚙",  "Machinery",          "Mechanical, Mechatronics & Industrial"},
        {"BUILDING",    "🏗",  "Building",           "Civil Engineering"},
        {"ENVIRONMENT", "🌿", "Environment",        "Environmental Engineering"},
        {"NETWORK",     "🔧", "Network & Hardware",  "Comp. Eng., Cyber Security & Telecom"},
        {"ELECTRICITY", "⚡", "Electricity",         "Electrical & Electronics Eng."},
    };

    public static VBox build() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color:" + UI.BG + ";");

        HBox hdr = UI.header(3, "Choose Your Interest");
        hdr.getChildren().add(0, UI.backBtn(() -> SceneManager.show(MarksScene.build())));
        root.getChildren().add(hdr);

        VBox content = new VBox(18);
        content.setPadding(new Insets(24, 60, 20, 60));
        VBox.setVgrow(content, Priority.ALWAYS);

        Label scTitle = new Label("🎯 What drives your passion?");
        scTitle.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");

        HBox aggBox = new HBox(10);
        aggBox.setStyle(
            "-fx-background-color:white;-fx-background-radius:7;" +
            "-fx-effect:dropshadow(gaussian,rgba(0,0,0,0.07),4,0,0,1);" +
            "-fx-padding:10 18 10 18;"
        );
        aggBox.setAlignment(Pos.CENTER_LEFT);

        Label aggLbl = new Label("Your Calculated Aggregate:");
        aggLbl.setStyle("-fx-font-size:13px;-fx-text-fill:" + UI.GRAY + ";");

        Label aggVal = new Label(String.format("%.2f%%", Session.aggregate));
        aggVal.setStyle("-fx-font-size:18px;-fx-font-weight:bold;-fx-text-fill:" + UI.GOLD + ";");

        aggBox.getChildren().addAll(aggLbl, aggVal);

        Label sub = new Label("Select a category to see available departments:");
        sub.setStyle("-fx-font-size:13px;-fx-text-fill:" + UI.GRAY + ";");

        FlowPane catPane = new FlowPane(16, 16);
        catPane.setAlignment(Pos.CENTER_LEFT);
        for (String[] cat : CATS) {
            VBox card = buildCategoryCard(cat[1], cat[2], cat[3], cat[0]);
            catPane.getChildren().add(card);
        }

        content.getChildren().addAll(scTitle, aggBox, sub, catPane);
        root.getChildren().addAll(content, UI.footer());
        return root;
    }

    private static VBox buildCategoryCard(String emoji, String title,
                                          String desc, String catKey) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(180);
        card.setPrefHeight(150);
        card.setPadding(new Insets(18));
        card.setStyle(
            "-fx-background-color:white;-fx-border-color:#C8D4E0;" +
            "-fx-border-width:1;-fx-border-radius:10;-fx-background-radius:10;" +
            "-fx-cursor:hand;-fx-effect:dropshadow(gaussian,rgba(0,0,0,0.07),5,0,0,1);"
        );

        Label eml = new Label(emoji);
        eml.setStyle("-fx-font-size:28px;");

        Label tl = new Label(title);
        tl.setStyle("-fx-font-size:13px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");
        tl.setWrapText(true);
        tl.setTextAlignment(TextAlignment.CENTER);

        Label dl = new Label(desc);
        dl.setStyle("-fx-font-size:11px;-fx-text-fill:" + UI.GRAY + ";");
        dl.setWrapText(true);
        dl.setTextAlignment(TextAlignment.CENTER);

        card.getChildren().addAll(eml, tl, dl);

        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color:#F0F4FA;-fx-border-color:" + UI.GOLD + ";-fx-border-width:2;" +
            "-fx-border-radius:10;-fx-background-radius:10;-fx-cursor:hand;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color:white;-fx-border-color:#C8D4E0;-fx-border-width:1;" +
            "-fx-border-radius:10;-fx-background-radius:10;-fx-cursor:hand;" +
            "-fx-effect:dropshadow(gaussian,rgba(0,0,0,0.07),5,0,0,1);"
        ));

        card.setOnMouseClicked(e -> {
            Session.category = catKey;
            card.setStyle(
                "-fx-background-color:#FFF8E8;-fx-border-color:" + UI.GOLD + ";" +
                "-fx-border-width:3;-fx-border-radius:10;-fx-background-radius:10;-fx-cursor:hand;"
            );
            PauseTransition pt = new PauseTransition(Duration.millis(250));
            pt.setOnFinished(ev -> SceneManager.show(DepartmentScene.build()));
            pt.play();
        });

        return card;
    }
          }

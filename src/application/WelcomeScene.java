package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class WelcomeScene {

    public static VBox build() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color:" + UI.BG + ";");
        root.getChildren().add(UI.header(1, "Program Selection"));

        VBox content = new VBox(22);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30, 60, 20, 60));
        VBox.setVgrow(content, Priority.ALWAYS);

        Label icon = new Label("🏛");
        icon.setStyle("-fx-font-size:50px;");

        Label uniName = new Label("University of Engineering & Technology, Taxila");
        uniName.setStyle("-fx-font-size:13px;-fx-font-weight:bold;-fx-text-fill:" + UI.GRAY + ";");

        Label title = new Label("Eligibility & Department Recommendation System");
        title.setStyle("-fx-font-size:23px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");
        title.setWrapText(true);
        title.setTextAlignment(TextAlignment.CENTER);

        Label sub = new Label("Select your admission program to get started");
        sub.setStyle("-fx-font-size:13px;-fx-text-fill:" + UI.GRAY + ";");

        HBox cards = new HBox(28);
        cards.setAlignment(Pos.CENTER);

        VBox ecatCard = makeCard(
            "💻", "ECAT Program",
            "Engineering & CS",
            "Software  CS  Mechanical\nElectrical  Civil  Telecom + more",
            UI.NAVY, false
        );
        VBox nonEcatCard = makeCard(
            "🔬", "Non-ECAT Program",
            "Technology & Pure Sciences",
            "BS Mathematics\nBS Physics",
            UI.GOLD, true
        );

        for (javafx.scene.Node n : ecatCard.getChildren()) {
            if (n instanceof Button) {
                ((Button) n).setOnAction(e -> {
                    Session.reset();
                    Session.studentType = "ECAT";
                    SceneManager.show(MarksScene.build());
                });
            }
        }

        for (javafx.scene.Node n : nonEcatCard.getChildren()) {
            if (n instanceof Button) {
                ((Button) n).setOnAction(e -> {
                    Session.reset();
                    Session.studentType = "NON_ECAT";
                    SceneManager.show(MarksScene.build());
                });
            }
        }

        cards.getChildren().addAll(ecatCard, nonEcatCard);
        content.getChildren().addAll(icon, uniName, title, sub, cards);
        root.getChildren().addAll(content, UI.footer());
        return root;
    }

    private static VBox makeCard(String emoji, String title, String sub,
                                 String detail, String borderColor, boolean goldBtn) {
        VBox c = new VBox(11);
        c.setAlignment(Pos.CENTER);
        c.setPrefWidth(255);
        c.setPadding(new Insets(26));
        c.setStyle(
            "-fx-background-color:white;" +
            "-fx-border-color:" + borderColor + ";-fx-border-width:0 0 4 0;" +
            "-fx-border-radius:10;-fx-background-radius:10;" +
            "-fx-effect:dropshadow(gaussian,rgba(0,0,0,0.11),8,0,0,2);"
        );

        Label ic = new Label(emoji);
        ic.setStyle("-fx-font-size:32px;");

        Label tl = new Label(title);
        tl.setStyle("-fx-font-size:15px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");

        Label sl = new Label(sub);
        sl.setStyle("-fx-font-size:12px;-fx-text-fill:" + UI.GRAY + ";");
        sl.setWrapText(true);
        sl.setTextAlignment(TextAlignment.CENTER);

        Label dl = new Label(detail);
        dl.setStyle("-fx-font-size:11px;-fx-text-fill:#7A90A8;");
        dl.setWrapText(true);
        dl.setTextAlignment(TextAlignment.CENTER);

        Button btn = goldBtn ? UI.secondaryBtn("SELECT") : UI.primaryBtn("SELECT");
        btn.setMaxWidth(Double.MAX_VALUE);

        c.getChildren().addAll(ic, tl, sl, dl, btn);
        return c;
    }
                             }

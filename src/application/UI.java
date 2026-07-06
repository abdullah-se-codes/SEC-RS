package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;

public class UI {

    public static final String NAVY  = "#0D2240";
    public static final String GOLD  = "#F5A623";
    public static final String BG    = "#F4F6FA";
    public static final String GRAY  = "#5A7090";
    public static final String GREEN = "#27AE60";
    public static final String RED   = "#E74C3C";

    public static HBox header(int step, String label) {
        HBox h = new HBox(12);
        h.setStyle("-fx-background-color:" + NAVY + ";-fx-padding:10 22 10 22;");
        h.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label("Step " + step + " of 5  " + label);
        lbl.setStyle("-fx-text-fill:#B8C8DC;-fx-font-size:12px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ProgressBar pb = new ProgressBar(0);
        pb.setPrefWidth(200);
        pb.setPrefHeight(9);
        pb.setStyle("-fx-accent:" + GOLD + ";");

        Timeline progressAnimation = new Timeline(
            new KeyFrame(Duration.millis(500),
                new KeyValue(pb.progressProperty(), step / 5.0))
        );
        progressAnimation.play();

        h.getChildren().addAll(lbl, spacer, pb);
        return h;
    }

    public static HBox footer() {
        HBox f = new HBox();
        f.setStyle("-fx-background-color:" + NAVY + ";-fx-padding:7 0 7 0;");
        f.setAlignment(Pos.CENTER);

        Label l = new Label("Department of Software Engineering  UET Taxila");
        l.setStyle("-fx-text-fill:#4A6080;-fx-font-size:11px;");

        f.getChildren().add(l);
        return f;
    }

    public static Button backBtn(Runnable action) {
        Button b = new Button("<- Back");
        b.setStyle(
            "-fx-background-color:transparent;-fx-text-fill:#B8C8DC;" +
            "-fx-border-color:#1A3560;-fx-border-radius:4;-fx-background-radius:4;" +
            "-fx-padding:4 10 4 10;-fx-cursor:hand;-fx-font-size:12px;"
        );
        b.setOnAction(e -> action.run());
        return b;
    }

    public static Button primaryBtn(String text) {
        Button b = new Button(text);
        b.setStyle(
            "-fx-background-color:" + NAVY + ";-fx-text-fill:white;" +
            "-fx-font-size:13px;-fx-font-weight:bold;-fx-padding:9 22 9 22;" +
            "-fx-background-radius:6;-fx-cursor:hand;"
        );
        return b;
    }

    public static Button secondaryBtn(String text) {
        Button b = new Button(text);
        b.setStyle(
            "-fx-background-color:" + GOLD + ";-fx-text-fill:" + NAVY + ";" +
            "-fx-font-size:13px;-fx-font-weight:bold;-fx-padding:9 22 9 22;" +
            "-fx-background-radius:6;-fx-cursor:hand;"
        );
        return b;
    }

    public static VBox card(int padding) {
        VBox c = new VBox(10);
        c.setStyle(
            "-fx-background-color:white;-fx-background-radius:8;" +
            "-fx-effect:dropshadow(gaussian,rgba(0,0,0,0.09),7,0,0,2);" +
            "-fx-padding:" + padding + ";"
        );
        return c;
    }

    public static Label sectionTitle(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size:13px;-fx-font-weight:bold;-fx-text-fill:" + NAVY + ";");
        return l;
    }

    public static Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size:11px;-fx-text-fill:" + GRAY + ";-fx-font-weight:bold;");
        return l;
    }

    public static TextField marksField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(130);
        tf.setStyle(
            "-fx-background-color:#F4F6FA;-fx-border-color:#C8D4E0;" +
            "-fx-border-radius:5;-fx-background-radius:5;" +
            "-fx-padding:7 11 7 11;-fx-font-size:13px;"
        );
        return tf;
    }

    public static Label pctLabel() {
        Label l = new Label("--");
        l.setStyle("-fx-font-size:14px;-fx-font-weight:bold;-fx-text-fill:" + GOLD + ";-fx-padding:7 0 7 0;");
        l.setPrefWidth(70);
        return l;
    }

    public static void wirePct(TextField obt, TextField tot, Label lbl) {
        Runnable update = () -> {
            try {
                double o = Double.parseDouble(obt.getText().trim());
                double t = Double.parseDouble(tot.getText().trim());
                lbl.setText(t > 0 ? String.format("%.1f%%", (o / t) * 100) : "--");
            } catch (NumberFormatException ex) {
                lbl.setText("--");
            }
        };
        obt.textProperty().addListener((a, b, c) -> update.run());
        tot.textProperty().addListener((a, b, c) -> update.run());
    }

    public static VBox fieldCol(String label, javafx.scene.Node field) {
        return new VBox(5, fieldLabel(label), field);
    }

    public static double parseD(TextField tf, String fieldName) throws Exception {
        String s = tf.getText().trim();
        if (s.isEmpty()) throw new Exception(fieldName + " is required.");
        try {
            double v = Double.parseDouble(s);
            if (v < 0) throw new Exception(fieldName + " cannot be negative.");
            return v;
        } catch (NumberFormatException ex) {
            throw new Exception(fieldName + " must be a valid number.");
        }
    }

    public static String fmt(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }

    public static String pct(double o, double t) {
        return t > 0 ? String.format("%.2f%%", (o / t) * 100) : "--";
    }
  }
      

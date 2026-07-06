package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ResultScene {

    public static VBox build() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color:" + UI.BG + ";");
        root.getChildren().add(UI.header(5, "Final Result Summary"));

        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color:transparent;-fx-border-color:transparent;");
        VBox.setVgrow(sp, Priority.ALWAYS);

        VBox content = new VBox(16);
        content.setPadding(new Insets(22, 60, 22, 60));
        content.setStyle("-fx-background-color:" + UI.BG + ";");

        Label scTitle = new Label("📋 Comprehensive Result Summary");
        scTitle.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");

        Department dept = Department.find(Session.department);
        String elig = dept != null ? dept.evaluate(Session.aggregate) : "NOT_ELIGIBLE";

        HBox banner = buildBanner(dept, elig);

        VBox marksCard = UI.card(14);
        marksCard.getChildren().add(UI.sectionTitle("Marks Breakdown"));
        marksCard.getChildren().add(buildMarksGrid());

        VBox formulaCard = UI.card(14);
        formulaCard.getChildren().add(UI.sectionTitle("Aggregate Calculation"));

        String formula = "NON_ECAT".equals(Session.studentType)
            ? "Aggregate = (Matric% x 0.30) + (FSc% x 0.70)"
            : "DAE".equals(Session.qualType)
            ? "Aggregate = (Matric% x 0.17) + (DAE% x 0.50) + (ECAT% x 0.33)"
            : "A_LEVEL".equals(Session.qualType)
            ? "Aggregate = (OLevels% x 0.67) + (ECAT% x 0.33)"
            : "Aggregate = (Matric% x 0.17) + (FSc% x 0.50) + (ECAT% x 0.33)";

        Label formulaL = new Label(formula);
        formulaL.setStyle(
            "-fx-font-size:13px;-fx-text-fill:#3A5070;-fx-font-style:italic;" +
            "-fx-background-color:#F0F4FA;-fx-padding:10;-fx-background-radius:5;"
        );

        String aggColor;
        if      (Session.aggregate >= 70) aggColor = UI.GREEN;
        else if (Session.aggregate >= 50) aggColor = "#E6A817";
        else                              aggColor = UI.RED;

        Label aggBig = new Label("Your Aggregate: 0.00%");
        aggBig.setStyle("-fx-font-size:21px;-fx-font-weight:bold;-fx-text-fill:" + aggColor + ";");

        formulaCard.getChildren().addAll(formulaL, aggBig);

        double target = Session.aggregate;
        Timeline aggregateAnimation = new Timeline();
        for (int i = 0; i <= 50; i++) {
            final double value = target * i / 50.0;
            aggregateAnimation.getKeyFrames().add(
                new KeyFrame(Duration.millis(i * 30),
                    e -> aggBig.setText(String.format("Your Aggregate: %.2f%%", value)))
            );
        }
        aggregateAnimation.play();

        VBox resultCard = UI.card(14);
        resultCard.getChildren().add(UI.sectionTitle("Selected Department - Full Detail"));
        resultCard.getChildren().add(buildResultGrid(dept, elig));

        VBox allCard = UI.card(14);
        allCard.getChildren().add(UI.sectionTitle("All Departments in Your Category"));
        List<Department> depts = Department.byCategory(Session.category);
        for (Department d : depts) {
            allCard.getChildren().add(buildMiniRow(d));
        }

        Button restartBtn = UI.primaryBtn("🔄 Start Over");
        restartBtn.setOnAction(e -> {
            Session.reset();
            SceneManager.show(WelcomeScene.build());
        });

        HBox btnRow = new HBox(restartBtn);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        content.getChildren().addAll(scTitle, banner, marksCard, formulaCard,
                                     resultCard, allCard, btnRow);

        banner.setOpacity(0);
        marksCard.setOpacity(0);
        formulaCard.setOpacity(0);
        resultCard.setOpacity(0);
        allCard.setOpacity(0);

        fadeIn(banner,      0);
        fadeIn(marksCard,   200);
        fadeIn(formulaCard, 400);
        fadeIn(resultCard,  600);
        fadeIn(allCard,     800);

        sp.setContent(content);
        content.layout();
        sp.setVvalue(0);

        root.getChildren().addAll(sp, UI.footer());
        javafx.application.Platform.runLater(() -> sp.setVvalue(0));
        return root;
    }

    private static void fadeIn(javafx.scene.Node node, int delayMs) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setDelay(Duration.millis(delayMs));
        ft.play();
    }

    private static HBox buildBanner(Department dept, String elig) {
        HBox banner = new HBox(16);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setStyle("OPEN_MERIT".equals(elig)
            ? "-fx-background-color:#D5F5E3;-fx-border-color:" + UI.GREEN + ";-fx-border-width:2;-fx-border-radius:8;-fx-background-radius:8;-fx-padding:16 22 16 22;"
            : "SELF_FINANCE".equals(elig)
            ? "-fx-background-color:#FEF9E7;-fx-border-color:" + UI.GOLD + ";-fx-border-width:2;-fx-border-radius:8;-fx-background-radius:8;-fx-padding:16 22 16 22;"
            : "-fx-background-color:#FADBD8;-fx-border-color:" + UI.RED + ";-fx-border-width:2;-fx-border-radius:8;-fx-background-radius:8;-fx-padding:16 22 16 22;"
        );

        Label bannerIcon = new Label(
            "OPEN_MERIT".equals(elig)   ? "🎉"
          : "SELF_FINANCE".equals(elig) ? "✅"
          : "❌"
        );
        bannerIcon.setStyle("-fx-font-size:34px;");

        VBox bannerText = new VBox(4);

        Label bannerTitle = new Label(
            "OPEN_MERIT".equals(elig)   ? "Congratulations! - Open Merit"
          : "SELF_FINANCE".equals(elig) ? "Eligible - Self Finance Basis"
          : "Not Eligible"
        );
        bannerTitle.setStyle("-fx-font-size:17px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");

        Label bannerSub = new Label(dept != null
            ? dept.reason(Session.aggregate)
            : "No department selected."
        );
        bannerSub.setStyle("-fx-font-size:13px;-fx-text-fill:#3A5070;");
        bannerSub.setWrapText(true);

        bannerText.getChildren().addAll(bannerTitle, bannerSub);
        banner.getChildren().addAll(bannerIcon, bannerText);
        return banner;
    }

    private static GridPane buildMarksGrid() {
        GridPane g = new GridPane();
        g.setHgap(0);
        g.setVgap(0);

        addHeader(g, 0, "Category", "Obtained", "Total", "Percentage");
        int row = 1;

        boolean isEcat = "ECAT".equals(Session.studentType);

        if ("DAE".equals(Session.qualType) && isEcat) {
            addRow(g, row++, "Matric / SSC",
                UI.fmt(Session.matricObt), UI.fmt(Session.matricTot),
                String.format("%.2f%%", Session.matricPct()));
            addRow(g, row, "DAE",
                UI.fmt(Session.daeObt), UI.fmt(Session.daeTot),
                UI.pct(Session.daeObt, Session.daeTot));
        } else {
            addRow(g, row++, "Matric / SSC",
                UI.fmt(Session.matricObt), UI.fmt(Session.matricTot),
                String.format("%.2f%%", Session.matricPct()));

            if (!isEcat) {
                addRow(g, row++, "Intermediate",
                    UI.fmt(Session.p1Obt), UI.fmt(Session.p1Tot),
                    String.format("%.2f%%", Session.interPct()));
            } else {
                addRow(g, row++, "FSc / Intermediate",
                    UI.fmt(Session.p1Obt), UI.fmt(Session.p1Tot),
                    String.format("%.2f%%", Session.interPct()));
            }

            if (isEcat) {
                addRow(g, row, "ECAT",
                    UI.fmt(Session.ecatObt), UI.fmt(Session.ecatTot),
                    String.format("%.2f%%", Session.ecatPct()));
            }
        }

        return g;
    }

    private static GridPane buildResultGrid(Department dept, String elig) {
        GridPane g = new GridPane();
        g.setHgap(0);
        g.setVgap(0);

        int r = 0;
        addResultRow(g, r++, "Student Type",          Session.studentType + " (" + Session.qualType + ")");
        addResultRow(g, r++, "Calculated Aggregate",  String.format("%.2f%%", Session.aggregate));
        addResultRow(g, r++, "Selected Department",   Session.department);

        if (dept != null) {
            addResultRow(g, r++, "Open Merit Cutoff",        String.format("%.0f%%", dept.openMerit));
            addResultRow(g, r++, "Self Finance Cutoff",      String.format("%.0f%%", dept.selfFinance));
            addResultRow(g, r++, "Highest Historical Merit", String.format("%.1f%%", dept.highestMerit));
            addResultRow(g, r++, "Eligibility",              elig.replace("_", " "));
            addResultRow(g, r,   "Reason",                   dept.reason(Session.aggregate));
        }

        return g;
    }

    private static HBox buildMiniRow(Department d) {
        String de   = d.evaluate(Session.aggregate);
        String icon = "OPEN_MERIT".equals(de)   ? "✅"
                    : "SELF_FINANCE".equals(de) ? "🟡"
                    : "❌";

        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(5, 10, 5, 10));
        row.setStyle("-fx-background-color:#F8FAFC;-fx-background-radius:5;");

        Label il = new Label(icon);

        Label nl = new Label(d.name);
        nl.setStyle("-fx-font-size:13px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");
        nl.setPrefWidth(250);

        Label el = new Label(de.replace("_", " "));
        el.setStyle("-fx-font-size:11px;-fx-font-weight:bold;-fx-text-fill:" +
            ("OPEN_MERIT".equals(de)   ? "#1E8449"
           : "SELF_FINANCE".equals(de) ? "#9A7D0A"
           : "#922B21") + ";"
        );
        el.setPrefWidth(120);

        Label cl = new Label(String.format("Open: %.0f%%  |  SF: %.0f%%",
            d.openMerit, d.selfFinance));
        cl.setStyle("-fx-font-size:11px;-fx-text-fill:#7A90A8;");

        row.getChildren().addAll(il, nl, el, cl);
        return row;
    }

    private static void addHeader(GridPane g, int row, String... cols) {
        for (int i = 0; i < cols.length; i++) {
            Label l = new Label(cols[i]);
            l.setStyle("-fx-font-weight:bold;-fx-background-color:" + UI.NAVY +
                       ";-fx-text-fill:white;-fx-padding:6 12 6 12;");
            l.setPrefWidth(i == 0 ? 180 : 130);
            g.add(l, i, row);
        }
    }

    private static void addRow(GridPane g, int row, String... vals) {
        String bg = row % 2 == 0 ? "white" : "#F4F6FA";
        for (int i = 0; i < vals.length; i++) {
            Label l = new Label(vals[i]);
            l.setStyle("-fx-background-color:" + bg + ";-fx-text-fill:#2A3A50;-fx-padding:5 12 5 12;");
            l.setPrefWidth(i == 0 ? 180 : 130);
            l.setWrapText(true);
            g.add(l, i, row);
        }
    }

    private static void addResultRow(GridPane g, int row, String key, String val) {
        Label k = new Label(key);
        k.setStyle("-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY +
                   ";-fx-font-size:12px;-fx-padding:5 10 5 5;");
        k.setPrefWidth(200);

        Label v = new Label(val);
        v.setStyle("-fx-text-fill:#3A5070;-fx-font-size:12px;-fx-padding:5 5 5 5;");
        v.setPrefWidth(480);
        v.setWrapText(true);

        g.add(k, 0, row);
        g.add(v, 1, row);
    }
              }
      

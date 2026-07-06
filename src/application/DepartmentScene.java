package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.List;

public class DepartmentScene {

    public static VBox build() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color:" + UI.BG + ";");

        HBox hdr = UI.header(4, "Department & Merit Analysis");
        hdr.getChildren().add(0, UI.backBtn(() -> {
            if ("NON_ECAT".equals(Session.studentType)) {
                SceneManager.show(MarksScene.build());
            } else {
                SceneManager.show(InterestScene.build());
            }
        }));
        root.getChildren().add(hdr);

        VBox content = new VBox(14);
        content.setPadding(new Insets(20, 50, 16, 50));

        String catLabel = Session.category;
        for (String[] c : InterestScene_Labels.CATS) {
            if (c[0].equals(Session.category)) {
                catLabel = c[1] + " " + c[2];
                break;
            }
        }

        Label scTitle = new Label(catLabel + " - Available Departments");
        scTitle.setStyle("-fx-font-size:19px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");

        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);

        HBox aggBox = new HBox(10);
        aggBox.setStyle(
            "-fx-background-color:white;-fx-background-radius:7;" +
            "-fx-effect:dropshadow(gaussian,rgba(0,0,0,0.07),4,0,0,1);" +
            "-fx-padding:8 16 8 16;"
        );
        aggBox.setAlignment(Pos.CENTER_LEFT);

        Label al = new Label("Your Aggregate:");
        al.setStyle("-fx-font-size:13px;-fx-text-fill:" + UI.GRAY + ";");

        Label av = new Label(String.format("%.2f%%", Session.aggregate));
        av.setStyle("-fx-font-size:17px;-fx-font-weight:bold;-fx-text-fill:" + UI.GOLD + ";");

        aggBox.getChildren().addAll(al, av);

        Label legend = new Label("  Open Merit   Self Finance   Not Eligible");
        legend.setStyle("-fx-font-size:12px;-fx-text-fill:" + UI.GRAY + ";");

        topRow.getChildren().addAll(aggBox, legend);
        content.getChildren().addAll(scTitle, topRow);

        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color:transparent;-fx-border-color:transparent;");
        VBox.setVgrow(sp, Priority.ALWAYS);

        VBox deptList = new VBox(10);
        deptList.setPadding(new Insets(4));
        deptList.setStyle("-fx-background-color:" + UI.BG + ";");

        List<Department> depts = Department.byCategory(Session.category);
        for (Department dept : depts) {
            deptList.getChildren().add(buildDeptCard(dept));
        }

        sp.setContent(deptList);
        VBox.setVgrow(sp, Priority.ALWAYS);
        content.getChildren().add(sp);
        VBox.setVgrow(content, Priority.ALWAYS);

        root.getChildren().addAll(content, UI.footer());
        return root;
    }

    private static HBox buildDeptCard(Department dept) {
        String elig = dept.evaluate(Session.aggregate);

        HBox card = new HBox(14);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(13, 16, 13, 16));

        String bc = "OPEN_MERIT".equals(elig)   ? UI.GREEN
                  : "SELF_FINANCE".equals(elig) ? UI.GOLD
                  : UI.RED;

        card.setStyle(
            "-fx-background-color:white;-fx-background-radius:7;" +
            "-fx-border-color:" + bc + ";-fx-border-width:0 0 0 5;-fx-border-radius:7;" +
            "-fx-effect:dropshadow(gaussian,rgba(0,0,0,0.08),5,0,0,1);"
        );

        Label badge = new Label(
            "OPEN_MERIT".equals(elig)   ? "✅ OPEN MERIT"
          : "SELF_FINANCE".equals(elig) ? "💰 SELF FINANCE"
          : "❌ NOT ELIGIBLE"
        );
        badge.setPrefWidth(140);
        badge.setWrapText(true);
        badge.setAlignment(Pos.CENTER);
        badge.setStyle("OPEN_MERIT".equals(elig)
            ? "-fx-background-color:#D5F5E3;-fx-text-fill:#1E8449;-fx-font-weight:bold;-fx-font-size:11px;-fx-padding:4 8 4 8;-fx-background-radius:5;"
            : "SELF_FINANCE".equals(elig)
            ? "-fx-background-color:#FEF9E7;-fx-text-fill:#9A7D0A;-fx-font-weight:bold;-fx-font-size:11px;-fx-padding:4 8 4 8;-fx-background-radius:5;"
            : "-fx-background-color:#FADBD8;-fx-text-fill:#922B21;-fx-font-weight:bold;-fx-font-size:11px;-fx-padding:4 8 4 8;-fx-background-radius:5;"
        );

        VBox info = new VBox(4);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label dname = new Label(dept.name);
        dname.setStyle("-fx-font-size:15px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");

        Label reason = new Label(dept.reason(Session.aggregate));
        reason.setStyle("-fx-font-size:12px;-fx-text-fill:" + UI.GRAY + ";");
        reason.setWrapText(true);

        Label cutoffs = new Label(String.format(
            "Open Merit: %.0f%%  |  Self Finance: %.0f%%  |  Highest Merit: %.1f%%",
            dept.openMerit, dept.selfFinance, dept.highestMerit
        ));
        cutoffs.setStyle("-fx-font-size:11px;-fx-text-fill:#7A90A8;");

        info.getChildren().addAll(dname, reason, cutoffs);

        Button selBtn = new Button("Select");
        selBtn.setMinWidth(70);
        selBtn.setStyle(
            "-fx-background-color:" + UI.NAVY + ";-fx-text-fill:white;" +
            "-fx-font-size:12px;-fx-padding:6 14 6 14;-fx-background-radius:5;-fx-cursor:hand;"
        );

        if ("NOT_ELIGIBLE".equals(elig)) {
            selBtn.setDisable(true);
            selBtn.setStyle(
                "-fx-background-color:#CCCCCC;-fx-text-fill:#888888;" +
                "-fx-font-size:12px;-fx-padding:6 14 6 14;-fx-background-radius:5;"
            );
        }

        selBtn.setOnAction(e -> {
            Session.department = dept.name;
            SceneManager.show(ResultScene.build());
        });

        card.getChildren().addAll(badge, info, selBtn);
        return card;
    }

    static class InterestScene_Labels {
        static final String[][] CATS = {
            {"CODING",      "💻", "Coding"},
            {"MACHINERY",   "⚙",  "Machinery"},
            {"BUILDING",    "🏗",  "Building"},
            {"ENVIRONMENT", "🌿", "Environment"},
            {"NETWORK",     "🔧", "Network & Hardware"},
            {"ELECTRICITY", "⚡", "Electricity"},
            {"SCIENCE",     "🔬", "Pure Sciences"},
        };
    }
          }

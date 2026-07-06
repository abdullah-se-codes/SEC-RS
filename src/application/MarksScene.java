package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MarksScene {

    public static VBox build() {
        VBox root = new VBox(0);
        root.setStyle("-fx-background-color:" + UI.BG + ";");

        HBox hdr = UI.header(2, "Academic Marks Entry");
        hdr.getChildren().add(0, UI.backBtn(() -> SceneManager.show(WelcomeScene.build())));
        root.getChildren().add(hdr);

        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color:transparent;-fx-border-color:transparent;");
        VBox.setVgrow(sp, Priority.ALWAYS);

        VBox content = new VBox(16);
        content.setPadding(new Insets(22, 60, 22, 60));
        content.setStyle("-fx-background-color:" + UI.BG + ";");

        Label scTitle = new Label("📝 Enter Your Academic Marks");
        scTitle.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:" + UI.NAVY + ";");

        boolean isEcat = "ECAT".equals(Session.studentType);

        Label progLbl = new Label(isEcat
            ? "Program: ECAT - Engineering & Computer Science"
            : "Program: Non-ECAT - BS Mathematics / BS Physics"
        );
        progLbl.setStyle("-fx-font-size:13px;-fx-text-fill:" + UI.GRAY + ";");

        content.getChildren().addAll(scTitle, progLbl);

        VBox matricSection       = buildMatricSection();
        VBox interSection        = buildInterSection();
        VBox nonEcatInterSection = buildNonEcatIntermediateSection();
        VBox daeSection          = buildDaeSection();
        VBox ecatSection         = buildEcatSection();

        HBox interHBoxRef = (HBox) interSection.getChildren().get(1);
        VBox interObtCol  = (VBox) interHBoxRef.getChildren().get(0);
        VBox interTotCol  = (VBox) interHBoxRef.getChildren().get(1);
        Label interObtLbl = (Label) interObtCol.getChildren().get(0);
        Label interTotLbl = (Label) interTotCol.getChildren().get(0);
        TextField[] interFieldsRef = (TextField[]) interSection.getUserData();

        if (isEcat) {
            VBox qualCard = UI.card(14);
            qualCard.getChildren().add(UI.sectionTitle("Select Your Qualification Background"));

            ChoiceBox<String> qualBox = new ChoiceBox<>();
            qualBox.getItems().addAll("FSc / HSSC", "DAE", "A-Level");
            qualBox.setValue("FSc / HSSC");
            qualBox.setPrefWidth(200);
            qualBox.setStyle("-fx-font-size:13px;");

            Session.qualType = "HSSC";

            qualBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal == null) return;

                switch (newVal) {
                    case "DAE":
                        Session.qualType = "DAE";
                        daeSection.setVisible(true);
                        daeSection.setManaged(true);
                        matricSection.setVisible(true);
                        matricSection.setManaged(true);
                        interSection.setVisible(false);
                        interSection.setManaged(false);
                        break;

                    case "A-Level":
                        Session.qualType = "A_LEVEL";
                        daeSection.setVisible(false);
                        daeSection.setManaged(false);
                        matricSection.setVisible(true);
                        matricSection.setManaged(true);
                        interSection.setVisible(true);
                        interSection.setManaged(true);

                        interObtLbl.setText("Obtained Marks (Not Applicable)");
                        interTotLbl.setText("Total Marks (Not Applicable)");

                        interFieldsRef[0].setDisable(true);
                        interFieldsRef[1].setDisable(true);
                        break;

                    default:
                        Session.qualType = "HSSC";
                        daeSection.setVisible(false);
                        daeSection.setManaged(false);
                        matricSection.setVisible(true);
                        matricSection.setManaged(true);
                        interSection.setVisible(true);
                        interSection.setManaged(true);

                        interObtLbl.setText("Obtained Marks");
                        interTotLbl.setText("Total Marks");

                        interFieldsRef[0].setDisable(false);
                        interFieldsRef[1].setDisable(false);
                        break;
                }
            });

            daeSection.setVisible(false);
            daeSection.setManaged(false);

            qualCard.getChildren().add(qualBox);
            content.getChildren().add(qualCard);
            content.getChildren().addAll(matricSection, interSection, daeSection);
        } else {
            content.getChildren().addAll(matricSection, nonEcatInterSection);
        }

        if (isEcat) content.getChildren().add(ecatSection);

        Label errLbl = new Label("");
        errLbl.setStyle("-fx-text-fill:#C0392B;-fx-font-size:12px;-fx-font-weight:bold;");
        errLbl.setWrapText(true);

        Button nextBtn = UI.primaryBtn("Calculate & Next ->");
        nextBtn.setOnAction(e -> {
            try {
                errLbl.setText("");

                readAndValidate(
                    matricSection,
                    isEcat ? interSection : nonEcatInterSection,
                    daeSection,
                    ecatSection,
                    isEcat
                );

                if (isEcat && "A_LEVEL".equals(Session.qualType)) {
                    double matricPercentage = (Session.matricObt / Session.matricTot) * 100.0;
                    double ecatPercentage = (Session.ecatObt / Session.ecatTot) * 100.0;
                    Session.aggregate = (matricPercentage * 0.67) + (ecatPercentage * 0.33);
                } else {
                    Session.calcAggregate();
                }

                if ("NON_ECAT".equals(Session.studentType)) {
                    Session.category = "SCIENCE";
                    SceneManager.show(DepartmentScene.build());
                } else {
                    SceneManager.show(InterestScene.build());
                }
            } catch (Exception ex) {
                errLbl.setText("⚠ " + ex.getMessage());
            }
        });

        HBox btnRow = new HBox(nextBtn);
        btnRow.setAlignment(Pos.CENTER_RIGHT);
        content.getChildren().addAll(errLbl, btnRow);

        sp.setContent(content);
        root.getChildren().addAll(sp, UI.footer());
        return root;
    }

    private static VBox buildMatricSection() {
        VBox sec = UI.card(14);
        sec.getChildren().add(UI.sectionTitle("Matriculation / SSC"));
        TextField obt = UI.marksField("e.g. 980");
        TextField tot = UI.marksField("e.g. 1100");
        Label pct = UI.pctLabel();
        UI.wirePct(obt, tot, pct);
        sec.getChildren().add(new HBox(14,
            UI.fieldCol("Obtained Marks", obt),
            UI.fieldCol("Total Marks", tot),
            UI.fieldCol("Percentage", pct)
        ));
        sec.setUserData(new TextField[]{obt, tot});
        return sec;
    }

    private static VBox buildInterSection() {
        VBox sec = UI.card(14);
        sec.getChildren().add(UI.sectionTitle("Enter your Intermediate Part 1 Marks"));
        TextField obt = UI.marksField("e.g. 900");
        TextField tot = UI.marksField("e.g. 1200");
        Label pct = UI.pctLabel();
        UI.wirePct(obt, tot, pct);
        sec.getChildren().add(new HBox(14,
            UI.fieldCol("Obtained Marks", obt),
            UI.fieldCol("Total Marks", tot),
            UI.fieldCol("Percentage", pct)
        ));
        sec.setUserData(new TextField[]{obt, tot});
        return sec;
    }

    private static VBox buildNonEcatIntermediateSection() {
        VBox sec = UI.card(14);
        sec.getChildren().add(UI.sectionTitle("Intermediate"));
        TextField obt = UI.marksField("e.g. 850");
        TextField tot = UI.marksField("e.g. 1100");
        Label pct = UI.pctLabel();
        UI.wirePct(obt, tot, pct);
        sec.getChildren().add(new HBox(14,
            UI.fieldCol("Obtained Marks", obt),
            UI.fieldCol("Total Marks", tot),
            UI.fieldCol("Percentage", pct)
        ));
        sec.setUserData(new TextField[]{obt, tot});
        return sec;
    }

    private static VBox buildDaeSection() {
        VBox sec = UI.card(14);
        sec.getChildren().add(UI.sectionTitle("DAE Marks"));
        TextField obt = UI.marksField("e.g. 1400");
        TextField tot = UI.marksField("e.g. 1800");
        Label pct = UI.pctLabel();
        UI.wirePct(obt, tot, pct);
        sec.getChildren().add(new HBox(14,
            UI.fieldCol("Obtained Marks", obt),
            UI.fieldCol("Total Marks", tot),
            UI.fieldCol("Percentage", pct)
        ));
        sec.setUserData(new TextField[]{obt, tot});
        return sec;
    }

    private static VBox buildEcatSection() {
        VBox sec = UI.card(14);
        sec.getChildren().add(UI.sectionTitle("ECAT / Entry Test"));
        TextField obt = UI.marksField("e.g. 280");
        TextField tot = UI.marksField("400");
        tot.setText("400");
        Label pct = UI.pctLabel();
        UI.wirePct(obt, tot, pct);
        sec.getChildren().add(new HBox(14,
            UI.fieldCol("Net ECAT Score", obt),
            UI.fieldCol("Total Marks", tot),
            UI.fieldCol("Percentage", pct)
        ));
        sec.setUserData(new TextField[]{obt, tot});
        return sec;
    }

    private static void readAndValidate(VBox matricSec, VBox interSec,
                                        VBox daeSec, VBox ecatSec,
                                        boolean isEcat) throws Exception {
        TextField[] mf = (TextField[]) matricSec.getUserData();
        Session.matricObt = UI.parseD(mf[0], "Matric Obtained");
        Session.matricTot = UI.parseD(mf[1], "Matric Total");
        if (Session.matricObt > Session.matricTot)
            throw new Exception("Matric obtained marks cannot exceed total marks.");

        if (isEcat) {
            if ("DAE".equals(Session.qualType)) {
                TextField[] df = (TextField[]) daeSec.getUserData();
                if (df == null) throw new Exception("DAE fields not found.");
                Session.daeObt = UI.parseD(df[0], "DAE Obtained");
                Session.daeTot = UI.parseD(df[1], "DAE Total");
                if (Session.daeObt > Session.daeTot)
                    throw new Exception("DAE obtained marks cannot exceed total marks.");
            } else if ("HSSC".equals(Session.qualType)) {
                TextField[] inf = (TextField[]) interSec.getUserData();
                Session.p1Obt = UI.parseD(inf[0], "FSc Obtained");
                Session.p1Tot = UI.parseD(inf[1], "FSc Total");
                if (Session.p1Obt > Session.p1Tot)
                    throw new Exception("FSc obtained marks cannot exceed total marks.");
            }

            TextField[] ef = (TextField[]) ecatSec.getUserData();
            Session.ecatObt = UI.parseD(ef[0], "ECAT Obtained");
            Session.ecatTot = ef[1].getText().trim().isEmpty()
                ? 400
                : UI.parseD(ef[1], "ECAT Total");
            if (Session.ecatObt > Session.ecatTot)
                throw new Exception("ECAT obtained marks cannot exceed total (max 400).");
        } else {
            TextField[] inf = (TextField[]) interSec.getUserData();
            Session.p1Obt = UI.parseD(inf[0], "Intermediate Obtained");
            Session.p1Tot = UI.parseD(inf[1], "Intermediate Total");
            if (Session.p1Obt > Session.p1Tot)
                throw new Exception("Intermediate obtained marks cannot exceed total marks.");
        }
    }
}

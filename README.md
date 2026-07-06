# 🎓 Student's Eligibility Checking & Recommendation System (SEC&RS)

A JavaFX desktop app that automates admission aggregate calculation, eligibility checking, and department recommendation for UET Taxila applicants.

📺 **[Watch the demo video](https://drive.google.com/file/d/1eLJxokZA3cgZ63vsYf-e9h8AzPY7sMbo/view?usp=drivesdk)** 

## What it does

- Enter marks for HSSC / A-Levels / DAE and get your aggregate calculated automatically
- Pick your area of interest and see which departments you qualify for
- Get a clear breakdown: Open Merit / Self Finance / Not Eligible, with cutoffs and reasoning
- Guided 5-step flow with a clean, professional UI

## Aggregate Formulas

| Path | Formula |
|---|---|
| Non-ECAT | `(Matric% × 0.30) + (Inter% × 0.70)` |
| ECAT — HSSC/FSc | `(Matric% × 0.17) + (FSc% × 0.50) + (ECAT% × 0.33)` |
| ECAT — DAE | `(Matric% × 0.17) + (DAE% × 0.50) + (ECAT% × 0.33)` |
| ECAT — A-Level | `(O-Levels% × 0.67) + (ECAT% × 0.33)` |

## Tech Stack

Java · JavaFX · Eclipse IDE · OOP

## Project Structure

```
src/application/
├── Main.java
├── SceneManager.java
├── UI.java
├── Session.java
├── Department.java
├── WelcomeScene.java
├── MarksScene.java
├── InterestScene.java
├── DepartmentScene.java
└── ResultScene.java
```

## How to Run

1. Install a JDK (17+) and the [JavaFX SDK](https://gluonhq.com/products/javafx/)
2. In Eclipse, install the **e(fx)clipse** plugin (`Help → Eclipse Marketplace`)
3. Create a new **JavaFX Project**, drop in the `src/application` folder from this repo
4. Run `Main.java` as a Java Application

## Author
Built by Muhammad Abdullah as part of an Object-Oriented Programming course.

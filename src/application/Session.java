package application;

public class Session {

    public static String studentType = "";
    public static String qualType = "HSSC";

    public static double matricObt, matricTot;
    public static double p1Obt, p1Tot;
    public static double daeObt, daeTot;
    public static double ecatObt, ecatTot = 400;

    public static double aggregate;

    public static String category = "";
    public static String department = "";

    public static void reset() {
        studentType = "";
        qualType    = "HSSC";
        matricObt   = matricTot = 0;
        p1Obt       = p1Tot = 0;
        daeObt      = daeTot = 0;
        ecatObt     = 0;
        ecatTot     = 400;
        aggregate   = 0;
        category    = "";
        department  = "";
    }

    public static double matricPct() {
        return matricTot > 0 ? (matricObt / matricTot) * 100 : 0;
    }

    public static double interPct() {
        return p1Tot > 0 ? (p1Obt / p1Tot) * 100 : 0;
    }

    public static double daePct() {
        return daeTot > 0 ? (daeObt / daeTot) * 100 : 0;
    }

    public static double ecatPct() {
        return ecatTot > 0 ? (ecatObt / ecatTot) * 100 : 0;
    }

    public static double calcAggregate() {
        if ("NON_ECAT".equals(studentType)) {
            aggregate = (matricPct() * 0.30) + (interPct() * 0.70);
        } else if ("DAE".equals(qualType)) {
            aggregate = (matricPct() * 0.17) + (daePct() * 0.50) + (ecatPct() * 0.33);
        } else if ("A_LEVEL".equals(qualType)) {
            aggregate = (matricPct() * 0.67) + (ecatPct() * 0.33);
        } else {
            aggregate = (matricPct() * 0.17) + (interPct() * 0.50) + (ecatPct() * 0.33);
        }
        return aggregate;
    }
}

package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Department {

    public String name;
    public String category;
    public double openMerit;
    public double selfFinance;
    public double highestMerit;

    public Department(String name, String category,
                      double openMerit, double selfFinance, double highestMerit) {
        this.name         = name;
        this.category     = category;
        this.openMerit    = openMerit;
        this.selfFinance  = selfFinance;
        this.highestMerit = highestMerit;
    }

    public String evaluate(double agg) {
        if (agg >= openMerit)   return "OPEN_MERIT";
        if (agg >= selfFinance) return "SELF_FINANCE";
        return "NOT_ELIGIBLE";
    }

    public String reason(double agg) {
        switch (evaluate(agg)) {
            case "OPEN_MERIT":
                return String.format(
                    "Your %.2f%% meets the Open Merit cutoff of %.0f%%.",
                    agg, openMerit);
            case "SELF_FINANCE":
                return String.format(
                    "Your %.2f%% qualifies for Self-Finance (%.0f%%). Open Merit needs %.0f%%.",
                    agg, selfFinance, openMerit);
            default:
                return String.format(
                    "Your %.2f%% is below both Self-Finance (%.0f%%) and Open Merit (%.0f%%) cutoffs.",
                    agg, selfFinance, openMerit);
        }
    }

    public static final List<Department> ALL = new ArrayList<>(Arrays.asList(
        new Department("Software Engineering",     "CODING",       82, 72, 82.3),
        new Department("Computer Science",         "CODING",       79, 71, 79.1),
        new Department("Mechanical Engineering",   "MACHINERY",    73, 66, 73.1),
        new Department("Mechatronics Engineering", "MACHINERY",    73, 68, 73.0),
        new Department("Industrial Engineering",   "MACHINERY",    69, 60, 69.2),
        new Department("Civil Engineering",        "BUILDING",     70, 60, 70.0),
        new Department("Environmental Engineering","ENVIRONMENT",  66, 57, 66.0),
        new Department("Electrical Engineering",   "ELECTRICITY",  70, 60, 70.1),
        new Department("Electronics Engineering",  "ELECTRICITY",  71, 60, 71.0),
        new Department("Computer Engineering",     "NETWORK",      77, 70, 77.2),
        new Department("Cyber Security",           "NETWORK",      68, 60, 68.2),
        new Department("Telecommunication Eng.",   "NETWORK",      68, 58, 68.0),
        new Department("Mathematics",              "SCIENCE",      55, 50, 55.0),
        new Department("Physics",                  "SCIENCE",      55, 50, 55.0)
    ));

    public static List<Department> byCategory(String cat) {
        List<Department> result = new ArrayList<>();
        for (Department d : ALL) {
            if (d.category.equals(cat)) result.add(d);
        }
        return result;
    }

    public static Department find(String name) {
        for (Department d : ALL) {
            if (d.name.equals(name)) return d;
        }
        return null;
    }
}

package zw.co.techtrendz.school.management.api.enums;

public enum Grade {
    A_P("A+"),
    A("A"),
    A_M("A-"),
    B_P("B+"),
    B("B"),
    B_M("B-"),
    C_P("C+"),
    C("C"),
    C_M("C-"),
    D_P("D+"),
    D("D"),
    D_M("D-"),
    E("E"),
    F("F"),
    U("U");

    private final String value;

    Grade(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Grade fromString(String grade) {
        return Grade.valueOf(grade.toUpperCase());
    }
}

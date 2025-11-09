package zw.co.techtrendz.school.management.api.academic.enums;

public enum SubjectType {
    CORE("Core"),
    ELECTIVE("Elective"),
    AP("Advanced Placement"),
    PHYSICAL("Physical"),
    OTHER("Other");

    private final String name;

    SubjectType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SubjectType fromString(String name) {
        for (SubjectType type : SubjectType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}

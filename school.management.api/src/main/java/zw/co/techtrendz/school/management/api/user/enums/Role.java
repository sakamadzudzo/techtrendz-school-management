package zw.co.techtrendz.school.management.api.user.enums;

public enum Role {
    ADMIN,
    TEACHER,
    STUDENT,
    PARENT,
    STAFF,
    GUEST;

    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}

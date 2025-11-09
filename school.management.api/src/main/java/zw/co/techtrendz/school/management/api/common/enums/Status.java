package zw.co.techtrendz.school.management.api.common.enums;

public enum Status {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    DELETED,
    PENDING,
    APPROVED,
    REJECTED,
    BLOCKED,
    UNBLOCKED,
    ENABLED,
    DISABLED;

    public static Status fromString(String status) {
        return Status.valueOf(status.toUpperCase());
    }
}

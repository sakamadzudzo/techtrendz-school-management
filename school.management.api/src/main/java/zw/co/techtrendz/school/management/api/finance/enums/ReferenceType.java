package zw.co.techtrendz.school.management.api.finance.enums;

public enum ReferenceType {
    TRANSACTION_ID("Transaction ID"),
    REF_NUMBER("Ref Number"),
    CODE("Code");

    private final String value;

    ReferenceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ReferenceType fromString(String refenceType) {
        return ReferenceType.valueOf(refenceType.toUpperCase());
    }
}

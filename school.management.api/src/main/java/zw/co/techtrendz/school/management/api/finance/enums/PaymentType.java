package zw.co.techtrendz.school.management.api.finance.enums;

public enum PaymentType {

    CASH("Cash"),
    MOBILE_MONEY("Mobile Money"),
    SWIPE("Swipe"),
    ZIPIT("Zipit"),
    RTGS("RTGS"),
    DEPOSIT("Deposit"),
    KIND("Kind");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentType fromString(String paymentType) {
        return PaymentType.valueOf(paymentType.toUpperCase());
    }
}

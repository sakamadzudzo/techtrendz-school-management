package zw.co.techtrendz.school.management.api.enums;

public enum Salutation {
    MR("Mr"),
    MRS("Mrs"),
    MS("Ms"),
    DR("Dr"),
    PROF("Prof"),
    ENG("Eng"),
    REV("Rev"),
    SNR("Snr"),
    JNR("Jnr");

    private final String value;

    Salutation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Salutation fromString(String salutation) {
        return Salutation.valueOf(salutation.toUpperCase());
    }
}

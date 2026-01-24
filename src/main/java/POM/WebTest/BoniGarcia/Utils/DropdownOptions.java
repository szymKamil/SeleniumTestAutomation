package POM.WebTest.BoniGarcia.Utils;

public enum DropdownOptions {
    OPTION_A("San Francisco"),
    OPTION_B("New York"),
    OPTION_C("Seattle"),
    OPTION_D("Los Angeles"),
    OPTION_E("Chicago");

    private final String value;

    DropdownOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

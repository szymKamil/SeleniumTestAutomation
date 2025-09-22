package POM.WebTest.SwagLabs.Utils;

public enum InventoryFilter {

	AtoZ("Name (A to Z)"),
	ZtoA("Name (Z to A)"),
	LowToHigh("Price (low to high)"),
	HighToLow("Price (high to low)");

	private String value;

	InventoryFilter(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

package POM.WebTest.SwagLabs.Utils;

import java.util.*;

public class PickRandomProducts {

	public static Set<String> pickRandomProducts(int bound){
		List<String> products = new ArrayList<>(List.of("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket",
				"Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)"));
		Random random = new Random();
		var randomNumOfProds = random.nextInt(1, bound);
		Set<String> productsSet = new HashSet<>();
			for (int i = 0; i < randomNumOfProds; i++){
				productsSet.add(products.get(i));
			}
		return productsSet;
	}

}

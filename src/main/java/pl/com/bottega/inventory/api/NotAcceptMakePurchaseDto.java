package pl.com.bottega.inventory.api;

import java.util.HashMap;
import java.util.Map;

public class NotAcceptMakePurchaseDto extends MakePurchaseDto{

    private Map<String,Integer> missingProducts;

    public NotAcceptMakePurchaseDto() {
        super(false);
        this.missingProducts = new HashMap<>();
    }

    public Map<String, Integer> getMissingProducts() {
        return missingProducts;
    }

    public void setMissingProducts(Map<String, Integer> missingProducts) {
        this.missingProducts = missingProducts;
    }
}

package pl.com.bottega.inventory.domain.commands;

import java.util.Map;

public class MakePurchaseCommand implements Validatable {

    private Map<String , Integer> products;

    public void validate(ValidationErrors errors){

        if(products.isEmpty())
            errors.add("skus","are required");

        products.entrySet().stream().forEach((para) -> {
            validatePresenceOf(para.getValue(),"para.getKey",errors);
            validatePresenceOf(para.getKey(),"para.getKey",errors);
        });
        products.entrySet().stream().forEach((para) ->{
            if(para.getValue() > 999 || para.getValue() < 1) {
                errors.add(para.getKey(),"must be between 1 and 999");
            }
            });


    }




    public MakePurchaseCommand(Map<String, Integer> products) {
        this.products = products;
    }

    public MakePurchaseCommand() {
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }
}

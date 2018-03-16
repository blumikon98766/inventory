package pl.com.bottega.inventory.domain;

import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Inventory {

    @Id
    @Column
    private String skuCode;

    @Column
    private Integer amount;

    public Inventory(AddInventoryCommand command) {
        this.skuCode = command.getSkuCode();
        this.amount = command.getAmount();
    }

    public Inventory() {
    }

    public void increase(Integer amount){
        this.amount += amount;
    }

    public void dicrease(Integer amount){
            this.amount -= amount;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

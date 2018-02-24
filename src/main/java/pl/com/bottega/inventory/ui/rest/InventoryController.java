package pl.com.bottega.inventory.ui.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.MakePurchaseDto;
import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;
import pl.com.bottega.inventory.domain.commands.MakePurchaseCommand;

import java.util.Map;

@RestController
public class InventoryController {

    private CommandGateway gateway;

    public InventoryController(CommandGateway gateway) {
        this.gateway = gateway;
    }

//
//    public void addInventory(@RequestBody String skuCode,
//                             @RequestBody Integer amount){
//        AddInventoryCommand command = new AddInventoryCommand();
//        command.setSkuCode(skuCode);
//        command.setamount(amount);


    @PostMapping("/inventory")
    public void addInventory(@RequestBody AddInventoryCommand command){
        gateway.execute(command);
    }


    @PostMapping("/purchase")
    public MakePurchaseDto Buy(@RequestBody Map<String,Integer> values){
        MakePurchaseCommand command= new MakePurchaseCommand(values);
        return gateway.execute(command);
    }

}

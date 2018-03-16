package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;
import pl.com.bottega.inventory.infrastructure.JPAInventoryRepository;

import java.util.Optional;


@Component
public class AddInventoryHandler implements Handler<AddInventoryCommand, Void>{


    private InventoryRepository inventoryRepository;


    public AddInventoryHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public Void handle(AddInventoryCommand command) {
        Optional<Inventory> inventory = inventoryRepository.findBySkuCode(command.getSkuCode());
        if(inventory.isPresent()){
            Inventory inventoryItem = inventory.get();
            inventoryItem.increase(command.getAmount());
            inventoryRepository.save(inventoryItem);
        }else{
            Inventory inventoryItem = new Inventory(command);
            inventoryRepository.save(inventoryItem);
        }

        return null;
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return AddInventoryCommand.class;
    }
}

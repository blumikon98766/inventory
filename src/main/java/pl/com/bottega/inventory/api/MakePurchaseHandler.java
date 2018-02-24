package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.MakePurchaseCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import java.util.Optional;


@Component
public class MakePurchaseHandler implements Handler<MakePurchaseCommand, MakePurchaseDto> {

    private InventoryRepository inventoryRepository;

    public MakePurchaseHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    @Transactional
    public MakePurchaseDto handle(MakePurchaseCommand command) {
        canMakePurchase(command);
        AcceptMakePurchaseDto acceptDto = new AcceptMakePurchaseDto();
        NotAcceptMakePurchaseDto noAcceptDto = new NotAcceptMakePurchaseDto();


        chooseGoodOption(command, acceptDto, noAcceptDto);

        if(acceptDto.getPurchasedProducts().size() == command.getProducts().size())
            command.getProducts().entrySet().stream().forEach((pair) -> {
                Optional<Inventory> inventoryItem = inventoryRepository.findBySkuCode(pair.getKey());
                inventoryItem.get().dicrease(pair.getValue());
                inventoryRepository.save(inventoryItem.get());
            });



        MakePurchaseDto makePurchaseDto;
        if (noAcceptDto.getMissingProducts().isEmpty()) {
            makePurchaseDto = acceptDto;
        } else
            makePurchaseDto = noAcceptDto;

        return makePurchaseDto;

    }

    private void chooseGoodOption(MakePurchaseCommand command, AcceptMakePurchaseDto acceptDto, NotAcceptMakePurchaseDto noAcceptDto) {
        command.getProducts().entrySet().stream().forEach(pair -> {
            String requestSkuCode = pair.getKey();
            Integer requestAmount = pair.getValue();
            Optional<Inventory> inventoryItem = inventoryRepository.findBySkuCode(requestSkuCode);

            if (inventoryItem.get().getAmount() >= requestAmount) {

                acceptDto.getPurchasedProducts().put(requestSkuCode, requestAmount);
            } else {
                noAcceptDto.getMissingProducts().put(requestSkuCode, requestAmount);
            }

        });
    }

    private void canMakePurchase(MakePurchaseCommand command) {

        Validatable.ValidationErrors validationErrors = new Validatable.ValidationErrors();
        command.getProducts().entrySet().forEach((para) -> {
            if (!ifExist(para.getKey())) {
                validationErrors.add(para.getKey(), "no such sku");
            }
        });
        if (!validationErrors.isValid())
            throw new InvalidCommandException(validationErrors);
    }

    private boolean ifExist(String key) {
        return inventoryRepository.ifExist(key);
    }


    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return MakePurchaseCommand.class;
    }

//    @Override
//    public boolean canHandle(Command command) {
//        return false;
//    }
}

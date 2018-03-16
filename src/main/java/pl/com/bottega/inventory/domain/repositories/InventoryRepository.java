package pl.com.bottega.inventory.domain.repositories;

import pl.com.bottega.inventory.domain.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {

    void save(Inventory inventory);

    boolean ifExist(String skuCode);

    public List<Inventory> getAll();

    public Inventory checkAndTakeAnTakeOne(String skuCode);

    public Optional<Inventory> findBySkuCode(String skuCode);
}

package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class JPAInventoryRepository implements InventoryRepository {

  @PersistenceContext
  private EntityManager entityManager;

    @Override
    public void save(Inventory inventory) {
        entityManager.persist(inventory);
    }



  @Override
  public Inventory checkAndTakeAnTakeOne(String skuCode) {
      Inventory result = entityManager.createQuery("FROM Inventory i WHERE i.skuCode =:skuCode", Inventory.class)
            .setParameter("skuCode", skuCode).getSingleResult();
    return result;
  }

    @Override
    public Optional<Inventory> findBySkuCode(String skuCode) {
        try {
            Query query = entityManager.createQuery("FROM Inventory i WHERE i.skuCode = :skuCode")
                    .setParameter("skuCode", skuCode);
            return Optional.of((Inventory) query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean ifExist(String skuCode){
        Inventory inventory =entityManager.find(Inventory.class,skuCode);
        if(inventory != null)
            return true;
        return false;
    }

  @Override
  public List<Inventory> getAll() {
      List<Inventory> resultList= entityManager.createQuery(
              "SELECT NEW pl.com.bottega.inventory.api.MakePurchaseDto(i.skuCode, i.amount) FROM Inventory i").getResultList();
      return resultList;
  }


}

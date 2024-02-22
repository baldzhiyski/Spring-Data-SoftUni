import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class RemoveTowns {
    public static void main(String[] args) {
        final String townNameToBeRemoved = new Scanner(System.in).nextLine();

        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        Town townWIthGivenName = entityManager
                .createQuery("FROM Town  WHERE name = :namegiv", Town.class)
                .setParameter("namegiv", townNameToBeRemoved)
                .getSingleResult();

        List<Address> addressesList = entityManager.createQuery("FROM Address WHERE town.id = :townOb", Address.class)
                .setParameter("townOb", townWIthGivenName.getId())
                .getResultList();

        addressesList.forEach(addr->addr.getEmployees().forEach(em->em.setAddress(null)));

        addressesList.forEach(entityManager::remove);
        entityManager.remove(townWIthGivenName);

        System.out.printf("%d address%s in %s deleted",
                addressesList.size(),addressesList.size()== 1 ? "" : "es",townNameToBeRemoved);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}

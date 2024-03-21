package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.enums.ApartmentType;

import java.util.List;

@Repository
public interface OfferRepository  extends JpaRepository<Offer,Long> {

    @Query(value = "select o from Offer  o" +
            " where o.apartment.apartmentType = :type" +
            " order by o.apartment.area DESC , o.price")
    List<Offer> findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc(ApartmentType type);

}

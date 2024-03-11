package bg.softuni.jsonexercise.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    @Column
    @Size(min = 3)
    private String name;

    @Column
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id")
    private User buyer;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id",nullable = false)
    private User seller;
}

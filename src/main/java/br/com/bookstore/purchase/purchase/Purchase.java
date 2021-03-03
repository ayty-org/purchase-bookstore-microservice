package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(builderClassName = "Builder")
public class Purchase implements Serializable {
    private static final long serialVersionUID = 923870238720232L;

    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @PrimaryKeyJoinColumn
    private ClientDTO specificIdClient;

    @ManyToMany(cascade = CascadeType.DETACH)
    @PrimaryKeyJoinColumn(name = "purchased_books")
    private Set<BookDTO> specificIdBooks = new HashSet<>();

    @Column(name = "amount_to_pay")
    private double amountToPay;

    @Column(name = "purchase_status")
    private Enum<Status> status;
}

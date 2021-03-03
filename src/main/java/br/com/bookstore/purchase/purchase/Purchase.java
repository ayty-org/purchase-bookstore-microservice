package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

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

    private String specificIdClient;

    private Set<String> specificIdBooks;

    @Column(name = "amount_to_pay")
    private double amountToPay;

    @Column(name = "purchase_status")
    private Enum<Status> status;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "specific_id")
    private String specificID = UUID.randomUUID().toString();

    public static Purchase to(PurchaseSaveDTO dto) {
        return Purchase
                .builder()
                .specificIdClient(dto.getSpecificIdClient())
                .specificIdBooks(dto.getSpecificIdBooks())
                .amountToPay(dto.getAmountToPay())
                .status(dto.getStatus())
                .specificID(dto.getSpecificID())
                .build();
    }
}

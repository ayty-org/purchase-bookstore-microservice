package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
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

    private String specificIdBooks;

    @Column(name = "amount_to_pay")
    private double amountToPay;

    @Column(name = "purchase_status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String specificID = UUID.randomUUID().toString();

    public static Purchase to(PurchaseSaveDTO dto, String specificIdBooks) {
        return Purchase
                .builder()
                .specificIdClient(dto.getSpecificIdClient())
                .specificIdBooks(specificIdBooks)
                .amountToPay(dto.getAmountToPay())
                .status(dto.getStatus())
                .specificID(dto.getSpecificID())
                .build();
    }
}

package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class PurchaseReturnDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String specificID;

    private ClientDTO client;

    private List<BookDTO> purchasedBooks;

    private double amountToPay;

    private Status status;

    public static PurchaseReturnDTO from(Purchase entity, ClientDTO client, List<BookDTO> purchasedBooks) {
        return PurchaseReturnDTO
                .builder()
                .id(entity.getId())
                .specificID(entity.getSpecificID())
                .client(client)
                .purchasedBooks(purchasedBooks)
                .amountToPay(entity.getAmountToPay())
                .status(entity.getStatus())
                .build();
    }
}

package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class PurchaseReturnDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private ClientDTO client;

    private Set<BookDTO> purchasedBooks;

    private double amountToPay;

    private Enum<Status> status;

    public static PurchaseReturnDTO from(Purchase entity, ClientDTO client, Set<BookDTO> purchasedBooks) {
        return PurchaseReturnDTO
                .builder()
                .id(entity.getId())
                .client(client)
                .purchasedBooks(purchasedBooks)
                .amountToPay(entity.getAmountToPay())
                .status(entity.getStatus())
                .build();
    }
}

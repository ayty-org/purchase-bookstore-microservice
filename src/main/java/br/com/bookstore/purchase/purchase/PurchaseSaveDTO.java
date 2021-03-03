package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseSaveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String specificIdClient;

    @NotNull
    private Set<String> specificIdBooks;

    @NotNull
    private double amountToPay;

    private Enum<Status> status;
}

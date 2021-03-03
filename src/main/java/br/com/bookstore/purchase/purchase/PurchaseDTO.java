package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private ClientDTO client;

    @NotNull
    private Set<BookDTO> purchasedBooks;

    @NotNull
    private double amountToPay;

    private Enum<Status> status;
}

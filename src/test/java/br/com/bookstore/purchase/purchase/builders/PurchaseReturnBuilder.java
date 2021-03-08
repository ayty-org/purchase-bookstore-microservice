package br.com.bookstore.purchase.purchase.builders;

import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.Status;

import java.util.ArrayList;
import java.util.List;

import static br.com.bookstore.purchase.purchase.builders.BookBuilder.createBook;
import static br.com.bookstore.purchase.purchase.builders.ClientBuilder.createClient;

public class PurchaseReturnBuilder {
    public static PurchaseReturnDTO.Builder createPurchaseReturn(){
        List<BookDTO> bookDTOList = new ArrayList<>();
        bookDTOList.add(createBook().build());
        bookDTOList.add(createBook().title("meow").specificID("69661bd1-6092-4068-bd28-c60517f8a16s").build());

        return PurchaseReturnDTO
                .builder()
                .id(1L)
                .specificID("69661bd1-6092-4068-bd28-c60517f8a16b")
                .amountToPay(100.0)
                .client(createClient().build())
                .purchasedBooks(bookDTOList)
                .status(Status.PENDING);
    }
}

package br.com.bookstore.purchase.purchase.builders;

import br.com.bookstore.purchase.purchase.PurchaseSaveDTO;
import br.com.bookstore.purchase.purchase.Status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseSaveBuilder {
    public static PurchaseSaveDTO.Builder createPurchaseSave(){
        List<String> books = new ArrayList<>();
        books.add("fe07d7bb-2cac-4c47-b9f0-19aa2df60949");
        return PurchaseSaveDTO
                .builder()
                .id(1L)
                .amountToPay(100.0)
                .specificID("5edc11dd-2017-4c20-9d89-cc96970435cb")
                .specificIdBooks(books)
                .specificIdClient("69661bd1-6092-4068-bd28-c60517f8a16b")
                .status(Status.PENDING);
    }
}

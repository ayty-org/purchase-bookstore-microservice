package br.com.bookstore.purchase.purchase.services.utils;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReturnSetBooksOfFeign {

    private final GetBook getBook;

    public List<BookDTO> findAllFeign(Purchase purchase) {
        String[] purchasedBookID = purchase.getSpecificIdBooks().split(",");
        List<BookDTO> purchasedBooksFound = new ArrayList<>();
        for(String book: purchasedBookID) {
            purchasedBooksFound.add(getBook.findSpecificID(book));
        }
        return purchasedBooksFound;
    }
}

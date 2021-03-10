package br.com.bookstore.purchase.purchase.services.utils;

import br.com.bookstore.purchase.feign.FeignGetBook;
import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReturnSetBooksOfFeign {

    private final FeignGetBook feignGetBook;

    public List<BookDTO> findAllFeign(Purchase purchase) {
        String[] purchasedBookID = purchase.getSpecificIdBooks().split(",");
        List<BookDTO> purchasedBooksFound = new ArrayList<>();
        for(String book: purchasedBookID) {
            purchasedBooksFound.add(feignGetBook.findSpecificID(book));
        }
        return purchasedBooksFound;
    }
}

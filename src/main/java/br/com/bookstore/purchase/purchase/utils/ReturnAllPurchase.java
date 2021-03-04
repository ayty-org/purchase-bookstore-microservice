package br.com.bookstore.purchase.purchase.utils;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.ClientDTO;
import br.com.bookstore.purchase.purchase.Purchase;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ReturnAllPurchase {

    private final PurchaseRepository purchaseRepository;

    public List<PurchaseReturnDTO> findAllPurchase(GetBook getBook, GetClient getClient ) {
        List<PurchaseReturnDTO> purchaseReturnDTOS = new ArrayList<>();
        for(Purchase purchase: purchaseRepository.findAll()){
            ClientDTO clientDTO = getClient.findSpecificID(purchase.getSpecificIdClient());
            String[] purchasedBookID = purchase.getSpecificIdBooks().split(",");
            Set<BookDTO> purchasedBooksFound = new HashSet<>();
            for(String book: purchasedBookID) {
                purchasedBooksFound.add(getBook.findSpecificID(book));
            }
            purchaseReturnDTOS.add(PurchaseReturnDTO.from(purchase, clientDTO, purchasedBooksFound));
        }
        return purchaseReturnDTOS;
    }
}

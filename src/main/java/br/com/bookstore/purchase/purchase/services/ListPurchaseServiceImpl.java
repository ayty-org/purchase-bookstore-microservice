package br.com.bookstore.purchase.purchase.services;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ListPurchaseServiceImpl implements ListPurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final GetBook getBook;
    private final GetClient getClient;
    private List<PurchaseReturnDTO> purchases;

    @Override
    public List<PurchaseReturnDTO> findAll() {
        purchases = new ArrayList<>();
        for(Purchase purchase: purchaseRepository.findAll()){
            ClientDTO clientDTO = getClient.findSpecificID(purchase.getSpecificIdClient());
            List<String> purchasedBookID = Arrays.asList(purchase.getSpecificIdBooks().split(","));
            Set<BookDTO> purchasedBooksFound = new HashSet<>();
            for(String book: purchasedBookID) {
                purchasedBooksFound.add(getBook.findSpecificID(book));
            }
            purchases.add(PurchaseReturnDTO.from(purchase, clientDTO, purchasedBooksFound));
        }
        return purchases;
    }
}

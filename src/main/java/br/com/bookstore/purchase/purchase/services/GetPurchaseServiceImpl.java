package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
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
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetPurchaseServiceImpl implements GetPurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final GetBook getBook;
    private final GetClient getClient;

    @Override
    public PurchaseReturnDTO findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(PurchaseNotFoundException::new);
        ClientDTO clientDTO = getClient.findSpecificID(purchase.getSpecificIdClient());
        String[] purchasedBookID = purchase.getSpecificIdBooks().split(",");
        List<BookDTO> purchasedBooksFound = new ArrayList<>();
        for(String book: purchasedBookID) {
            purchasedBooksFound.add(getBook.findSpecificID(book));
        }
        return PurchaseReturnDTO.from(purchase, clientDTO, purchasedBooksFound);
    }
}

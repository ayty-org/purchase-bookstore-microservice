package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.feign.FeignGetBook;
import br.com.bookstore.purchase.feign.FeignGetClient;
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
    private final FeignGetBook feignGetBook;
    private final FeignGetClient feignGetClient;

    @Override
    public PurchaseReturnDTO findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(PurchaseNotFoundException::new);
        ClientDTO clientDTO = feignGetClient.findSpecificID(purchase.getSpecificIdClient());
        String[] purchasedBookID = purchase.getSpecificIdBooks().split(",");
        List<BookDTO> purchasedBooksFound = new ArrayList<>();
        for(String book: purchasedBookID) {
            purchasedBooksFound.add(feignGetBook.findSpecificID(book));
        }
        return PurchaseReturnDTO.from(purchase, clientDTO, purchasedBooksFound);
    }
}

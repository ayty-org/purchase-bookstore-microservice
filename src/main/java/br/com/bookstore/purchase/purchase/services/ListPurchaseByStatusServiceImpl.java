package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.ClientDTO;
import br.com.bookstore.purchase.purchase.Purchase;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.Status;
import br.com.bookstore.purchase.purchase.utils.ReturnAllPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ListPurchaseByStatusServiceImpl implements ListPurchaseByStatusService {

    private final ReturnAllPurchase returnAllPurchase;
    private final GetBook getBook;
    private final GetClient getClient;
    private final PurchaseRepository purchaseRepository;

    @Override
    public List<PurchaseReturnDTO> findAllPurchaseByStatus(Status status) {
        List<PurchaseReturnDTO> purchaseReturnDTOList = new ArrayList<>();
        List<Purchase> purchaseList = purchaseRepository.findPurchaseByStatus(status);

        if(purchaseList.isEmpty()){
            throw new PurchaseNotFoundException();
        }

        for(Purchase purchase: purchaseList){
            ClientDTO clientDTO = getClient.findSpecificID(purchase.getSpecificIdClient());
            String[] purchasedBookID = purchase.getSpecificIdBooks().split(",");
            Set<BookDTO> purchasedBooksFound = new HashSet<>();
            for(String book: purchasedBookID) {
                purchasedBooksFound.add(getBook.findSpecificID(book));
            }
            purchaseReturnDTOList.add(PurchaseReturnDTO.from(purchase, clientDTO, purchasedBooksFound));
        }

        return purchaseReturnDTOList;
    }
}

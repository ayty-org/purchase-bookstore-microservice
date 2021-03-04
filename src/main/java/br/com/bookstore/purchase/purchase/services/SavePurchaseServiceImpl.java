package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.BookNotFoundException;
import br.com.bookstore.purchase.exceptions.ClientNotFoundException;
import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.Purchase;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import br.com.bookstore.purchase.purchase.PurchaseSaveDTO;
import br.com.bookstore.purchase.purchase.Status;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SavePurchaseServiceImpl implements SavePurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final GetBook getBook;
    private final GetClient getClient;

    @Override
    public void insert(PurchaseSaveDTO purchaseSaveDTO) {
        String booksID = "";
        try {
            getClient.findSpecificID(purchaseSaveDTO.getSpecificIdClient());
        } catch (FeignException.NotFound requisition){
            throw new ClientNotFoundException(requisition.getMessage());
        }

        try {
            for(String books: purchaseSaveDTO.getSpecificIdBooks()){
                getBook.findSpecificID(books);
                booksID += books;
                booksID += ",";
            }
        } catch (FeignException.NotFound requisition){
            throw new BookNotFoundException(requisition.getMessage());
        }

        purchaseSaveDTO.setStatus(Status.PENDING);

        Purchase purchase = Purchase.to(purchaseSaveDTO, booksID);

        purchaseRepository.save(purchase);
    }
}

package br.com.bookstore.purchase.purchase.services.utils;

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
public class ReturnAllPurchase {

    private final PurchaseRepository purchaseRepository;
    private final ReturnSetBooksOfFeign returnSetBooksOfFeign;
    private final FeignGetBook feignGetBook;
    private final FeignGetClient feignGetClient;

    public List<PurchaseReturnDTO> findAllPurchase() {
        List<PurchaseReturnDTO> purchaseReturnDTOS = new ArrayList<>();
        for(Purchase purchase: purchaseRepository.findAll()){
            ClientDTO clientDTO = feignGetClient.findSpecificID(purchase.getSpecificIdClient());
            List<BookDTO> bookDTOSet = returnSetBooksOfFeign.findAllFeign(purchase);
            purchaseReturnDTOS.add(PurchaseReturnDTO.from(purchase, clientDTO, bookDTOSet));
        }
        return purchaseReturnDTOS;
    }
}

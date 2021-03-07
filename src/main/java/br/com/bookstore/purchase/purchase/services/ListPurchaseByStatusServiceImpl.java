package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.ClientDTO;
import br.com.bookstore.purchase.purchase.Purchase;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.Status;
import br.com.bookstore.purchase.purchase.services.utils.ReturnSetBooksOfFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ListPurchaseByStatusServiceImpl implements ListPurchaseByStatusService {

    private final ReturnSetBooksOfFeign returnSetBooksOfFeign;
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
            List<BookDTO> bookDTOSet = returnSetBooksOfFeign.findAllFeign(purchase);
            purchaseReturnDTOList.add(PurchaseReturnDTO.from(purchase, clientDTO, bookDTOSet));
        }

        return purchaseReturnDTOList;
    }
}

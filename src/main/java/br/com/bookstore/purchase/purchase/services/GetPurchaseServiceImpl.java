package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.ClientDTO;
import br.com.bookstore.purchase.purchase.Purchase;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.services.utils.ReturnSetBooksOfFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class GetPurchaseServiceImpl implements GetPurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final ReturnSetBooksOfFeign returnSetBooksOfFeign;
    private final GetClient getClient;

    @Override
    public PurchaseReturnDTO findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(PurchaseNotFoundException::new);
        ClientDTO clientDTO = getClient.findSpecificID(purchase.getSpecificIdClient());
        Set<BookDTO> purchasedBooksFound = returnSetBooksOfFeign.findAllFeign(purchase);
        return PurchaseReturnDTO.from(purchase, clientDTO, purchasedBooksFound);
    }
}

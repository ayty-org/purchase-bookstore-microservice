package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.utils.ReturnAllPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetPurchaseServiceImpl implements GetPurchaseService{

    private final ReturnAllPurchase returnAllPurchase;
    private final GetBook getBook;
    private final GetClient getClient;

    @Override
    public PurchaseReturnDTO findById(Long id) {
        List<PurchaseReturnDTO> purchaseReturnDTOList = returnAllPurchase.findAllPurchase(getBook, getClient);
        return purchaseReturnDTOList.stream()
                .filter(objectID -> Objects.equals(objectID.getId(), id))
                .collect(Collectors.toList()).get(0);
    }
}

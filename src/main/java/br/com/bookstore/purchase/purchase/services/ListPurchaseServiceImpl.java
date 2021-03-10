package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.services.utils.ReturnAllPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListPurchaseServiceImpl implements ListPurchaseService{

    private final ReturnAllPurchase returnAllPurchase;

    @Override
    public List<PurchaseReturnDTO> findAll() {
        return returnAllPurchase.findAllPurchase();
    }
}


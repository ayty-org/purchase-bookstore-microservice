package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListPagePurchaseServiceImpl implements ListPagePurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final GetBook getBook;
    private final GetClient getClient;

    @Override
    public Page<PurchaseReturnDTO> findPurchase(Pageable pageable) {
        return null;
    }
}

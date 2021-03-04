package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface ListPagePurchaseService {
    Page<PurchaseReturnDTO> findPage(Pageable pageable);
}

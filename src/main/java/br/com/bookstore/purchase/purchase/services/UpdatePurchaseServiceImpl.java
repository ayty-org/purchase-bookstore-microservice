package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.purchase.Purchase;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import br.com.bookstore.purchase.purchase.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdatePurchaseServiceImpl implements UpdatePurchaseService{

    private final PurchaseRepository purchaseRepository;
    @Override
    public void update(Long id) {
        Purchase purchaseSaved = purchaseRepository.findById(id).orElseThrow(PurchaseNotFoundException::new);

        purchaseSaved.setStatus(Status.DONE);
        purchaseRepository.save(purchaseSaved);
    }
}

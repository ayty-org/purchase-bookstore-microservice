package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.utils.ReturnAllPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListPagePurchaseServiceImpl implements ListPagePurchaseService{

    private final ReturnAllPurchase returnAllPurchase;
    private final GetBook getBook;
    private final GetClient getClient;

    @Override
    public Page<PurchaseReturnDTO> findPage(Pageable pageable) {
        List<PurchaseReturnDTO> purchaseReturnDTOList = returnAllPurchase.findAllPurchase(getBook, getClient);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), purchaseReturnDTOList.size());
        return new PageImpl(purchaseReturnDTOList.subList(start, end), pageable, purchaseReturnDTOList.size());
    }
}

package br.com.bookstore.purchase.purchase.v1;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.PurchaseSaveDTO;
import br.com.bookstore.purchase.purchase.services.ListPagePurchaseService;
import br.com.bookstore.purchase.purchase.services.ListPurchaseService;
import br.com.bookstore.purchase.purchase.services.SavePurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/api/purchase")
public class PurchaseControllerV1 {

    private final SavePurchaseService savePurchaseService;
    private final ListPurchaseService listPurchaseService;
    private final ListPagePurchaseService listPagePurchaseService;

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseReturnDTO> findAll(){
        return listPurchaseService.findAll();
    }

    @GetMapping(value = {"/"})//list all purchase inside object page
    @ResponseStatus(HttpStatus.OK)
    public Page<PurchaseReturnDTO> findPage(Pageable pageable){
        return listPagePurchaseService.findPage(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody PurchaseSaveDTO purchaseSaveDTO){
        savePurchaseService.insert(purchaseSaveDTO);
    }
}

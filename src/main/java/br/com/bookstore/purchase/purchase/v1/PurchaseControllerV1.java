package br.com.bookstore.purchase.purchase.v1;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.PurchaseSaveDTO;
import br.com.bookstore.purchase.purchase.Status;
import br.com.bookstore.purchase.purchase.services.DeletePurchaseService;
import br.com.bookstore.purchase.purchase.services.GetPurchaseService;
import br.com.bookstore.purchase.purchase.services.ListPagePurchaseService;
import br.com.bookstore.purchase.purchase.services.ListPurchaseByStatusService;
import br.com.bookstore.purchase.purchase.services.ListPurchaseService;
import br.com.bookstore.purchase.purchase.services.SavePurchaseService;
import br.com.bookstore.purchase.purchase.services.UpdatePurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final GetPurchaseService getPurchaseService;
    private final ListPurchaseByStatusService listPurchaseByStatusService;
    private final DeletePurchaseService deletePurchaseService;
    private final UpdatePurchaseService updatePurchaseService;

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseReturnDTO> findAll(){
        return listPurchaseService.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseReturnDTO findByID(@PathVariable Long id){
        return getPurchaseService.findById(id);
    }

    @GetMapping//list all purchase inside object page
    @ResponseStatus(HttpStatus.OK)
    public Page<PurchaseReturnDTO> findPage(Pageable pageable){
        return listPagePurchaseService.findPage(pageable);
    }

    @GetMapping(path = "/status/{status}") //list purchase by status
    public List<PurchaseReturnDTO> findAllPurchaseByStatus(@PathVariable Status status){
        return listPurchaseByStatusService.findAllPurchaseByStatus(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody PurchaseSaveDTO purchaseSaveDTO){
        savePurchaseService.insert(purchaseSaveDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) { deletePurchaseService.delete(id);}

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/done/{id}")
    public void update(@PathVariable Long id) {
        updatePurchaseService.update(id);
    }
}

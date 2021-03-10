package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.purchase.services.DeletePurchaseService;
import br.com.bookstore.purchase.purchase.services.GetPurchaseService;
import br.com.bookstore.purchase.purchase.services.ListPagePurchaseService;
import br.com.bookstore.purchase.purchase.services.ListPurchaseByStatusService;
import br.com.bookstore.purchase.purchase.services.ListPurchaseService;
import br.com.bookstore.purchase.purchase.services.SavePurchaseService;
import br.com.bookstore.purchase.purchase.services.UpdatePurchaseService;
import br.com.bookstore.purchase.purchase.v1.PurchaseControllerV1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static br.com.bookstore.purchase.purchase.builders.PurchaseReturnBuilder.createPurchaseReturn;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PurchaseControllerV1.class)
@DisplayName("Verify all the endpoints utilizing the implemented services")
class PurchaseControllerV1Test {

    private final String URL_PURCHASE = "/v1/api/purchase";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeletePurchaseService deletePurchaseService;

    @MockBean
    private GetPurchaseService getPurchaseService;

    @MockBean
    private ListPurchaseService listPurchaseService;

    @MockBean
    private ListPurchaseByStatusService listPurchaseByStatusService;

    @MockBean
    private ListPagePurchaseService listPagePurchaseService;

    @MockBean
    private SavePurchaseService savePurchaseService;

    @MockBean
    private UpdatePurchaseService updatePurchaseService;

    @Test
    @DisplayName("findById return purchase when succesful")
    void findByIdReturnPurchaseWhenSuccessful() throws Exception {
        when(getPurchaseService.findById(anyLong())).thenReturn(createPurchaseReturn().build());

        mockMvc.perform(get(URL_PURCHASE + "/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.client.specificID", is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")))
                .andExpect(jsonPath("$.purchasedBooks.[0].specificID", is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")))
                .andExpect(jsonPath("$.amountToPay", is(100.00)))
                .andExpect(jsonPath("$.status", is("PENDING")));

        verify(getPurchaseService).findById(anyLong());
    }

    @Test
    @DisplayName("findById throws PurchaseNotFoundException when purchase is not found")
    void findByIdPurchaseThrowPurchaseNotFoundExceptionWhenPurchaseNotFound() throws Exception {

        when(getPurchaseService.findById(anyLong())).thenThrow(new PurchaseNotFoundException());

        mockMvc.perform(get(URL_PURCHASE + "/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getPurchaseService).findById(1L);
    }

    @Test
    @DisplayName("listAll returns list of purchase when successful")
    void listAllReturnsListOfPurchaseWhenSuccessfull() throws Exception {

        when(listPurchaseService.findAll()).thenReturn(Lists.newArrayList(
                createPurchaseReturn().id(1L).build(),
                createPurchaseReturn().id(2L).build()
        ));

        mockMvc.perform(get(URL_PURCHASE+ "/all").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].client.specificID", is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")))
                .andExpect(jsonPath("$[0].purchasedBooks.[0].specificID", is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")))
                .andExpect(jsonPath("$[0].amountToPay", is(100.00)))
                .andExpect(jsonPath("$[0].status", is("PENDING")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].client.specificID", is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")))
                .andExpect(jsonPath("$[1].purchasedBooks.[0].specificID", is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")))
                .andExpect(jsonPath("$[1].amountToPay", is(100.00)))
                .andExpect(jsonPath("$[1].status", is("PENDING")));

        verify(listPurchaseService).findAll();
    }

    @Test
    @DisplayName("listAll returns list of purchase inside page object when successful")
    void listAllReturnsListOfPurchaseInsidePageObject_WhenSuccessful() throws Exception{

        Page<PurchaseReturnDTO> purchasePage = new PageImpl<>(Collections.singletonList(createPurchaseReturn().build()));

        Pageable pageable = PageRequest.of(0,2);

        when(listPagePurchaseService.findPage(pageable)).thenReturn(purchasePage);

        mockMvc.perform(get(URL_PURCHASE + "/?page=0&size=2").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].client.specificID", is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")))
                .andExpect(jsonPath("$.content[0].purchasedBooks.[0].specificID", is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")))
                .andExpect(jsonPath("$.content[0].amountToPay", is(100.00)))
                .andExpect(jsonPath("$.content[0].status", is("PENDING")));

        verify(listPagePurchaseService).findPage(pageable);
    }

    @Test
    @DisplayName("listAll returns list of purchase by status when successful")
    void listAllByStatusReturnsListOfPurchaseWhenSuccessful() throws Exception {

        PurchaseReturnDTO purchase = createPurchaseReturn().build();

        Status statusPurchase = Status.PENDING;

        when(listPurchaseByStatusService.findAllPurchaseByStatus(statusPurchase)).thenReturn(Collections.singletonList(purchase));

        mockMvc.perform(get(URL_PURCHASE + "/status/{status}", statusPurchase).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].client.specificID", is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")))
                .andExpect(jsonPath("$[0].purchasedBooks.[0].specificID", is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")))
                .andExpect(jsonPath("$[0].amountToPay", is(100.00)))
                .andExpect(jsonPath("$[0].status", is("PENDING")));

        verify(listPurchaseByStatusService).findAllPurchaseByStatus(statusPurchase);
    }

    @Test
    @DisplayName("save returns purchase when successful")
    void saveReturnsPurchaseWhenSuccessful() throws Exception{
        mockMvc.perform(post(URL_PURCHASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("purchaseSaveDTO.json")))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(savePurchaseService).insert(any(PurchaseSaveDTO.class));
    }

    @Test
    @DisplayName("update purchase when successful")
    void updateReturnsPurchaseUpdateWhenSuccessful() throws Exception{
        mockMvc.perform(put(URL_PURCHASE + "/done/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updatePurchaseService).update(eq(1L));
    }

    @Test
    @DisplayName("delete remove purchase when successful")
    void deleteRemovePurchaseWhenSuccessful() throws Exception{
        mockMvc.perform(delete(URL_PURCHASE + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deletePurchaseService).delete(anyLong());
    }

    public static String readJson(String file) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/dataJson/" + file).toAbsolutePath());
        return new String(bytes);
    }
}
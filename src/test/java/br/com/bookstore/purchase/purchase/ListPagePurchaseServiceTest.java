package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.services.ListPagePurchaseServiceImpl;
import br.com.bookstore.purchase.purchase.services.utils.ReturnAllPurchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static br.com.bookstore.purchase.purchase.builders.BookBuilder.createBook;
import static br.com.bookstore.purchase.purchase.builders.ClientBuilder.createClient;
import static br.com.bookstore.purchase.purchase.builders.PurchaseReturnBuilder.createPurchaseReturn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for pagination of all purchase")
class ListPagePurchaseServiceTest {

    @Mock
    private ReturnAllPurchase returnAllPurchase;

    @Mock
    private GetBook getBook;

    @Mock
    private GetClient getClient;

    private ListPagePurchaseServiceImpl listPagePurchaseService;

    @BeforeEach
    void setUp() {
        this.listPagePurchaseService = new ListPagePurchaseServiceImpl(returnAllPurchase, getBook, getClient);
    }

    @Test
    @DisplayName("listAll returns list of purchase inside page object when successful")
    void listAllReturnsListOfPurchaseInsidePageObjectWhenSuccessful() {

        Pageable pageable = PageRequest.of(0,2);

        when(returnAllPurchase.findAllPurchase(getBook, getClient)).thenReturn(Stream.of(
                createPurchaseReturn().specificID("69661bd1-6092-4068-bd28-c60517f8a16a").build(),
                createPurchaseReturn().specificID("69661bd1-6092-4068-bd28-c60517f8a16b").build()).collect(Collectors.toList())
        );

        lenient().when(getClient.findSpecificID(anyString())).thenReturn(createClient().build());

        Page<PurchaseReturnDTO> result = this.listPagePurchaseService.findPage(pageable);

        assertAll("Purchase",
                ()-> assertThat(result.getNumber(), is(0)),
                ()-> assertThat(result.getSize(), is(2)),
                ()-> assertThat(result.getContent().get(0).getSpecificID(), is("69661bd1-6092-4068-bd28-c60517f8a16a")),
                ()-> assertThat(result.getContent().get(0).getClient().getSpecificID(), is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")),
                ()-> assertThat(result.getContent().get(0).getPurchasedBooks().get(0).getSpecificID(), is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")),
                ()-> assertThat(result.getContent().get(0).getStatus(), is(Status.PENDING))
        );

        verify(returnAllPurchase).findAllPurchase(getBook,getClient);
    }
}
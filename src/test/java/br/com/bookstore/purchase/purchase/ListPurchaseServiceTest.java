package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.services.ListPurchaseServiceImpl;
import br.com.bookstore.purchase.purchase.services.utils.ReturnAllPurchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.bookstore.purchase.purchase.builders.BookBuilder.createBook;
import static br.com.bookstore.purchase.purchase.builders.ClientBuilder.createClient;
import static br.com.bookstore.purchase.purchase.builders.PurchaseReturnBuilder.createPurchaseReturn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for list all purchase")
class ListPurchaseServiceTest {

    @Mock
    private ReturnAllPurchase returnAllPurchase;

    @Mock
    private GetBook getBook;

    @Mock
    private GetClient getClient;

    private ListPurchaseServiceImpl listPurchaseService;

    @BeforeEach
    void setUp() {
        this.listPurchaseService = new ListPurchaseServiceImpl(returnAllPurchase, getBook, getClient);
    }

    @Test
    @DisplayName("listAll returns list of purchase when successful")
    void listAllReturnsListOfPurchaseWhenSuccessful() {
        when(returnAllPurchase.findAllPurchase(getBook, getClient)).thenReturn(Stream.of(createPurchaseReturn().build()).collect(Collectors.toList()));

        List<PurchaseReturnDTO> result = this.listPurchaseService.findAll();

        assertAll( "Purchase",
                ()-> assertThat(result.size(), is(1)),
                ()-> assertThat(result.get(0).getClient().getSpecificID(), is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")),
                ()-> assertThat(result.get(0).getPurchasedBooks().get(0).getSpecificID(), is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")),
                ()-> assertThat(result.get(0).getAmountToPay(), is(100.00)),
                ()-> assertThat(result.get(0).getStatus(), is(Status.PENDING))
        );

        verify(returnAllPurchase, times(1)).findAllPurchase(getBook, getClient);
    }
}
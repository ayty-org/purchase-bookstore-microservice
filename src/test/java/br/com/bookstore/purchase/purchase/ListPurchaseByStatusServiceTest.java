package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.feign.FeignGetBook;
import br.com.bookstore.purchase.feign.FeignGetClient;
import br.com.bookstore.purchase.purchase.services.ListPurchaseByStatusServiceImpl;
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
import static br.com.bookstore.purchase.purchase.builders.PurchaseBuilder.createPurchase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for list all purchase by status")
class ListPurchaseByStatusServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private FeignGetBook feignGetBook;

    @Mock
    private FeignGetClient feignGetClient;

    private ListPurchaseByStatusServiceImpl listPurchaseByStatusService;

    @BeforeEach
    void setUp() {
        this.listPurchaseByStatusService = new ListPurchaseByStatusServiceImpl(feignGetBook, feignGetClient, purchaseRepository);
    }

    @Test
    @DisplayName("listAll returns list of purchase by status when successful")
    void listAllReturnsListOfPurchaseWhenSuccessful() {

        when(purchaseRepository.findPurchaseByStatus(any())).thenReturn(Stream.of(createPurchase().build()).collect(Collectors.toList()));

        when(feignGetClient.findSpecificID(anyString())).thenReturn(createClient().build());

        BookDTO bookDTO = createBook().build();

        when(feignGetBook.findSpecificID(anyString())).thenReturn(bookDTO);

        List<PurchaseReturnDTO> result = this.listPurchaseByStatusService.findAllPurchaseByStatus(Status.PENDING);

        assertAll( "Purchase",
                ()-> assertThat(result.get(0).getClient().getSpecificID(), is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")),
                ()-> assertThat(result.get(0).getPurchasedBooks().get(0).getSpecificID(), is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")),
                ()-> assertThat(result.get(0).getAmountToPay(), is(200.00)),
                ()-> assertThat(result.get(0).getStatus(), is(Status.PENDING))
        );

        verify(purchaseRepository).findPurchaseByStatus(any());
    }
}
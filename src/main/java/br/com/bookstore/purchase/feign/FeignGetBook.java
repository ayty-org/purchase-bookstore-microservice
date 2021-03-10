package br.com.bookstore.purchase.feign;

import br.com.bookstore.purchase.purchase.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "GetBook", url = "http://localhost:8080/v1/api/book", name = "book-service")
public interface FeignGetBook {

    @GetMapping(value = "/id/{specificID}") //list books by specificId
    BookDTO findSpecificID(@PathVariable String specificID);
}

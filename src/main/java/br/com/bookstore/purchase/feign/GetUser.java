package br.com.bookstore.purchase.feign;

import br.com.bookstore.purchase.purchase.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "GetUser", url = "http://localhost:8080/v1/api/client", name = "user-service")
public interface GetUser {

    @GetMapping(value = "/id/{specificID}") //list clients by specificId
    ClientDTO findSpecificID(@PathVariable String specificID);
}
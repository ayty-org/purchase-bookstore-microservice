package br.com.bookstore.purchase.feign;

import br.com.bookstore.purchase.purchase.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId = "GetClient", url = "http://localhost:9092/v1/api/client", name = "client-service")
public interface GetClient {

    @GetMapping(value = "/id/{specificID}") //list clients by specificId
    ClientDTO findSpecificID(@PathVariable String specificID);
}
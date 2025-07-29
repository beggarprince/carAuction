package aaa.pfa.carAuctionBackend.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RestController
public class QuoteController {

    @GetMapping("/api/getQuote")
    public String getQuote(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://zenquotes.io/api/today",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference <List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> quoteResponse = response.getBody();
        if(quoteResponse != null && !quoteResponse.isEmpty()){
            Map<String, Object> quote = quoteResponse.getFirst();
            String text = (String) quote.get("q");  // q = quote
            String author = (String) quote.get("a"); // a= author
            return text + " - " + author;
        }
        return "Quote return failed - Spring Boot Himself";
    }
}

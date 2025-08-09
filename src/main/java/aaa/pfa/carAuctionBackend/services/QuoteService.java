package aaa.pfa.carAuctionBackend.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class QuoteService {

    private final RestTemplate restTemplate;

    public QuoteService() {
        this.restTemplate = new RestTemplate();
    }

    public String getDailyQuote(){
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                "https://zenquotes.io/api/today",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> quoteResponse = response.getBody();
        if (quoteResponse != null && !quoteResponse.isEmpty()) {
            Map<String, Object> quote = quoteResponse.getFirst();
            String text = (String) quote.get("q");  // q = quote
            String author = (String) quote.get("a"); // a= author
            return text + " - " + author;
        }
        return "Quote return failed - Spring Boot Himself";
    }
}

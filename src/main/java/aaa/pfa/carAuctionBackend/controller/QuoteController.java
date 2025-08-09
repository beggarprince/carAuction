package aaa.pfa.carAuctionBackend.controller;

import aaa.pfa.carAuctionBackend.services.QuoteService;
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

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/api/getQuote")
    public String getQuote() {
        return quoteService.getDailyQuote();
    }
}

package az.edu.turing.bankingapplication.controller;

import az.edu.turing.bankingapplication.service.CurrencyRateFetcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyRateFetcher currencyRateFetcher;

    public CurrencyController(CurrencyRateFetcher currencyRateFetcher) {
        this.currencyRateFetcher = currencyRateFetcher;
    }

    @GetMapping("/rates")
    public String getCurrencyRates() throws JsonProcessingException {
        String ratesJson = currencyRateFetcher.fetchRates();

        // Konsola yazdırın
        System.out.println("Currency rates: " + ratesJson);

        return ratesJson;
    }
}

package az.edu.turing.bankingapplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class CurrencyRateFetcher {

    @Value("${currency.api.url}")
    private String url;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private ObjectNode cachedRates;
    private LocalDateTime lastFetched;

    private static final long CACHE_DURATION_MINUTES = 1; // Keşin yenilənmə müddəti

    public CurrencyRateFetcher(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        // İlk dəfə başlamazdan əvvəl məlumatları əldə edin
        refreshRates();
    }

    public String fetchRates() throws JsonProcessingException {
        // Keşin yenilənmə vaxtını yoxlayın
        if (cachedRates == null || isCacheExpired()) {
            refreshRates();
        }
        // JSON obyektini pretty print ilə qaytarın
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cachedRates);
    }

    private boolean isCacheExpired() {
        return lastFetched == null || ChronoUnit.MINUTES.between(lastFetched, LocalDateTime.now()) >= CACHE_DURATION_MINUTES;
    }

    private void refreshRates() {
        try {
            // JSON məzmununu əldə edin
            String jsonResponse = restTemplate.getForObject(url, String.class);

            if (jsonResponse != null) {
                // JSON məzmununu parse edin
                JsonNode root = objectMapper.readTree(jsonResponse);
                JsonNode rates = root.path("rates");

                // İstədiyiniz valyuta məzənnələrini çıxarın
                double usdRate = rates.path("USD").asDouble();
                double eurRate = rates.path("EUR").asDouble();
                double aznRate = rates.path("AZN").asDouble();

                // 1 manatın USD və EUR-a görə dəyəri
                double aznToUsd = aznRate / usdRate;
                double aznToEur = aznRate / eurRate;

                // JSON obyektini yaradın
                cachedRates = objectMapper.createObjectNode();
                cachedRates.put("AZN", 1);
                cachedRates.put("USD", String.format("%.3f", aznToUsd));
                cachedRates.put("EUR", String.format("%.3f", aznToEur));

                // Keşin yenilənmə vaxtını qeyd edin
                lastFetched = LocalDateTime.now();
            }
        } catch (Exception e) {
            e.printStackTrace();
            cachedRates = objectMapper.createObjectNode();
            cachedRates.put("error", "JSON çevrilməsi xətası");
        }
    }
}

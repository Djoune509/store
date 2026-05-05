/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.djoune.store.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author StHilaireDjoune
 */
@Service
public class MonCashService {
    @Value("${moncash.client.id}")
    private String clientId;

    @Value("${moncash.client.secret}")
    private String clientSecret;

    @Value("${moncash.mode}")
    private String mode;

    // Metòd sa a fè tout travay la san li pa bezwen SDK a
    public String genererLienPaiement(Long produitId, Double montant) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String baseUrl = mode.equalsIgnoreCase("live") 
                ? "https://moncashbutton.com/Api/v1" 
                : "https://sandbox.moncash.com/Api/v1";

            // 1. Mande Token an
            String authUrl = baseUrl + "/CreateToken";
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(clientId, clientSecret);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(authUrl, HttpMethod.GET, request, Map.class);

            String token = (String) response.getBody().get("access_token");

            // 2. Kreye Peman an
            String paymentUrl = baseUrl + "/CreatePayment";
            Map<String, Object> body = new HashMap<>();
            body.put("amount", montant);
            body.put("orderId", "DJ-" + produitId + "-" + System.currentTimeMillis());

            HttpHeaders paymentHeaders = new HttpHeaders();
            paymentHeaders.setBearerAuth(token);
            paymentHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> paymentRequest = new HttpEntity<>(body, paymentHeaders);
            ResponseEntity<Map> paymentResponse = restTemplate.postForEntity(paymentUrl, paymentRequest, Map.class);

            // 3. Rekipere URL pou redirection a
            Map paymentMap = (Map) paymentResponse.getBody().get("payment_token");
            String paymentToken = (String) paymentMap.get("token");

            return (mode.equalsIgnoreCase("live") ? "https://moncashbutton.com" : "https://sandbox.moncash.com") 
                    + "/payment/PaymentFromToken?token=" + paymentToken;

        } catch (Exception e) {
            System.err.println("Erè nan pwosesis MonCash: " + e.getMessage());
            return null;
        }
    }
}
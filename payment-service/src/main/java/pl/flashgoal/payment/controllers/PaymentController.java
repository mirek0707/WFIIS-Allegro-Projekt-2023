package pl.flashgoal.payment.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.*;
import java.net.URL;
import java.net.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.io.IOException;


import pl.flashgoal.payment.enums.*;
import pl.flashgoal.payment.payload.request.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private Map<String, PaymentStatus> paymentStatusMap = new ConcurrentHashMap<>();

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest, @RequestHeader("Authorization") String userToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", userToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> userResponse = restTemplate.exchange("http://user-service:8084/api/test/user", HttpMethod.GET, entity, String.class);

            System.out.println(userResponse.getStatusCode());

            if(userResponse.getStatusCode() == HttpStatus.OK) {
                String paymentId = generatePaymentId();
                paymentStatusMap.put(paymentId, PaymentStatus.PENDING);
                return ResponseEntity.ok(paymentId);
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
            }
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
        }
    }

    @PostMapping("/confirm/{paymentId}")
    public ResponseEntity<String> confirmPayment(@PathVariable String paymentId,
                                                 @RequestHeader("Authorization") String userToken,
                                                 @RequestBody String username) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", userToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> userResponse = restTemplate.exchange("http://user-service:8084/api/test/user", HttpMethod.GET, entity, String.class);

            if(userResponse.getStatusCode() == HttpStatus.OK) {
                if (paymentStatusMap.containsKey(paymentId)) {
                    paymentStatusMap.put(paymentId, PaymentStatus.CONFIRMED);

                    JSONObject jsonObject = new JSONObject(username);
                    String usernameString = jsonObject.getString("username");

                    String url = "http://user-service:8084/user/givePremium/"+usernameString;

                    ResponseEntity<String> givePremiumResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

                    if(givePremiumResponse.getStatusCode() == HttpStatus.OK) {
                        return ResponseEntity.ok("Payment confirmed successfully");
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error granting premium");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
                }
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user token");
            }
        } catch (HttpClientErrorException e) {
            if(e.getMessage().contains("400")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            if(e.getMessage().contains("404")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    private String generatePaymentId() {
        return UUID.randomUUID().toString();
    }
}
package pl.flashgoal.predictions.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.lang.RuntimeException;
import org.springframework.web.server.ResponseStatusException;


import java.time.Duration;

@RestController
@RequestMapping("/predictions")
public class PredictionsController {

    private final WebClient webClient;
    private final String externalApiUrl = "https://api.sportmonks.com/v3/football/odds/pre-match?api_token="+System.getenv("PREDICTIONS_API_TOKEN");

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpStatusCode checkCredentials(String userToken, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", userToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> userResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return userResponse.getStatusCode();
    }
    public PredictionsController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/stream")
    public Flux<ServerSentEvent<String>> streamData(@RequestHeader("Authorization") String userToken) {
        return Flux.defer(() -> {
            try {
                HttpStatusCode status = checkCredentials(userToken, "http://user-service:8084/api/test/premiumOrAdmin");
                if (status == HttpStatus.OK) {
                    return Flux.interval(Duration.ofSeconds(30))
                            .flatMap(interval -> fetchData())
                            .map(data -> ServerSentEvent.builder(data).build());
                }
                return Flux.error(new ResponseStatusException(status, "Unauthorized"));
            } catch (HttpClientErrorException e) {
                return Flux.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user token"));
            }
        });
    }

    private Flux<String> fetchData() {
        return webClient.get()
                .uri(externalApiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .flux();
    }
}
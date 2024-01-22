package pl.flashgoal.livescore.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/live")
public class LivescoreController {

    private final WebClient webClient;
    private final String externalApiUrl = "https://api.football-data.org/v4/matches";

    public LivescoreController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/stream")
    public Flux<ServerSentEvent<String>> streamData() {
        return Flux.interval(Duration.ofSeconds(10))
                .flatMap(interval -> fetchData())
                .map(data -> ServerSentEvent.builder(data).build());
    }

    private Flux<String> fetchData() {
        return webClient.get()
                .uri(externalApiUrl)
                .header("X-Auth-Token", System.getenv("LIVESCORE_API_TOKEN"))
                .retrieve()
                .bodyToMono(String.class)
                .flux();
    }
}
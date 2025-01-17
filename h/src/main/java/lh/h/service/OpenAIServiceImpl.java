package lh.h.service;

import lh.h.interfaces.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.List;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final WebClient webClient;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    public OpenAIServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<String> getChatResponse(String message) {
        return webClient.post()
                .uri(apiUrl) // URL이 설정되었는지 확인
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(buildRequestBody(message))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to call OpenAI API", e)));
    }

    private Map<String, Object> buildRequestBody(String message) {
        return Map.of(
                "model", "gpt-3.5-turbo", // 모델 이름 확인
                "messages", List.of(Map.of("role", "user", "content", message))
        );
    }
}

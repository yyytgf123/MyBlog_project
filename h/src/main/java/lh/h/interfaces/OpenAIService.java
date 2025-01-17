package lh.h.interfaces;

import reactor.core.publisher.Mono;

public interface OpenAIService {
    Mono<String> getChatResponse(String message);
}

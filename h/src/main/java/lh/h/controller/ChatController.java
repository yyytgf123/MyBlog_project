package lh.h.controller;

import lh.h.interfaces.OpenAIService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
@RequestMapping("/ai")
public class ChatController {

    private final OpenAIService openAIService;

    public ChatController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    /**
     * Serves the OpenAI chat page.
     *
     * @return The name of the HTML template to render.
     */
    @GetMapping("/openai")
    public String openaiPage() {
        return "ai/openai"; // `templates/ai/openai.html` 반환
    }

    /**
     * Handles chat requests sent by the user.
     *
     * @param request The user's message.
     * @return The response from the OpenAI service.
     */
    @PostMapping("/chat")
    @ResponseBody
    public Mono<String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");
        return openAIService.getChatResponse(userMessage);
    }
}

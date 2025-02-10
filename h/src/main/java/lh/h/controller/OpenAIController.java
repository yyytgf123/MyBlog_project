package lh.h.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/ai")
public class OpenAIController {

    /* -- application.properties -- */
    @Value("${openai.api.key}")
    private String openaiApiKey;

    @GetMapping("/openAIPage")
    public String openAiPage(Model model) {
        model.addAttribute("apiKey", openaiApiKey);
        return "ai/openAIPage";
    }
}

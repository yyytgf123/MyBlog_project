package lh.h.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/ai")
public class OpenAIController {

    @GetMapping("/openAIPage")
    public String openaitest() {
        return "ai/openAIPage";
    }
}

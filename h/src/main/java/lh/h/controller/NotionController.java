package lh.h.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notion")
public class NotionController {

    @RequestMapping("/notionpage")
    public String notionpage() {
        return "notion/notionpage";
    }
}

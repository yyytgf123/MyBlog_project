package lh.h.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

    @RequestMapping("/aboutpage")
    public String aboutpage() {
        return "about/aboutpage";
    }
}

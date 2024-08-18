package hello.typeconverter.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class FormatterController {

    @GetMapping("formatter/edit")
    public String formatterForm(Model model) {
        model.addAttribute("form", new Form(1000, LocalDateTime.now()));
        return "formatter-form";
    }

    @PostMapping("formatter/edit")
    public String formatterEdit(@ModelAttribute Form form) { // -> 자동으로 모델에 추가
        return "formatter-view";
    }

    @Data
    @AllArgsConstructor
    static class Form {
        @NumberFormat(pattern = "###,###")
        private Integer number;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime localDateTime;
    }
}

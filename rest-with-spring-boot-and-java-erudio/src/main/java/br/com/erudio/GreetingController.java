package br.com.erudio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;


@RestController // Combinação das anotations = @Controller e @ResponseBody, adcionadas na versão 4.0 do Spring.
public class GreetingController {

    private static final String template = "Hello, %s!";
    // Responsável por mocar um id:
    private  final AtomicLong counter = new AtomicLong();

    // Método responsável por devolver um endpoint Greeting:
    @RequestMapping("/greeting")
    public Greeting greeting (@RequestParam(value = "name", defaultValue = "World")
    String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}

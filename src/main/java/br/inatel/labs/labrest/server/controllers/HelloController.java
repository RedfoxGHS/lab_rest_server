package br.inatel.labs.labrest.server.controllers;


import br.inatel.labs.labrest.server.entities.MyMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public MyMessage getHello() {
        MyMessage message = new MyMessage();
        message.setInfo("Hello World");
        return message;
    }
}

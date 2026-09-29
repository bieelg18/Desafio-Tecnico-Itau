package dev.bieelg18.DesafioItau.controllers;

import dev.bieelg18.DesafioItau.objects.Transacao;
import dev.bieelg18.DesafioItau.services.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping
public class TransacaoController {

    private final TransacaoService service;

    //Rota para criar uma transação
    @PostMapping("/transacao")
    public void criarTransacao(@RequestBody Transacao transacao){
        service.criarTransacao(transacao);
    }

}

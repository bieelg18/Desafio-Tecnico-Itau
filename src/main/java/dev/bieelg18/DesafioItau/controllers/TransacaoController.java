package dev.bieelg18.DesafioItau.controllers;

import dev.bieelg18.DesafioItau.dto.EstatisticaDTO;
import dev.bieelg18.DesafioItau.objects.Transacao;
import dev.bieelg18.DesafioItau.services.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping
public class TransacaoController {

    private final TransacaoService service;

    //Rota para criar uma transação
    @PostMapping("/transacao")
    public ResponseEntity<Void> criarTransacao(@RequestBody Transacao transacao){
        service.criarTransacao(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Rota para deletar todas as transacoes
    @DeleteMapping("/transacao")
    public void deletarTransacao(){
        service.deletarTransacoes();
    }

    //Rota para trazer as estatisticas das transações
    @GetMapping("/transacao")
    public EstatisticaDTO estatisticas(){
        return service.estatisticas();
    }

    //Rota para adicionar varias transações ao mesmo tempo
    @PostMapping("/lote")
    public ResponseEntity<Void> criarEmLote(@RequestBody List<Transacao> transacoes){
        transacoes.forEach(service::criarTransacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

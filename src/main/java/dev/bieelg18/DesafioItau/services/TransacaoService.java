package dev.bieelg18.DesafioItau.services;

import dev.bieelg18.DesafioItau.dto.EstatisticaDTO;
import dev.bieelg18.DesafioItau.exceptions.DataInvalidaException;
import dev.bieelg18.DesafioItau.exceptions.ValorInvalidoException;
import dev.bieelg18.DesafioItau.objects.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    private final List<Transacao> transacoes = new ArrayList<>();

    //Método para criar uma nova transação
    public void criarTransacao(Transacao transacao){

        if (transacao.getDataHora() == null || transacao.getDataHora().isAfter(OffsetDateTime.now())){
            throw new DataInvalidaException(
                    "A data da transação é inválida"
            );
        }

        if (transacao.getValor() == null || transacao.getValor().compareTo(BigDecimal.ZERO) < 0){
            throw new ValorInvalidoException(
                    "O valor da transação é inválido"
            );
        }

        transacoes.add(transacao);

    }

    //Método para deletar todas as transações
    public void deletarTransacoes(){

        transacoes.clear();

    }

    //Método para retornar as estatisticas das transações que ocorreram nos últimos 60 segundos
    public EstatisticaDTO estatisticas(){

        OffsetDateTime limite = OffsetDateTime.now().minusSeconds(60);

        List<Transacao> ultimasTransacoes = transacoes.stream()
                .filter(transacao -> !transacao.getDataHora().isBefore(limite))
                .toList();

        Long contagemTransacoes = ultimasTransacoes.stream().count();
        BigDecimal somaTransacoes = ultimasTransacoes.stream().map(Transacao::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal mediaTransacoes = contagemTransacoes == 0 ? BigDecimal.ZERO : somaTransacoes.divide(BigDecimal.valueOf(contagemTransacoes), 2, RoundingMode.HALF_UP);

        BigDecimal maiorValor = ultimasTransacoes.stream().map(Transacao::getValor).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal menorValor = ultimasTransacoes.stream().map(Transacao::getValor).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);


        return new EstatisticaDTO(
                contagemTransacoes,
                somaTransacoes,
                mediaTransacoes,
                menorValor,
                maiorValor
        );

    }

}

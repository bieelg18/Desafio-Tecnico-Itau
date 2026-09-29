package dev.bieelg18.DesafioItau.services;

import dev.bieelg18.DesafioItau.dto.EstatisticaDTO;
import dev.bieelg18.DesafioItau.exceptions.DataInvalidaException;
import dev.bieelg18.DesafioItau.exceptions.ValorInvalidoException;
import dev.bieelg18.DesafioItau.objects.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransacaoServiceTest {

    private Transacao transacao;
    private TransacaoService service;

    @BeforeEach
    public void setUp(){

        service = new TransacaoService();

        transacao = new Transacao();
        transacao.setValor(new BigDecimal("150.00"));
        transacao.setDataHora(OffsetDateTime.now().minusSeconds(10));

    }

    //Testes para criar uma nova transação
    @Test
    public void deveCriarTransacao(){

        assertDoesNotThrow(() -> service.criarTransacao(transacao));

        assertEquals(1, service.estatisticas().count());

    }

    //Não deve criar transação com data e hora posterior a data e hora atual
    @Test
    public void naoDeveCriarTransacaoComDataFutura(){

        transacao.setDataHora(OffsetDateTime.now().plusMinutes(10));

        DataInvalidaException exception = assertThrows(
                DataInvalidaException.class,
                () -> service.criarTransacao(transacao)
        );

        assertEquals(
                "A data da transação é inválida",
                exception.getMessage()
        );

        assertEquals(0, service.estatisticas().count());

    }

    //Não deve criar transação com valores negativos
    @Test
    public void naoDeveCriarTransacaoComValorNegativo(){

        transacao.setValor(new BigDecimal("-100.00"));

        ValorInvalidoException exception = assertThrows(
                ValorInvalidoException.class,
                () -> service.criarTransacao(transacao)
        );

        assertEquals(
                "O valor da transação é inválido",
                exception.getMessage()
        );

        assertEquals(0, service.estatisticas().count());

    }

    //Não deve criar transação com data nula
    @Test
    public void naoDeveCriarTransacaoComDataNula(){

        transacao.setDataHora(null);

        DataInvalidaException exception = assertThrows(
                DataInvalidaException.class,
                () -> service.criarTransacao(transacao)
        );

        assertEquals(
                "A data da transação é inválida",
                exception.getMessage()
        );

        assertEquals(0, service.estatisticas().count());

    }

    //Não deve criar transação com valor nulo
    @Test
    public void naoDeveCriarTransacaoComValorNulo(){

        transacao.setValor(null);

        ValorInvalidoException exception = assertThrows(
                ValorInvalidoException.class,
                () -> service.criarTransacao(transacao)
        );

        assertEquals(
                "O valor da transação é inválido",
                exception.getMessage()
        );

        assertEquals(0, service.estatisticas().count());

    }

    //Deve criar transação quando o valor for 0
    @Test
    public void deveCriarTransacaoQuandoOValorForZero(){

        transacao.setValor(BigDecimal.ZERO);

        assertDoesNotThrow(() -> service.criarTransacao(transacao));

        assertEquals(1, service.estatisticas().count());


    }

    //Deve deletar todas as transações armazenadas
    @Test
    public void deveDeletarTodasAsTransações(){

        service.criarTransacao(transacao);

        Transacao antiga = new Transacao();
        antiga.setValor(new BigDecimal("250.00"));
        antiga.setDataHora(OffsetDateTime.now().minusHours(2));

        service.criarTransacao(antiga);

        assertEquals(2, service.quantidadeTransacoes());

        service.deletarTransacoes();

        assertEquals(0, service.quantidadeTransacoes());

    }

    //Teste para retornar estatisticas zeradas
    @Test
    public void deveRetornarEstatisticasZeradas(){

        EstatisticaDTO resultado = service.estatisticas();

        assertEquals(0L, resultado.count());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.sum()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.avg()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.min()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.max()));

    }

    //Deve retornar as estatisticas das transações
    @Test
    public void deveRetornarAsEstatisticasDasTransacoes(){

        service.criarTransacao(transacao);

        Transacao transacao1 = new Transacao();
        transacao1.setValor(new BigDecimal("50.00"));
        transacao1.setDataHora(transacao.getDataHora());
        service.criarTransacao(transacao1);

        Transacao transacao2 = new Transacao();
        transacao2.setValor(new BigDecimal("300.00"));
        transacao2.setDataHora(transacao.getDataHora());
        service.criarTransacao(transacao2);

        Transacao antiga = new Transacao();
        antiga.setValor(new BigDecimal("250.00"));
        antiga.setDataHora(OffsetDateTime.now().minusHours(2));
        service.criarTransacao(antiga);

        EstatisticaDTO resultado = service.estatisticas();

        assertAll(
                () -> assertEquals(3L, resultado.count()),
                () -> assertEquals(new BigDecimal("500.00"), resultado.sum()),
                () -> assertEquals(new BigDecimal("166.67"), resultado.avg()),
                () -> assertEquals(new BigDecimal("50.00"), resultado.min()),
                () -> assertEquals(new BigDecimal("300.00"), resultado.max())
        );

    }

    //Teste para retornar estastisticas zeradas porque tem apenas transações com mais de 60s
    @Test
    public void deveRetornarEstatisticasVaziasTransacoesAposSessentaSegundos(){

        transacao.setDataHora(OffsetDateTime.now().minusHours(2));
        service.criarTransacao(transacao);

        Transacao transacao1 = new Transacao();
        transacao1.setValor(new BigDecimal("50.00"));
        transacao1.setDataHora(OffsetDateTime.now().minusHours(5));
        service.criarTransacao(transacao1);

        Transacao transacao2 = new Transacao();
        transacao2.setValor(new BigDecimal("300.00"));
        transacao2.setDataHora(OffsetDateTime.now().minusHours(3));
        service.criarTransacao(transacao2);

        Transacao antiga = new Transacao();
        antiga.setValor(new BigDecimal("250.00"));
        antiga.setDataHora(OffsetDateTime.now().minusHours(1));
        service.criarTransacao(antiga);

        EstatisticaDTO resultado = service.estatisticas();

        assertEquals(0L, resultado.count());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.sum()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.avg()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.min()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.max()));

    }


}

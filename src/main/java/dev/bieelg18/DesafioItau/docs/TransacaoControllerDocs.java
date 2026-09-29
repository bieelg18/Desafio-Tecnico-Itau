package dev.bieelg18.DesafioItau.docs;

import dev.bieelg18.DesafioItau.dto.EstatisticaDTO;
import dev.bieelg18.DesafioItau.objects.Transacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface TransacaoControllerDocs {

    @Operation(summary = "Cria uma nova transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação Criada e Armazenada"),
            @ApiResponse(responseCode = "422", description = "O valor da transação é inválido"),
            @ApiResponse(responseCode = "422", description = "A data da transação é inválida"),
            @ApiResponse(responseCode = "400", description = "Transação não criada")
    })
    ResponseEntity<Void> criarTransacao(Transacao transacao);

    @Operation(summary = "Deleta todas as transações armazenadas")
    @ApiResponse(responseCode = "200", description = "Transações deletadas com sucesso")
    void deletarTransacao();

    @Operation(summary = "Retorna estatísticas sobre as transações realizadas nos útlimos 60 segundos")
    @ApiResponse(responseCode = "200", description = "Estatísticas das transações realizadas nos últimos 60 segundos")
    EstatisticaDTO estatisticas();

}

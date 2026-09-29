package dev.bieelg18.DesafioItau.dto;

import java.math.BigDecimal;

public record EstatisticaDTO(
        Long count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {
}

package dev.bieelg18.DesafioItau.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transacao {

    BigDecimal valor;
    OffsetDateTime dataHora;

}

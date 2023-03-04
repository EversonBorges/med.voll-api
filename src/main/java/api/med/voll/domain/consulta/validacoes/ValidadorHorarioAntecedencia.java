package api.med.voll.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import api.med.voll.domain.consulta.DadosAgendamentoConsulta;
import api.med.voll.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsulta{

public void validar(DadosAgendamentoConsulta dados) {
		
		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
		
		if(diferencaEmMinutos < 30) {
			throw new ValidacaoException("Consulta deve ser agendada com antecedência de 30 minutos");
		}
	}
}

package api.med.voll.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import api.med.voll.domain.consulta.DadosAgendamentoConsulta;
import api.med.voll.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsulta{

	public void validar(DadosAgendamentoConsulta dados) {
		
		var dataConsulta = dados.data();
		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var antesHorarioAbertura = dataConsulta.getHour() < 7;
		var depoisHorarioFechamento = dataConsulta.getHour() > 17;
		
		if(domingo || antesHorarioAbertura || depoisHorarioFechamento) {
			throw new ValidacaoException("Consulta fora do horário de expediente!");
		}
	}
}

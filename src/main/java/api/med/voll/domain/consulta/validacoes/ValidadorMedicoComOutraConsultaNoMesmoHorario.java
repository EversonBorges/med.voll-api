package api.med.voll.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.med.voll.domain.consulta.ConsultaRepository;
import api.med.voll.domain.consulta.DadosAgendamentoConsulta;
import api.med.voll.infra.exception.ValidacaoException;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta{

	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void validar(DadosAgendamentoConsulta dados){

		var medicoPossuiConsultaMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
		if(medicoPossuiConsultaMesmoHorario) {
			throw new ValidacaoException("Médico já possui consulta agendada neste horário!");
		}
		
	}
}

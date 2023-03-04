package api.med.voll.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.med.voll.domain.consulta.DadosAgendamentoConsulta;
import api.med.voll.domain.paciente.PacienteRepository;
import api.med.voll.infra.exception.ValidacaoException;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta{
	
@Autowired
private PacienteRepository pacienteRepository;
	
	public void validar(DadosAgendamentoConsulta dados){

		var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
		if(!pacienteEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendadao com paciente inativo!");
		}
	}
}

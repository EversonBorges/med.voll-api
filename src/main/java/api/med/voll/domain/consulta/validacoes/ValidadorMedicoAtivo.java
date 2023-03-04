package api.med.voll.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.med.voll.domain.consulta.DadosAgendamentoConsulta;
import api.med.voll.domain.medico.MedicoRepository;
import api.med.voll.infra.exception.ValidacaoException;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta{
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		
		if(dados.idMedico() != null) {
			var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());
			if(!medicoEstaAtivo) {
				throw new ValidacaoException("Consulta não pode ser agendadao com médico inativo!");
			}
		}

	}
}

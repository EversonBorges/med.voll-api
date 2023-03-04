package api.med.voll.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.med.voll.domain.consulta.validacoes.ValidadorAgendamentoConsulta;
import api.med.voll.domain.medico.Medico;
import api.med.voll.domain.medico.MedicoRepository;
import api.med.voll.domain.paciente.PacienteRepository;
import api.med.voll.infra.exception.ValidacaoException;

@Service
public class AgendaConsultas {
	
	@Autowired 
	private ConsultaRepository consultaRepository;
	@Autowired 
	private MedicoRepository medicoRepository;
	@Autowired 
	private PacienteRepository pacienteRepository;
	@Autowired
	private List<ValidadorAgendamentoConsulta> validadores;
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("Id do paciente informado não existe na base de dados!");
		}
		
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("Id do medico informado não existe na base de dados!");
		}
		
		validadores.forEach(v -> v.validar(dados));
		
		var medico = selecionarMedico(dados);
		
		if(medico == null) {
			throw new ValidacaoException("Não foi encontrado médicos disponiveis para esta data e horario!");
		}
		var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		var consulta = new Consulta(null, paciente, medico, dados.data(),null);
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico selecionarMedico(DadosAgendamentoConsulta dados) {

		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Deve ser informado a especialidade!");
		}
		
		return medicoRepository.selecionarMedicoDisponivelData(dados.especialidade(), dados.data());
	}

	public void cancelar(DadosCancelamentoConsulta dados) {

		if(!consultaRepository.existsById(dados.idConsulta())) {
			throw new ValidacaoException("Id da consulta informada não existe!");
		}
		
		var consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());
	}
}

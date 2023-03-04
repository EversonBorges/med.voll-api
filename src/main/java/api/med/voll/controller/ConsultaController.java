package api.med.voll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.med.voll.domain.consulta.AgendaConsultas;
import api.med.voll.domain.consulta.DadosAgendamentoConsulta;
import api.med.voll.domain.consulta.DadosCancelamentoConsulta;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	@Autowired
	private AgendaConsultas agenda;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> agendar(@Valid @RequestBody DadosAgendamentoConsulta dados){
		
		var consulta = agenda.agendar(dados);
		return ResponseEntity.ok(consulta);
	}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
		
		agenda.cancelar(dados);
		return ResponseEntity.noContent().build();
	}
}
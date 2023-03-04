package api.med.voll.domain.paciente;

import api.med.voll.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;

public record DadosAtualizacaoPaciente(
		Long id, 
		String nome, 
		String telefone, 
		@Valid DadosEndereco endereco) {
}

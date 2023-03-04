package api.med.voll.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import api.med.voll.domain.consulta.Consulta;
import api.med.voll.domain.endereco.DadosEndereco;
import api.med.voll.domain.paciente.DadosCadastroPaciente;
import api.med.voll.domain.paciente.Paciente;
import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

	@Autowired
	private MedicoRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	@DisplayName("deveria retronar nulo quando unico medico cadastrado nao esta disponivel na data")
	void selecionarMedicoDisponivelDataCenario1() {
		
		//given or arrange
		var proximaSegunda = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0);

		var medico = cadastrarMedico("Medico 01", "medico01@voll.med.com", "124578", Especialidade.CARDIOLOGIA);
		var paciente = cadastrarPaciente("Paciente01", "paciente01@gmailcom", "12345678900");
		cadastrarConsulta(medico, paciente, proximaSegunda);
		
		//when or act
		var medicoLivre = repository.selecionarMedicoDisponivelData(Especialidade.CARDIOLOGIA,proximaSegunda);
		
		//then or assert
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("deveria retornar medico quando ele estiver disponivel na data")
	void selecionarMedicoDisponivelDataCenario2() {
		var proximaSegunda = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0);
		
		var medico = cadastrarMedico("Medico 01", "medico01@voll.med.com", "124578", Especialidade.CARDIOLOGIA);
		
		var medicoLivre = repository.selecionarMedicoDisponivelData(Especialidade.CARDIOLOGIA,proximaSegunda);
		assertThat(medicoLivre).isEqualTo(medico);
	}
	
	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
	    em.persist(new Consulta(null, paciente, medico, data, null));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
	    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
	    em.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    em.persist(paciente);
	    return paciente;
	}

	private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
	    return new DadosCadastroMedico(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private DadosEndereco dadosEndereco() {
	    return new DadosEndereco(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}

}

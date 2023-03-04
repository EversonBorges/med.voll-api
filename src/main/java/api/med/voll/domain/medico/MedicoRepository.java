package api.med.voll.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	Page<Medico> findAllByAtivoTrue(Pageable paginacao);

	@Query("""
			SELECT m FROM Medico m
			WHERE m.ativo = 1
			AND m.especialidade = :especialidade
			AND m.id NOT IN(
				SELECT c.medico.id FROM Consulta c
				WHERE c.data = :data)
			ORDER BY RAND()
			LIMIT 1
			""")
	Medico selecionarMedicoDisponivelData(Especialidade especialidade, LocalDateTime data);

	@Query("""
			SELECT m.ativo FROM Medico m
			WHERE m.id = :idMedico
			""")
	Boolean findAtivoById(Long idMedico);
}

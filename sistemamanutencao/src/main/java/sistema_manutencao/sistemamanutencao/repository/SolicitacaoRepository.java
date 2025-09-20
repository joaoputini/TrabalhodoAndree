package sistema_manutencao.sistemamanutencao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sistema_manutencao.sistemamanutencao.model.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    
}

package sistema_manutencao.sistemamanutencao.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Solicitacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do solicitante é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(length = 100, nullable = false)
    private String nomeSolicitante;
    
    @NotBlank(message = "A descrição do problema é obrigatória.")
    @Column(nullable = false, columnDefinition = "TEXT") 
    private String descricaoProblema;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataSolicitacao = LocalDateTime.now();
    
    @Column(nullable = true)
    private LocalDateTime dataEncerramento;

}
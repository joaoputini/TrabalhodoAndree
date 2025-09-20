package sistema_manutencao.sistemamanutencao.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import sistema_manutencao.sistemamanutencao.model.Solicitacao;
import sistema_manutencao.sistemamanutencao.repository.SolicitacaoRepository;

@Controller
@RequestMapping("/solicitacoes")
public class ManutencaoController {

    @Autowired
    SolicitacaoRepository solicitacaoRepository;

    @GetMapping
    public ModelAndView listar() {
        return new ModelAndView(
                "list", 
                Map.of("solicitacoes", solicitacaoRepository.findAll(Sort.by("dataSolicitacao"))));
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        return new ModelAndView("form", Map.of("solicitacao", new Solicitacao()));
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Solicitacao solicitacao, BindingResult result) {
        if (result.hasErrors()) {
            return "form"; // Se houver erro, volta para o form.html
        }
        solicitacaoRepository.save(solicitacao);
        return "redirect:/solicitacoes";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);

        if (solicitacao.isPresent() && solicitacao.get().getDataEncerramento() == null) {
            return new ModelAndView("form", Map.of("solicitacao", solicitacao.get()));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada ou já finalizada.");
    }

    @PostMapping("/editar/{id}")
    public String editar(@Valid Solicitacao solicitacao, BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        solicitacaoRepository.save(solicitacao);
        return "redirect:/solicitacoes";
    }
    
    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id){
        Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
        if (solicitacao.isPresent()) {
            return new ModelAndView("delete", Map.of("solicitacao", solicitacao.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada.");
    }

    @PostMapping("/excluir/{id}")
    public String excluir(Solicitacao solicitacao){
        solicitacaoRepository.delete(solicitacao);
        return "redirect:/solicitacoes";
    }

    @PostMapping("/finalizar/{id}")
    public String finalizar(@PathVariable Long id) {
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findById(id);
        if (optionalSolicitacao.isPresent()) {
            Solicitacao solicitacao = optionalSolicitacao.get();
            solicitacao.setDataEncerramento(LocalDateTime.now()); 
            solicitacaoRepository.save(solicitacao);
            return "redirect:/solicitacoes";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada.");
    }
}
package br.edu.unicesumar.api.service;

import br.edu.unicesumar.api.entity.Palestrante;
import br.edu.unicesumar.api.exception.BusinessException;
import br.edu.unicesumar.api.exception.ResourceNotFoundException;
import br.edu.unicesumar.api.repository.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PalestranteService {

    @Autowired
    private PalestranteRepository palestranteRepository;

    public List<Palestrante> listarTodos() {
        return palestranteRepository.findAll();
    }

    public Palestrante buscarPorId(Long id) {
        return palestranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palestrante não encontrado com ID: " + id));
    }

    @Transactional
    public Palestrante criar(Palestrante palestrante) {
        return palestranteRepository.save(palestrante);
    }

    @Transactional
    public Palestrante atualizar(Long id, Palestrante palestrante) {
        Palestrante palestranteExistente = palestranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palestrante não encontrado com ID: " + id));

        palestranteExistente.setNome(palestrante.getNome());
        palestranteExistente.setMiniCurriculo(palestrante.getMiniCurriculo());
        palestranteExistente.setInstituicao(palestrante.getInstituicao());

        return palestranteRepository.save(palestranteExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Palestrante palestrante = palestranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palestrante não encontrado com ID: " + id));

        if (palestranteRepository.temEventosVinculados(id)) {
            throw new BusinessException("Não é possível excluir um palestrante que esteja vinculado a algum evento");
        }

        palestranteRepository.delete(palestrante);
    }
}
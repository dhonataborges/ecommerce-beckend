package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.domain.model.Estoque;
import com.backend.ecommerce.domain.repository.EstoqueRepository;
import com.backend.ecommerce.domain.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/produto-em-estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @GetMapping
    public List<Estoque> listar() { return estoqueRepository.findAll();}

    @GetMapping("/{estoqueId}")
    public  Estoque buscar(@PathVariable Long estoqueId) { return estoqueService.buscarOuFalhar(estoqueId);}

   /* @GetMapping("cosmeticos")
    public List<Estoque> buscarCosmeticos(@RequestParam("tipoProd") Long tipoProd) {
        return estoqueRepository.buscarTipoProd(Math.toIntExact(tipoProd));
    }*/

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Estoque adicionar(@RequestBody @Valid Estoque estoque) {

        return estoqueService.salvar(estoque);
    }

    @PutMapping("/{estoqueId}")
    public Estoque atualizar(@PathVariable Long estoqueId, @RequestBody @Valid Estoque estoque) {
        Estoque estoqueAtual = estoqueService.buscarOuFalhar(estoqueId);

        BeanUtils.copyProperties(estoque, estoqueAtual, "id");

        return estoqueService.salvar(estoqueAtual);
    }

    @DeleteMapping("/{estoqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estoqueId) {
        estoqueService.excluir(estoqueId);
    }

}

package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.domain.model.VendaProduto;
import com.backend.ecommerce.domain.repository.VendaProdutoRepository;
import com.backend.ecommerce.domain.service.VendaProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/produto-a-venda")
public class VendaProdutoController {

    @Autowired
    private VendaProdutoService vendaProdutoService;

    @Autowired
    private VendaProdutoRepository vendaProdutoRepository;

    @GetMapping
    public List<VendaProduto> listar() { return vendaProdutoRepository.findAll();}

    @GetMapping("cosmeticos")
    public List<VendaProduto> buscarCosmeticos(@RequestParam("categoria") Long categoria) {
        return vendaProdutoRepository.buscarTipoProd(Math.toIntExact(categoria));
    }

    @GetMapping("/{vendaId}")
    public  VendaProduto buscar(@PathVariable Long vendaId) { return vendaProdutoService.buscarOuFalhar(vendaId);}

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public VendaProduto adicionar(@RequestBody @Valid VendaProduto vendaProduto) {

        return vendaProdutoService.salvar(vendaProduto);
    }

    @PutMapping("/{vendaId}")
    public VendaProduto atualizar(@PathVariable Long vendaId, @RequestBody @Valid VendaProduto vendaProduto) {
        VendaProduto vendaProdutoAtual = vendaProdutoService.buscarOuFalhar(vendaId);

        BeanUtils.copyProperties(vendaProduto, vendaProdutoAtual, "id");

        return vendaProdutoService.salvar(vendaProdutoAtual);
    }

    @DeleteMapping("/{vendaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long vendaId) {
        vendaProdutoService.excluir(vendaId);
    }

}

package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import com.backend.ecommerce.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

	@GetMapping("/{produtoId}")
	public Produto buscar(@PathVariable Long produtoId) {
		return produtoService.buscarOuFalhar(produtoId);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Produto adicionar(@RequestBody @Valid Produto produto) {
		return produtoService.salvar(produto);
	}

	@PutMapping("/{produtoId}")
	public Produto atualizar(@PathVariable Long produtoId, @RequestBody @Valid Produto produto) {
		Produto produtoAtual = produtoService.buscarOuFalhar(produtoId);

		BeanUtils.copyProperties(produto, produtoAtual, "id");

		return produtoService.salvar(produtoAtual);

	}

	@DeleteMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long produtoId) {
		produtoService.excluir(produtoId);
	}

}

package com.backend.ecommerce.domain.service;

import com.backend.ecommerce.domain.exception.entidadeException.EntidadeEmUsoException;
import com.backend.ecommerce.domain.exception.produtoException.ProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
	
private static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	public Produto salvar(@Valid Produto produto) {

		if (buscaCodProd(produto) != null) {
			throw new DataIntegrityViolationException("Produto já está cadastrado!");
		}

	//	Long fotoProdutoId = produto.getFotoProduto().getId();
	//	FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(fotoProdutoId);

		//produto.setFotoProduto(fotoProduto);

		return produtoRepository.save(produto);
	}

	public void excluir(Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);

		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(produtoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO, produtoId));
		}
	}

	public Produto buscarOuFalhar(Long produtoId) {
		return produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
	}

	/*
	* Busca o codigo do produto
	*/
	private Produto buscaCodProd(Produto codProd) {
		Produto cod = produtoRepository.findByCodProd(codProd.getCodProd());

		if (cod != null) {
			return cod;
		}
		return null;

	}
}



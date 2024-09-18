package com.backend.ecommerce.api.controller;

import com.backend.ecommerce.api.assembler.FotoProdutoModelAssembler;
import com.backend.ecommerce.api.model.FotoProdutoModel;
import com.backend.ecommerce.api.model.input.FotoProdutoInput;
import com.backend.ecommerce.domain.exception.entidadeException.EntidadeNaoEncontradaException;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.model.Produto;
import com.backend.ecommerce.domain.service.CatalogoFotoProdutoService;
import com.backend.ecommerce.domain.service.FotoStorageService;
import com.backend.ecommerce.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/produtos/{produtoId}/foto")
public class FotoProdutoController {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	@Autowired
	private FotoStorageService fotoStorage;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		Produto produto = produtoService.buscarOuFalhar(produtoId);

		MultipartFile arquivo = fotoProdutoInput.getArquivo();

		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());

		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(produtoId);

		return fotoProdutoModelAssembler.toModel(fotoProduto);
	}

	@GetMapping
	public ResponseEntity<InputStreamResource> servir(@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader)
			throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(produtoId);

			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

			//Essa linha recupera a foto para ser usada
			InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(inputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
												   List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}


	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long produtoId) {
		catalogoFotoProdutoService.excluir(produtoId);
	}

}

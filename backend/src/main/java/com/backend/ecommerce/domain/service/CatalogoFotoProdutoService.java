package com.backend.ecommerce.domain.service;


import com.backend.ecommerce.domain.exception.produtoException.ProdutoNaoEncontradoException;
import com.backend.ecommerce.domain.model.FotoProduto;
import com.backend.ecommerce.domain.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository
                .findFotoById(produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto =  produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeAquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscarOuFalhar(Long produtoId) {
        return produtoRepository.findFotoById(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }

    @Transactional
    public void excluir(Long produtoId) {
        FotoProduto foto = buscarOuFalhar(produtoId);

        produtoRepository.delete(foto);
        produtoRepository.flush();

        fotoStorage.remover(foto.getNomeArquivo());
    }

}
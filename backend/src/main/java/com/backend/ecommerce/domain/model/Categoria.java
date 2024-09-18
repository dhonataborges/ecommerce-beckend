package com.backend.ecommerce.domain.model;

import lombok.Getter;

@Getter
public enum Categoria {

    PERFUMARIA(0,"Perfumaria"),
    COPOR_E_BANHO(1, "Corpo e Banho"),
    CABELOS(2, "Cabelos"),
    ROSTO(3, "Rosto"),
    MAQUIAGEM(4, "Maquiagem"),
    INFANTIL(5,"Infantil");

    private Integer codCategoria;
    private String descricao;

    private Categoria(Integer codCategoria, String descricao) {
        this.codCategoria = codCategoria;
        this.descricao = descricao;
    }

    public static Categoria toEnum(Integer cod) {

        if(cod == null) {
            return null;
        }

        for(Categoria x : Categoria.values()) {
            if (cod.equals(x.getCodCategoria())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Categoria Invalida!" + cod);
    }
}

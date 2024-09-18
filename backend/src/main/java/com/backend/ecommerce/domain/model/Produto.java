package com.backend.ecommerce.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_produto")
public class Produto {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cod_prod")
	@Size(min=6, max = 6)
	private String codProd;
	
	@Column(name = "nome_prod")
	private String nomeProd;
	
	@Column(name = "desc_prod")
	private String descProd;

	@Column(name = "valor_unid")
	private BigDecimal valorUnid;

	@Column(name = "num_parcela_na_venda")
	private Integer numParcelaNaVenda;

	@Column(name = "valor_parcela")
	private BigDecimal valorParcela;

	@Column(name = "desconto_porcento")
	private BigDecimal descontaPorcento;

	@JoinColumn(name = "categoria")
	private Integer categoria;

	@JsonIgnore
	@OneToMany(mappedBy = "produto")
	private List<Estoque> estoques = new ArrayList<>();

}

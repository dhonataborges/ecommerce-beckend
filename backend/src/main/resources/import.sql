
INSERT INTO tb_produto(id,cod_prod,nome_prod,desc_prod,valor_unid,num_parcela_na_venda,valor_parcela,desconto_porcento,categoria) VALUES (null,'p00120','Creme de Pele','O Creme que vai te surpreender.',72.00,2,36.00,15,2);
INSERT INTO tb_produto(id,cod_prod,nome_prod,desc_prod,valor_unid,num_parcela_na_venda,valor_parcela,desconto_porcento,categoria) VALUES (null,'p00121','Perfume kaik','O perfume do momento.',72.00,2,36.00,15,1);
INSERT INTO tb_produto(id,cod_prod,nome_prod,desc_prod,valor_unid,num_parcela_na_venda,valor_parcela,desconto_porcento,categoria) VALUES (null,'p00122','Creme para o rosto','O Creme que vai te surpreender.',72.00,2,36.00,15,2);


INSERT INTO tb_foto_produto(produto_id,tamanho,content_type,descricao,nome_arquivo) VALUES (1,40608,"image/jpeg","2024-08-22","03a3054c-b831-46ad-9800-7602bf2ba5fb_carrossel_img4.jpg");
INSERT INTO tb_foto_produto(produto_id,tamanho,content_type,descricao,nome_arquivo) VALUES (2,40608,"image/jpeg","2024-08-22","03a3054c-b831-46ad-9800-7602bf2ba5fb_carrossel_img4.jpg");

INSERT INTO tb_estoque(id,cod_prod_estoque,produto_id,data_entrada,valor_und,qtd_prod,valor_total_prod) VALUES (null,'p00121',1,"2024-04-12",56.99,50,2849.50), (null,'p00120',1,"2024-04-12",40.98,75,3073.50), (null,'p00122',1,"2024-04-12",56.99,50,2849.50),(null,'p00123',1,"2024-04-12",56.99,50,2849.50), (null,'p00124',1,"2024-04-12",40.98,75,3073.50), (null,'p00122',1,"2024-04-12",56.99,50,2849.50);

INSERT INTO tb_venda_produto(id,estoque_id,valor_venda,num_parcela,valor_parcela,desconto_porcento) VALUES (null,1,68.50,3,22.83,5), (null,1,72.00,2,36.00,10), (null,1,175.00,3,58.33,15);
## uma forma (utilizando ignore):
insert ignore into cozinha (id, nome) values (1, 'Tailandesa'); 
insert ignore into cozinha (id, nome) values (2, 'Indiana'); 
insert ignore into cozinha (id, nome) values (3,'Brasileira'); 
insert ignore into cozinha (id, nome) values (4, 'Italiana'); 
insert ignore into cozinha (id, nome) values (5, 'Variada'); 

## outra forma (deletando tudo e recriando os dados)

set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento ;
delete from grupo;
delete from grupo_permissao ;
delete from permissao ;
delete from produto ;
delete from restaurante ;
delete from restaurante_forma_pagamento ;
delete from usuario ;
delete from usuario_grupo ;
delete from pedido ;
delete from item_pedido ;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table grupo_permissao  auto_increment = 1;
alter table permissao  auto_increment = 1;
alter table produto  auto_increment = 1;
alter table restaurante  auto_increment = 1;
alter table restaurante_forma_pagamento  auto_increment = 1;
alter table usuario  auto_increment = 1;
alter table usuario_grupo  auto_increment = 1;
alter table pedido  auto_increment = 1;
alter table item_pedido  auto_increment = 1;

insert into algafood.cozinha (id, nome) values (1, 'Tailandesa'); 
insert into algafood.cozinha (id, nome) values (2, 'Indiana'); 
insert into algafood.cozinha (id, nome) values (3,'Brasileira'); 
insert into algafood.cozinha (id, nome) values (4, 'Italiana'); 
insert into algafood.cozinha (id, nome) values (5, 'Variada'); 


insert into algafood.estado (id, nome) values (1,'Santa Catarina');
insert into algafood.estado (id, nome) values (2,'São Paulo');

insert into algafood.cidade (id, nome, estado_id) values (1,'Florianópolis', 1);
insert into algafood.cidade (id, nome, estado_id) values (2,'São José', 1);

insert into algafood.restaurante (nome, cozinha_id, taxa_frete, data_atualizacao, data_cadastro) values ('Rocco', 4, 20,utc_timestamp, utc_timestamp);
insert into algafood.restaurante (nome, cozinha_id, taxa_frete, data_atualizacao, data_cadastro) values ('La Boheme', 4, 20,utc_timestamp, utc_timestamp);
INSERT INTO algafood.restaurante (data_atualizacao, data_cadastro, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES (utc_timestamp, utc_timestamp, 'trindade', '88036200', 'casa', 'Rua',  '114', 'Trindog', 0, 1, 1);
	
INSERT INTO algafood.restaurante (data_atualizacao, data_cadastro, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES (utc_timestamp, utc_timestamp, 'Vargem Grande', '88036100', 'casa', 'SC 401',  '114', 'Jun', 0, 1, 1);


insert into algafood.permissao values (1,'Permissao geral', 'Acesso a todas as funcionalidades');

insert into algafood.forma_pagamento values (1,'Cartão');
insert into algafood.forma_pagamento values (2,'Dinheiro');
insert into algafood.forma_pagamento values (3,'PIX');

INSERT INTO algafood.restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 1), (1, 2), (1, 3), (2, 1);


INSERT INTO algafood.produto (ativo, descricao, nome, preco, restaurante_id) VALUES(1, 'Peixa Branco', 'Sashimi', 10, 4);
INSERT INTO algafood.produto (ativo, descricao, nome, preco, restaurante_id) VALUES(1, 'Peixa salmão', 'Sashimi', 10, 4);


INSERT INTO algafood.usuario (data_cadastro, email, nome, senha) VALUES(utc_timestamp, 'edu@edu.com', 'Eduardo Cordeiro', '123');
INSERT INTO algafood.usuario (data_cadastro, email, nome, senha) VALUES(utc_timestamp, 'edu2@edu2.com', 'Eduardo2 Cordeiro2', '1234');

INSERT INTO algafood.grupo (nome) VALUES('Adm');
INSERT INTO algafood.grupo (nome) VALUES('Financeiro');
INSERT INTO algafood.grupo (nome) VALUES('Vendas');

INSERT INTO algafood.permissao (descricao, nome) VALUES('cadastro', 'cadastro');
INSERT INTO algafood.permissao (descricao, nome) VALUES('consulta', 'consulta');
INSERT INTO algafood.permissao (descricao, nome) VALUES('total', 'total');

INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(1, 1);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(1, 2);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(1, 3);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(2, 1);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(3, 2);


INSERT INTO algafood.usuario_grupo (usuario_id, grupo_id) VALUES(1, 1);
INSERT INTO algafood.usuario_grupo (usuario_id, grupo_id) VALUES(2, 2);
INSERT INTO algafood.usuario_grupo (usuario_id, grupo_id) VALUES(2, 3);


INSERT INTO algafood.pedido (subtotal, frete, valor_total, data_criacao, data_confirmacao, data_cancelamento, data_entrega, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, forma_pagamento_id, restaurante_id, cliente_id, status) 
VALUES(100, 10, 110, '2021-07-10', null, null, null, 1, '88036200', 'rua lauro linhares', '2055', null, 'Trindade', 1, 1, 1, 'CRIADO');

INSERT INTO algafood.pedido (subtotal, frete, valor_total, data_criacao, data_confirmacao, data_cancelamento, data_entrega, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, forma_pagamento_id, restaurante_id, cliente_id, status) 
VALUES(200, 20, 220, '2021-07-11', null, null, null, 1, '88036201', 'rua percy', '1141', null, 'Trindade', 1, 2, 1, 'CRIADO');

INSERT INTO algafood.item_pedido (quantidade, precoUnitario, precoTotal, observacao, produto_id, pedido_id) VALUES(2, 10, 20, 'Sushi', 1, 1);
INSERT INTO algafood.item_pedido (quantidade, precoUnitario, precoTotal, observacao, produto_id, pedido_id) VALUES(2, 20, 40, 'Sashimi Peixe Branco', 1, 2);
INSERT INTO algafood.item_pedido (quantidade, precoUnitario, precoTotal, observacao, produto_id, pedido_id) VALUES(2, 30, 60, 'Sashimi Salmão', 2, 2);


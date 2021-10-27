insert into cozinha (id, nome) values (1, 'Tailandesa'); 
insert into cozinha (id, nome) values (2, 'Indiana'); 
insert into cozinha (id, nome) values (3,'Brasileira'); 
insert into cozinha (id, nome) values (4, 'Italiana'); 
insert into cozinha (id, nome) values (5, 'Variada'); 


insert into estado (id, nome) values (1,'Santa Catarina');
insert into estado (id, nome) values (2,'São Paulo');

insert into cidade (id, nome, estado_id) values (1,'Florianópolis', 1);
insert into cidade (id, nome, estado_id) values (2,'São José', 1);

insert into restaurante (nome, cozinha_id, taxa_frete, data_atualizacao, data_cadastro) values ('Rocco', 4, 20,utc_timestamp, utc_timestamp);
insert into restaurante (nome, cozinha_id, taxa_frete, data_atualizacao, data_cadastro) values ('La Boheme', 4, 20,utc_timestamp, utc_timestamp);
INSERT INTO restaurante (data_atualizacao, data_cadastro, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES (utc_timestamp, utc_timestamp, 'trindade', '88036200', 'casa', 'Rua',  '114', 'Trindog', 0, 1, 1);
	
INSERT INTO restaurante (data_atualizacao, data_cadastro, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero, nome, taxa_frete, cozinha_id, endereco_cidade_id) VALUES (utc_timestamp, utc_timestamp, 'Vargem Grande', '88036100', 'casa', 'SC 401',  '114', 'Jun', 0, 1, 1);


insert into permissao values (1,'Permissao geral', 'Acesso a todas as funcionalidades');

insert into forma_pagamento values (1,'Cartão');
insert into forma_pagamento values (2,'Dinheiro');
insert into forma_pagamento values (3,'PIX');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 1), (1, 2), (1, 3), (2, 1);


INSERT INTO produto (ativo, descricao, nome, preco, restaurante_id) VALUES(1, 'Peixa salmão', 'Sashimi', 10, 4);


INSERT INTO usuario (data_cadastro, email, nome, senha) VALUES(utc_timestamp, 'edu@edu.com', 'Eduardo Cordeiro', '123');
INSERT INTO usuario (data_cadastro, email, nome, senha) VALUES(utc_timestamp, 'edu2@edu2.com', 'Eduardo2 Cordeiro2', '1234');

INSERT INTO grupo (nome) VALUES('Adm');
INSERT INTO grupo (nome) VALUES('Financeiro');
INSERT INTO grupo (nome) VALUES('Vendas');

INSERT INTO permissao (descricao, nome) VALUES('cadastro', 'cadastro');
INSERT INTO permissao (descricao, nome) VALUES('consulta', 'consulta');
INSERT INTO permissao (descricao, nome) VALUES('total', 'total');

INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(1, 1);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(1, 2);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(1, 3);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(2, 1);
INSERT INTO algafood.grupo_permissao (grupo_id, permissao_id) VALUES(3, 2);


INSERT INTO algafood.usuario_grupo (usuario_id, grupo_id) VALUES(1, 1);
INSERT INTO algafood.usuario_grupo (usuario_id, grupo_id) VALUES(2, 2);
INSERT INTO algafood.usuario_grupo (usuario_id, grupo_id) VALUES(2, 3);


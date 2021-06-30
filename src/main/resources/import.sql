insert into cozinha (id, nome) values (1, 'Tailandesa'); 
insert into cozinha (id, nome) values (2, 'Indiana'); 
insert into cozinha (id, nome) values (3,'Brasileira'); 
insert into cozinha (id, nome) values (4, 'Italiana'); 
insert into cozinha (id, nome) values (5, 'Variada'); 

insert into restaurante (nome, cozinha_id, taxa_frete) values ('Rocco', 4, 20);
insert into restaurante (nome, cozinha_id, taxa_frete) values ('La Boheme', 4, 20);

insert into estado (id, nome) values (1,'Santa Catarina');
insert into estado (id, nome) values (2,'São Paulo');

insert into cidade (id, nome, estado_id) values (1,'Florianópolis', 1);
insert into cidade (id, nome, estado_id) values (2,'São José', 1);

insert into permissao values (1,'Permissao geral', 'Acesso a todas as funcionalidades');

insert into forma_pagamento values (1,'Cartão');
insert into forma_pagamento values (2,'Dinheiro');
insert into forma_pagamento values (3,'PIX');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES(1, 1), (1, 2), (1, 3), (2, 1);

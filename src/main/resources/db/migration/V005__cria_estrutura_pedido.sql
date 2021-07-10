create table pedido(
	id bigint not null auto_increment,
	subtotal decimal(10,2) not null,
	frete decimal(10,2) not null,
	valor_total decimal(10,2) not null,
	data_criacao datetime not null,
	data_confirmacao datetime,
	data_cancelamento datetime,
	data_entrega datetime,
	
	endereco_cidade_id bigint not null,
	endereco_cep varchar(9) not null,
	endereco_logradouro varchar(100) not null,
	endereco_numero varchar(20) not null,
	endereco_complemento varchar(60),
	endereco_bairro varchar(60) not null,
	
	forma_pagamento_id bigint not null,
	restaurante_id bigint not null,
	cliente_id bigint not null,
	
	status varchar(10) not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

alter table pedido add constraint fk_forma_pagamento_pedido
	foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table pedido add constraint fk_restaurante_pedido
	foreign key (restaurante_id) references restaurante (id);

alter table pedido add constraint fk_cliente_pedido
	foreign key (cliente_id) references usuario (id);



create table item_pedido(
	id bigint not null auto_increment,
	quantidade int not null,
	precoUnitario decimal(10,2),
	precoTotal decimal(10,2),
	observacao varchar (255),
	
	produto_id bigint not null,
	pedido_id bigint not null,
	
	primary key (id),
	
	unique key uk_item_pedido_produto (pedido_id, produto_id),
	
	constraint fk_item_pedido_produto foreign key (produto_id) references produto (id),
	constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id)
) engine=InnoDB default charset=utf8;



	



	
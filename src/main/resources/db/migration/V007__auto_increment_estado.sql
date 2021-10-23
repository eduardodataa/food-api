ALTER TABLE cidade DROP FOREIGN KEY fk_cidade_estado;

ALTER TABLE estado MODIFY COLUMN id bigint(20) auto_increment NOT NULL;

alter table cidade add constraint fk_cidade_estado foreign key (estado_id) references estado (id);
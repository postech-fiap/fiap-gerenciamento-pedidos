use gerenciamento_pedidos_db;

create table if not exists cliente (
    id bigint,
    cpf char(11) not null,
    email varchar(100) not null,
    nome varchar(100) not null,
    constraint pk_cliente primary key (id)
);

create table if not exists item (
    id bigint,
    valor decimal(15,2) not null,
    nome varchar(50) not null,
    categoria smallint not null,
    disponivel bit(1) not null,
    excluido bit(1) not null,
    descricao varchar(250),
    constraint pk_item primary key (id)
);

create table if not exists item_imagem (
    id bigint,
    caminho varchar(100) not null,
    item_id bigint not null,
    constraint pk_item_imagem primary key (id),
    constraint fk_item_imagem_item foreign key (item_id) references item (id) on delete restrict on update restrict
);

create table if not exists pedido (
    id bigint,
    data_hora timestamp not null,
    status tinyint not null,
    cliente_id bigint,
    tempo_preparo smallint not null,
    numero varchar(10) not null,
    constraint pk_pedido primary key (id),
    constraint fk_pedido_cliente foreign key (cliente_id) references cliente (id) on delete restrict on update restrict
);

create table if not exists pedido_item (
    pedido_id bigint,
    item_id bigint,
    valor_pago decimal(15,2) not null,
    quantidade smallint not null,
    comentario varchar(100),
    constraint pk_pedido_item primary key (pedido_id, item_id),
    constraint fk_pedido_item_pedido foreign key (pedido_id) references pedido (id) on delete restrict on update restrict,
    constraint fk_pedido_item_item foreign key (item_id) references item (id) on delete restrict on update restrict
);

create table if not exists pagamento (
    id bigint,
    data_hora timestamp not null,
    status smallint not null,
    pedido_id bigint not null,
    constraint pk_pagamento primary key (id),
    constraint fk_pagamento_pedido foreign key (pedido_id) references pedido (id) on delete restrict on update restrict
);

use gerenciamento_pedidos_db;

create table cliente (
    id bigint,
    cpf char(11) not null,
    email varchar(100) not null,
    constraint pk_cliente primary key (id)
);

create table item (
    id bigint,
    valor decimal(15,2) not null,
    nome varchar(50) not null,
    categoria smallint not null,
    disponivel bool not null,
    excluido bool not null,
    descricao varchar(250),
    constraint pk_item primary key (id)
);

create table item_imagem (
    id bigint,
    caminho varchar(100) not null,
    item_id bigint not null,
    constraint pk_item_imagem primary key (id),
    constraint fk_item_image_item foreign key (item_id) references item (id) on delete restrict on update restrict
);

create table pedido (
    id bigint,
    data_hora timestamp not null,
    status tinyint not null,
    cliente_id bigint,
    tempo_preparo smallint not null,
    numero varchar(10) not null,
    valor_pago decimal(15,2),
    constraint pk_pedido primary key (id),
    constraint fk_pedido_cliente foreign key (cliente_id) references cliente (id) on delete restrict on update restrict
);

create table pedido_item (
    pedido_id bigint,
    item_id bigint,
    valor_pago decimal(15,2) not null,
    quantidade smallint not null,
    comentario varchar(100),
    constraint pk_pedido_item primary key (pedido_id, item_id),
    constraint fk_pedido_item_pedido foreign key (pedido_id) references pedido (id) on delete restrict on update restrict,
    constraint fk_pedido_item_item foreign key (item_id) references item (id) on delete restrict on update restrict
);

create table pagamento (
    id bigint,
    data_hora timestamp not null,
    status smallint not null,
    pedido_id bigint not null,
    constraint pk_pagamento primary key (id),
    constraint fk_pagamento_pedido foreign key (pedido_id) references pedido (id) on delete restrict on update restrict
);

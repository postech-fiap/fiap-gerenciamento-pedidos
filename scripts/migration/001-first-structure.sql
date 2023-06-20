use gerenciamento_pedidos_db;

create table if not exists cliente (
    id bigint auto_increment,
    cpf char(11) not null,
    email varchar(100) not null,
    nome varchar(100) not null,
    constraint pk_cliente primary key (id),
    constraint uc_cliente_cpf unique (cpf)
);

create table if not exists produto (
    id bigint auto_increment,
    valor decimal(10,2) not null,
    nome varchar(100) not null,
    categoria enum('lanche', 'acompanhamento', 'bebida', 'sobremesa') not null,
    disponivel bit(1) not null,
    excluido bit(1) not null,
    descricao varchar(1000),
    tempo_preparo int not null,
    constraint pk_produto primary key (id)
);

create table if not exists produto_imagem (
    produto_id bigint not null,
    caminho varchar(100) not null,
    constraint pk_produto_imagem primary key (produto_id),
    constraint fk_produto_imagem_produto foreign key (produto_id) references produto (id) on delete restrict on update restrict
);

create table if not exists pedido (
    id bigint auto_increment,
    data_hora timestamp not null default now(),
    status enum('pendente', 'em_preparacao', 'finalizado') not null,
    cliente_id bigint,
    tempo_espera int not null,
    numero char(4) not null,
    constraint pk_pedido primary key (id),
    constraint fk_pedido_cliente foreign key (cliente_id) references cliente (id) on delete restrict on update restrict
);

create table if not exists pedido_produto (
    pedido_id bigint,
    produto_id bigint,
    valor_pago decimal(10,2) not null,
    quantidade int not null,
    comentario varchar(1000),
    constraint pk_pedido_produto primary key (pedido_id, produto_id),
    constraint fk_pedido_produto_pedido foreign key (pedido_id) references pedido (id) on delete restrict on update restrict,
    constraint fk_pedido_produto_produto foreign key (produto_id) references produto (id) on delete restrict on update restrict
);

create table if not exists pagamento (
    pedido_id bigint not null,
    data_hora timestamp not null default now(),
    status enum('pendente', 'aprovado', 'rejeitado') not null,
    constraint pk_pagamento primary key (pedido_id),
    constraint fk_pagamento_pedido foreign key (pedido_id) references pedido (id) on delete restrict on update restrict
);

# 9.999.999,99

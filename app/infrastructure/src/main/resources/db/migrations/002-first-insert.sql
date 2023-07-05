use gerenciamento_pedidos_db;

insert into cliente (id, cpf, nome, email)
    values (1, '56378816425', 'Fulano', 'fulano@gmail.com');

insert into produto (id, nome, descricao, categoria, valor, tempo_preparo_minutos, disponivel, excluido)
    values (1, 'Hamburger Completo', 'Muito saboroso', 'LANCHE', 35.9, 20, true, false),
           (2, 'Batata Frita', 'Com sal', 'ACOMPANHAMENTO', 10.9, 10, true, false),
           (3, 'Suco de Uva', 'Com gelo', 'BEBIDA', 7.9, 0, true, false),
           (4, 'Pudim', 'Com calda de caramelo', 'SOBREMESA', 15.9, 5, true, false);

insert into imagem (id, produto_id, caminho)
    values (1, 1, 'hamburguer-completo.png'),
           (2, 2, 'batata-frita.png'),
           (3, 3, 'suco-uva.png'),
           (4, 4, 'pudim.png');

insert into pedido (id, data_hora, status, cliente_id, tempo_espera_minutos, numero)
    values (1, now(), 'PENDENTE', 1, 20, '0001'),
          (2, now(), 'EM_PREPARACAO', 1, 10, '0002'),
          (3, now(), 'PRONTO', 1, 5, '0003'),
          (4, now(), 'FINALIZADO', 1, 5, '0004');

insert into pedido_produto (id, pedido_id, produto_id, valor_pago, quantidade, comentario)
    values (1, 1, 1, 35.9, 1, 'Sem cebola'),
           (2, 1, 2, 10.9, 1, null),
           (3, 1, 3, 7.9, 1, null),
           (4, 1, 4, 15.9, 1, null),
           (5, 2, 2, 10.9, 1, null),
           (6, 3, 4, 15.9, 1, null),
           (7, 4, 4, 15.9, 1, null);

insert into pagamento (pedido_id, data_hora, status)
    values (1, now(), 'REPROVADO'),
           (2, now(), 'APROVADO'),
           (3, now(), 'APROVADO'),
           (4, now(), 'APROVADO');

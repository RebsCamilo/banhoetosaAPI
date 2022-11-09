
INSERT INTO servicos(descricao_servico, preco, data_cadastro) VALUES ('banho cachorro pequeno', 30.0, '2022-09-25 19:00:00')
INSERT INTO servicos(descricao_servico, preco, data_cadastro) VALUES ('tosa cachorro medio', 70.0, '2022-09-25 19:00:00')

INSERT INTO enderecos(rua, data_cadastro) VALUES ('rua paraiba', '2022-09-25 19:00:00')
INSERT INTO enderecos(rua, data_cadastro) VALUES ('rua recife', '2022-09-25 19:00:00')

INSERT INTO tutores(nome, email, telefone1, data_cadastro, endereco_id) VALUES ('maria', 'maria@mail', '0923940294', '2022-09-25 19:00:00', 1)
INSERT INTO tutores(nome, email, telefone1, data_cadastro, endereco_id) VALUES ('ana', 'ana@mail', '923840294', '2022-09-25 19:00:00', 2)

INSERT INTO pets(nome, raca, idade, data_cadastro, tutor_id) VALUES ('miina', 'pinscher', 9, '2022-09-25 19:00:00', 1)
INSERT INTO pets(nome, raca, idade, data_cadastro, tutor_id) VALUES ('khadija', 'poodle', 2, '2022-09-25 19:00:00', 2)

INSERT INTO pet_servico(id_pet, id_servico, data_cadastro) VALUES (1, 2, '2022-09-25 19:00:00')
INSERT INTO pet_servico(id_pet, id_servico, data_cadastro) VALUES (2, 1, '2022-09-25 19:00:00')


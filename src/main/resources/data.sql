
INSERT INTO servicos(descricao_servico, preco, data_cadastro) VALUES ('banho cachorro pequeno', 30.0, '2022-09-25 19:00:00')
INSERT INTO servicos(descricao_servico, preco, data_cadastro) VALUES ('tosa cachorro medio', 70.0, '2022-09-25 19:00:00')

INSERT INTO tutores(nome, telefone1, data_cadastro) VALUES ('maria', '0923940294', '2022-09-25 19:00:00')
INSERT INTO tutores(nome, telefone1, data_cadastro) VALUES ('ana', '923840294', '2022-09-25 19:00:00')

INSERT INTO enderecos(rua, data_cadastro, id_tutor) VALUES ('rua paraiba', '2022-09-25 19:00:00', 1)
INSERT INTO enderecos(rua, data_cadastro, id_tutor) VALUES ('rua recife', '2022-09-25 19:00:00', 2)

INSERT INTO pets(nome, raca, idade, data_cadastro, id_tutor) VALUES ('miina', 'pinscher', 9, '2022-09-25 19:00:00', 1)
INSERT INTO pets(nome, raca, idade, data_cadastro, id_tutor) VALUES ('khadija', 'poodle', 2, '2022-09-25 19:00:00', 2)

INSERT INTO pet_servico(id_pet, id_servico, data_cadastro) VALUES (1, 2, '2022-09-25 19:00:00')
INSERT INTO pet_servico(id_pet, id_servico, data_cadastro) VALUES (2, 1, '2022-09-25 19:00:00')



CREATE TABLE clientes(
   cdCliente serial      NOT NULL,
   nome      varchar(50) NOT NULL,
   cpf 	     varchar(50) NOT NULL,
   telefone  varchar(50) NOT NULL,
   CONSTRAINT pk_clientes
     PRIMARY KEY(cdCliente)
);

CREATE TABLE locais(
   cdLocal serial      NOT NULL,
   nome    varchar(50) NOT NULL,
   uf      char(2)     NOT NULL,
   cidade  varchar(50) NOT NULL,
   cep     varchar(50) NOT NULL,
   CONSTRAINT pk_locais
     PRIMARY KEY(cdLocal)
);

CREATE TABLE orcamentos(
   cdOrcamento  serial 	NOT NULL,
   data 	date  	NOT NULL,
   valor 	float 	NOT NULL,
   pago 	boolean NOT NULL,
   cdCliente 	int,
   cdLocal 	int,
   CONSTRAINT pk_orcamentos
     PRIMARY KEY(cdOrcamento),
   CONSTRAINT fk_orcamentos_clientes
     FOREIGN KEY(cdCliente)
     REFERENCES clientes(cdCliente),
   CONSTRAINT fk_orcamentos_locais
     FOREIGN KEY(cdLocal)
     REFERENCES locais(cdLocal)
);

CREATE TABLE produtos(
   cdProduto  serial      NOT NULL,
   nome       varchar(50) NOT NULL,
   preco      float       NOT NULL,
   quantidade int     	  NOT NULL,
   CONSTRAINT pk_produtos
     PRIMARY KEY(cdProduto)
);

CREATE TABLE itensdeorcamento(
   cdItemDeOrcamento serial NOT NULL,
   quantidade        int    NOT NULL,
   valor       	     float  NOT NULL,
   cdProduto   	     int,
   cdOrcamento 	     int,
   CONSTRAINT pk_itensdeorcamento
      PRIMARY KEY(cdItemDeOrcamento),
   CONSTRAINT fk_itensdeorcamento_produtos
      FOREIGN KEY(cdProduto)
      REFERENCES produtos(cdProduto),
   CONSTRAINT fk_itensdeorcamento_orcamentos
      FOREIGN KEY(cdOrcamento)
      REFERENCES orcamentos(cdOrcamento)
);


INSERT INTO clientes(nome, cpf, telefone) VALUES('Rachel','111.111.111-11','(11) 1111-1111');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Ross','222.222.222-22','(22) 2222-2222');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Monica','333.333.333-33','(33) 3333-3333');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Joey','444.444.444-44','(44) 4444-4444');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Phoebe','555.555.555-55','(55) 5555-5555');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Chandler','666.666.666-66','(66) 6666-6666');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Janice','777.777.777-77','(77) 7777-7777');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Ben','888.888.888-88','(88) 8888-8888');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Gunther','999.999.999-99','(99) 9999-9999');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Emily','111.999.999-44','(11) 1111-9999');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Richard','999.111.444-99','(22) 9999-2222');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Emma','999.444.111-99','(33) 3333-9999');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Peter','444.999.999-11','(44) 9999-4444');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Kathy','222.999.999-55','(55) 5555-9999');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Ursula','999.222.555-99','(66) 9999-6666');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Judy','999.555.222-99','(77) 7777-9999');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Roy','333.999.999-22','(88) 9999-8888');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Gary','999.333.999-99','(99) 1414-9999');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Sarah','555.999.333-99','(29) 9999-5858');
INSERT INTO clientes(nome, cpf, telefone) VALUES('Ellen','666.999.999-33','(41) 7676-9999');

INSERT INTO locais(nome, uf, cidade, cep) VALUES('Club Campestre','ES','Muqui','29480-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Angra dos Reis','RJ','Rio de Janeiro','23900-050');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Trindade','RJ','Rio de Janeiro','74475-090');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Fernando de Noronha','PE','Fernando de Noronha','53990-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Rio das Ostras','RJ','Rio de Janeiro','54475-090');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Ilhabela','SP','Ilhabela','11630-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Trancoso','BA','Porto Seguro','45818-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Hotel Miramar','SC','lages','88520-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Parque Ibirapuera','SP','Sao Paulo','04094-050');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Parque Jordao','AC','Rio Branco','70520-124');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Pousada das Estrelas','ES','Itauna','28541-027');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Pousada cafe da Mata','ES','Caparao','36978-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Pedra Azul','ES','Domingos Martins','29260-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Praia dos Milagres','AL','Sao Miguel','57940-000');
INSERT INTO locais(nome, uf, cidade, cep) VALUES('Hotel Weddings','RS','Gramado','95670-010');

INSERT INTO produtos(nome, preco, quantidade) VALUES('Buffet', '5000.00', '50');
INSERT INTO produtos(nome, preco, quantidade) VALUES('Coquetel', '3000.00', '50');
INSERT INTO produtos(nome, preco, quantidade) VALUES('Corporativo', '2550.00', '50');
INSERT INTO produtos(nome, preco, quantidade) VALUES('Lual', '1150.00', '50');

INSERT INTO produtos(nome, preco, quantidade) VALUES('Aperitivos', '1000.00', '50');
INSERT INTO produtos(nome, preco, quantidade) VALUES('Decoracoes', '500.00', '50');

INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/01/2021', '6000.00', true, '1', '1');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/02/2021', '3050.00', true, '2', '2');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/03/2021', '4000.00', true, '3', '3');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/04/2021', '1650.00', true, '4', '4');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/05/2021', '5500.00', true, '5', '5');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/06/2021', '3550.00', true, '6', '6');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/07/2021', '3500.00', true, '7', '7');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/08/2021', '2150.00', true, '8', '8');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/09/2021', '1650.00', true, '9', '9');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/10/2021', '3550.00', true, '10', '10');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/11/2021', '4000.00', true, '11', '11');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/12/2021', '6000.00', true, '12', '12');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/02/2021', '3500.00', true, '13', '13');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('03/02/2021', '3550.00', true, '14', '14');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/03/2021', '4500.00', true, '15', '4');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/08/2021', '2150.00', true, '16', '8');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('03/08/2021', '6500.00', true, '17', '8');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('04/08/2021', '3550.00', true, '18', '8');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/05/2021', '3550.00', true, '19', '10');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('03/05/2021', '4500.00', true, '15', '6');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/10/2021', '3550.00', true, '11', '3');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/11/2021', '1650.00', true, '7', '9');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('03/11/2021', '4050.00', true, '16', '2');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/01/2022', '6000.00', true, '8', '7');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/01/2022', '3500.00', true, '16', '14');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('03/01/2022', '4050.00', true, '17', '5');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('04/01/2022', '3550.00', true, '18', '11');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/02/2022', '4000.00', true, '19', '2');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/03/2022', '6000.00', true, '13', '3');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('01/04/2022', '3550.00', true, '10', '4');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('02/04/2022', '4000.00', true, '4', '6');
INSERT INTO orcamentos(data, valor, pago, cdCliente, cdLocal) VALUES('03/04/2022', '4050.00', true, '18', '12');


INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '5000.00', '1', '1');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00', '5', '1');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '2');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '2');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '3');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '3');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1150.00' , '4', '4');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '4');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '5000.00', '1', '5');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '5');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '6');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '6');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '7');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '7');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1150.00' , '4', '8');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '8');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1150.00' , '4', '9');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '9');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '10');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '10');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '11');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '11');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '5000.00', '1', '12');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00', '5', '12');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '13');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '13');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '14');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '14');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '15');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '15');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '15');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1150.00' , '4', '16');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '16');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '5000.00', '1', '17');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '17');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '17');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '18');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '18');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '19');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '19');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '20');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '20');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '20');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '21');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '21');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1150.00' , '4', '22');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '22');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '23');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '23');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '23');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '5000.00', '1', '24');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00', '5', '24');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '25');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '25');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '26');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '26');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '26');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '27');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '27');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '28');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '28');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '5000.00', '1', '29');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00', '5', '29');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '30');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '30');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '3000.00' , '2', '31');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '31');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '2550.00', '3', '32');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '1000.00' , '5', '32');
INSERT INTO itensdeorcamento(quantidade, valor, cdProduto, cdOrcamento) VALUES('1', '500.00' , '6', '32');
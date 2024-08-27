-- DDL: Criação tabela Cartao
CREATE TABLE Cartao (
    numero_do_pagamento INT AUTO_INCREMENT PRIMARY KEY,
    tipo_de_pessoa INT NOT NULL,
    cpf_cnpj VARCHAR(14) NOT NULL,
    pagamento DECIMAL(8,2) NOT NULL,
    nome_do_titular VARCHAR(50) NOT NULL,
    numero_do_cartao VARCHAR(19) NOT NULL,
    mes_vencimento INT NOT NULL,
    ano_vencimento INT NOT NULL,
    codigo_de_seguranca VARCHAR(4) NOT NULL
);

-- Criação de um índice
CREATE INDEX idx_cpf_cnpj ON Cartao(cpf_cnpj);

-- DML: Dados a inserir no banco. Descomentar em caso de testes
INSERT INTO Cartao (tipo_de_pessoa, cpf_cnpj, pagamento, nome_do_titular, numero_do_cartao, mes_vencimento, ano_vencimento, codigo_de_seguranca) VALUES
(2, '77777777777777', 3500.00, 'Empresa 202', '1234.5678.9101-112', 9, 2030, '567');
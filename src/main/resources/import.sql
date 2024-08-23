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
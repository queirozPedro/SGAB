CREATE TABLE Usuario (
    cpf VARCHAR(11) NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    email VARCHAR(75) NOT NULL
);

CREATE TABLE Telefone(
    cpf VARCHAR(11),
    numero VARCHAR(11) NOT NULL,
    FOREIGN KEY (cpf) REFERENCES Usuario(cpf)
);

CREATE TABLE Adm (
    idAdm Serial PRIMARY KEY,
    cpf VARCHAR(11),
    FOREIGN KEY (cpf) REFERENCES Usuario(cpf)
);

CREATE TABLE Cliente (
    idCliente Serial PRIMARY KEY,
    cpf VARCHAR(11),
    FOREIGN KEY (cpf) REFERENCES Usuario(cpf)
);

CREATE TABLE Livro (
    idLivro Serial PRIMARY KEY,
    Titulo VARCHAR(50) NOT NULL,
    Genero VARCHAR(20) NOT NULL,
    Autor VARCHAR(255) NOT NULL,
    DataPublicacao Date NOT NULL,
    Edicao VARCHAR(50) NOT NULL,
    Editora VARCHAR(50) NOT NULL,
    ISBN VARCHAR(13) NOT NULL, 
    quantLivros int NOT NULL,
    quantDisponiveis int NOT NULL
);

CREATE TABLE Emprestimo (
    cpf VARCHAR(11),
    idLivro Serial,
    DataEmprestimo Date NOT NULL,
    DataPrevista Date NOT NULL,
    DataDevolucao Date NOT NULL,
    FOREIGN KEY (cpf) REFERENCES Usuario(cpf),
    FOREIGN KEY (idLivro) REFERENCES Livro(idLivro)
);

-- CREATE TABLE Penalizacao(
--     idCliente Serial,
--     motivoPenalizacao VARCHAR(100) NOT NULL,
--     FimPenalizacao Date NOT NULL,
--     FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
-- )

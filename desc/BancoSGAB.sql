CREATE TABLE Usuario (
    cpf VARCHAR(11) NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(11) NOT NULL
);

CREATE TABLE Adm (
    idAdm Serial PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    FOREIGN KEY (cpf) REFERENCES Usuario(cpf)
);

CREATE TABLE Livro (
    idLivro Serial PRIMARY KEY,
    Titulo VARCHAR(255) NOT NULL,
    Genero VARCHAR(255) NOT NULL,
    Autor VARCHAR(255) NOT NULL,
    DataPublicacao Date NOT NULL,
    Edicao VARCHAR(255) NOT NULL,
    Editora VARCHAR(255) NOT NULL,
    ISBN VARCHAR(13) NOT NULL, 
    quantLivros int NOT NULL,
    quantEmprestados int DEFAULT 0
);

CREATE TABLE Emprestimo (
    cpf VARCHAR(11) NOT NULL,
    idLivro int NOT NULL,
    DataEmprestimo Date NOT NULL,
    DataPrevista Date NOT NULL,
    DataDevolucao Date,
    FOREIGN KEY(idLivro) REFERENCES Livro(idLivro),
    FOREIGN KEY(cpf) REFERENCES Usuario(cpf)
);
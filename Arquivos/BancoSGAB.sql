CREATE TABLE Usuario (
    idusuario Serial PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    email VARCHAR(75) NOT NULL
);

CREATE TABLE Adm (
    idusuario Serial PRIMARY KEY,
    FOREIGN KEY (idusuario) REFERENCES Usuario(idusuario)
);

CREATE TABLE Cliente (
    idusuario Serial PRIMARY KEY,
    livrosEmprestados VARCHAR(3) NOT NULL, 
    FOREIGN KEY (idusuario) REFERENCES Usuario(idusuario)
);

CREATE TABLE Livro (
    idlivro Serial PRIMARY KEY,
    Titulo VARCHAR(50) NOT NULL,
    Genero VARCHAR(20) NOT NULL,
    Autor VARCHAR(255) NOT NULL,
    DataPublicacao VARCHAR(10) NOT NULL,
    Edicao VARCHAR(50) NOT NULL,
    Editora VARCHAR(50) NOT NULL,
    ISBN VARCHAR(13) NOT NULL, 
    LivroAcervo Bool NOT NULL,
    LivroDisponivel Bool NOT NULL
);

CREATE TABLE Emprestimo (
    idusuario Serial,
    idlivro Serial,
    DataEmprestimo VARCHAR(10) NOT NULL,
    FOREIGN KEY (idusuario) REFERENCES Usuario(idusuario),
    FOREIGN KEY (idlivro) REFERENCES Livro(idlivro)
);

-- Drop table adm, cliente, endereco, livro, usuario;
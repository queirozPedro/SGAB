import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Livro {

    private int idLivro;
    private String titulo;
    private String genero;
    private String autor;
    private java.sql.Date dataPublicacao; // Date
    private String edicao;
    private String editora;
    private String isbn;
    private int quantLivros;
    private int quantEmprestados;

    public Livro(int idLivro, String titulo, String genero, String autor, String dataPublicacao, String edicao,
            String editora, String isbn,int quantLivros) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        setDataPublicacao(dataPublicacao);
        this.edicao = edicao;
        this.editora = editora;
        this.isbn = isbn;
        this.quantLivros = quantLivros;
    }

    public Livro(int idLivro, String titulo, String genero, String autor, Date dataPublicacao, String edicao,
            String editora, String isbn, int quantLivros, int quantEmprestados) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editora = editora;
        this.isbn = isbn;
        this.quantLivros = quantLivros;
        this.quantEmprestados = quantEmprestados;
    }

    /**
     * Método que cadastra uma instância de Livro no acervo da biblioteca.
     */
    public void cadastrarLivro() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            String query = "INSERT Into Livro (titulo, genero, autor, dataPublicacao, edicao, editora, isbn, quantLivros) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
            state = connection.prepareStatement(query);
            state.setString(1, titulo);
            state.setString(2, genero);
            state.setString(3, autor);
            state.setDate(4, dataPublicacao);
            state.setString(5, edicao);
            state.setString(6, editora);
            state.setString(7, isbn);
            state.setInt(8, quantLivros);
            state.executeUpdate();
            System.out.println(" Livro Cadastrado! ");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo de busca por id
     * 
     * @param id
     * @return Livro
     */
    public static Livro buscaLivroId(int id) { // Usado pelos administradores, para alteração, Arraylistagem e remoção
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {// se a conexão funcionar
            String query = "Select * from livro where idLivro = ?"; // cria a query, que é a pesquisa que iremos fazer
            state = connection.prepareStatement(query); // cria o state, aquele que executa a pesquisa
            state.setInt(1, id); // preenche os ? com as informações desejadas
            result = state.executeQuery(); // recebe a tabela com as respostas da pesquisa
            if (result.next()) {// enquanto houverem respostas, imprima-as
                return new Livro(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
                        result.getDate(5), result.getString(6), result.getString(7), result.getString(8),
                        result.getInt(9), result.getInt(10));
            }
        } catch (Exception e) {// se der erro, mostre qual foi
            System.out.println(e);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Livro> pesquisarLivro(String pesquisa) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null; // cria o state, aquele que executa a pesquisa
        ResultSet result = null;
        ArrayList<Livro> livrosEncontrados = new ArrayList<>();

        try {
            // Seleciona tudo (*) na tabela Livro onde o a pesquisa é igual a recebida
            String query = "SELECT * FROM Livro WHERE titulo like ? OR genero like ? OR autor like ?"; // cria a query,
                                                                                                       // que é a
                                                                                                       // pesquisa que
                                                                                                       // iremos fazer
            state = connection.prepareStatement(query);
            state.setString(1, "%" + pesquisa + "%");
            state.setString(2, "%" + pesquisa + "%");
            state.setString(3, "%" + pesquisa + "%");
            result = state.executeQuery();

            while (result.next()) {
                // Para cada registro encontrado, cria um objeto Livro e adiciona na Arraylist
                Livro livro = new Livro(
                        result.getInt("idLivro"),
                        result.getString("Titulo"),
                        result.getString("Genero"),
                        result.getString("Autor"),
                        result.getDate("DataPublicacao"),
                        result.getString("Edicao"),
                        result.getString("Editora"),
                        result.getString("ISBN"),
                        result.getInt("quantLivros"),
                        result.getInt("quantEmprestados"));

                livrosEncontrados.add(livro);
            }
            return livrosEncontrados;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Livro> listarLivros() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null; // cria o state, aquele que executa a pesquisa
        ResultSet result = null;
        ArrayList<Livro> listaLivro = new ArrayList<>();

        try {
            // Seleciona tudo (*) na tabela Livro onde o título é igual ao recebido
            String query = "SELECT * FROM Livro"; // cria a query, que é a pesquisa que iremos fazer
            state = connection.prepareStatement(query);
            result = state.executeQuery();

            while (result.next()) {
                // Para cada registro encontrado, cria um objeto Livro e adiciona na Arraylist
                Livro livro = new Livro(
                        result.getInt("idLivro"),
                        result.getString("Titulo"),
                        result.getString("Genero"),
                        result.getString("Autor"),
                        result.getDate("DataPublicacao"),
                        result.getString("Edicao"),
                        result.getString("Editora"),
                        result.getString("ISBN"),
                        result.getInt("quantLivros"),
                        result.getInt("quantEmprestados"));
                listaLivro.add(livro);
            }
            return listaLivro;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Método que excluir um Livro do acervo com base em seu IP
     * 
     * @param idLivro
     */
    public static void excluirLivro(int idLivro) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            Emprestimo.finalizaEmprestimos(idLivro);
            String query = "Delete From livro where idLivro = ?";
            state = connection.prepareStatement(query);
            state.setInt(1, idLivro);
            state.executeUpdate();
            System.out.println(" Livro Excluido!");

        } catch (Exception e) {// se der erro, mostre qual foi
            System.out.println(e);
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void editarLivro(String campo, String valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        try {
            String query = "UPDATE Livro SET " + campo + " = ? WHERE idLivro = ?";
            state = connection.prepareStatement(query);
            state.setString(1, valor);
            state.setInt(2, getIdLivro());
            state.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void editarLivro(String campo, java.sql.Date valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

            try {
                String query = "UPDATE Livro SET DataPublicacao = ? WHERE idLivro = ?";
                state = connection.prepareStatement(query);
                state.setDate(1, valor);
                state.setInt(2, getIdLivro());
                state.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (state != null) {
                        state.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    public void editarLivro(String campo, int valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            String query = "UPDATE Livro SET " + campo + " = ? WHERE idLivro = ?";
            state = connection.prepareStatement(query);
            state.setInt(1, valor);
            state.setInt(2, getIdLivro());
            state.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "\n Livro: " + titulo + "\n Id: " + idLivro + "\n Autor: " + autor + "\n Gênero: " + genero +
                "\n Data da Publicação: " + dataPublicacao + "\n Edição: " + edicao + "\n Editora: " +
                editora + "\n ISBN: " + isbn + "\n Quantidade de Livros: " + quantLivros + 
                "\n Quantidade Emprestada: "+ quantEmprestados;
    }

    /**
     * Recebe data como String e converte pra Date
     * 
     * @param dataPublicacao
     */
    public void setDataPublicacao(String dataPublicacao) {
        try {
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = formatoData.parse(dataPublicacao);
            this.dataPublicacao = new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.err.println("Erro ao converter a data");
        }
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public java.sql.Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(java.sql.Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantLivros() {
        return quantLivros;
    }

    public void setQuantLivros(int quantLivros) {
        this.quantLivros = quantLivros;
    }

    public int getQuantEmprestados() {
        return quantEmprestados;
    }

    public void setQuantEmprestados(int quantEmprestados) {
        this.quantEmprestados = quantEmprestados;
    }

}

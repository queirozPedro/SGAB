import java.sql.*;
import java.util.Scanner;

public class Livro {
    // Table livro
    private int idLivro;
    private String titulo;
    private String genero;
    private String autor;
    private String dataPublicacao;
    private String edicao;
    private String editora;
    private String isbn;
    private boolean livroAcervo;
    private boolean livroDisponivel;

    // Table empretimo
    //private int idUsuario;
    // int idLivro;
    //private String dataEmprestimo;

    public Livro(String titulo, String genero, String autor, String dataPublicacao, String edicao, String editora,
            String isbn, boolean livroAcervo, boolean livroDisponivel, int idUsuario, String dataEmprestimo) {
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editora = editora;
        this.isbn = isbn;
        this.livroAcervo = livroAcervo;
        this.livroDisponivel = livroDisponivel;
        // this.idUsuario = idUsuario;
        // this.dataEmprestimo = dataEmprestimo;
    }

    public Livro(int idLivro, String titulo, String genero, String autor, String dataPublicacao, String edicao,
            String editora,
            String isbn, boolean livroAcervo, boolean livroDisponivel) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editora = editora;
        this.isbn = isbn;
        this.livroAcervo = livroAcervo;
        this.livroDisponivel = livroDisponivel;
    }

    /**
     * Metodo de busca por id, exclusivo para adms
     * 
     * @param id
     * @return Livro
     */
    public static Livro BuscaLivroId(int id) {
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()) {
            String query = "Select * from livro where idLivro = ?";
            PreparedStatement state = connection.prepareStatement(query);
            state.setInt(1, id);
            ResultSet result = state.executeQuery();
            while (result.next()) {
                return new Livro(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
                        result.getString(5), result.getString(6), result.getString(7), result.getString(8),
                        result.getBoolean(9), result.getBoolean(10));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static Livro BuscaLivro(Scanner sc) {
        String tipo, busca;
        System.out.println("Por que parametro deseja buscar o livro:\n1-Titulo;\n2-Autor;\n3-Genero;\n0-Voltar;");
        int op = sc.nextInt();
        sc.nextLine();
        switch (op) {
            case 1:
                tipo = "titulo";
                System.out.print("Titulo: ");
                busca = sc.nextLine();
                break;
            case 2:
                tipo = "autor";
                System.out.print("Autor: ");
                busca = sc.nextLine();
                break;
            case 3:
                tipo = "genero";
                System.out.print("Genero: ");
                busca = sc.nextLine();
                break;
            case 0: // sair
            default:
                return null;
        }

        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()) {

            String query = "Select * from livro where "+ tipo +" = ?"; // Busca no banco de dados
            PreparedStatement state = connection.prepareStatement(query);
            state.setString(1, busca);
            ResultSet result = state.executeQuery();// Resultados da execução da query.
            
            // Enquanto houverem linhas de resultados da busca para serem impressas, retorna-os.
            while (result.next()) { 
                return new Livro(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getBoolean(9), result.getBoolean(10));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void inserirLivro() {
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Livro (titulo, genero, autor, dataPublicacao, edicao, editora, isbn, livroAcervo, livroDisponivel) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement state = connection.prepareStatement(query);
            state.setString(1, titulo);
            state.setString(2, genero);
            state.setString(3, autor);
            state.setString(4, dataPublicacao);
            state.setString(5, edicao);
            state.setString(6, editora);
            state.setString(7, isbn);
            state.setBoolean(8, livroAcervo);
            state.setBoolean(9, livroDisponivel);
            state.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void excluirLivro() {

    }

    @Override
    public String toString() {
        return "Livro [idLivro=" + idLivro + ", titulo=" + titulo + ", genero=" + genero + ", autor=" + autor
                + ", dataPublicacao=" + dataPublicacao + ", edicao=" + edicao + ", editora=" + editora + ", isbn="
                + isbn + ", livroAcervo=" + livroAcervo + ", livroDisponivel=" + livroDisponivel;
    }

}

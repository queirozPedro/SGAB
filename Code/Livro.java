import java.sql.*;

public class Livro {
    // Table livro
    private int idLivro; 
    private String titulo;
    private String genero;
    private String autor;
    private String dataPublicacao;
    private String edicao;
    private String editor;
    private String isbn;

    // Table statuslivro
    //      int idLivro;
    private boolean livroAcervo;
    private boolean livroDisponivel;

    // Table empretimo
    private int idUsuario;
    //      int idLivro;
    private String dataEmprestimo;
    
    public Livro(int idLivro, String titulo, String genero, String autor, String dataPublicacao, String edicao,
            String editor, String isbn, boolean livroAcervo, boolean livroDisponivel, int idUsuario,
            String dataEmprestimo) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editor = editor;
        this.isbn = isbn;
        this.livroAcervo = livroAcervo;
        this.livroDisponivel = livroDisponivel;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = dataEmprestimo;
    }

    public Livro(int idLivro, String titulo, String genero, String autor, String dataPublicacao, String edicao, String editor, String isbn) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editor = editor;
        this.isbn = isbn;
    }

    public Livro(String titulo, String genero, String autor, String dataPublicacao, String edicao, String editor,
            String isbn, boolean livroAcervo, boolean livroDisponivel, int idUsuario, String dataEmprestimo) {
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editor = editor;
        this.isbn = isbn;
        this.livroAcervo = livroAcervo;
        this.livroDisponivel = livroDisponivel;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = dataEmprestimo;
    }

    public static Livro BuscaLivro(int id){        
        try(Connection connection = PostgreSQLConnection.getInstance().getConnection()) {
            if(connection != null){
                String query = "Select * from livro where idLivro = ?";
                PreparedStatement state = connection.prepareStatement(query);
                state.setInt(1, id);
                ResultSet result = state.executeQuery();
                while (result.next()) {
                    return new Livro(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8));
                }
            }
            else
                System.out.println("ERROOO!!");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Livro [idLivro=" + idLivro + ", titulo=" + titulo + ", genero=" + genero + ", autor=" + autor
                + ", dataPublicacao=" + dataPublicacao + ", edicao=" + edicao + ", editor=" + editor + ", isbn="
                + isbn + ", livroAcervo=" + livroAcervo + ", livroDisponivel=" + livroDisponivel + ", idUsuario="
                + idUsuario + ", dataEmprestimo=" + dataEmprestimo + "]";
    }        

}

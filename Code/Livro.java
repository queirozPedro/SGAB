import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Livro {

    private int idLivro;
    private String titulo;
    private String genero;
    private String autor;
    private String dataPublicacao; // Date
    private String edicao;
    private String editora;
    private String isbn;
    private int quantLivros;
    private int quantDisponivel;
    
    public Livro(String titulo, String genero, String autor, String dataPublicacao, String edicao, String editora,
            String isbn, int quantLivros, int quantDisponivel) {
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editora = editora;
        this.isbn = isbn;
        this.quantLivros = quantLivros;
        this.quantDisponivel = quantDisponivel;
    }

    public Livro(int idLivro, String titulo, String genero, String autor, String dataPublicacao, String edicao,
            String editora, String isbn, int quantLivros, int quantDisponivel) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.genero = genero;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.edicao = edicao;
        this.editora = editora;
        this.isbn = isbn;
        this.quantLivros = quantLivros;
        this.quantDisponivel = quantDisponivel;
    }

    /**
     * Metodo de busca por id
     * @param id
     * @return Livro
     */
    public static Livro BuscaLivroId(int id) { //Usado pelos administradores, para alteração, listagem e remoção
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()) {//se a conexão funcionar
            String query = "Select * from livro where idLivro = ?"; //cria a query, que é a pesquisa que iremos fazer
            PreparedStatement state = connection.prepareStatement(query); //cria o state, aquele que executa a pesquisa
            state.setInt(1, id); //preenche os ? com as informações desejadas
            ResultSet result = state.executeQuery(); //recebe a tabela com as respostas da pesquisa
            while (result.next()) {// enquanto houverem respostas, imprima-as
                // Corrigir a questão da Data
                //return new Livro(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getDate(5), result.getString(6), result.getString(7), result.getString(8), result.getBoolean(9), result.getBoolean(10));
            }
        } catch (Exception e) {//se der erro, mostre qual foi
            System.out.println(e);
        }
        return null;
    }


    public static Livro BuscaLivro(String tipo, String busca) {

        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()) {

            String query = "Select * from livro where "+ tipo +" like ? "; // Busca no banco de dados, neste caso, já que o ? é substituido por
            PreparedStatement state = connection.prepareStatement(query); // 'algo', usamos a varivavel diretamente para a pesquisa ficar correta
            state.setString(1, "%" + busca + "%");
            ResultSet result = state.executeQuery();// Resultados da execução da query.
            
            // Enquanto houverem linhas de resultados da busca para serem impressas, retorna-os.
            while (result.next()) {  
                //return new Livro(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getDate(5), result.getString(6), result.getString(7), result.getString(8), result.getBoolean(9), result.getBoolean(10));    
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Medoto que insere uma instância de Livro no banco de dados
     */
    public void inserirLivro() {
                
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date utilDate = dateFormat.parse(data);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            System.out.println("Você digitou a data: " + sqlDate);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Certifique-se de usar o formato (dd/MM/yyyy).");
        }

        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()) {
            String query = "INSERT INTO Livro (titulo, genero, autor, dataPublicacao, edicao, editora, isbn, livroAcervo, livroDisponivel) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement state = connection.prepareStatement(query);
            state.setString(1, titulo);
            state.setString(2, genero);
            state.setString(3, autor);
            state.setDate(4, sqlDate);
            state.setString(5, edicao);
            state.setString(6, editora);
            state.setString(7, isbn);
            state.setInt(8, quantLivros);
            state.setInt(9, quantDisponivel);
            state.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void excluirLivro(int idLivro) {
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()) {
            String query = "Delete From livro where idLivro = ?"; 
            PreparedStatement state = connection.prepareStatement(query); 
            state.setInt(1, idLivro);
            state.executeQuery(); 
        } catch (Exception e) {//se der erro, mostre qual foi
            System.out.println(e);
        }
    }

    @Override
    public String toString() {
        return "Livro [idLivro=" + idLivro + ", titulo=" + titulo + ", genero=" + genero + ", autor=" + autor
                + ", dataPublicacao=" + dataPublicacao + ", edicao=" + edicao + ", editora=" + editora + ", isbn="
                + isbn + ", quantLivros=" + quantLivros + ", quantDisponivel=" + quantDisponivel + "]";
    }



}

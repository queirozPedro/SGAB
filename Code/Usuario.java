import java.sql.Connection;
import java.sql.PreparedStatement;

public class Usuario {

    private String cpf;
    private String nome;
    private String telefone;
    private String senha;
    private String email;

    public Usuario( String cpf, String nome, String telefone, String senha, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.senha = senha;
        this.email = email;
    }

    public void insereUsuario(){
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
            String query = "INSERT Into usuario (cpf, nome, telefone, senha, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.setString(2, nome);
            state.setString(3, telefone);
            state.setString(4, senha);
            state.setString(5, email);
            state.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // public void buscaUsuarioId(){
    //     try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
    //         String 
    //         PreparedStatement state = connection.prepareStatement(query);
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //     }
    // }

}

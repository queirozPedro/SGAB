import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Usuario {

    private String cpf;
    private String nome;
    private String senha;
    private String email;
    private ArrayList<String> telefone;

    public Usuario(){};
    public Usuario(String cpf, String nome, String senha, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    /**
     * Uma metodo que insere uma instância de Usuario no banco de dados
     */
    public void insereUsuario(){
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
            String query = "INSERT Into usuario (cpf, nome, telefone, senha, email) VALUES (?, ?, ?, ?, ?)"; // telefone vai ser armazenado em outra tabela do banco
            PreparedStatement state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.setString(2, nome);
            state.setString(3, telefone); // Ele vai pra outra tabela
            state.setString(4, senha);
            state.setString(5, email);
            state.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Recebe um cpf de um usuario e retorna uma instância dele
     * @param idUsuario
     * @return Usuario
     */
    public static Usuario buscaUsuarioId(String cpf){
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
            String query = "SELECT * From usuario where idusuario = ?"; // trocar idusuario para cpf
            PreparedStatement state = connection.prepareStatement(query);
            state.setInt(1, idUsuario); // trocar pra cpf
            ResultSet result = state.executeQuery();

            while (result.next()) { 
                // Esses get são na ordem que os dados estão no banco, consultar o SQL disponivel nos arquivos
                return new Usuario(result.getInt(1),result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getString(6));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Remove um usuário do banco com base em seu cpf
     * @param cpf
     */
    public static void removeUsuario(String cpf){
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
            String query = "DELETE From usuario where id = ?"; // trocar aqui
            PreparedStatement state = connection.prepareStatement(query);
            state.setInt(1, idUsuario); // trocar pra cpf
            state.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario [cpf=" + cpf + ", nome=" + nome + ", senha=" + senha + ", email=" + email + "]";
    }

    
}

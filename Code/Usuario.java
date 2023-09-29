import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario {

    private int idUsuario;
    private String cpf;
    private String nome;
    private String telefone;
    private String senha;
    private String email;

    public Usuario(){};

    public Usuario(int idUsuario, String cpf, String nome, String telefone, String senha, String email) {
        this.idUsuario = idUsuario;
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.senha = senha;
        this.email = email;
    }

    public Usuario(String cpf, String nome, String telefone, String senha, String email) {
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

    public static Usuario buscaUsuarioId(int idUsuario){
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
            String query = "SELECT * From usuario where idusuario = ?";
            PreparedStatement state = connection.prepareStatement(query);
            state.setInt(1, idUsuario);
            ResultSet result = state.executeQuery();

            while (result.next()) {
                return new Usuario(result.getInt(1),result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getString(6));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void removeUsuario(int idUsuario){
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
            String query = "DELETE From usuario where id = ?";
            PreparedStatement state = connection.prepareStatement(query);
            state.setInt(1, idUsuario);
            state.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", cpf=" + cpf + ", nome=" + nome + ", telefone=" + telefone
                + ", senha=" + senha + ", email=" + email + "]";
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    
}

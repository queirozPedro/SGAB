import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsuarioCliente extends Usuario {

    public UsuarioCliente(int idUsuario, String cpf, String nome, String telefone, String senha, String email) {
        super(idUsuario, cpf, nome, telefone, senha, email);
    }

    public void insereCliente(Usuario usuario){
        usuario.insereUsuario();
        try (Connection connection = PostgreSQLConnection.getInstance().getConnection()){
            String query = "INSERT INTO Cliente";   
            PreparedStatement state = connection.prepareStatement(query);
            state.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}

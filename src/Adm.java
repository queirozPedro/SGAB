import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Adm extends Usuario {
    private int idAdm;

    public Adm(int idAdm, Usuario usuario) {
        super(usuario.getCpf(), usuario.getNome(), usuario.getSenha(), usuario.getEmail(), usuario.getTelefone());
        this.idAdm = idAdm;
    }

    public Adm(Usuario usuario) {
        super(usuario.getCpf(), usuario.getNome(), usuario.getSenha(), usuario.getEmail(), usuario.getTelefone());
    }

    public Adm(String cpf, String nome, String senha, String email, String telefone) {
        super(cpf, nome, senha, email, telefone);
    }

    /**
     * Método criarAdm: O método recebe as informações de um usuário, e o cadastra
     * na tabela adm, gerando
     * um idAdm que será usado para verificação de métodos de emprestimo e dentre
     * outros.
     */
    public void criarAdm() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        if (buscaUsuario(getCpf()) == null) {
            try {
                super.criarConta();
                String query = "INSERT INTO Adm (cpf) VALUES (?)";
                state = connection.prepareStatement(query);
                state.setString(1, super.getCpf());
                state.executeUpdate();
                System.out.println(" Adm Cadastrado! ");
            } catch (Exception e) {
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
        } else if (buscaUsuario(getCpf()) != null && buscaAdm(getCpf()) == null) {
            try {
                String query = "INSERT INTO Adm (cpf) VALUES (?)";
                state = connection.prepareStatement(query);
                state.setString(1, super.getCpf());
                state.executeUpdate();
                System.out.println(" Adm Criado! ");
            } catch (Exception e) {
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
        } else {
            System.out.println(" Adm já cadastrado!");
        }
    }

    /**
     * Método buscaAdm: Responsável por buscar um adm na tabela de acordo com seu
     * cpf.
     * 
     * @param cpf
     * @return Adm
     */
    public static Adm buscaAdm(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {
            // Busca por pelo cpf
            String query = "SELECT idAdm from Adm where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            result = state.executeQuery();

            if (result.next()) {
                return new Adm(result.getInt(1), Usuario.buscaUsuario(cpf));
            }
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método excluirAdm: Responsável por remover um adm do sistema bom base em seu
     * cpf.
     * 
     * @param cpf
     */
    public static void excluirAdm(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            String query = "Delete From Adm where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.executeUpdate();
            Usuario.excluirConta(cpf);

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

    public static Adm loginAdm(String email, String senha) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {
            if (loginUsuario(email, senha) != null) {
                Usuario adm = loginUsuario(email, senha);
                String query = "SELECT idAdm From adm where cpf = ?";
                state = connection.prepareStatement(query);
                state.setString(1, adm.getCpf());
                result = state.executeQuery();

                if (result.next()) {
                    return new Adm(result.getInt(1), adm);
                }
            }
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
     * Método listaAdm: Método que lista todos os Administradores do Acervo.
     * 
     * @return ArrayList<Adm>
     */
    public static ArrayList<Adm> listaAdm() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        ArrayList<Adm> Admins = new ArrayList<Adm>();

        try {

            String query = "SELECT * From adm";
            state = connection.prepareStatement(query);
            result = state.executeQuery();

            while (result.next()) {
                Adm admin = new Adm(result.getInt(1), buscaUsuario(result.getString(2)));
                Admins.add(admin);
            }
            return Admins;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdAdm() {
        return idAdm;
    }

    public void setIdAdm(int idAdm) {
        this.idAdm = idAdm;
    }

    @Override
    public String toString() {
        return " Id do Adm: " + idAdm + "\n" + super.toString();

    }

    public String mostrarPerfil() {
        return "\n Id do Adm: " + idAdm + "\n" + super.mostrarPerfil();
    }
}

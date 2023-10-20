import java.sql.*;
import java.util.ArrayList;

public class Usuario {

    private String cpf;
    private String nome;
    private String senha;
    private String email;
    private String telefone;

    /**
     * Construtor da classe Usuario, ele recebe os dados e usa os set's pra checar
     * se estão preechidos ou vazios.
     * 
     * @param cpf
     * @param nome
     * @param senha
     * @param email
     * @param telefone
     */
    public Usuario(String cpf, String nome, String senha, String email, String telefone) {
        setCpf(cpf);
        setNome(nome);
        setSenha(senha);
        setEmail(email);
        setTelefone(telefone);
    }

    /**
     * Método criarConta: Responsável por criar uma nova conta de Usuário no banco
     * de dados sistema. Obs.: Recebe uma instancia da Classe Usuario com dados
     * já formatados corretamente. A falta dessa formatação pode causar erros.
     */
    public void criarConta() {
        // Cria uma conexão com o banco de dados usando a classe PostegreSQLConnection
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        if (buscaUsuario(getCpf()) == null) {
            try {

                // Insere o usuário na tabela Usuario
                String query = "INSERT Into usuario (cpf, nome, senha, email, telefone) VALUES (?, ?, ?, ?, ?)";
                state = connection.prepareStatement(query);
                state.setString(1, cpf);
                state.setString(2, nome);
                state.setString(3, senha);
                state.setString(4, email);
                state.setString(5, telefone);
                state.executeUpdate();

                // Insere os telefones dele na tabela Telefone com base em seu cpf
                System.out.println(" Usuário Cadastrado!");

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
        } else {
            System.out.println(" ERRO! Cpf já Cadastrado!");
        }
    }

    /**
     * Método buscaUsuario: Responsável por retornar um Usuário do banco de dados de
     * acordo com seu cpf. Obs.: O Método não trata dados, portanto o cpf deve ser
     * recebido no formato correto. Retorna null caso não encontre.
     * 
     * @param cpf
     * @return Usuario
     */
    public static Usuario buscaUsuario(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {
            // Seleciona tudo (*) na tabela Usuario onde o cpf foi o igual ao recebido
            String query = "SELECT * From usuario where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            result = state.executeQuery();

            // Retorna o usuário
            if (result.next()) {
                return new Usuario(result.getString(1), result.getString(2), result.getString(3), result.getString(4),
                        result.getString(5));
            }

        } catch (SQLException e) {
            // Trate a exceção ou registre o erro, não apenas imprima a pilha de exceção
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
     * Método editarUsuario: Edita o usuário no banco de dados. Obs.: Deve receber
     * nulo todos os valores que NÃO serão editados. Não faz tratamento de dados,
     * deve receber dados já formatados.
     * 
     * @param nome
     * @param senha
     * @param email
     * @param telefone
     */
    public void editarUsuario(String campo, String valor) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        /*
         * Primeiro checa se algum desses dados foi recebido e aplica valores
         * locais aos que forem null.
         */

        try {

            // Atualiza nome, senha e email na tabela usuario na posição do cpf usado.
            String query = "UPDATE Usuario SET "+ campo +" = ? WHERE cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, valor);
            state.setString(2, getCpf());
            state.executeUpdate();
            state.close();

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

    /**
     * Método excluirConta: Método que recebe um cpf de um usário e remove ele do
     * banco de dados do sistema. Obs.: O Método não trata dados, portanto o cpf
     * deve ser recebido no formato correto.
     * 
     * @param cpf
     */
    public static void excluirConta(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            if(Emprestimo.listarPorCpf(cpf) != null){
                ArrayList<Emprestimo> emprestimos = Emprestimo.listarPorCpf(cpf);
                for(int i = 0; i < emprestimos.size(); i++){
                    Livro livro = Livro.buscaLivroId(emprestimos.get(i).getIdLivro());
                    livro.editarLivro("quantEmprestados", livro.getQuantEmprestados() - 1);
                }
                Emprestimo.finalizaEmprestimos(cpf);
            }
            // Remove o usuário da tabela Usuario
            String query = "DELETE From usuario where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.executeUpdate();
            System.out.println(" Usuário Excluido! ");

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

    /**
     * Método loginUsuario: Método que recebe um email e uma senha e retorna o
     * usuário que seja correspondente aos dois. Ele será usado juntamente com o
     * buscaUsuario para efetuar o login. Obs.: O Método não trata dados, portanto
     * o email e senha devem ser recebidos no formato correto.
     * 
     * @param email
     * @param senha
     * @return Usuario
     */
    public static Usuario loginUsuario(String email, String senha) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {

            // Seleciona o cpf do usuario na tabela que tenha os mesmos email e senha
            String query = "SELECT cpf From usuario where email = ? AND senha = ?";
            state = connection.prepareStatement(query);
            state.setString(1, email);
            state.setString(2, senha);
            result = state.executeQuery();
            if (result.next()) {
                return buscaUsuario(result.getString(1));
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
     * Método listaUsuario: Método que acessa o banco de dados e retorna um
     * ArrayList de Usuario.
     * 
     * @return ArrayList<Usuario>
     */
    public static ArrayList<Usuario> listaUsuario() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        // ArrayList do tipo Usuario, que será retornado com todos os usuarios do banco.
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        try {

            // Seleciona todos os usuarios.
            String query = "SELECT * From Usuario";
            state = connection.prepareStatement(query);
            result = state.executeQuery();

            while (result.next()) {

                // Cria um objeto para cada um e coloca no ArrayList.
                Usuario user = new Usuario(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5));
                usuarios.add(user);
            }
            return usuarios;

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

    /*
     * A partir daqui temos declarações de seters e geters. os Seters vão funcionar
     * pra formatar os dados,
     * para que erros não ocorram no momento de mexer com o Banco de Dados. Os
     * Geters estão como auxiliares,
     * sendo usados por métodos internos e externos (da Classe Herdeira).
     */

    public void setCpf(String cpf) {
        if (cpf != null)
            this.cpf = cpf;
        else
            System.out.println("O valor de cpf não pode ser null");
    }

    public void setNome(String nome) {
        if (nome != null)
            this.nome = nome;
        else
            System.out.println("O valor de nome não pode ser null");
    }

    public void setEmail(String email) {
        if (email != null)
            this.email = email;
        else
            System.out.println("O valor de email não pode ser null");
    }

    public void setSenha(String senha) {
        if (senha != null)
            this.senha = senha;
        else
            System.out.println("O valor de senha não pode ser null");
    }

    public void setTelefone(String telefone) {
        if (telefone != null)
            this.telefone = telefone;
        else
            System.out.println("O valor de telefone não pode ser null");
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return " Nome: " + nome + "\n Cpf: " + cpf + "\n Email: " + email + "\n Telefone: " + telefone;
    }

    public String mostrarPerfil() {
        return " Nome: " + nome + "\n Cpf: " + cpf + "\n Email: " + email + "\n Senha: " + senha + "\n Telefone: "
                + telefone;
    }
}

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Emprestimo {

    private String cpf;
    private int idLivro;
    private java.sql.Date dataEmprestimo;
    private java.sql.Date dataPrevista;
    private java.sql.Date dataDevolucao;

    public Emprestimo(String cpf, int idLivro, String dataEmprestimo, String dataPrevista, String dataDevolucao) {
        this.cpf = cpf;
        this.idLivro = idLivro;
        setDataEmprestimo(dataEmprestimo);
        setDataPrevista(dataPrevista);
        setDataDevolucao(dataDevolucao);
    }

    public Emprestimo(String cpf, int idLivro, java.sql.Date dataEmprestimo, java.sql.Date dataPrevista,
            java.sql.Date dataDevolucao) {
        this.cpf = cpf;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
    }

    /**
     * Metodo que insere uma instância de Emprestimo no bando de dados.
     */
    public void criarEmprestimo() {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        ArrayList<Emprestimo> emprestimos = buscaMeusEmprestimos(getCpf());
        if (emprestimos.size() < 3) {
            Livro livro = Livro.buscaLivroId(getIdLivro());
            if (livro.getQuantDisponivel() > 0) {
                try {
                    String query = "INSERT Into emprestimo (cpf, idLivro, dataEmprestimo, dataPrevista) VALUES (?, ?, ?, ?)";
                    state = connection.prepareStatement(query);
                    state.setString(1, cpf);
                    state.setInt(2, idLivro);
                    state.setDate(3, dataEmprestimo);
                    state.setDate(4, dataPrevista);
                    state.executeUpdate();

                    livro.editarLivro("quantDisponivel", livro.getQuantDisponivel() - 1);
                    System.out.println(" Emprestimo Cadastrado!");
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
                System.out.println(" Livro Indisponível");
            }
        } else {
            System.out.println(" Quantidade Máxima de Emprestimos Atingida! ");
        }

    }

    /**
     * Metodo que busca emprestimo no banco de dados com base no cpf e idLivro
     * 
     * @param cpf
     * @param idLivro
     * @return Emprestimo
     */
    public static Emprestimo buscaEmprestimo(String cpf, int idLivro) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {// se a conexão funcionar
            String query = "Select * from emprestimo where cpf = ? AND idLivro = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.setInt(2, idLivro);
            result = state.executeQuery();

            if (result.next()) {
                return new Emprestimo(
                        result.getString(1),
                        result.getInt(2),
                        result.getDate(3),
                        result.getDate(4),
                        result.getDate(5));
            }

        } catch (Exception e) {// se der erro, mostre qual foi o erro
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

    public static ArrayList<Emprestimo> buscaMeusEmprestimos(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        try {// se a conexão funcionar
            String query = "Select * from emprestimo where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            result = state.executeQuery();

            while (result.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        result.getString(1),
                        result.getInt(2),
                        result.getDate(3),
                        result.getDate(4),
                        result.getDate(5));
                if (result.getDate(5) == null) {
                    emprestimos.add(emprestimo);
                }
            }

            return emprestimos;

        } catch (Exception e) {// se der erro, mostre qual foi o erro
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

    /**
     * Cria uma lista emprestimo que irá armazenar os objetos de Emprestimo que
     * foram criadas no banco, e depois de passar por cada um armazena na lista
     * e retorna ela
     * 
     * @param cpf
     * @return ArrayList<Emprestimo>
     */
    public static ArrayList<Emprestimo> ListaEmprestimo() {
        ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM emprestimo";
            state = connection.prepareStatement(query);
            result = state.executeQuery();

            while (result.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        result.getString(1),
                        result.getInt(2),
                        result.getDate(3),
                        result.getDate(4),
                        result.getDate(5));
                emprestimos.add(emprestimo);
            }
            return emprestimos;

        } catch (Exception e) {
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

    /**
     * Metodo que devolve um livro, ou seja, finaliza uma operação de emprestimo
     * 
     * @param dataDevolucao
     */
    public void devolverLivro(String dataDevolucao) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        setDataDevolucao(dataDevolucao);
        Livro livro = Livro.buscaLivroId(getIdLivro());

        try {
            String query = "UPDATE emprestimo SET dataDevolucao = ? WHERE cpf = ? AND idLivro = ?";
            state = connection.prepareStatement(query);
            state.setDate(1, this.dataDevolucao);
            state.setString(2, this.cpf);
            state.setInt(3, idLivro);
            state.executeUpdate();
            livro.editarLivro("quantDisponivel", livro.getQuantDisponivel() + 1);
            System.out.println(" Livro Devolvido ao Acervo! ");

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
    
    public void setDataEmprestimo(String dataEmprestimo) {
        try {
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = formatoData.parse(dataEmprestimo);
            this.dataEmprestimo = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            System.err.println("Erro ao converter a data");
        }
    }
    
    
    public void setDataPrevista(String dataPrevista) {
        try {
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = formatoData.parse(dataPrevista);
            this.dataPrevista = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            System.err.println("Erro ao converter a data");
        }
    }
    
    public void setDataDevolucao(String dataDevolucao) {
        if (dataDevolucao == null) {
            try {
                SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date utilDate = formatoData.parse(dataDevolucao);
                this.dataDevolucao = new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                System.err.println("Erro ao converter a data");
            }
        }
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }


    public java.sql.Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(java.sql.Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public java.sql.Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(java.sql.Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public java.sql.Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(java.sql.Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Emprestimo [cpf=" + cpf + ", idLivro=" + idLivro + ", dataEmprestimo=" + dataEmprestimo
                + ", dataPrevista=" + dataPrevista + ", dataDevolucao=" + dataDevolucao + "]";
    }

}

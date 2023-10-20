import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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
        ArrayList<Emprestimo> emprestimos = listaAbertos(getCpf());

        if (emprestimos.size() < 3) {
            Livro livro = Livro.buscaLivroId(getIdLivro());
            if (livro.getQuantLivros() >= livro.getQuantEmprestados() || livro.getQuantLivros() > 1) {
                try {
                    String query = "INSERT Into emprestimo (cpf, idLivro, dataEmprestimo, dataPrevista) VALUES (?, ?, ?, ?)";
                    state = connection.prepareStatement(query);
                    state.setString(1, cpf);
                    state.setInt(2, idLivro);
                    state.setDate(3, dataEmprestimo);
                    state.setDate(4, dataPrevista);
                    state.executeUpdate();

                    livro.editarLivro("quantEmprestados", livro.getQuantEmprestados() + 1);
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
                System.out.println(" Livro em Falta! ");
            }
        } else {
            System.out.println(" Quantidade Máxima de Emprestimos Atingida! ");
        }

    }

    /**
     * Metodo que devolve um livro, ou seja, finaliza uma operação de emprestimo
     * 
     * @param dataDevolucao
     */
    public static void devolverLivro(String cpf, int idLivro) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        if (Emprestimo.buscaEmprestimo(cpf, idLivro) != null
                && Emprestimo.buscaEmprestimo(cpf, idLivro).getDataDevolucao() == null) {
            try {
                java.util.Date dataAtual = new java.util.Date();
                java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());

                String query = "UPDATE emprestimo SET dataDevolucao = ? WHERE cpf = ? AND idLivro = ?";
                state = connection.prepareStatement(query);
                state.setDate(1, dataSql);
                state.setString(2, cpf);
                state.setInt(3, idLivro);
                state.executeUpdate();

                Livro livro = Livro.buscaLivroId(idLivro);
                livro.editarLivro("quantEmprestados", livro.getQuantEmprestados() - 1);
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
        } else {
            System.out.println(" Empréstimo Inexistente ou Livro já Devolvido! ");
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

    public static void renovarEmprestimo(String cpf, int idLivro) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            java.sql.Date dataprv = Emprestimo.buscaEmprestimo(cpf, idLivro).getDataPrevista();
            java.sql.Date dataemp = Emprestimo.buscaEmprestimo(cpf, idLivro).getDataEmprestimo();

            java.util.Date dataUtilprv = new java.util.Date(dataprv.getTime());
            java.util.Date dataUtilemp = new java.util.Date(dataemp.getTime());

            long diferencaDias = TimeUnit.DAYS.convert(dataUtilprv.getTime() - dataUtilemp.getTime(),
                    TimeUnit.MILLISECONDS);
            if (diferencaDias == 7) {

                // Adicionar 7 dias
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dataUtilprv);
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                java.util.Date novaDataUtil = calendar.getTime();

                // Converter de volta para java.sql.Date
                java.sql.Date novaDataSql = new java.sql.Date(novaDataUtil.getTime());

                String query = "Update emprestimo SET dataPrevista = ? where cpf = ? AND idLivro = ?";
                state = connection.prepareStatement(query);
                state.setDate(1, novaDataSql);
                state.setString(2, cpf);
                state.setInt(3, idLivro);
                state.executeUpdate();

                System.out.println(" Empréstimo Estendido por Mais 7 dias! ");
            } else {
                System.out.println(" Não foi possível renovar o empréstimo! ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que lista os Empréstimos em aberto de um Usuário.
     * 
     * @param cpf
     * @return ArrayList<Emprestimo>
     */
    public static ArrayList<Emprestimo> listaAbertos(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        try {
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

    public static ArrayList<Emprestimo> listarPorCpf(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        try {
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
                emprestimos.add(emprestimo);
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
     * Método que retorna um ArrayList com todos os Empréstimos do Banco de Dados
     * 
     * @return ArrayList<Emprestimo>
     */
    public static ArrayList<Emprestimo> listaTodos() {
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
     * Método que lista todos os emprestimos Atrazados
     * 
     * @return ArrayList<Emprestimo>
     */
    public static ArrayList<Emprestimo> listaAtrazados() {
        ArrayList<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;
        ResultSet result = null;

        try {
            java.util.Date dataAtual = new java.util.Date();
            java.sql.Date dataSql = new java.sql.Date(dataAtual.getTime());
            String query = "SELECT * FROM emprestimo WHERE dataPrevista < ? AND dataDevolucao IS NULL";
            state = connection.prepareStatement(query);
            state.setDate(1, dataSql);
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

    public static void finalizaEmprestimos(String cpf) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            String query = "Delete from emprestimo where cpf = ?";
            state = connection.prepareStatement(query);
            state.setString(1, cpf);
            state.executeUpdate();

            System.out.println(" Usuário desvilculado com Empréstimos! ");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void finalizaEmprestimos(int idLivro) {
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement state = null;

        try {
            String query = "Delete from emprestimo where idLivro = ?";
            state = connection.prepareStatement(query);
            state.setInt(1, idLivro);
            state.executeUpdate();

            System.out.println(" Livro desvilculado com Empréstimos! ");

        } catch (Exception e) {
            e.printStackTrace();
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
        if (dataDevolucao != null) {
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
        if (dataDevolucao != null) {
            return "\n Cpf: " + cpf + "\n idLivro: " + idLivro + "\n Data do Emprestimo: " + dataEmprestimo
                    + "\n Data Prevista para Entrega: " + dataPrevista + "\n Data da Devolucao: " + dataDevolucao;
        } else {
            return "\n Cpf: " + cpf + "\n idLivro: " + idLivro + "\n Data do Emprestimo: " + dataEmprestimo
                    + "\n Data Prevista para Entrega: " + dataPrevista + "\n Ainda não devolvido! ";
        }
    }
}

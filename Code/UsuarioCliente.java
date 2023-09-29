public class UsuarioCliente extends Usuario {
    private int livrosEmprestados;
    private int idCliente;

    public UsuarioCliente(String nome, String cpf, String telefone, String senha, String email, int livrosEmprestados, int idCliente) {
        super(nome, cpf, telefone, senha, email);
        this.livrosEmprestados = livrosEmprestados;
        this.idCliente = idCliente;
    }

    public int getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void setLivrosEmprestados(int livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "UsuarioCliente [livrosEmprestados=" + livrosEmprestados + ", idCliente=" + idCliente + "]";
    }

}

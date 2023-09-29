public class UsuarioAdministrativo extends Usuario {
    private int idAdministrativo;

    public UsuarioAdministrativo(int idUsuario,String nome, String cpf, String telefone, String senha, String email, int idAdministrativo) {
        super(idUsuario,nome, cpf, telefone, senha, email);
        this.idAdministrativo = idAdministrativo;
    }

    public int getIdAdministrativo() {
        return idAdministrativo;
    }

    public void setIdAdministrativo(int idAdministrativo) {
        this.idAdministrativo = idAdministrativo;
    }

    @Override
    public String toString() {
        return "UsuarioAdministrativo [idAdministrativo=" + idAdministrativo + "]";
    }

}

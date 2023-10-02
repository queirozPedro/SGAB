public class Adm extends Usuario {
    private int idAdm;

    public Adm(int idAdm) {
        this.idAdm = idAdm;
    }

    public Adm(String cpf, String nome, String senha, String email, int idAdm) {
        super(cpf, nome, senha, email);
        this.idAdm = idAdm;
    }

    public int getidAdm() {
        return idAdm;
    }

    public void setidAdm(int idAdm) {
        this.idAdm = idAdm;
    }

    @Override
    public String toString() {
        return "UsuarioAdministrativo [idAdm=" + idAdm + "]" + super.toString();
    }

}

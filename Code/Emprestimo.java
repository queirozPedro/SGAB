import java.sql.Date;

public class Emprestimo {
    private int idLivro;
    private int idUsuario;
    private Date dataEmpretimo;
    private Date dataPrevista;
    private Date dataDevolucao;

    @Override
    public String toString() {
        return "Emprestimo [idLivro=" + idLivro + ", idUsuario=" + idUsuario + ", dataEmpretimo=" + dataEmpretimo + "]";
    }
    
}

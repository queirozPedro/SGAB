public class ValidarDados {
        /*
     *
     * Validações para Usuário
     * 
     */

     public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s0-9,?.-]+$")) {
            return false;
        }
        return true;
    }

    public static boolean validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }
        if (!cpf.matches("\\d{11}")) {
            return false;
        }
        return true;
    }

    public static boolean validarTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return false;
        }
        if (!telefone.matches("\\d{11}")) {
            return false;
        }
        return true;
    }

    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return false;
        }
        return true;

    }

    public static boolean validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty() || senha.length() < 6) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Validações para Livro
     */

    public static boolean validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return false;
        }
        if (!titulo.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s0-9,?.-]+$")) {
            return false;
        }
        return true;
    }

    public static boolean validarAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            return false;
        }
        if (!autor.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s0-9,?.-]+$")) {
            return false;
        }
        return true;
    }

    public static boolean validarGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            return false;
        }
        if (!genero.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s0-9,?.-]+$")) {
            return false;
        }
        return true;
    }

    public static boolean validarDataPublicacao(String dataPublicacao) {
        if (dataPublicacao == null || dataPublicacao.trim().isEmpty()) {
            return false;
        }
        // dd/MM/yyyy
        if (!dataPublicacao.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$")) {
            return false;
        }
        return true;
    }

    public static boolean validarEdicao(String edicao) {
        if (edicao == null || edicao.trim().isEmpty()) {
            return false;
        }
        if (!edicao.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s0-9,?.-]+$")) {
            return false;
        }
        return true;
    }

    public static boolean validarEditora(String editora) {
        if (editora == null || editora.trim().isEmpty()) {
            return false;
        }
        if (!editora.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s0-9,?.-]+$")) {
            return false;
        }
        return true;
    }

    public static boolean validarIsbn(String isnb) {
        if (isnb == null || isnb.isEmpty()) {
            return false;
        }
        if (!isnb.matches("\\d{13}")) {
            return false;
        }
        return true;
    }

    public static boolean validarQuantLivros(int quantLivros) {
        if (quantLivros <= 0) {
            return false;
        }
        return true;
    }
}

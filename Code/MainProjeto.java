import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainProjeto {

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        boolean sair = false;
        int op;

        while (!sair) {

            try {
                op = Integer.valueOf(MenuPrincipal(sc));

                switch (op) {
                    case 1: // Usuário
                        MenuLogin(sc, op);
                        break;
                    case 2: // Cliente
                        MenuCadastro(sc, op);
                        break;
                    case 0: // Sair
                        sair = true;
                        System.out.println("\n     - Finalizando -\n");
                        return;
                }

            } catch (NumberFormatException e) {
            }

        }
        sc.close();
    }

    public static String MenuPrincipal(Scanner sc) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println(" ===  Menu Principal  ===");
        System.out.println(" 1 -> Login");
        System.out.println(" 2 -> Registrar-se");
        System.out.println(" 0 -> Sair");
        System.out.print(" >> ");
        return sc.nextLine();
    }

    /**
     * Menu de login, responsável por logar um usuário e diferenciar Usuários de
     * Adms,
     * direcionando eles para seus respectivos menus.
     * 
     * @param sc
     * @param op
     * @throws InterruptedException
     * @throws IOException
     */
    public static void MenuLogin(Scanner sc, int op) throws InterruptedException, IOException {
        int out = 3;
        try {
            boolean sair = false;
            do {
                LimpaTela();
                System.out.println(" ===  Menu Login  ===");
                System.out.println(" 1 -> Realizar Login");
                System.out.println(" 0 -> Voltar");
                System.out.print(" >> ");
                op = Integer.valueOf(sc.nextLine());

                switch (op) {
                    case 1:
                        LimpaTela();
                        System.out.println(" < Login >");
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Senha: ");
                        String senha = sc.nextLine();
                        Usuario usuario = Usuario.loginUsuario(email, senha);
                        if (usuario != null) {
                            Adm admin = Adm.loginAdm(email, senha);
                            if (admin != null) {
                                menuAdmin(admin, sc);
                            } else {
                                menuUsuario(usuario, sc);
                            }
                        } else {
                            LimpaTela();
                            System.out.println(" Email ou Senha incorretos! ");
                            if (--out == 0) {
                                System.out.print(" Aguarde alguns minutos e tente novamente! ");
                                sc.nextLine();
                                return;
                            }
                        }
                        System.out.print("\n Aperte Enter para Continuar! ");
                        sc.nextLine();
                        break;
                    case 0:
                        sair = true;
                        break;
                }
            } while (!sair);

        } catch (NumberFormatException e) {
        }

    }

    /**
     * Menu de Cadastro, onde usuários são capazes de criar suas próprias contas
     * para acessar
     * o sistema do acervo.
     * 
     * @param sc
     * @param op
     * @throws InterruptedException
     * @throws IOException
     */
    public static void MenuCadastro(Scanner sc, int op) throws InterruptedException, IOException {
        try {
            LimpaTela();
            System.out.println(" ===  Tela de Cadastro  ===");
            System.out.println(" 1 -> Registrar-se");
            System.out.println(" 0 -> Voltar");
            System.out.print(" >> ");

            switch (Integer.valueOf(sc.nextLine())) {
                case 1:
                    boolean sair = false;
                    String nome = null, cpf = null, email = null, senha = null, senhaConfirma = null, telefone = null;

                    do {
                        LimpaTela();
                        System.out.println(" < Cadastro >");
                        if (!validarNome(nome)) {
                            System.out.print(" Nome: ");
                            nome = sc.nextLine();
                        } else {
                            System.out.println(" Nome: " + nome);
                            if (!validarCpf(cpf)) {
                                System.out.print(" Cpf (11 digitos): ");
                                cpf = sc.nextLine();
                            } else {
                                System.out.println(" Cpf: " + cpf);
                                if (!validarEmail(email)) {
                                    System.out.print(" Email: ");
                                    email = sc.nextLine();
                                } else {
                                    System.out.println(" Email: " + email);
                                    if (!validarSenha(senha)) {
                                        System.out.print(" Senha (minimo de 6 digitos): ");
                                        senha = sc.nextLine();
                                        System.out.print(" Confirme a Senha: ");
                                        senhaConfirma = sc.nextLine();
                                        if (!senha.equals(senhaConfirma)) {
                                            senha = null;
                                        }
                                    } else {
                                        System.out.println(" Senha: " + senha);
                                        if (!validarTelefone(telefone)) {
                                            System.out.print(" Telefone (11 digitos)): ");
                                            telefone = sc.nextLine();
                                        } else {
                                            System.out.println(" Telefone: " + telefone);
                                            System.out.print(" Cadastrar Conta (1 -> Sim, 2 - > Não): ");
                                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                                Usuario usuario = new Usuario(cpf, nome, senha, email, telefone);
                                                usuario.criarConta();
                                            }
                                            System.out.print(" Aperte Enter para Continuar! ");
                                            sc.nextLine();
                                            sair = true;
                                        }
                                    }
                                }
                            }
                        }
                    } while (!sair);
                    break;
                case 0:
                    return;
            }
        } catch (NumberFormatException e) {
        }
    }

    /**
     * Menu de Usuário, para onde você vem depois de logar, aqui você pode
     * vizualizar e editar seu perfil,
     * pesquisar por livros no acervo e realizar emprestimos.
     * 
     * @param usuario
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    public static void menuUsuario(Usuario usuario, Scanner sc) throws InterruptedException, IOException {
        boolean sair = false;
        do {
            try {
                LimpaTela();
                System.out.println(" > Bem Vindo " + usuario.getNome() + " (^O^) < ");
                System.out.println(" 1 -> Exibir Perfil");
                System.out.println(" 2 -> Pesquisar por Livros");
                System.out.println(" 3 -> Pedir Emprestimo");
                System.out.println(" 0 -> Sair da Conta");
                System.out.print(" > ");

                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        editarPerfil(sc, usuario);
                        break;
                    case 2: // buscarLivro
                        // Falta Implementar
                        break;
                    case 3: // realizarEmprestimo
                        // Falta Implementar
                        break;
                    case 0:
                        sair = true;
                        System.out.println(" Saindo da Conta");
                        return;
                }
            } catch (NumberFormatException e) {
            }
        } while (!sair);
    }

    /**
     * O menu do Administrador, que dispõe de opções para vizualizar e editar seu
     * perfil,
     * manter usuários, manter acervo, e gerenciar os emprestimos.
     * 
     * @param admin
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    private static void menuAdmin(Adm admin, Scanner sc) throws InterruptedException, IOException {
        boolean sair = false;
        do {
            try {
                LimpaTela();
                System.out.println(" >< Acesso de Administrador ><");
                System.out.println(" 1 -> Exibir Perfil");
                System.out.println(" 2 -> Gerenciar Usuários");
                System.out.println(" 3 -> Gerenciar Acervo");
                System.out.println(" 4 -> Gerenciar Emprestimos");
                System.out.println(" 0 -> Sair da Conta");
                System.out.print(" > ");
                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        editarPerfil(sc, admin);
                        break;
                    case 2:
                        manterUsuarios(sc);
                        break;
                    case 3:
                        LimpaTela();
                        System.out.println(" < Manter Acervo >");
                        System.out.println(" 1 -> Cadastrar");
                        System.out.println(" 2 -> Buscar");
                        System.out.println(" 3 -> Listar");
                        System.out.println(" 4 -> Editar");
                        System.out.println(" 5 -> Excluir");
                        System.out.println(" 0 -> Voltar");
                        switch (Integer.valueOf(sc.nextLine())) {
                            case 1:
                                LimpaTela();
                                System.out.println("Insira os dados do livro");
                                System.out.print("Titulo: ");
                                String titulo = sc.nextLine();
                                System.out.print("Genero: ");
                                String genero = sc.nextLine();
                                System.out.print("Autor: ");
                                String autor = sc.nextLine();
                                System.out.print("Data da Publicacao (dd/MM/yyyy): ");
                                String dataPublicacao = sc.nextLine();
                                System.out.println("Edicao: ");
                                String edicao = sc.nextLine();
                                System.out.println("Editora: ");
                                String editora = sc.nextLine();
                                System.out.println("ISBN: ");
                                String isbn = sc.nextLine();
                                System.out.println("quantLivros: ");
                                int quantLivros = sc.nextInt();
                                System.out.println("quantDisponivel: ");
                                int quantDisponivel = sc.nextInt();
                                Livro livro = new Livro(quantLivros, titulo, genero, autor, dataPublicacao, edicao,
                                        editora, isbn, quantLivros, quantDisponivel);

                                livro.cadastrarLivro();

                                System.out.println(livro);
                                sc.nextLine();
                                break;
                            case 2:
                                Livro buscaLivro = null;
                                ArrayList<Livro> livrosBuscados = new ArrayList<>();
                                LimpaTela();
                                System.out.println(" < Busca de Livro> ");
                                System.out.println(" 1 -> Buscar por ID");
                                System.out.println(" 2 -> Buscar por Titulo");
                                System.out.println(" 3 -> Buscar por Genero");
                                System.out.println(" 4 -> Buscar por Autor");
                                System.out.println(" 0 -> Sair");
                                switch (Integer.valueOf(sc.nextLine())) {
                                    case 1:
                                        LimpaTela();
                                        System.out.println(" < Busca de Livro >");
                                        System.out.print(" Id: ");
                                        buscaLivro = Livro.buscaLivroId(Integer.valueOf(sc.nextLine()));
                                        System.out.println(buscaLivro);
                                        break;
                                    case 2:
                                        LimpaTela();
                                        System.out.println(" < Busca de Livro >");
                                        System.out.print(" Titulo: ");
                                        livrosBuscados = Livro.buscaLivroTitulo(sc.nextLine());
                                        for (int i = 0; i < livrosBuscados.size(); i++) {
                                            System.out.println(livrosBuscados.get(i) + "\n");
                                        }
                                        break;
                                    case 3:
                                        LimpaTela();
                                        System.out.println(" < Busca de Livro >");
                                        System.out.print(" Genero: ");
                                        livrosBuscados = Livro.buscaLivroGenero(sc.nextLine());
                                        for (int i = 0; i < livrosBuscados.size(); i++) {
                                            System.out.println(livrosBuscados.get(i) + "\n");
                                        }
                                        break;
                                    case 4:
                                        LimpaTela();
                                        System.out.println(" < Busca de Livro >");
                                        System.out.print(" Autor: ");
                                        livrosBuscados = Livro.buscaLivroAutor(sc.nextLine());
                                        for (int i = 0; i < livrosBuscados.size(); i++) {
                                            System.out.println(livrosBuscados.get(i) + "\n");
                                        }
                                        break;
                                    case 0:
                                        break;
                                }
                                System.out.println("Aperte Enter para Continuar");
                                sc.nextLine();
                                break;
                            case 3:

                            case 4:

                                break;
                            case 0:
                            default:
                                break;
                        }
                        break;
                    case 4:
                        LimpaTela();
                        System.out.println(" < Manter Emprestimos >");
                        System.out.println(" 1 -> Cadastrar");
                        System.out.println(" 2 -> Buscar");
                        System.out.println(" 3 -> Listar");
                        System.out.println(" 4 -> Editar");
                        System.out.println(" 5 -> Excluir");
                        System.out.println(" 0 -> Voltar");
                        sc.nextLine();
                        // Falta Implementar
                        break;
                    case 0:
                        System.out.println(" Saindo da Conta Adm");
                        sair = true;
                        return;

                }

            } catch (NumberFormatException e) {
            }
        } while (!sair);
    }

    /**
     * Editar perfil exclusivo para Administrador
     * 
     * @param sc
     * @param admin
     * @throws InterruptedException
     * @throws IOException
     */
    public static void editarPerfil(Scanner sc, Adm admin) throws InterruptedException, IOException {
        try {
            LimpaTela();
            System.out.println(" < Meu Perfil >");
            System.out.println(admin.mostrarPerfil());
            System.out.println("\n 1 -> Editar Perfil");
            System.out.println(" 2 -> Excluir Conta");
            System.out.println(" 0 -> Sair");
            System.out.print(" > ");
            switch (Integer.valueOf(sc.nextLine())) {
                case 1:
                    LimpaTela();
                    System.out.println("Qual dado quer editar?");
                    System.out.println(" 1 -> Nome");
                    System.out.println(" 2 -> Email");
                    System.out.println(" 3 -> Senha");
                    System.out.println(" 4 -> Telefone");
                    System.out.println(" 0 -> Voltar");
                    System.out.print(" > ");
                    switch (Integer.valueOf(sc.nextLine())) {
                        case 1:
                            System.out.print(" Novo Nome: ");
                            String nome = sc.nextLine();
                            if (validarNome(nome))
                                admin.editarUsuario(nome, null, null, null);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 2:
                            System.out.print(" Novo Email: ");
                            String email = sc.nextLine();
                            if (validarEmail(email))
                                admin.editarUsuario(null, null, email, null);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 3:
                            System.out.print(" Nova Senha: ");
                            String senha = sc.nextLine();
                            if (validarSenha(senha))
                                admin.editarUsuario(null, senha, null, null);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 4:
                            System.out.print(" Novo Telefone: ");
                            String telefone = sc.nextLine();
                            if (validarTelefone(telefone))
                                admin.editarUsuario(null, null, null, telefone);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 0:
                        default:
                            System.out.print("\n Aperte Enter para Continuar! ");
                            sc.nextLine();
                            break;
                    }
                    break;
                case 2:
                    System.out.println(" < Excluir Conta >");
                    System.out.println(" 1 -> Excluir ");
                    System.out.println(" 0 -> Sair");
                    System.out.print(" > ");
                    switch (Integer.valueOf(sc.nextLine())) {
                        case 1:
                            System.out.print("Insira o email: ");
                            String email = sc.nextLine();
                            System.out.print("Insira o a senha: ");
                            String senha = sc.nextLine();
                            if (Adm.loginUsuario(email, senha) != null) {
                                Adm.excluirAdm(admin.getCpf());
                                System.out.println("Conta Adm Excluida!");
                                return;
                            } else
                                System.out.println("Email ou Senha Incorretos");
                        default:
                            System.out.print("\n Aperte Enter para Continuar! ");
                            sc.nextLine();
                            break;
                    }
                    return;
                case 0:
                default:
                    break;
            }
        } catch (NumberFormatException e) {
        }
    }

    /**
     * Editar perfil exclusivo para Usuários
     * 
     * @param sc
     * @param usuario
     * @throws InterruptedException
     * @throws IOException
     */
    public static void editarPerfil(Scanner sc, Usuario usuario) throws InterruptedException, IOException {
        try {
            LimpaTela();
            System.out.println(" < Meu Perfil >");
            System.out.println(usuario.mostrarPerfil());
            System.out.println("\n 1 -> Editar Perfil");
            System.out.println(" 2 -> Excluir Conta");
            System.out.println(" 0 -> Sair");
            System.out.print(" > ");
            switch (Integer.valueOf(sc.nextLine())) {
                case 1:
                    LimpaTela();
                    System.out.println(" Qual dado quer editar?");
                    System.out.println(" 1 -> Nome");
                    System.out.println(" 2 -> Email");
                    System.out.println(" 3 -> Senha");
                    System.out.println(" 4 -> Telefone");
                    System.out.println(" 0 -> Voltar");
                    System.out.print(" > ");
                    switch (Integer.valueOf(sc.nextLine())) {
                        case 1:
                            System.out.print(" Novo Nome: ");
                            String nome = sc.nextLine();
                            if (validarNome(nome))
                                usuario.editarUsuario(nome, null, null, null);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 2:
                            System.out.print(" Novo Email: ");
                            String email = sc.nextLine();
                            if (validarEmail(email))
                                usuario.editarUsuario(null, null, email, null);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 3:
                            System.out.print(" Nova Senha: ");
                            String senha = sc.nextLine();
                            if (validarSenha(senha))
                                usuario.editarUsuario(null, senha, null, null);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 4:
                            System.out.print(" Novo Telefone: ");
                            String telefone = sc.nextLine();
                            if (validarTelefone(telefone))
                                usuario.editarUsuario(null, null, null, telefone);
                            else
                                System.out.println(" Formato inválido ");
                            break;
                        case 0:
                        default:
                            System.out.print("\n Aperte Enter para Continuar! ");
                            sc.nextLine();
                            break;
                    }
                    break;
                case 2:
                    LimpaTela();
                    System.out.println(" < Excluir Conta >");
                    System.out.println(" 1 -> Excluir ");
                    System.out.println(" 0 -> Sair");
                    System.out.print(" > ");
                    switch (Integer.valueOf(sc.nextLine())) {
                        case 1:
                            LimpaTela();
                            System.out.println(" < Excluir Conta >");
                            System.out.print(" Email: ");
                            String email = sc.nextLine();
                            System.out.print(" Senha: ");
                            String senha = sc.nextLine();
                            if (Usuario.loginUsuario(email, senha) != null) {
                                Usuario.excluirConta(usuario.getCpf());
                                System.out.println("Conta Excluida!");
                                return;
                            } else
                                System.out.println(" Email ou Senha Incorretos");
                        default:
                            System.out.print("\n Aperte Enter para Continuar! ");
                            sc.nextLine();
                            break;
                    }
                    return;
                case 0:
                    break;
            }
        } catch (NumberFormatException e) {
        }
    }

    /**
     * Manter Usuários, que é uma da operações disponiveis para os administradores.
     * 
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    public static void manterUsuarios(Scanner sc) throws InterruptedException, IOException {
        try {
            LimpaTela();
            System.out.println(" < Manter Usuários >");
            System.out.println(" 1 -> Cadastrar");
            System.out.println(" 2 -> Buscar");
            System.out.println(" 3 -> Listar");
            System.out.println(" 4 -> Editar");
            System.out.println(" 5 -> Excluir");
            System.out.println(" 0 -> Voltar");
            System.out.print(" > ");
            switch (Integer.valueOf(sc.nextLine())) {

                case 1:
                    boolean sair = false;
                    String nome = null, cpf = null, email = null, senha = null, senhaConfirma = null, telefone = null;
                    LimpaTela();

                    System.out.println(" < Cadastrar >");
                    System.out.println(" 1 -> Cadastrar Administrador");
                    System.out.println(" 2 -> Cadastrar Usuario");
                    System.out.println(" 0 -> Voltar ");
                    System.out.print(" > ");

                    switch (Integer.valueOf(sc.nextLine())) {
                        case 1:
                            LimpaTela();
                            System.out.println(" < Cadastrar Administrador >");
                            System.out.println(" 1 -> Cadastrar Novo");
                            System.out.println(" 2 -> Tornar Administrador");
                            System.out.println(" 0 -> Voltar");
                            System.out.print(" > ");

                            switch (Integer.valueOf(sc.nextLine())) {
                                case 1:
                                    sair = false;
                                    do {
                                        LimpaTela();
                                        System.out.println(" < Cadastrar Novo Administrador >");
                                        if (!validarNome(nome)) {
                                            System.out.print(" Nome: ");
                                            nome = sc.nextLine();
                                        } else {
                                            System.out.println(" Nome: " + nome);
                                            if (!validarCpf(cpf)) {
                                                System.out.print(" Cpf (11 digitos): ");
                                                cpf = sc.nextLine();
                                            } else {
                                                System.out.println(" Cpf: " + cpf);
                                                if (!validarEmail(email)) {
                                                    System.out.print(" Email: ");
                                                    email = sc.nextLine();
                                                } else {
                                                    System.out.println(" Email: " + email);
                                                    if (!validarSenha(senha)) {
                                                        System.out.print(" Senha (minimo de 6 digitos): ");
                                                        senha = sc.nextLine();
                                                        System.out.print(" Confirme a Senha: ");
                                                        senhaConfirma = sc.nextLine();
                                                        if (!senha.equals(senhaConfirma)) {
                                                            senha = null;
                                                        }
                                                    } else {
                                                        System.out.println(" Senha: " + senha);
                                                        if (!validarTelefone(telefone)) {
                                                            System.out.print(" Telefone (11 digitos)): ");
                                                            telefone = sc.nextLine();
                                                        } else {
                                                            System.out.println(" Telefone: " + telefone);
                                                            System.out.print(
                                                                    " Cadastrar Conta (1 -> Sim, 2 - > Não): ");
                                                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                                                Adm adm = new Adm(cpf, nome, senha, email,
                                                                        telefone);
                                                                adm.criarAdm();
                                                            }
                                                            System.out.print(" Aperte Enter para Continuar! ");
                                                            sc.nextLine();
                                                            sair = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } while (!sair);
                                    break;
                                case 2:
                                    LimpaTela();
                                    System.out.println(" < Tornar Administrador >");
                                    System.out.print(" Cpf: ");
                                    cpf = sc.nextLine();
                                    if (Adm.buscaAdm(cpf) != null) {
                                        System.out.println(" Administrador já Cadastrado! ");
                                    } else {
                                        if (Usuario.buscaUsuario(cpf) == null) {
                                            System.out.println(" Usuário Não Existente! ");
                                        }
                                        try {
                                            Usuario user = Usuario.buscaUsuario(cpf);
                                            Adm admin = new Adm(user.getCpf(), user.getNome(), user.getSenha(),
                                                    user.getEmail(), user.getTelefone());
                                            admin.criarAdm();
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    System.out.print(" Aperte Enter para Continuar! ");
                                    sc.nextLine();
                                    break;
                                case 0:

                                    break;
                            }

                            break;
                        case 2:
                            sair = false;
                            do {
                                LimpaTela();
                                System.out.println(" < Cadastro de Usuário >");
                                if (!validarNome(nome)) {
                                    System.out.print(" Nome: ");
                                    nome = sc.nextLine();
                                } else {
                                    System.out.println(" Nome: " + nome);
                                    if (!validarCpf(cpf)) {
                                        System.out.print(" Cpf (11 digitos): ");
                                        cpf = sc.nextLine();
                                    } else {
                                        System.out.println(" Cpf: " + cpf);
                                        if (!validarEmail(email)) {
                                            System.out.print(" Email: ");
                                            email = sc.nextLine();
                                        } else {
                                            System.out.println(" Email: " + email);
                                            if (!validarSenha(senha)) {
                                                System.out.print(" Senha (minimo de 6 digitos): ");
                                                senha = sc.nextLine();
                                                System.out.print(" Confirme a Senha: ");
                                                senhaConfirma = sc.nextLine();
                                                if (!senha.equals(senhaConfirma)) {
                                                    senha = null;
                                                }
                                            } else {
                                                System.out.println(" Senha: " + senha);
                                                if (!validarTelefone(telefone)) {
                                                    System.out.print(" Telefone (11 digitos)): ");
                                                    telefone = sc.nextLine();
                                                } else {
                                                    System.out.println(" Telefone: " + telefone);
                                                    System.out.print(
                                                            " Cadastrar Conta (1 -> Sim, 2 - > Não): ");
                                                    if (Integer.valueOf(sc.nextLine()) == 1) {
                                                        Usuario usuario = new Usuario(cpf, nome, senha, email,
                                                                telefone);
                                                        usuario.criarConta();
                                                    }
                                                    System.out.print(" Aperte Enter para Continuar! ");
                                                    sc.nextLine();
                                                    sair = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            } while (!sair);

                            break;
                        case 0:
                            break;
                    }

                    break;
                case 2:
                    LimpaTela();
                    System.out.println(" < Buscar Usuário >");
                    System.out.print(" Cpf: ");
                    cpf = sc.nextLine();
                    if (Adm.buscaAdm(cpf) != null) {
                        System.out.println(Adm.buscaAdm(cpf).toString());
                    } else if (Usuario.buscaUsuario(cpf) != null) {
                        System.out.println(Usuario.buscaUsuario(cpf));
                    } else {
                        System.out.println(" Usuário não encontrado!");
                    }
                    System.out.print("\n Aperte Enter para Continuar! ");
                    sc.nextLine();
                    break;
                case 3:
                    LimpaTela();
                    System.out.println(" < Listar Usuários >");
                    System.out.println(" 1 -> Usuários");
                    System.out.println(" 2 -> Administradores");
                    System.out.println(" 0 -> Sair");
                    System.out.print(" > ");
                    switch (Integer.valueOf(sc.nextLine())) {
                        case 1:
                            LimpaTela();
                            System.out.println(" Usuários do Acervo");
                            ArrayList<Usuario> usuarios = Usuario.listaUsuario();

                            for (int i = 0; i < usuarios.size(); i++) {
                                System.out.println("\n" + usuarios.get(i));
                            }

                            System.out.print("\n Aperte Enter para Continuar! ");
                            sc.nextLine();
                            break;
                        case 2:
                            LimpaTela();
                            System.out.println("Administradores do Acervo");
                            ArrayList<Adm> adms = Adm.listaAdm();

                            for (int i = 0; i < adms.size(); i++) {
                                System.out.println("\n" + adms.get(i));
                            }

                            System.out.print("\n Aperte Enter para Continuar! ");
                            sc.nextLine();
                            break;
                        default:
                            break;
                    }
                    break;
                case 4: // Editar Usuários
                    LimpaTela();
                    System.out.println(" < Editar Usuários >");
                    System.out.print(" Cpf: ");
                    cpf = sc.nextLine();

                    break;
                case 5:
                    LimpaTela();
                    System.out.println(" < Excluir Usuário >");
                    System.out.println(" 1 -> Excluir");
                    System.out.println(" 0 -> Voltar");
                    System.out.print(" > ");
                    switch (Integer.valueOf(sc.nextLine())) {
                        case 1:
                            LimpaTela();
                            System.out.println(" < Excluir Usuário >");
                            System.out.print(" Cpf: ");
                            cpf = sc.nextLine();
                            if (Adm.buscaAdm(cpf) != null) {
                                System.out.println(Adm.buscaAdm(cpf).toString());
                            } else if (Usuario.buscaUsuario(cpf) != null) {
                                System.out.println(Usuario.buscaUsuario(cpf));
                            } else {
                                System.out.println(" Usuário não encontrado!");
                                break;
                            }
                            System.out.print(" Excluir o Usuário (1 -> Sim, 2 - > Não): ");
                            switch (sc.nextInt()) {
                                case 1:
                                    sc.nextLine();
                                    Adm.excluirAdm(cpf);
                                    break;
                                default:
                                    break;
                            }
                            break;

                        case 0:
                        default:
                            break;
                    }
                    System.out.print("\n Aperte Enter para Continuar! ");
                    sc.nextLine();
                    break;
                case 0:
                default:
                    System.out.print("\n Aperte Enter para Continuar! ");
                    sc.nextLine();
                    break;
            }
        } catch (NumberFormatException e) {
        }
    }

    /*
     *
     * Validação de dados e correções de impressão
     * 
     */

    public static void LimpaTela() throws InterruptedException, IOException {
        // Isso aqui funciona pra identificar qual SO está sendo usado
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();
        }
    }

    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$")) {
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

}

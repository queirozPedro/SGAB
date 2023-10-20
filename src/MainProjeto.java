import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainProjeto {

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            try {
                switch (Integer.valueOf(MenuPrincipal(sc))) {
                    case 1: // Usuário
                        MenuLogin(sc);
                        break;
                    case 2: // Cliente
                        MenuCadastro(sc);
                        break;
                    case 0: // Sair
                        sair = true;
                        System.out.println("\n     - Finalizando -\n");
                        return;
                    default:
                        break;
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
     * Adms, direcionando eles para seus respectivos menus.
     * 
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    public static void MenuLogin(Scanner sc) throws InterruptedException, IOException {
        int out = 3;
        boolean sair = false;
        do {
            try {
                LimpaTela();
                System.out.println(" ===  Menu Login  ===");
                System.out.println(" 1 -> Realizar Login");
                System.out.println(" 0 -> Voltar");
                System.out.print(" >> ");

                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        LimpaTela();
                        System.out.println(" < Login >");
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Senha: ");
                        String senha = sc.nextLine();

                        if (Usuario.loginUsuario(email, senha) != null) {
                            if (Adm.loginAdm(email, senha) != null) {
                                menuAdmin(Adm.loginAdm(email, senha), sc);
                            } else {
                                menuUsuario(Usuario.loginUsuario(email, senha), sc);
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
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
            }
        } while (!sair);

    }

    /**
     * Menu de Cadastro, onde usuários são capazes de criar suas próprias contas
     * para acessar o sistema do acervo.
     * 
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    public static void MenuCadastro(Scanner sc) throws InterruptedException, IOException {
        boolean sair = false;
        do {
            try {
                LimpaTela();
                System.out.println(" ===  Tela de Cadastro  ===");
                System.out.println(" 1 -> Registrar-se");
                System.out.println(" 0 -> Voltar");
                System.out.print(" >> ");
                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        String nome = null, cpf = null, email = null, senha = null, senhaConfirma = null,
                                telefone = null;
                        do {
                            LimpaTela();
                            System.out.println(" < Cadastro >");
                            if (!ValidarDados.validarNome(nome)) {
                                System.out.print(" Nome: ");
                                nome = sc.nextLine();
                            } else {
                                System.out.println(" Nome: " + nome);
                                if (!ValidarDados.validarCpf(cpf)) {
                                    System.out.print(" Cpf (11 digitos): ");
                                    cpf = sc.nextLine();
                                } else {
                                    System.out.println(" Cpf: " + cpf);
                                    if (!ValidarDados.validarEmail(email)) {
                                        System.out.print(" Email: ");
                                        email = sc.nextLine();
                                    } else {
                                        System.out.println(" Email: " + email);
                                        if (!ValidarDados.validarSenha(senha)) {
                                            System.out.print(" Senha (minimo de 6 digitos): ");
                                            senha = sc.nextLine();
                                            System.out.print(" Confirme a Senha: ");
                                            senhaConfirma = sc.nextLine();
                                            if (!senha.equals(senhaConfirma)) {
                                                senha = null;
                                            }
                                        } else {
                                            System.out.println(" Senha: " + senha);
                                            if (!ValidarDados.validarTelefone(telefone)) {
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
                        sair = false;
                        break;
                    case 0:
                        return;
                }
            } catch (NumberFormatException e) {
            }

        } while (!sair);
    }

    /**
     * Menu de Usuário, para onde você vem depois de logar, aqui você pode
     * vizualizar e editar seu perfil, pesquisar por livros no acervo
     * e realizar emprestimos.
     * 
     * @param usuario
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    public static void menuUsuario(Usuario usuario, Scanner sc) throws InterruptedException, IOException {
        boolean sair = false;
        int idLivro;
        do {
            try {
                LimpaTela();
                System.out.println(" > Bem Vindo " + usuario.getNome() + " (^O^) < ");
                System.out.println(" 1 -> Exibir Perfil");
                System.out.println(" 2 -> Pesquisar por Livros");
                System.out.println(" 3 -> Pedir Emprestimo");
                System.out.println(" 4 -> Meus Emprestimos");
                System.out.println(" 0 -> Sair da Conta");
                System.out.print(" > ");

                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        editarPerfil(sc, usuario);
                        break;
                    case 2: // buscarLivro
                        ArrayList<Livro> listaLivro = new ArrayList<>();
                        LimpaTela();
                        System.out.println(" < Pesquisar Livros > ");
                        System.out.print(" > ");
                        listaLivro = Livro.pesquisarLivro(sc.nextLine());
                        if (listaLivro.size() != 0) {
                            for (int i = 0; i < listaLivro.size(); i++) {
                                System.out.println(listaLivro.get(i).toString() + "\n");
                            }
                        } else
                            System.out.println(" Livro não Encontrado! ");

                        System.out.print("\n Aperte Enter para Continuar! ");
                        sc.nextLine();

                        break;
                    case 3: // realizarEmprestimo
                        LimpaTela();
                        System.out.println(" < Realizar Emprestimo >");
                        System.out.print(" id do Livro: ");
                        idLivro = Integer.valueOf(sc.nextLine());
                        if (Livro.buscaLivroId(idLivro) != null) {
                            Livro livro = Livro.buscaLivroId(idLivro);
                            System.out.println(livro.toString());
                            System.out.print(" Continuar (1 -> Sim, 2 -> Não): ");
                            if (Integer.valueOf(sc.nextLine()) == 1) {

                                System.out.println("\n < Credenciais do Administrador >");
                                System.out.print(" Email: ");
                                String email = sc.nextLine();
                                System.out.print(" Senha: ");
                                String senha = sc.nextLine();
                                if (Adm.loginAdm(email, senha) != null) {

                                    LocalDate dataAtual = LocalDate.now();
                                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    String dataEmprestimo = dataAtual.format(formato);
                                    LocalDate dataprev = dataAtual.plusDays(7);
                                    String dataPrevista = dataprev.format(formato);

                                    Emprestimo emprestimo = new Emprestimo(
                                            usuario.getCpf(),
                                            idLivro,
                                            dataEmprestimo,
                                            dataPrevista,
                                            null);
                                    emprestimo.criarEmprestimo();

                                } else {
                                    System.out.println(" Empréstimo Cancelado por Invalidação!");
                                }
                            } else {
                                System.out.println(" Livro Não Encontrado!");
                            }
                            System.out.print(" Aperte Enter para Continuar! ");
                            sc.nextLine();
                        }
                        break;
                    case 4:
                        LimpaTela();
                        System.out.println(" < Meus Emprestimos > ");
                        ArrayList<Emprestimo> emprestimos = Emprestimo.listaAbertos(usuario.getCpf());
                        if (emprestimos.size() != 0) {
                            for (int i = 0; i < emprestimos.size(); i++) {
                                System.out.println(emprestimos.get(i).toString());
                            }
                        } else {
                            System.out.println(" Sem Emprestimos Cadastrados! ");
                        }
                        System.out.print(" Aperte Enter para Continuar! ");
                        sc.nextLine();
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
     * perfil, manter usuários, manter acervo, e gerenciar os emprestimos.
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
                        manterUsuarios(sc, admin);
                        break;
                    case 3: // Manter Livro
                        manterLivros(sc);
                        break;
                    case 4: // Manter Emprestimo
                        manterEmprestimo(sc);
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
                            if (ValidarDados.validarNome(nome)) {
                                admin.editarUsuario("nome", nome);
                                admin.setNome(nome);
                            } else
                                System.out.println(" Formato inválido ");
                            break;
                        case 2:
                            System.out.print(" Novo Email: ");
                            String email = sc.nextLine();
                            if (ValidarDados.validarEmail(email)) {
                                admin.editarUsuario("email", email);
                                admin.setEmail(email);
                            } else
                                System.out.println(" Formato inválido ");
                            break;
                        case 3:
                            System.out.print(" Nova Senha: ");
                            String senha = sc.nextLine();
                            if (ValidarDados.validarSenha(senha)) {
                                admin.editarUsuario("senha", senha);
                                admin.setSenha(senha);
                            } else
                                System.out.println(" Formato inválido ");
                            break;
                        case 4:
                            System.out.print(" Novo Telefone: ");
                            String telefone = sc.nextLine();
                            if (ValidarDados.validarTelefone(telefone)) {
                                admin.editarUsuario("telefone", telefone);
                                admin.setTelefone(telefone);
                            } else
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
        boolean sair = false;

        do {
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
                                if (ValidarDados.validarNome(nome)) {
                                    usuario.editarUsuario("nome", nome);
                                    usuario.setNome(nome);
                                } else
                                    System.out.println(" Formato inválido ");
                                break;
                            case 2:
                                System.out.print(" Novo Email: ");
                                String email = sc.nextLine();
                                if (ValidarDados.validarEmail(email)) {
                                    usuario.editarUsuario("email", email);
                                    usuario.setEmail(email);
                                } else
                                    System.out.println(" Formato inválido ");
                                break;
                            case 3:
                                System.out.print(" Nova Senha: ");
                                String senha = sc.nextLine();
                                if (ValidarDados.validarSenha(senha)) {
                                    usuario.editarUsuario("senha", senha);
                                    usuario.setSenha(senha);
                                } else
                                    System.out.println(" Formato inválido ");
                                break;
                            case 4:
                                System.out.print(" Novo Telefone: ");
                                String telefone = sc.nextLine();
                                if (ValidarDados.validarTelefone(telefone)) {
                                    usuario.editarUsuario("telefone", telefone);
                                    usuario.setTelefone(telefone);
                                } else
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
                                    System.out.println(" Conta Excluida!");
                                    System.out.print("\n Aperte Enter para Continuar! ");
                                    sc.nextLine();
                                    sair = true;
                                    break;
                                } else
                                    System.out.println(" Email ou Senha Incorretos");
                            default:

                                break;
                        }

                        break;
                    case 0:
                        sair = true;
                        break;
                }
            } catch (NumberFormatException e) {
            }
        } while (!sair);
    }

    /**
     * Manter Usuários, que é uma da operações disponiveis para os administradores.
     * 
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    public static void manterUsuarios(Scanner sc, Adm admin) throws InterruptedException, IOException {
        boolean sair = false;
        do {
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

                String nome = null, cpf = null, email = null, senha = null, senhaConfirma = null, telefone = null;
                Usuario usuario = null;
                Adm adm = null;

                switch (Integer.valueOf(sc.nextLine())) {

                    case 1:
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
                                            if (!ValidarDados.validarNome(nome)) {
                                                System.out.print(" Nome: ");
                                                nome = sc.nextLine();
                                            } else {
                                                System.out.println(" Nome: " + nome);
                                                if (!ValidarDados.validarCpf(cpf)) {
                                                    System.out.print(" Cpf (11 digitos): ");
                                                    cpf = sc.nextLine();
                                                } else {
                                                    System.out.println(" Cpf: " + cpf);
                                                    if (!ValidarDados.validarEmail(email)) {
                                                        System.out.print(" Email: ");
                                                        email = sc.nextLine();
                                                    } else {
                                                        System.out.println(" Email: " + email);
                                                        if (!ValidarDados.validarSenha(senha)) {
                                                            System.out.print(" Senha (minimo de 6 digitos): ");
                                                            senha = sc.nextLine();
                                                            System.out.print(" Confirme a Senha: ");
                                                            senhaConfirma = sc.nextLine();
                                                            if (!senha.equals(senhaConfirma)) {
                                                                senha = null;
                                                            }
                                                        } else {
                                                            System.out.println(" Senha: " + senha);
                                                            if (!ValidarDados.validarTelefone(telefone)) {
                                                                System.out.print(" Telefone (11 digitos)): ");
                                                                telefone = sc.nextLine();
                                                            } else {
                                                                System.out.println(" Telefone: " + telefone);
                                                                System.out.print(
                                                                        " Cadastrar Conta (1 -> Sim, 2 - > Não): ");
                                                                if (Integer.valueOf(sc.nextLine()) == 1) {
                                                                    adm = new Adm(cpf, nome, senha, email,
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
                                        sair = false;
                                        break;
                                    case 2:
                                        LimpaTela();
                                        System.out.println(" < Tornar Administrador >");
                                        System.out.print(" Cpf: ");
                                        cpf = sc.nextLine();
                                        if (Adm.buscaAdm(cpf) == null) {
                                            if (Usuario.buscaUsuario(cpf) != null) {
                                                System.out.println(Usuario.buscaUsuario(cpf).toString());
                                                System.out
                                                        .print(" Cadastrar Como Administrador (1 -> Sim, 2 -> Não): ");
                                                if (Integer.valueOf(sc.nextLine()) == 1) {
                                                    Usuario user = Usuario.buscaUsuario(cpf);
                                                    adm = new Adm(user);
                                                    adm.criarAdm();
                                                } else
                                                    break;
                                            } else {
                                                System.out.println(" Usuário não Existe! ");
                                            }
                                        } else {
                                            System.out.println(" Administrador já Cadastrado! ");
                                        }
                                        System.out.print(" Aperte Enter para Continuar! ");
                                        sc.nextLine();
                                        break;
                                    case 0:
                                        break;
                                }
                                break;
                            case 2:
                                do {
                                    LimpaTela();
                                    System.out.println(" < Cadastro de Usuário >");
                                    if (!ValidarDados.validarNome(nome)) {
                                        System.out.print(" Nome: ");
                                        nome = sc.nextLine();
                                    } else {
                                        System.out.println(" Nome: " + nome);
                                        if (!ValidarDados.validarCpf(cpf)) {
                                            System.out.print(" Cpf (11 digitos): ");
                                            cpf = sc.nextLine();
                                        } else {
                                            System.out.println(" Cpf: " + cpf);
                                            if (!ValidarDados.validarEmail(email)) {
                                                System.out.print(" Email: ");
                                                email = sc.nextLine();
                                            } else {
                                                System.out.println(" Email: " + email);
                                                if (!ValidarDados.validarSenha(senha)) {
                                                    System.out.print(" Senha (minimo de 6 digitos): ");
                                                    senha = sc.nextLine();
                                                    System.out.print(" Confirme a Senha: ");
                                                    senhaConfirma = sc.nextLine();
                                                    if (!senha.equals(senhaConfirma)) {
                                                        senha = null;
                                                    }
                                                } else {
                                                    System.out.println(" Senha: " + senha);
                                                    if (!ValidarDados.validarTelefone(telefone)) {
                                                        System.out.print(" Telefone (11 digitos)): ");
                                                        telefone = sc.nextLine();
                                                    } else {
                                                        System.out.println(" Telefone: " + telefone);
                                                        System.out.print(
                                                                " Cadastrar Conta (1 -> Sim, 2 - > Não): ");
                                                        if (Integer.valueOf(sc.nextLine()) == 1) {
                                                            usuario = new Usuario(cpf, nome, senha, email,
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
                                sair = false;
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
                        if (cpf.equals(admin.getCpf())) {
                            System.out.println(" Erro! você não pode editar seus dados por aqui! ");
                            System.out.print(" Aperte Enter para Continuar! ");
                            sc.nextLine();
                            break;
                        }
                        if (Adm.buscaAdm(cpf) != null) {
                            System.out.println(Adm.buscaAdm(cpf).toString());
                            System.out.print(" Editar Adm (1 -> Sim, 2 -> Não): ");
                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                LimpaTela();
                                adm = Adm.buscaAdm(cpf);
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
                                        nome = sc.nextLine();
                                        if (ValidarDados.validarNome(nome)) {
                                            adm.editarUsuario("nome", nome);
                                            adm.setNome(nome);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 2:
                                        System.out.print(" Novo Email: ");
                                        email = sc.nextLine();
                                        if (ValidarDados.validarEmail(email)) {
                                            adm.editarUsuario("email", email);
                                            adm.setEmail(email);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 3:
                                        System.out.print(" Nova Senha: ");
                                        senha = sc.nextLine();
                                        if (ValidarDados.validarSenha(senha)) {
                                            adm.editarUsuario("senha", senha);
                                            adm.setSenha(senha);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 4:
                                        System.out.print(" Novo Telefone: ");
                                        telefone = sc.nextLine();
                                        if (ValidarDados.validarTelefone(telefone)) {
                                            adm.editarUsuario("telefone", telefone);
                                            adm.setTelefone(telefone);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 0:
                                    default:
                                        System.out.print("\n Aperte Enter para Continuar! ");
                                        sc.nextLine();
                                        break;
                                }
                            }
                        } else if (Usuario.buscaUsuario(cpf) != null) {
                            System.out.println(Usuario.buscaUsuario(cpf));
                            System.out.print(" Editar Usuário (1 -> Sim, 2 -> Não): ");
                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                LimpaTela();
                                usuario = Usuario.buscaUsuario(cpf);
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
                                        nome = sc.nextLine();
                                        if (ValidarDados.validarNome(nome)) {
                                            usuario.editarUsuario("nome", nome);
                                            usuario.setNome(nome);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 2:
                                        System.out.print(" Novo Email: ");
                                        email = sc.nextLine();
                                        if (ValidarDados.validarEmail(email)) {
                                            usuario.editarUsuario("email", email);
                                            usuario.setEmail(email);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 3:
                                        System.out.print(" Nova Senha: ");
                                        senha = sc.nextLine();
                                        if (ValidarDados.validarSenha(senha)) {
                                            usuario.editarUsuario("senha", senha);
                                            usuario.setSenha(senha);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 4:
                                        System.out.print(" Novo Telefone: ");
                                        telefone = sc.nextLine();
                                        if (ValidarDados.validarTelefone(telefone)) {
                                            usuario.editarUsuario("telefone", telefone);
                                            usuario.setTelefone(telefone);
                                        } else
                                            System.out.println(" Formato inválido ");
                                        break;
                                    case 0:
                                    default:
                                        System.out.print("\n Aperte Enter para Continuar! ");
                                        sc.nextLine();
                                        break;
                                }
                            }
                        } else {
                            System.out.println(" Usuário não encontrado!");
                        }
                        System.out.print("\n Aperte Enter para Continuar! ");
                        sc.nextLine();
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
                                if (cpf.equals(admin.getCpf())) {
                                    System.out.println(" Erro! você não pode excluir sua conta por aqui! ");
                                    System.out.print(" Aperte Enter para Continuar! ");
                                    sc.nextLine();
                                    break;
                                }
                                if (Adm.buscaAdm(cpf) != null) {
                                    System.out.println(Adm.buscaAdm(cpf).toString());
                                } else if (Usuario.buscaUsuario(cpf) != null) {
                                    System.out.println(Usuario.buscaUsuario(cpf));
                                } else {
                                    System.out.println(" Usuário não encontrado!");
                                    break;
                                }
                                System.out.print(" Excluir o Usuário (1 -> Sim, 2 - > Não): ");
                                if (Integer.valueOf(sc.nextLine()) == 1) {
                                    Adm.excluirAdm(cpf);
                                    System.out.print("\n Aperte Enter para Continuar! ");
                                    sc.nextLine();
                                }
                                break;

                            case 0:
                            default:
                                break;
                        }
                        break;
                    case 0:
                        sair = true;
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
            }
        } while (!sair);
    }

    /**
     * Método que fazer as operações relacionadas a Livro: Cadastrar, Buscar,
     * Listar,
     * Editar e Excluir
     * 
     * @param sc
     * @throws InterruptedException
     * @throws IOException
     */
    public static void manterLivros(Scanner sc) throws InterruptedException, IOException {
        boolean sair = false;
        do {
            String titulo = null, genero = null, autor = null, dataPublicacao = null,
                    edicao = null, editora = null, isbn = null;
            int quantLivros = 0, idLivro = 0;
            Livro livro = null;
            ArrayList<Livro> listaLivro = new ArrayList<>();

            try {
                LimpaTela();
                System.out.println(" < Manter Acervo >");
                System.out.println(" 1 -> Cadastrar");
                System.out.println(" 2 -> Buscar");
                System.out.println(" 3 -> Listar");
                System.out.println(" 4 -> Editar");
                System.out.println(" 5 -> Excluir");
                System.out.println(" 0 -> Voltar");
                System.out.print(" > ");

                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        do {
                            LimpaTela();
                            System.out.println(" < Cadastro de Livro >");
                            if (!ValidarDados.validarTitulo(titulo)) {
                                System.out.print(" Titulo: ");
                                titulo = sc.nextLine();
                            } else {
                                System.out.println(" Titulo: " + titulo);
                                if (!ValidarDados.validarGenero(genero)) {
                                    System.out.print(" Genero: ");
                                    genero = sc.nextLine();
                                } else {
                                    System.out.println(" Genero: " + genero);
                                    if (!ValidarDados.validarAutor(autor)) {
                                        System.out.print(" Autor: ");
                                        autor = sc.nextLine();
                                    } else {
                                        System.out.println(" Autor: " + autor);
                                        if (!ValidarDados.validarDataPublicacao(dataPublicacao)) {
                                            System.out.print(" Data da Publicação (dd/mm/aaaa): ");
                                            dataPublicacao = sc.nextLine();
                                        } else {
                                            System.out.println(" Data da Publicação: " + dataPublicacao);
                                            if (!ValidarDados.validarEdicao(edicao)) {
                                                System.out.print(" Edicao: ");
                                                edicao = sc.nextLine();
                                            } else {
                                                System.out.println(" Edicao: " + edicao);
                                                if (!ValidarDados.validarEditora(editora)) {
                                                    System.out.print(" Editora: ");
                                                    editora = sc.nextLine();
                                                } else {
                                                    System.out.println(" Editora: " + editora);
                                                    if (!ValidarDados.validarIsbn(isbn)) {
                                                        System.out.print(" Isbn (13 digitos): ");
                                                        isbn = sc.nextLine();
                                                    } else {
                                                        System.out.println(" Isbn: " + isbn);
                                                        if (!ValidarDados.validarQuantLivros(quantLivros)) {
                                                            System.out.print(" Quantidade de Livros (>= 1): ");
                                                            quantLivros = Integer.valueOf(sc.nextLine());
                                                        } else {
                                                            System.out.println(" Quantidade de Livros: " + quantLivros);
                                                            System.out.print(
                                                                    " Cadastrar Livro (1 -> Sim, 2 - > Não): ");
                                                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                                                livro = new Livro(quantLivros, titulo, genero, autor,
                                                                        dataPublicacao, edicao, editora, isbn,
                                                                        quantLivros);
                                                                livro.cadastrarLivro();
                                                            }
                                                            System.out.print(" Aperte Enter para Continuar! ");
                                                            sc.nextLine();
                                                            sair = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } while (!sair);
                        sair = false;
                        break;

                    case 2:
                        LimpaTela();
                        System.out.println(" < Pesquisar Livros > ");
                        System.out.print(" ~Pesquise por um título, por autor ou por gênero!~ ");
                        System.out.print(" > ");
                        listaLivro = Livro.pesquisarLivro(sc.nextLine());
                        if (listaLivro != null) {
                            for (int i = 0; i < listaLivro.size(); i++) {
                                System.out.println(listaLivro.get(i).toString() + "\n");
                            }
                        } else
                            System.out.println(" Livro não Encontrado! ");

                        System.out.print("\n Aperte Enter para Continuar");
                        sc.nextLine();
                        break;

                    case 3:
                        LimpaTela();
                        System.out.println(" < Listando Acervo >");
                        listaLivro = Livro.listarLivros();
                        if (listaLivro != null && listaLivro.size() > 0) {
                            for (int i = 0; i < listaLivro.size(); i++) {
                                System.out.println(listaLivro.get(i).toString() + "\n");
                            }
                        } else
                            System.out.println("\n Nenhum livro encontrado! ");
                        System.out.print("\n Aperte Enter para Continuar! ");
                        sc.nextLine();
                        break;
                    case 4:
                        LimpaTela();
                        System.out.println(" < Editar Livro >");
                        System.out.print(" Id do Livro: ");
                        livro = Livro.buscaLivroId(Integer.valueOf(sc.nextLine()));
                        if (livro != null) {
                            System.out.println(" \n" + livro.toString());
                            System.out.println(" Editar Livro (1 -> Sim, 2 -> Não)");
                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                do {
                                    System.out.println(" < Editar Livro >");
                                    System.out.println(" 1 -> Editar Titulo");
                                    System.out.println(" 2 -> Editar Genero");
                                    System.out.println(" 3 -> Editar Autor");
                                    System.out.println(" 4 -> Editar Data da Publicação");
                                    System.out.println(" 5 -> Editar Edição");
                                    System.out.println(" 6 -> Editar Editora");
                                    System.out.println(" 7 -> Editar ISBN");
                                    System.out.println(" 0 -> Voltar");
                                    System.out.print(" > ");
                                    switch (Integer.valueOf(sc.nextLine())) {
                                        case 1:
                                            System.out.print(" Novo Titulo: ");
                                            titulo = sc.nextLine();
                                            if (ValidarDados.validarNome(titulo)) {
                                                livro.editarLivro("titulo", titulo);
                                                livro.setTitulo(titulo);
                                                sair = true;
                                            } else
                                                System.out.println(" Formato inválido ");
                                            break;
                                        case 2:
                                            System.out.print(" Novo Genero: ");
                                            genero = sc.nextLine();
                                            if (ValidarDados.validarGenero(genero)) {
                                                livro.editarLivro("genero", genero);
                                                livro.setGenero(genero);
                                                sair = true;
                                            } else
                                                System.out.println(" Formato inválido ");
                                            break;
                                        case 3:
                                            System.out.print(" Novo Autor: ");
                                            autor = sc.nextLine();
                                            if (ValidarDados.validarAutor(autor)) {
                                                livro.editarLivro("autor", autor);
                                                livro.setAutor(autor);
                                                sair = true;
                                            } else
                                                System.out.println(" Formato inválido ");
                                            break;
                                        case 4:
                                            System.out.print(" Nova Data de Publicação: ");
                                            dataPublicacao = sc.nextLine();
                                            if (ValidarDados.validarDataPublicacao(dataPublicacao)) {
                                                livro.editarLivro("dataPublicacao", dataPublicacao);
                                                livro.setDataPublicacao(dataPublicacao);
                                                sair = true;
                                            } else
                                                System.out.println(" Formato inválido ");
                                            break;
                                        case 5:
                                            System.out.print(" Nova Edicao: ");
                                            edicao = sc.nextLine();
                                            if (ValidarDados.validarEdicao(edicao)) {
                                                livro.editarLivro("edicao", edicao);
                                                livro.setEdicao(edicao);
                                                sair = true;
                                            } else
                                                System.out.println(" Formato inválido ");
                                            break;
                                        case 6:
                                            System.out.print(" Nova Editora: ");
                                            editora = sc.nextLine();
                                            if (ValidarDados.validarEditora(editora)) {
                                                livro.editarLivro("editora", editora);
                                                livro.setEditora(editora);
                                                sair = true;
                                            } else
                                                System.out.println(" Formato inválido ");
                                            break;
                                        case 7:
                                            System.out.print(" Novo Isbn: ");
                                            isbn = sc.nextLine();
                                            if (ValidarDados.validarIsbn(isbn)) {
                                                livro.editarLivro("isbn", isbn);
                                                livro.setIsbn(isbn);
                                                sair = true;
                                            } else
                                                System.out.println(" Formato inválido ");
                                            break;
                                        case 0:
                                            break;
                                    }
                                    System.out.print(" Aperte Enter para Continuar! ");
                                    sc.nextLine();
                                } while (!sair);
                            }
                        } else {
                            System.out.println(" Livro não Encontrado! ");
                            System.out.print("\n Aperte Enter para Continuar");
                            sc.nextLine();
                        }
                        sair = false;
                        break;
                    case 5:
                        LimpaTela();
                        System.out.println(" < Excluir Livro >");
                        System.out.print(" Id do Livro: ");
                        idLivro = Integer.valueOf(sc.nextLine());
                        if (Livro.buscaLivroId(idLivro) != null) {
                            System.out.println(Livro.buscaLivroId(idLivro).toString());
                            System.out.print(" Excluir Livro (1 -> Sim, 2 -> Não): ");
                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                Livro.excluirLivro(idLivro);
                            }
                        } else
                            System.out.println(" Livro não Encontrado!");
                        System.out.print("\n Aperte Enter para Continuar");
                        sc.nextLine();
                        break;
                    case 0:
                        sair = true;
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } while (!sair);
    }

    public static void manterEmprestimo(Scanner sc) throws InterruptedException, IOException {
        boolean sair = false;
        String cpf = null;
        int idLivro;
        ArrayList<Emprestimo> emprestimos = null;
        do {
            try {
                LimpaTela();
                System.out.println(" < Manter Emprestimo >");
                System.out.println(" 1 -> Receber Livro");
                System.out.println(" 2 -> Extender Prazo");
                System.out.println(" 3 -> Listar por Usuário");
                System.out.println(" 4 -> Listar Atrazados");
                System.out.println(" 5 -> Listar Emprestimos");
                System.out.println(" 0 -> Sair");
                System.out.print(" > ");
                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        LimpaTela();
                        System.out.println(" < Receber Livro >");
                        System.out.print(" Cpf: ");
                        cpf = sc.nextLine();
                        System.out.print(" IdLivro: ");
                        idLivro = Integer.valueOf(sc.nextLine());
                        if (Emprestimo.buscaEmprestimo(cpf, idLivro) != null) {
                            Emprestimo emprestimo = Emprestimo.buscaEmprestimo(cpf, idLivro);
                            System.out.println(emprestimo.toString());
                            System.out.print(" Devolver (1 -> Sim, 2 -> Não): ");
                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                Emprestimo.devolverLivro(cpf, idLivro);
                            }
                        } else {
                            System.out.println("\n Empréstimo não Encontrado! ");
                        }
                        System.out.print(" Aperte Enter para Continuar! ");
                        sc.nextLine();
                        break;
                    case 2:
                        LimpaTela();
                        System.out.println(" < Renovar Empréstimo > ");
                        System.out.print(" Cpf: ");
                        cpf = sc.nextLine();
                        System.out.print(" IdLivro: ");
                        idLivro = Integer.valueOf(sc.nextLine());
                        if (Emprestimo.buscaEmprestimo(cpf, idLivro) != null) {
                            Emprestimo emprestimo = Emprestimo.buscaEmprestimo(cpf, idLivro);
                            System.out.println(emprestimo.toString());
                            System.out.print(" Renovar Empréstimo (1 -> Sim, 2 -> Não): ");
                            if (Integer.valueOf(sc.nextLine()) == 1) {
                                Emprestimo.renovarEmprestimo(cpf, idLivro);
                            }
                        } else {
                            System.out.println(" Empréstimo não encontrado! ");
                        }
                        System.out.print(" Aperte Enter para Continuar! ");
                        sc.nextLine();
                        break;
                    case 3:
                        LimpaTela();
                        System.out.println(" < Empréstimos por Usuário > ");
                        System.out.print(" Cpf: ");
                        cpf = sc.nextLine();
                        emprestimos = Emprestimo.listaAbertos(cpf);
                        if (emprestimos.size() != 0) {
                            for (int i = 0; i < emprestimos.size(); i++) {
                                System.out.println(emprestimos.get(i).toString());
                            }
                        } else {
                            System.out.println(" Sem Emprestimos Cadastrados! ");
                        }
                        System.out.print(" Aperte Enter para Continuar! ");
                        sc.nextLine();
                        break;
                    case 4:
                        LimpaTela();
                        System.out.println(" < Empréstimos Atrazados > ");
                        emprestimos = Emprestimo.listaAtrazados();
                        if (emprestimos.size() != 0) {
                            for (int i = 0; i < emprestimos.size(); i++) {
                                System.out.println(emprestimos.get(i).toString());
                            }
                        } else {
                            System.out.println(" Sem Emprestimos Atrazados! ");
                        }
                        System.out.print(" Aperte Enter para Continuar! ");
                        sc.nextLine();
                        break;
                    case 5:
                        LimpaTela();
                        emprestimos = Emprestimo.listaTodos();
                        System.out.println(" < Listar Emprestimos >");
                        if (emprestimos.size() != 0) {
                            for (int i = 0; i < emprestimos.size(); i++) {
                                System.out.println(emprestimos.get(i).toString());
                            }
                        } else {
                            System.out.println(" Nenhum Empréstimo Cadastrado! ");
                        }
                        System.out.print(" Aperte Enter Para Continuar! ");
                        sc.nextLine();
                        break;
                    case 0:
                        sair = true;
                        break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } while (!sair);
    }

    /**
     * Método para limpar a tela do terminal
     * 
     * @throws InterruptedException
     * @throws IOException
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

}

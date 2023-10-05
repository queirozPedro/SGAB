import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuTeste {
        public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        boolean sair = false;
        int op;


        while (!sair) {
            // opções do MainProjeto Testes
            op = MenuTest(sc);
            sc.nextLine();
            switch (op) {
                case 1: // Usuário
                    MenuUsuario(sc, op);
                    break;
                case 2: // Cliente
                    MenuCliente(sc, op);
                    break;
                case 3: // Adm
                    MenuAdm(sc, op); 
                    break;
                case 4: // Livro
                    MenuLivro(sc, op);
                    break;
                case 5: // Emprestimo
                    MenuEmprestimo(sc, op);
                    break;
                case 0: // Sair
                    sair = true;
                    return;
                default: // Opção inválida
                    sair = true;
                    return;
            }
        }
        sc.close();
    }

    // MENUS PARA TESTES

    public static int MenuTest(Scanner sc) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println("   Menu Usuário");
        System.out.println("    1 -> Usuário");
        System.out.println("    2 -> Cliente");
        System.out.println("    3 -> Adm");
        System.out.println("    4 -> Livro");
        System.out.println("    5 -> Emprestimo");
        System.out.println("    0 -> Voltar");
        System.out.print("Digite uma opção: ");
        return sc.nextInt();
    }

    public static void MenuUsuario(Scanner sc, int op) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println("   Menu Usuário");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar");
        System.out.print(" Digite uma opção: ");
        op = sc.nextInt();
        sc.nextLine();
        switch (op) {
            case 1:
                LimpaTela();
                System.out.println("Insira os dados do Usuário: ");
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Cpf: ");
                String cpf = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Senha: ");
                String senha = sc.nextLine();
                ArrayList<String> telefone = new ArrayList<String>();
                String aux;
                System.out.println("\nTelefone");
                System.out.println(" 1 -> Inserir 1 telefone");
                System.out.println(" 2 -> Inserir 2 telefones");
                op = sc.nextInt(); sc.nextLine();
                switch (op) {
                    case 2:
                        System.out.print("\nTelefone 1: ");
                        aux = sc.nextLine();
                        telefone.add(aux);
                        System.out.print("Telefone 2: ");
                        aux = sc.nextLine();
                        telefone.add(aux);
                        break;
                    case 1:
                    default:
                        System.out.print("\nTelefone: ");
                        aux = sc.nextLine();
                        telefone.add(aux);
                        break;
                }
                
                Usuario usuario = new Usuario(cpf, nome, senha, email, telefone);
                usuario.criarConta();
                sc.nextLine();
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 0:
            default:
                break;
        }
    }


    public static void MenuCliente(Scanner sc, int op) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println("   Menu Cliente");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar");
        System.out.print(" Digite uma opção: ");
        op = sc.nextInt();
        sc.nextLine();
        switch (op) {
            case 1:
                
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 0:
            default:
                break;
        }
    }

    public static void MenuAdm(Scanner sc, int op) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println("   Menu Adm");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar");
        System.out.print(" Digite uma opção: ");
        op = sc.nextInt();
        sc.nextLine();
        switch (op) {
            case 1:
                
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 0:
            default:
                break;
        }
    }


    public static void MenuLivro(Scanner sc, int op) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println("   Menu Livro");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar");
        System.out.print(" Digite uma opção: ");
        op = sc.nextInt();
        sc.nextLine();
        switch (op) {
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

                // Livro livroAux = new Livro(quantLivros, titulo, genero, autor, null, edicao, editora, isbn, false, false);

                // livroAux.inserirLivro();

                // System.out.println(livroAux);
                sc.nextLine();
                sc.nextLine();
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 0:
            default:
                break;
        }
    }

    public static void MenuEmprestimo(Scanner sc, int op) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println("   Menu Emprestimo");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar");
        System.out.print(" Digite uma opção: ");
        op = sc.nextInt();
        sc.nextLine();
        switch (op) {
            case 1:
                
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 0:
            default:
                break;
        }
    }

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

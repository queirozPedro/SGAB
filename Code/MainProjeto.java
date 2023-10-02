import java.io.IOException;
import java.util.Scanner;

public class MainProjeto{
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        boolean sair = false; int op;

        while (!sair) {
            //opções do MainProjeto principal
            op = MenuPrincipal(sc);
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

    public static int MenuPrincipal(Scanner sc) throws InterruptedException, IOException{
        LimpaTela();
        System.out.println("-------------------------");
        System.out.println("   Menu Principal\n");
        System.out.println("    1 -> Usuário");
        System.out.println("    2 -> Cliente");
        System.out.println("    3 -> Administrador");
        System.out.println("    4 -> Livro");
        System.out.println("    5 -> Empréstimo");
        System.out.println("    0 -> Sair");  
        System.out.println("-------------------------");
        System.out.println(" Digite uma opção: ");
        return sc.nextInt();
    }

    public static void MenuUsuario(Scanner sc, int op) throws InterruptedException, IOException{
        LimpaTela();
        System.out.println("-------------------------");
        System.out.println("   Menu Usuário\n");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar"); 
        System.out.println("-------------------------");
        System.out.println(" Digite uma opção: ");
        op = sc.nextInt(); sc.nextLine();
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

    public static void MenuCliente(Scanner sc, int op) throws InterruptedException, IOException{
        LimpaTela();
        System.out.println("-------------------------");
        System.out.println("   Menu Cliente\n");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar"); 
        System.out.println("-------------------------");
        System.out.println(" Digite uma opção: ");
        op = sc.nextInt(); sc.nextLine();
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

    public static void MenuAdm(Scanner sc, int op) throws InterruptedException, IOException{
        LimpaTela();
        System.out.println("-------------------------");
        System.out.println("   Menu Adm\n");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar"); 
        System.out.println("-------------------------");
        System.out.println(" Digite uma opção: ");
        op = sc.nextInt(); sc.nextLine();
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

    public static void MenuLivro(Scanner sc, int op) throws InterruptedException, IOException{
        LimpaTela();
        System.out.println("-------------------------");
        System.out.println("   Menu Livro\n");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar"); 
        System.out.println("-------------------------");
        System.out.println(" Digite uma opção: ");
        op = sc.nextInt(); sc.nextLine();
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

    public static void MenuEmprestimo(Scanner sc, int op) throws InterruptedException, IOException{
        LimpaTela();
        System.out.println("-------------------------");
        System.out.println("   Menu Emprestimo\n");
        System.out.println("    1 -> Inserir");
        System.out.println("    2 -> Buscar");
        System.out.println("    3 -> Editar");
        System.out.println("    4 -> Remover");
        System.out.println("    0 -> Voltar"); 
        System.out.println("-------------------------");
        System.out.println(" Digite uma opção: ");
        op = sc.nextInt(); sc.nextLine();
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

    public static void LimpaTela() throws InterruptedException, IOException{
        // Isso aqui funciona pra identificar qual SO está sendo usado
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();
        }
    }
}

    // String tipo, busca;
    // System.out.println("Por que parametro deseja buscar o livro:\n1-Titulo;\n2-Autor;\n3-Genero;\n0-Voltar;");
    // int op = sc.nextInt();
    // sc.nextLine();
    // switch (op) {//escolhendo o tipo de busca, que será usada na pesquisa posteriormente
    //     case 1:
    //         tipo = "titulo";
    //         System.out.print("Titulo: ");
    //         busca = sc.nextLine();//recebendo o que queremos buscar
    //         break;
    //     case 2:
    //         tipo = "autor";
    //         System.out.print("Autor: ");
    //         busca = sc.nextLine();
    //         break;
    //     case 3:
    //         tipo = "genero";
    //         System.out.print("Genero: ");
    //         busca = sc.nextLine();
    //         break;
    //     case 0: // sair
    //     default:
    //         return null;
    // }
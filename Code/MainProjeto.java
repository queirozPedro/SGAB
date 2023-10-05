import java.io.IOException;
import java.util.Scanner;

public class MainProjeto {

    // public static void main(String[] args) throws InterruptedException, IOException {
    //     Scanner sc = new Scanner(System.in);
    //     boolean sair = false;
    //     int op;

    //     while (!sair) {
    //         // opções do MainProjeto Testes
    //         op = MenuPrincipal(sc);
    //         sc.nextLine();
    //         switch (op) {
    //             case 1: // Usuário
    //                 MenuLogin(sc, op);
    //                 break;
    //             case 2: // Cliente
    //                 MenuCadastro(sc, op);
    //                 break;
    //             case 0: // Sair
    //                 sair = true;
    //                 return;
    //             default: // Opção inválida
    //                 sair = true;
    //                 return;
    //         }
    //     }

    //     sc.close();
    // }

    public static int MenuPrincipal(Scanner sc) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println(" ---  Menu Principal  ---");
        System.out.println("     1 -> Login");
        System.out.println("     2 -> Registrar-se");
        System.out.println("     0 -> Sair");
        System.out.print("  Digite uma opção: ");
        return sc.nextInt();
    }

    public static void MenuLogin(Scanner sc, int op) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println(" +++  Menu Login  +++");
        System.out.println("     1 -> Login");
        System.out.println("     0 -> Voltar");
        System.out.print("  Escolha uma opção: ");
        op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1:
                LimpaTela();
                System.out.println("Insira o e-mail: ");
                //String email = sc.nextLine();
                System.out.println("Insira o login: ");
                //String login = sc.nextLine();
                break;
            case 0:
            default:
                break;
        }

    }

    public static void MenuCadastro(Scanner sc, int op) throws InterruptedException, IOException {
        LimpaTela();
        System.out.println(" ===  Tela de Cadastro  ===");
        System.out.println("1. Registrar-se");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        op = sc.nextInt();sc.nextLine();
        
        switch (op) {
            case 1:
                LimpaTela();
                System.out.print("Nome: ");
                //String nome = sc.nextLine();
                System.out.print("Cpf: ");
                //String cpf = sc.nextLine();
                System.out.print("Email: ");
                //String email = sc.nextLine();
                System.out.print("Senha: ");
                //String senha = sc.nextLine();
                System.out.println("Telefone");
                System.out.println(" 1 -> Inserir 1");
                System.out.println(" 2 -> Inserir 2");
                System.out.print(" >> ");
                //String[] telefone = new String[2];
                op = sc.nextInt();
                sc.nextLine();
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
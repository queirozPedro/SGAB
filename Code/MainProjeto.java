import java.io.IOException;
import java.util.Scanner;

public class MainProjeto{
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        boolean sair = false;
        int op;

        // Isso aqui funciona pra identificar qual SO está sendo usado
        String osName = System.getProperty("os.name").toLowerCase();
        ProcessBuilder limpaTela;
        if (osName.contains("windows")) {
            limpaTela = new ProcessBuilder("cmd", "/c", "cls");
        } else {
            limpaTela = new ProcessBuilder("sh", "-c", "clear");
        }
        

        while (!sair) {
            //opções do MainProjeto principal
            MainProjeto.MenuPrincipal();
            op = sc.nextInt();
            
            switch (op) {
                case 1: // Usuário
                    limpaTela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Usuário.");
                    sc.nextLine();
                    break;
                case 2: // Cliente  
                    limpaTela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Cliente.");
                    sc.nextLine();
                    break;          
                case 3: // Adm
                    limpaTela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Administrador.");
                    sc.nextLine();
                    break;
                case 4: // Livro
                    limpaTela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Livro.");
                    sc.nextLine();
                    break;
                case 5: // Emprestimo
                    limpaTela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Empréstimo.");
                    sc.nextLine();
                    break;
                case 6: // Voltar
                    limpaTela.inheritIO().start().waitFor();
                    System.out.println("Voltando ao MainProjeto anterior.");
                    sc.nextLine();
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
    public static void MenuPrincipal(){
        System.out.println("-------------------------");
        System.out.println("   MainProjeto Principal\n");
        System.out.println("    1. Usuário");
        System.out.println("    2. Cliente");
        System.out.println("    3. Administrador");
        System.out.println("    4. Livro");
        System.out.println("    5. Empréstimo");
        System.out.println("    6. Voltar");
        System.out.println("    0. Sair");  
        System.out.println("-------------------------");
        System.out.println(" Digite uma opção: ");
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
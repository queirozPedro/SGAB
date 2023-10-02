import java.io.IOException;
import java.util.Scanner;

public class MainProjeto{
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner entrada = new Scanner(System.in);
        boolean sair = false;
        int op;

        // Isso aqui funciona pra identificar qual SO está sendo usado
        String osName = System.getProperty("os.name").toLowerCase();
        String limpar1, limpar2, limpar3;
        if (osName.contains("windows")) {
            limpar1 = "cmd";
            limpar2 = "/c";
            limpar3 = "cls";
        } else {
            limpar1 = "sh";
            limpar2 = "-c";
            limpar3 = "clear";
        }
        // Isso aqui é pra limpar a tela
        ProcessBuilder limpatela = new ProcessBuilder(limpar1, limpar2, limpar3);

        while (!sair) {
            //opções do MainProjeto principal
            MainProjeto.MenuPrincipal();
            op = entrada.nextInt();
            
            switch (op) {
                case 1: // Usuário
                    limpatela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Usuário.");
                    break;
                case 2: // Cliente  
                    limpatela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Cliente.");
                    break;          
                case 3: // Adm
                    limpatela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Administrador.");
                    break;
                case 4: // Livro
                    limpatela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Livro.");
                    break;
                case 5: // Emprestimo
                    limpatela.inheritIO().start().waitFor();
                    System.out.println("Você escolheu a opção Empréstimo.");
                    break;
                case 6: // Voltar
                    limpatela.inheritIO().start().waitFor();
                    System.out.println("Voltando ao MainProjeto anterior.");
                    break;
                case 0: // Sair
                    sair = true;
                    return;
                default: // Opção inválida
                    sair = true;
                    return;
            }
        }
        entrada.close();
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
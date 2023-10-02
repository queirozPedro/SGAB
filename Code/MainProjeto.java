import java.io.IOException;
import java.util.Scanner;

public class MainProjeto {

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        int op = -1;

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
        
        do {
            limpatela.inheritIO().start().waitFor();
            menuInicial();
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    limpatela.inheritIO().start().waitFor();
                    menulogin();


                    break;
                case 2:
                    limpatela.inheritIO().start().waitFor();
                    menuRegistrar();
                    op = sc.nextInt();

                    break;
                case 0:
                    System.out.println(" - Finalizando -");
                    return;
                default:
                    limpatela.inheritIO().start().waitFor();
                    System.out.println("Erro, operação indisponível!");
                    System.out.println("Aperte Enter Para Continuar");
                    sc.nextLine();
                    op = -1;
                    break;
            }
        } while (op != 0);

        sc.close();
    }


    public static void menuInicial(){
        System.out.println(" -- Menu inicial --");
        System.out.println(" 1 -> Login");
        System.out.println(" 2 -> Registrar-se");
        System.out.println(" 0 -> Sair");
        System.out.print(" >> ");
    }

    public static void menulogin(){
        System.out.println(" -- Login --");
        System.out.println(" 1 -> Usuário");
        System.out.println(" 2 -> Admnistrador");
        System.out.println(" 0 -> Sair");
        System.out.print(" >> ");
    }

    public static void menuRegistrar(){
        System.out.println(" -- Registrar --");
        System.out.println(" 1 -> Novo Cliente");
        System.out.println(" 2 -> Novo Administrador");
        System.out.println(" 0 -> Sair");
        System.out.print(" >> ");
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
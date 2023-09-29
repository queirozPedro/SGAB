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
        ProcessBuilder limpatela = new ProcessBuilder(limpar1, limpar2, limpar3);

        do {
            limpatela.inheritIO().start().waitFor();
            menuPrincipal();
            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    
                    break;
                case 2: // Usuario
                    limpatela.inheritIO().start().waitFor();
                    menuUsuario();
                    op = sc.nextInt();
                    sc.nextLine();
                    switch (op) {
                        case 1:
                            Usuario auxUsuario = new Usuario("123"," nome ", "telefone", "senha", "email");
                            auxUsuario.insereUsuario();   

                            break;
                        case 2:
                            System.out.println(Usuario.buscaUsuarioId(2));

                            break;
                        case 3:
                                Usuario.removeUsuario(1);
                            break;
                        case 0:
                        default:
                            op = -1;
                            break;
                    }
                    break;
                default:
                case 0:
                    return;
            }
            System.out.println("Aperte enter para continuar");
            sc.nextLine();
        } while (op != 0);

        

        sc.close();
    }


    public static void menuPrincipal(){
        System.out.println(" -- Menu Principal --");
        System.out.println(" 1 -> Livro");
        System.out.println(" 2 -> Usuário");
        System.out.println(" 0 -> Sair");
    }

    public static void menuUsuario(){
        System.out.println(" -- Menu Usuario --");
        System.out.println(" 1 -> Inserir");
        System.out.println(" 2 -> Buscar");
        System.out.println(" 3 -> Apagar");
        System.out.println(" 0 -> Sair");
    }
}

import java.util.Scanner;

public class MainProjeto {
    
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);
        // Livro livro = new Livro("HELLO WORld", "B", "CU", "2021-01-01", "10ª", "F", "o", true, false, 0, "nu");
        // livro.inserirLivro();
        // Livro livro = Livro.BuscaLivroId(5);
        // System.out.println(livro);
        Livro livro = Livro.BuscaLivro(sc);
        System.out.println(livro);

        sc.close();
    }
}
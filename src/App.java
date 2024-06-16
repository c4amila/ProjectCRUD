import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private double preco;

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Produto() {
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", nome=" + nome + ", preco=" + preco + "]";
    }
}


public class App{
    private static final String FILE_NAME = "produtos.txt";
    private static List<Produto> produtos = new ArrayList<>();

    public static void main(String[] args) {
        carregarDados();
        try (Scanner scanner = new Scanner(System.in)) {
            int opcao;
            
            do {
                exibirMenu();
                opcao = scanner.nextInt();
                scanner.nextLine();  // Consome a nova linha
                
                switch (opcao) {
                    case 1 -> adicionarProduto(scanner);
                    case 2 -> listarProdutos();
                    case 3 -> atualizarProduto(scanner);
                    case 4 -> removerProduto(scanner);
                    case 5 -> buscarProduto(scanner);
                    case 6 -> salvarDados();
                    case 7 -> {
                        System.out.println("Saindo...");
                        salvarDados();
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 7);
        }
    }

    private static void exibirMenu() {
        System.out.println("Menu:");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("3. Atualizar Produto");
        System.out.println("4. Remover Produto");
        System.out.println("5. Buscar Produto");
        System.out.println("6. Salvar Dados");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void adicionarProduto(Scanner scanner) {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        produtos.add(new Produto(id, nome, preco));
        System.out.println("Produto adicionado com sucesso!");
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    private static void atualizarProduto(Scanner scanner) {
        System.out.print("ID do produto a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consome a nova linha
        Produto produto = buscarProdutoPorId(id);
        if (produto != null) {
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Novo preço: ");
            double preco = scanner.nextDouble();
            produto.setNome(nome);
            produto.setPreco(preco);
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void removerProduto(Scanner scanner) {
        System.out.print("ID do produto a ser removido: ");
        int id = scanner.nextInt();
        Produto produto = buscarProdutoPorId(id);
        if (produto != null) {
            produtos.remove(produto);
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void buscarProduto(Scanner scanner) {
        System.out.print("ID do produto a ser buscado: ");
        int id = scanner.nextInt();
        Produto produto = buscarProdutoPorId(id);
        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static Produto buscarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    private static void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(produtos);
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            produtos = (List<Produto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum dado encontrado para carregar.");
        }
    }
}

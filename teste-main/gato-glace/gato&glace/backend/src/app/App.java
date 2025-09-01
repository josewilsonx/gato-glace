package app;

import java.util.Scanner;
import model.Product;
import model.Product.Category;
import model.StockItem;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Cadastro de Produto e Item de Estoque ---");

        String productName;
        Category productCategory = null;
        double productPrice = 0;
        int initialQuantity = 0;

        // 1. Nome do produto
        do {
            System.out.print("Digite o nome do produto: ");
            productName = scanner.nextLine().trim();

            if (!productName.matches(".*[a-zA-Z].*")) {
                System.out.println("❌ Nome inválido. Deve conter letras.");
                productName = ""; // força repetição
            }
        } while (productName.isEmpty());

        // 2. Categoria do produto
        while (productCategory == null) {
            System.out.print("Digite a categoria do produto (1 para Doce, 2 para Cafe): ");
            String input = scanner.nextLine().trim();

            if (input.matches("\\d+")) {
                int categoryInput = Integer.parseInt(input);
                if (categoryInput == 1) {
                    productCategory = Category.DOCE;
                } else if (categoryInput == 2) {
                    productCategory = Category.CAFE;
                } else {
                    System.out.println("❌ Categoria inválida. Use 1 para Doce ou 2 para Cafe.");
                }
            } else {
                System.out.println("❌ Entrada inválida. Digite apenas números (1 ou 2).");
            }
        }

        // 3. Preço do produto
        boolean validPrice = false;
        while (!validPrice) {
            System.out.print("Digite o preço do produto: ");
            String input = scanner.nextLine().trim();

            try {
                productPrice = Double.parseDouble(input);
                if (productPrice < 0) {
                    System.out.println("❌ Preço inválido. O valor deve ser positivo.");
                } else {
                    validPrice = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número válido para o preço.");
            }
        }

        Product newProduct = new Product(productName, productCategory, productPrice);
        System.out.println("\n✅ Produto cadastrado: " + newProduct);

        // 4. Quantidade inicial
        boolean validQuantity = false;
        while (!validQuantity) {
            System.out.print("Digite a quantidade inicial em estoque para este produto: ");
            String input = scanner.nextLine().trim();

            try {
                initialQuantity = Integer.parseInt(input);
                if (initialQuantity < 0) {
                    System.out.println("❌ Quantidade inválida. Deve ser zero ou maior.");
                } else {
                    validQuantity = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número inteiro.");
            }
        }

        StockItem newStockItem = new StockItem(newProduct, initialQuantity);
        System.out.println("✅ Item de estoque cadastrado: " + newStockItem);

        // 5. Adicionar ao estoque
        boolean validAdd = false;
        while (!validAdd) {
            System.out.print("\nQuantos itens você quer adicionar ao estoque? ");
            String input = scanner.nextLine().trim();

            try {
                int addAmount = Integer.parseInt(input);
                if (addAmount < 0) {
                    System.out.println("❌ Valor inválido. Não é possível adicionar quantidade negativa.");
                } else {
                    newStockItem.addQuantity(addAmount);
                    System.out.println("📦 Estoque atualizado: " + newStockItem);
                    validAdd = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número inteiro.");
            }
        }

        // 6. Remover do estoque
        boolean validRemove = false;
        while (!validRemove) {
            System.out.print("Quantos itens você quer remover do estoque? ");
            String input = scanner.nextLine().trim();

            try {
                int removeAmount = Integer.parseInt(input);
                if (removeAmount < 0 || removeAmount > newStockItem.getQuantity()) {
                    System.out.println("❌ Valor inválido. Não é possível remover essa quantidade.");
                } else {
                    newStockItem.removeQuantity(removeAmount);
                    System.out.println("📦 Estoque atualizado: " + newStockItem);
                    validRemove = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número inteiro.");
            }
        }

        scanner.close();
    }
}
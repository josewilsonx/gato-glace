package model;

public class Product {

    private static long nextId = 1;

    private long id;
    private String name;
    private double price;
    private Category category;

    /**
     * Enum Category com descrição
     */
    public enum Category {
        DOCE("Produtos doces"),
        CAFE("Produtos de café");

        private final String descricao;

        Category(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Construtor com validações
     */
    public Product(String name, Category productCategory, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        this.id = nextId++;
        this.name = name;
        this.category = productCategory;
        this.price = price;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Atualiza o preço do produto com validação
     */
    public void setPrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | %s (%s) - R$ %.2f", id, name, category.getDescricao(), price);
    }
}
package model;

public class StockItem {
    private Product product;
    private int quantity;

    /**
     * Construtor com validações
     */
    public StockItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Adiciona quantidade ao estoque com validação
     */
    public void addQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Quantidade a adicionar não pode ser negativa.");
        }
        this.quantity += amount;
    }

    /**
     * Remove quantidade do estoque com validação
     */
    public void removeQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Quantidade a remover não pode ser negativa.");
        }
        this.quantity -= amount;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }

    @Override
    public String toString() {
        return String.format("%s | Estoque: %d", product.toString(), quantity);
    }
}
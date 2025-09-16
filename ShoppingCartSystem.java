import java.util.*;

// Product class (Encapsulation)
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    // Total price for this product
    public double getTotalPrice() {
        return price * quantity;
    }
}

// Abstract Discount class (Polymorphism)
abstract class Discount {
    public abstract double applyDiscount(double total, List<Product> products);
}

// Festive Discount (10%)
class FestiveDiscount extends Discount {
    @Override
    public double applyDiscount(double total, List<Product> products) {
        return total * 0.90;
    }
}

// Bulk Discount (20% off if any product qty > 5)
class BulkDiscount extends Discount {
    @Override
    public double applyDiscount(double total, List<Product> products) {
        for (Product p : products) {
            if (p.getQuantity() > 5) {
                return total * 0.80;
            }
        }
        return total; // no discount if not bulk
    }
}

// Payment interface
interface Payment {
    void pay(double amount);
}

// CashPayment implementation
class CashPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Total Amount Payable: " + amount);
    }
}

// Main class
public class ShoppingCartSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());
        List<Product> cart = new ArrayList<>();

        // Reading product details
        for (int i = 0; i < n; i++) {
            String[] input = sc.nextLine().split(" ");
            String name = input[0];
            double price = Double.parseDouble(input[1]);
            int quantity = Integer.parseInt(input[2]);

            cart.add(new Product(name, price, quantity));
        }

        String discountType = sc.nextLine().trim().toLowerCase();

        // Calculate total
        double total = 0;
        for (Product p : cart) {
            total += p.getTotalPrice();
        }

        // Apply discount
        Discount discount;
        if (discountType.equals("festive")) {
            discount = new FestiveDiscount();
        } else if (discountType.equals("bulk")) {
            discount = new BulkDiscount();
        } else {
            discount = new Discount() { // no discount if invalid
                @Override
                public double applyDiscount(double total, List<Product> products) {
                    return total;
                }
            };
        }

        double finalAmount = discount.applyDiscount(total, cart);

        // Display product details
        for (Product p : cart) {
            System.out.println("Product: " + p.getName() + ", Price: " + p.getPrice() + ", Quantity: " + p.getQuantity());
        }

        // Payment
        Payment payment = new CashPayment();
        payment.pay(finalAmount);

        sc.close();
    }
}

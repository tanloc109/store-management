package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.DailyProduct;
import model.Product;

public class ProductController {

    Scanner sc = new Scanner(System.in);
    protected List<Product> products = new ArrayList<>();

    public boolean addProduct() {
        boolean done = false, check = false;
        try {
            String code, name;
            do {
                System.out.print("Input code of new product: ");
                code = sc.nextLine();
                for (Product product : products) {
                    if (product.getCode().equalsIgnoreCase(code) || code.isEmpty()) {
                        check = true;
                        System.out.println("Code of product cannot duplicate");
                        break;
                    }
                }
                if (code.isEmpty()) {
                    System.out.println("Code of product cannot empty.Please try again.");
                    check = true;
                }
            } while (check);

            do {
                System.out.print("Input name of new product: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Name of product cannot empty. Please try again.");
                    check = true;
                } else {
                    check = false;
                }
            } while (check);
            Product newProduct = new Product(code, name);
            products.add(newProduct);
            done = true;
        } catch (Exception e) {
        }
        return done;
    }

    public boolean addDailyProduct() {
        boolean done = false, check = false;
        try {
            String code, name, type;
            do {
                System.out.print("Input code of new product: ");
                code = sc.nextLine();
                for (Product product : products) {
                    if (product.getCode().equalsIgnoreCase(code) || code.isEmpty()) {
                        check = true;
                        System.out.println("Code of product cannot duplicate");
                        break;
                    }
                }
                if (code.isEmpty()) {
                    System.out.println("Code of product cannot empty.Please try again.");
                    check = true;
                }
            } while (check);

            do {
                System.out.print("Input name of new product: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Name of product cannot empty. Please try again.");
                    check = true;
                } else {
                    check = false;
                }
            } while (check);

            Product newProduct = new DailyProduct(code, name, "Daily");
            products.add(newProduct);
            done = true;
        } catch (Exception e) {
        }
        return done;
    }

    public boolean updateProduct() {
        boolean done = false, exist = false;
        try {
            System.out.print("Input code of product to update: ");
            String codeUpdate = sc.nextLine();
            if (codeUpdate.isEmpty()) {
                System.out.println("Code of product cannot empty. Please try again.");
            }
            for (Product product : products) {
                if (codeUpdate.equalsIgnoreCase(product.getCode())) {
                    exist = true;
                }
            }
            if (exist) {
                String code, name;
                System.out.print("Input new code of product: ");
                code = sc.nextLine();
                if (code.isEmpty()) {
                    System.out.println("Code of product cannot empty. Please try again.");
                    return false;
                }
                for (Product product : products) {
                    if (code.equalsIgnoreCase(product.getCode())) {
                        System.out.println("New code of product cannot duplicate code.");
                        return false;
                    }
                }
                System.out.print("Input new name of product: ");
                name = sc.nextLine();
                if (name.isEmpty()) {
                    System.out.println("Name of product cannot empty. Please try again.");
                    return false;
                }
                for (Product product : products) {
                    if (codeUpdate.equalsIgnoreCase(product.getCode())) {
                        product.setCode(code);
                        product.setName(name);
                        System.out.println("Code of product: " + product.getCode() + ", name of Product: " + product.getName() + " is updated");
                    }
                }
                done = true;

            } else {
                System.out.println("Product does not exist");
                done = false;
            }

        } catch (Exception e) {
        }
        return done;
    }

    public boolean deleteProduct() {
        boolean done = false, exist = false;
        try {
            System.out.print("Input code of product to delete: ");
            String codeDelete = sc.nextLine();
            for (Product product : products) {
                if (codeDelete.equalsIgnoreCase(product.getCode())) {
                    exist = true;
                    if (exist) {
                        System.out.print("Are you sure to remove product with code: " + codeDelete + "(yes/no): ");
                        String confirm = sc.nextLine();
                        if (confirm.equalsIgnoreCase("no")) {
                            return false;
                        }
                        products.remove(product);
                        System.out.println("Product have code: " + codeDelete + " is removed");
                    }
                    break;
                }
            }

            if (!exist) {
                System.out.println("Product does not exist");
                return false;
            }
            return true;
        } catch (Exception e) {
        }
        return done;
    }

    public boolean showAllProduct() {
        boolean done = false;
        try {
            if (products.isEmpty()) {
                System.out.println("No products available.");
            } else {

                System.out.println("List of product: ");
                System.out.println("---------------------------------------------------------");
                System.out.println("|Product code|      Product name      |Availble quantity|");
                System.out.println("|------------|------------------------|-----------------|");
                for (Product product : products) {
                    System.out.printf("|%-12s|%-24s|%17s|\n", product.getCode(), product.getName(), product.getAvailableQuantity());
                }
                System.out.println("---------------------------------------------------------");
            }
            done = true;
        } catch (Exception e) {
        }
        return done;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}

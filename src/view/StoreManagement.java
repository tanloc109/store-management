package view;

import controller.WarehouseController;
import java.util.Scanner;

public class StoreManagement {  
   
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Store Management - @ 2023 by SE171129 - Pham Tan Loc");
        int choice, num;
        final String productLink = "src\\output\\product.dat";
        final String warehouseLink = "src\\output\\warehouse.dat";
        WarehouseController warehouse = new WarehouseController();
        do {
            System.out.println("------------MENU-----------");
            System.out.println("|1 - Manage products      |");
            System.out.println("|2 - Manage Warehouse     |");
            System.out.println("|3 - Report               |");
            System.out.println("|4 - Store data to files  |");
            System.out.println("|5 - Close the application|");
            System.out.println("---------------------------");
            System.out.print("Input your choice: ");
            choice = Integer.parseInt(sc.nextLine());
            while (choice < 1 || choice > 5) {
                System.out.print("Invalid choice. Please try again: ");
                choice = Integer.parseInt(sc.nextLine());
            }

            switch (choice) {
                case 1:
                    System.out.println("--------------------------------");
                    System.out.println("|1 - Add a product             |");
                    System.out.println("|2 - Update product information|");
                    System.out.println("|3 - Delete product            |");
                    System.out.println("|4 - Show all product          |");
                    System.out.println("--------------------------------");
                    System.out.print("Input your choice: ");
                    num = Integer.parseInt(sc.nextLine());
                    while (num < 1 || num > 4) {
                        System.out.print("Invalid choice. Please try again: ");
                        num = Integer.parseInt(sc.nextLine());
                    }
                    switch (num) {
                        case 1:
                            System.out.print("Input type of product you want add. (longlife or daily): ");
                            String typeOfProduct = sc.nextLine();
                            if(typeOfProduct.equalsIgnoreCase("daily")) {
                                if(warehouse.addDailyProduct()) {
                                    System.out.println("Add new product success.");
                                } else {
                                    System.out.println("Add new product fail.");
                                }
                            } else if (typeOfProduct.equalsIgnoreCase("longlife")) {
                                if(warehouse.addProduct()) {
                                    System.out.println("Add new product success.");
                                } else {
                                    System.out.println("Add new product fail.");
                                }
                            } else {
                                System.out.println("Please choose longlife or daily product.");
                            }
                            break;
                        case 2:
                            if (warehouse.updateProduct()) {
                                System.out.println("Update information of product is success");
                            } else {
                                System.out.println("Update information of product is fail");
                            }
                            break;
                        case 3:
                            if (warehouse.deleteProduct()) {
                                System.out.println("Delete product is success");
                            } else {
                                System.out.println("Delete product is fail");
                            }
                            break;
                        case 4:
                            if (warehouse.showAllProduct()) {
                                System.out.println("Show all product is success");
                            } else {
                                System.out.println("Show all product is fail");
                            }
                            break;
                    }
                    break;
                case 2:
                    System.out.println("------------------------------");
                    System.out.println("|1 - Create an import receipt|");
                    System.out.println("|2 - Create an export receipt|");
                    System.out.println("------------------------------");

                    System.out.print("Input your choice: ");
                    num = Integer.parseInt(sc.nextLine());
                    while (num < 1 || num > 2) {
                        System.out.print("Invalid choice. Please try again: ");
                        num = Integer.parseInt(sc.nextLine());
                    }
                    switch (num) {
                        case 1:
                            if (warehouse.showAllProduct() && warehouse.createImportReceipt()) {
                                System.out.println("Create an import receipt is success");
                            } else {
                                System.out.println("Create an import receipt is fail");
                            }
                            break;
                        case 2:
                            if (warehouse.showAllProduct() && warehouse.createExportReceipt()) {
                                System.out.println("Create an export receipt is success");
                            } else {
                                System.out.println("Create an export receipt is else");
                            }
                            break;
                    }
                    break;
                case 3:
                    System.out.println("------------------------------------------------------------------------");
                    System.out.println("|1 - Product that have expired                                         |");
                    System.out.println("|2 - The products that the store is selling                            |");
                    System.out.println("|3 - Products that are running out of stock (Sorted in ascending order)|");
                    System.out.println("|4 - Import/export receipt of a product                                |");
                    System.out.println("------------------------------------------------------------------------");
                    System.out.print("Input your choice: ");
                    num = Integer.parseInt(sc.nextLine());
                    while (num < 1 || num > 4) {
                        System.out.print("Invalid choice. Please try again: ");
                        num = Integer.parseInt(sc.nextLine());
                    }

                    switch (num) {
                        case 1:
                            warehouse.reportExpiredProducts();
                            break;
                        case 2:
                            warehouse.reportSellingProduct();
                            break;
                        case 3:
                            warehouse.reportRunningOutOfStockProduct();
                            break;
                        case 4:
                            if (warehouse.readAndPrintFile(warehouseLink)) {
                                System.out.println("Show history of import and export warehouse success");
                            } else {
                                System.out.println("Show history of import and export warehouse fail");
                            }
                            break;
                    }
                    break;
                case 4:
                    System.out.println("-------------------------------------");
                    System.out.println("|1 - Stored information of product  |");
                    System.out.println("|2 - Stored information of warehouse|");
                    System.out.println("-------------------------------------");

                    System.out.print("Input your choice: ");
                    num = Integer.parseInt(sc.nextLine());
                    while (num < 1 || num > 2) {
                        System.out.print("Invalid choice. Please try again: ");
                        num = Integer.parseInt(sc.nextLine());
                    }
                    switch (num) {
                        case 1:
                            if (warehouse.writeFileProduct(productLink, warehouse.getProducts())) {
                                System.out.println("Successfully wrote to the file.");
                            } else {
                                System.out.println("Error occurred while writing to the file.");
                            }
                            break;
                        case 2:
                            if (warehouse.writeFileProduct(warehouseLink, warehouse.getReceipts())) {
                                System.out.println("Successfully wrote to the file.");
                            } else {
                                System.out.println("Error occurred while writing to the file.");
                            }
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Goodbye.");
                    break;
            }
        } while (choice != 5);
    }
}

package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import model.DailyProduct;
import model.ItemReceipt;
import model.Product;
import model.Receipt;

public class WarehouseController extends ProductController {

    Scanner sc = new Scanner(System.in);
    private List<Receipt> receipts = new ArrayList<>();
    int code = 999999;

    public boolean createImportReceipt() {
        boolean done = false;
        Receipt importReceipt = new Receipt(++code, "import");
        try {

            boolean continueAdding = true;

            while (continueAdding) {
                System.out.println("Enter product details for import.");
                System.out.print("Product code: ");
                String productCode = sc.nextLine();

                // Nhập số lượng hàng hóa
                System.out.print("Quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());

                // Kiểm tra số lượng nhập không âm
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }

                // Kiểm tra sản phẩm tồn tại trong kho
                Product productToUpdate = null;
                for (Product product : products) {
                    if (product.getCode().equalsIgnoreCase(productCode)) {
                        productToUpdate = product;
                        break;
                    }
                }

                Date manufacturingDate = null, expirationDate = null;
                boolean loop = true;
                long millis = System.currentTimeMillis();
                java.sql.Date today = new java.sql.Date(millis);
                while (loop) {

                    // Nhập ngày sản xuất
                    System.out.print("Manufacturing Date (yyyy-MM-dd): ");
                    String manufacturingDateStr = sc.nextLine();
                    manufacturingDate = Date.valueOf(manufacturingDateStr);

                    if (manufacturingDate.after(today)) {
                        System.out.println("Manufacturing Date cannot after today. Please try again.");
                        loop = true;
                        break;
                    } else {
                        loop = false;
                    }

                    // Nhập ngày hạn sử dụng
                    if (productToUpdate instanceof DailyProduct) {
                        expirationDate = manufacturingDate;
                    } else {
                        System.out.print("Expiration Date (yyyy-MM-dd): ");
                        String expirationDateStr = sc.nextLine();
                        expirationDate = Date.valueOf(expirationDateStr);
                        if (manufacturingDate.after(expirationDate)) {
                            System.out.println("Manufacturing Date cannot after expiration Date. Please try again.");
                            loop = true;
                        } else {
                            loop = false;
                        }

                        if (expirationDate.before(today)) {
                            System.out.println("Expired products cannot be imported.");
                            loop = true;
                        } else {
                            loop = false;
                        }
                    }

                }

                if (productToUpdate != null) {
                    // Tạo WarehouseItem và thêm nó vào phiếu nhập
                    ItemReceipt item = new ItemReceipt(productCode, quantity, manufacturingDate, expirationDate);
                    importReceipt.addItem(item);
                    // Cập nhật số lượng trong kho
                    productToUpdate.setAvailableQuantity(productToUpdate.getAvailableQuantity() + quantity);
                    System.out.print("Do you want to add another product to import receipt? (yes/no): ");
                    String choice = sc.nextLine();
                    if (!choice.equalsIgnoreCase("yes")) {
                        continueAdding = false;
                    }
                } else {
                    System.out.println("Product with code " + productCode + " does not exist.");
                }
            }

            receipts.add(importReceipt);
            done = true;
        } catch (Exception e) {

        }
        return done;
    }

    public boolean createExportReceipt() {
        boolean done = false;
        try {
            Receipt exportReceipt = new Receipt(++code, "export");
            boolean continueAdding = true;

            while (continueAdding) {
                System.out.print("Product code to export: ");
                String productCode = sc.nextLine();

                // Nhập số lượng hàng hóa
                System.out.print("Quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());

                // Kiểm tra số lượng xuất không âm
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }

                // Kiểm tra sản phẩm tồn tại trong kho
                Product productToUpdate = null;
                for (Product product : products) {
                    if (product.getCode().equalsIgnoreCase(productCode)) {
                        productToUpdate = product;
                        break;
                    }
                }

                if (productToUpdate != null) {
                    // Kiểm tra có đủ số lượng trong kho để xuất không
                    if (productToUpdate.getAvailableQuantity() >= quantity) {
                        // Tạo WarehouseItem và thêm nó vào phiếu xuất
                        ItemReceipt item = new ItemReceipt(productCode, quantity);
                        exportReceipt.addItem(item);

                        // Cập nhật số lượng trong kho
                        productToUpdate.setAvailableQuantity(productToUpdate.getAvailableQuantity() - quantity);

                        System.out.print("Do you want to add another product to export receipt? (yes/no): ");
                        String choice = sc.nextLine();
                        if (!choice.equalsIgnoreCase("yes")) {
                            continueAdding = false;
                        }
                    } else {
                        System.out.println("Not enough quantity in stock to export for product: " + productCode);
                    }
                } else {
                    System.out.println("Product with code " + productCode + " does not exist.");
                }
            }

            receipts.add(exportReceipt);
            done = true;
        } catch (Exception e) {
            // Xử lý các exception nếu cần
        }
        return done;
    }

    public void reportExpiredProducts() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print("Input day you want to check (yyyy-MM-dd): ");
        try {
            String checkDateStr = sc.nextLine();
            Date checkDate = Date.valueOf(checkDateStr);
            List<Product> expiredProducts = new ArrayList<>();

            for (Receipt receipt : receipts) {
                for (ItemReceipt item : receipt.getItems()) {
                    if (item.getExpirationDate() != null && item.getExpirationDate().before(checkDate)) {
                        Product product = getProductByCode(item.getProductCode());
                        if (product != null) {
                            expiredProducts.add(product);
                        }
                    }
                }
            }
            printReportProduct(expiredProducts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportSellingProduct() {
        List<Product> sellingProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getAvailableQuantity() > 0) {
                sellingProducts.add(product);
            }
        }
        System.out.println("size: " + sellingProducts.size());
        printReportProduct(sellingProducts);
    }

    public void printReportProduct(List<Product> list) {
        if (list.isEmpty()) {
            System.out.println("No products available.");
        } else {

            System.out.println("List of product: ");
            System.out.println("----------------------------------------------------------");
            System.out.println("|Product code|      Product name      |Availble quantity|");
            System.out.println("|------------|------------------------|-----------------|");
            for (Product product : list) {
                System.out.printf("|%-12s|%-24s|%17s|\n", product.getCode(), product.getName(), product.getAvailableQuantity());
            }
            System.out.println("---------------------------------------------------------");
        }
    }

    public void reportRunningOutOfStockProduct() {
        List<Product> outOfStockProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getAvailableQuantity() < 4) {
                outOfStockProducts.add(product);
            }
        }
        Comparator com = new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return (int) (p1.getAvailableQuantity() - p2.getAvailableQuantity());
            }
        ;
        };
        outOfStockProducts.sort(com);
        System.out.println("size: " + outOfStockProducts.size());
        printReportProduct(outOfStockProducts);
    }

    public boolean writeFileProduct(String path, List list) {
        try {
            FileWriter writer = new FileWriter(path);
            for (Object pro : list) {
                writer.write(pro.toString());
                writer.write("\n");
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean readAndPrintFile(String filePath) {
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line); // In dòng dữ liệu lên màn hình
            }
            bufferedReader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Product getProductByCode(String productCode) {
        for (Product product : products) {
            if (product.getCode().equalsIgnoreCase(productCode)) {
                return product;
            }
        }
        return null;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

}

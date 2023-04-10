import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class Main implements Commands {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final CategoryManager CATEGORY_MANAGER = new CategoryManager();
    private static final ProductManager PRODUCT_MANAGER = new ProductManager();

    public static void main(String[] args) {
        while (true) {
            Commands.printCommands();
            String command = SCANNER.nextLine();
            switch (command) {
                case EXIT -> {
                    return;
                }

                case ADD_CATEGORY -> addCategory();
                case EDIT_CATEGORY_BY_ID -> editCategoryById();
                case DELETE_CATEGORY_BY_ID -> deleteCategoryById();

                case ADD_PRODUCT -> addProduct();
                case EDIT_PRODUCT_BY_ID -> editProductById();
                case DELETE_PRODUCT_BY_ID -> deleteProductById();

                default -> System.out.println("wrong command!");
            }
        }
    }


    // ADD METHODS:
    private static void addProduct() {
        List<Category> categories = CATEGORY_MANAGER.getAll();
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.print("input category id: ");
        int categoryId = Integer.parseInt(SCANNER.nextLine());
        Category category = CATEGORY_MANAGER.getById(categoryId);
        if (category == null) {
            System.out.println("wrong id!");
            return;
        }

        System.out.println("input product name,description,price,quantity");
        String productData = SCANNER.nextLine();
        String[] dataArray = productData.split(",");
        Product product = new Product();

        product.setName(dataArray[0]);
        product.setDescription(dataArray[1]);
        product.setPrice(Integer.parseInt(dataArray[2]));
        product.setQuantity(Integer.parseInt(dataArray[3]));
        product.setCategory(category);

        PRODUCT_MANAGER.save(product);
    }

    private static void addCategory() {
        System.out.print("input category name: ");
        String categoryName = SCANNER.nextLine();
        Category category = new Category();
        category.setName(categoryName);
        CATEGORY_MANAGER.save(category);
    }

    // DELETE METHODS:
    private static void deleteCategoryById() {
        List<Category> categories = CATEGORY_MANAGER.getAll();
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.print("input category id: ");
        int id = Integer.parseInt(SCANNER.nextLine());

        if (CATEGORY_MANAGER.getById(id) != null) {
            CATEGORY_MANAGER.removeById(id);
            System.out.println("success!");
        } else {
            System.out.println("wrong id!");
        }
    }

    private static void deleteProductById() {
        List<Product> products = PRODUCT_MANAGER.getAll();
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.print("input product id: ");
        int id = Integer.parseInt(SCANNER.nextLine());

        if (PRODUCT_MANAGER.getById(id) == null) {
            System.out.println("wrong id!");
            return;
        }

        PRODUCT_MANAGER.removeById(id);
        System.out.println("success!");
    }


    // EDIT METHODS:
    private static void editCategoryById() {
        List<Category> categories = CATEGORY_MANAGER.getAll();
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.print("input category id: ");
        int id = Integer.parseInt(SCANNER.nextLine());

        if (CATEGORY_MANAGER.getById(id) != null) {
            System.out.print("input new category name: ");
            String newName = SCANNER.nextLine();
            Category category = new Category();
            category.setId(id);
            category.setName(newName);
            CATEGORY_MANAGER.update(category);
            System.out.println("success!");
        } else {
            System.out.println("wrong id!");
        }
    }

    private static void editProductById() {
        List<Product> products = PRODUCT_MANAGER.getAll();
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.print("input product id: ");
        int productId = Integer.parseInt(SCANNER.nextLine());
        if (PRODUCT_MANAGER.getById(productId) == null) {
            System.out.println("wrong product id!");
            return;
        }

        List<Category> categories = CATEGORY_MANAGER.getAll();
        for (Category category : categories) {
            System.out.println(category);
        }
        System.out.print("input new category id: ");
        int categoryId = Integer.parseInt(SCANNER.nextLine());
        if (CATEGORY_MANAGER.getById(categoryId) == null) {
            System.out.println("wrong category id!");
            return;
        }

        System.out.println("input product new name,description,price,quantity");
        String newData = SCANNER.nextLine();
        String[] dataArray = newData.split(",");

        Product product = new Product();
        product.setId(productId);
        product.setName(dataArray[0]);
        product.setDescription(dataArray[1]);
        product.setPrice(Integer.parseInt(dataArray[2]));
        product.setQuantity(Integer.parseInt(dataArray[3]));
        product.setCategory(CATEGORY_MANAGER.getById(categoryId));

        PRODUCT_MANAGER.update(product);
        System.out.println("success!");
    }

}
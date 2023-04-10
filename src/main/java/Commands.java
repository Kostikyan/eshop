public interface Commands {
    String EXIT = "0";
    String ADD_CATEGORY = "1";
    String EDIT_CATEGORY_BY_ID = "2";
    String DELETE_CATEGORY_BY_ID = "3";
    String ADD_PRODUCT = "4";
    String EDIT_PRODUCT_BY_ID = "5";
    String DELETE_PRODUCT_BY_ID = "6";

    static void printCommands() {
        System.out.println("input: " + EXIT + " for exit");
        System.out.println("       " + ADD_CATEGORY + " for add category");
        System.out.println("       " + EDIT_CATEGORY_BY_ID + " for edit category by id");
        System.out.println("       " + DELETE_CATEGORY_BY_ID + " for delete category by id");
        System.out.println("       " + ADD_PRODUCT + " for add product");
        System.out.println("       " + EDIT_PRODUCT_BY_ID + " for edit product by id");
        System.out.println("       " + DELETE_PRODUCT_BY_ID + " for delete product by id");
    }
}
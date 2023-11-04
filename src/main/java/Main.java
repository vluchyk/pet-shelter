import service.MenuService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        System.out.println("Welcome! Please specify the name of the shelter: ");
        String name = scanner.next();

        MenuService menuService = new MenuService(scanner);
        System.out.println("What do you want to do?");
        menuService.menu();

        scanner.close();
    }
}

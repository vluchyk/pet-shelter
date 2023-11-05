import enums.Menu;
import service.DataService;
import service.MenuService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        System.out.println("Welcome! Please specify the name of the shelter: ");
        String name = scanner.next();

        MenuService menuService = new MenuService(scanner);
        DataService dataService = new DataService(menuService, name);

        System.out.println("What do you want to do?");
        menuService.menu();
        String command;
        do {
            command = scanner.next().toLowerCase();
            execute(dataService, command);
            if (!Menu.EXIT.getCommand().equals(command)) {
                System.out.println("What do you want to do next?");
            }
        } while (!Menu.EXIT.getCommand().equals(command));

        scanner.close();
    }

    private static void execute(DataService dataService, String command) {
        if (Menu.ADD.getCommand().equals(command)) {
            dataService.add().run();
            dataService.save().run();
        } else if (Menu.TAKE.getCommand().equals(command)) {
            dataService.take().run();
            dataService.save().run();
        } else if (Menu.SHOW.getCommand().equals(command)) {
            dataService.show().run();
        } else if (Menu.EXIT.getCommand().equals(command)) {
            dataService.exit().run();
        } else {
            System.out.println("Incorrect command. Please try again.");
        }
    }
}

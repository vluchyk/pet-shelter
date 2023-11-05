package service;

import enums.Menu;
import model.Animal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class MenuService {
    private Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void menu() {
        System.out.println(Arrays.toString(Menu.values()));
    }

    public Animal add() {
        Animal.AnimalBuilder builder = Animal.builder();
        System.out.println("Specify the name of the pet:");
        builder.name(scanner.next());
        System.out.println("breed: ");
        builder.breed(scanner.next());
        System.out.println("color: ");
        builder.color(scanner.next());
        System.out.println("date of birth (dd/mm/yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        builder.birthDate(LocalDate.parse(scanner.next(), formatter));
        return builder.build();
    }

    public String take() {
        System.out.println("Specify the name of the pet:");
        return scanner.next();
    }

    public void show() {
        System.out.println("Available animals are: ");
    }
}

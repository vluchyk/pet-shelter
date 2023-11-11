package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Animal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class DataServiceTest {

    private Scanner scanner;
    private ObjectMapper mapper;
    private String fileName;
    private String destination;
    private Path path;
    private File shelter;
    private MenuService menuService;
    private DataService dataService;
    private List<Animal> animals;
    private Animal.AnimalBuilder builder;
    private DateTimeFormatter formatter;

    @Rule
    public final TextFromStandardInputStream systemIn = emptyStandardInputStream();

    @Before
    public void init() {
        scanner = new Scanner(System.in);
        mapper = new JsonMapper();
        mapper.registerModule(new JavaTimeModule());

        fileName = "Test";
        destination = "src/main/resources/" + fileName + ".json";
        path = Path.of(destination);
        shelter = new File(destination);

        menuService = new MenuService(scanner);
        dataService = new DataService(menuService, fileName);

        animals = new ArrayList<>();
        builder = Animal.builder();
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        animals.add(builder.name("Alf").breed("Lykoi").color("Grey").birthDate(LocalDate.parse("01/01/2020", formatter)).build());
        animals.add(builder.name("Tom").breed("Birman").color("Black").birthDate(LocalDate.parse("02/02/2021", formatter)).build());
        animals.add(builder.name("Gavrosh").breed("Sphynx").color("Brown").birthDate(LocalDate.parse("25/12/2022", formatter)).build());

        try {
            mapper.writeValue(shelter, animals);
        } catch (IOException e) {
            System.err.println("It is not possible to get information about a specific shelter.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Test
    public void addTest() {
        animals.add(builder.name("Gabby").breed("Ragdoll").color("Grey").birthDate(LocalDate.parse("03/03/2022", formatter)).build());

        systemIn.provideLines("Gabby", "Ragdoll", "Grey", "03/03/2022");
        dataService.add().run();

        dataService.getAnimals().stream().forEach(System.out::println);
        Assert.assertEquals(animals, dataService.getAnimals());
    }

    @Test
    public void takeTest() {
        List<Animal> animals = new ArrayList<>();
        animals.add(builder.name("Alf").breed("Lykoi").color("Grey").birthDate(LocalDate.parse("01/01/2020", formatter)).build());
        animals.add(builder.name("Gavrosh").breed("Sphynx").color("Brown").birthDate(LocalDate.parse("25/12/2022", formatter)).build());

        systemIn.provideLines("Tom");
        dataService.take().run();

        dataService.getAnimals().stream().forEach(System.out::println);
        Assert.assertEquals(animals, dataService.getAnimals());
    }

    @Test
    public void takeNotExistingTest() {
        systemIn.provideLines("Djek");
        dataService.take().run();

        dataService.getAnimals().stream().forEach(System.out::println);
        Assert.assertEquals(animals, dataService.getAnimals());
    }

    @Test
    public void showTest() {
        dataService.show().run();

        Assert.assertEquals(animals, dataService.getAnimals());
    }

    @Test
    public void showFromEmptyTest() {
        int expected = 0;
        String fileName = "Empty";

        MenuService menuService = new MenuService(scanner);
        DataService dataService = new DataService(menuService, fileName);

        dataService.show().run();
        int result = dataService.getAnimals().size();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void saveTest() throws IOException {
        String fileName = "Result";
        String resultDestination = "src/main/resources/" + fileName + ".json";
        Path resultPath = Path.of(resultDestination);

        MenuService menuService = new MenuService(scanner);
        DataService dataService = new DataService(menuService, fileName);

        systemIn.provideLines("Alf", "Lykoi", "Grey", "01/01/2020");
        dataService.add().run();
        systemIn.provideLines("Tom", "Birman", "Black", "02/02/2021");
        dataService.add().run();
        systemIn.provideLines("Gavrosh", "Sphynx", "Brown", "25/12/2022");
        dataService.add().run();
        dataService.save().run();

        Assert.assertEquals(-1, Files.mismatch(path, resultPath));
    }
}
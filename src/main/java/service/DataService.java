package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Animal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private List<Animal> animals;
    private MenuService menuService;
    private ObjectMapper mapper;
    private File shelter;

    public DataService(MenuService menuService, String name) {
        this.menuService = menuService;
        this.mapper = new JsonMapper();
        this.shelter = new File("src/main/resources/" + name + ".json");

        CollectionType animalsType = mapper.getTypeFactory().constructCollectionType(List.class, Animal.class);
        mapper.registerModule(new JavaTimeModule());

        try {
            if (!shelter.exists()) {
                shelter.createNewFile();
                this.animals = new ArrayList<>();
            } else {
                this.animals = mapper.readValue(shelter, animalsType);
            }
        } catch (IOException e) {
            System.err.println("It is not possible to get information about a specific shelter.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Runnable add() {
        return () -> this.animals.add(menuService.add());
    }

    public Runnable take() {
        String name = menuService.take();
        return () -> this.animals.removeIf(animal -> animal.getName().equalsIgnoreCase(name));
    }

    public Runnable show() {
        menuService.show();
        return () -> this.animals.forEach(System.out::println);
    }

    public Runnable save() {
        return () -> {
            try {
                mapper.writeValue(shelter, this.animals);
            } catch (IOException e) {
                System.err.println("It is not possible to save information about a specific shelter.");
                e.printStackTrace();
            }
        };
    }

    public Runnable exit() {
        return save();
    }
}

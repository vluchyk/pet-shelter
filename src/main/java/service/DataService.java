package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import model.Animal;

import java.io.File;
import java.io.IOException;
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

        try {
            if (!shelter.exists()) shelter.createNewFile();
            this.animals = mapper.readValue(shelter, animalsType);
        } catch (IOException e) {
            System.err.println("It is not possible to get information about a specific shelter.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}

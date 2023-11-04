package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Animal {
    private String name;
    private String breed;
    private String color;
    private LocalDate birthDate;
    private int age;

    public Animal(String name, String breed, String color, LocalDate birthDate) {
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.birthDate = birthDate;
        this.age = LocalDate.now().getYear() - birthDate.getYear();
    }

    public static AnimalBuilder builder() {
        return new AnimalBuilder();
    }

    public static class AnimalBuilder {
        private String name;
        private String breed;
        private String color;
        private LocalDate birthDate;
        private int age;

        AnimalBuilder() {
        }

        public AnimalBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AnimalBuilder breed(String breed) {
            this.breed = breed;
            return this;
        }

        public AnimalBuilder color(String color) {
            this.color = color;
            return this;
        }

        public AnimalBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            this.age = LocalDate.now().getYear() - birthDate.getYear();
            return this;
        }

        public Animal build() {
            return new Animal(this.name, this.breed, this.color, this.birthDate);
        }

        public String toString() {
            return "Name=" + this.name + ", breed=" + this.breed + ", color=" + this.color + ", date of the birth=" + this.birthDate + ", age=" + this.age + ")";
        }
    }
}

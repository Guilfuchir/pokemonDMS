package org.example;

import java.io.*;
import java.util.*;

/**
 * PokemonDMS class provides functionalities for managing the Pokémon database, including CRUD operations
 * and the custom feature to compare Pokémon attack stats.
 */
class PokemonDMS {
    public List<Pokemon> pokemonList;
    public Scanner scanner;

    public PokemonDMS() {
        this.pokemonList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }


    public boolean loadDataFromFile(String fileName) {


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    Pokemon p = new Pokemon(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                    pokemonList.add(p);
                }
            }
            System.out.println("Pokémon data loaded successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
            return false;
        }
    }

    public void displayPokemon() {
        if (pokemonList.isEmpty()) {
            System.out.println("No Pokémon available.");
        } else {
            for (Pokemon p : pokemonList) {
                System.out.println(p);
            }
        }
    }

    void addPokemon() {
        System.out.print("Enter Pokémon Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Pokedex Number: ");
        int pokedexNumber = scanner.nextInt();


        System.out.println("Enter Pokémon HP: ");
        int hp = scanner.nextInt();

        System.out.println("Enter Pokémon Attack: ");
        int attack = scanner.nextInt();


        System.out.println("Enter Pokémon Special Attack: ");
        int specialAttack = scanner.nextInt();

        pokemonList.add(new Pokemon(name, pokedexNumber, hp, attack, specialAttack));
        System.out.println("Pokémon added successfully!");
    }

    void updatePokemon() {
        int pokedexNumber = getValidInteger("Enter Pokedex Number of Pokémon to update: ");
        for (Pokemon p : pokemonList) {
            if (p.getPokedexNumber() == pokedexNumber) {
                p.setHp(getValidInteger("Enter new HP: "));
                p.setAttack(getValidInteger("Enter new Attack: "));
                p.setSpecialAttack(getValidInteger("Enter new Special Attack: "));
                System.out.println("Pokémon updated successfully!");
                return;
            }
        }
        System.out.println("Pokémon not found.");
    }

    public void removePokemon() {
        int pokedexNumber = getValidInteger("Enter Pokedex Number of Pokémon to remove: ");
        pokemonList.removeIf(p -> p.getPokedexNumber() == pokedexNumber);
        System.out.println("Pokémon removed successfully!");
    }

    public Pokemon comparePokemon() {
        int firstPokedex = getValidInteger("Enter first Pokémon's Pokedex Number: ");
        int secondPokedex = getValidInteger("Enter second Pokémon's Pokedex Number: ");

        Pokemon first = null;
        Pokemon second = null;
        for (Pokemon p : pokemonList) {
            if (p.getPokedexNumber() == firstPokedex) first = p;
            if (p.getPokedexNumber() == secondPokedex) second = p;
        }

        if (first == null || second == null) {
            System.out.println("One or both Pokémon not found.");
            return null;
        }
        if (first.getAttack() > second.getAttack()) {
            System.out.println("Stronger Pokémon: " + first);
            return first;
        } else {
            System.out.println("Stronger Pokémon: " + second);
            return second;
        }
    }

    public int getValidInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void addPokemon(String name, int pokedex, int hp, int attack, int specialAttack) {
        pokemonList.add(new Pokemon(name, pokedex, hp, attack, specialAttack));
    }
    public boolean removePokemon(int pokedex) {
        for (Pokemon pokemon: pokemonList) {
            if (pokemon.getPokedexNumber() == pokedex){
                pokemonList.remove(pokemon);
                return true;
            }
        }
        return false;
    }

    public boolean updatePokemon(String name, int pokedex, int hp, int attack, int specialAttack, int startingPokedex){
        for (Pokemon pokemon: pokemonList) {
            if (pokemon.getPokedexNumber() == startingPokedex){
                pokemon.setName(name);
                pokemon.setHp(hp);
                pokemon.setPokedexNumber(pokedex);
                pokemon.setAttack(attack);
                pokemon.setSpecialAttack(specialAttack);

                return true;
            }
        }
        return false;
    }

    public boolean comparePokemon (int pokedex1, int pokedex2){
        Pokemon pokemon1 = null;
        Pokemon pokemon2 = null;
        for (Pokemon pokemon: pokemonList) {
            if (pokemon.getPokedexNumber() == pokedex1){
                pokemon1 = pokemon;
            }
            if(pokemon.getPokedexNumber() == pokedex2){
                pokemon2 = pokemon;
            }
        }

        if(pokemon1.getAttack() > pokemon2.getAttack()){
            return true;
        }else{
            return false;
        }
    }

}
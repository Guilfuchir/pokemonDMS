package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class PokemonTest
{
    private PokemonDMS dms;
     @BeforeEach
    void setupPokemonDatabaseManagementSystem() {
        dms = new PokemonDMS();
    }

    @Test
    void addingPokemonSuccess() {
        String pokemonName = "Pikachu";
        String simulatedInput = "Pikachu\n25\n35\n55\n50";
        InputStream is = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(is);
        dms.scanner = new Scanner(System.in);

        dms.addPokemon();

        List<Pokemon> pokemonList = dms.getPokemonList();

        System.out.println(pokemonList.get(0).toString());
        assertFalse(pokemonList.isEmpty());
        assertEquals(pokemonName, pokemonList.get(0).getName());
    }

    @Test
    void removingPokemonSuccess() {
        String simulatedInput = "Pikachu\n25\n35\n55\n50\n25";
        InputStream is = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(is);
        dms.scanner = new Scanner(System.in);

        dms.addPokemon();

        dms.removePokemon();

        assertTrue(dms.getPokemonList().isEmpty());
    }

    @Test
    void updatingPokemonSuccess() {
        String simulatedInput = "Pikachu\n25\n35\n55\n50\n25\n50\n60\n70\n";
        InputStream is = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(is);
        dms.scanner = new Scanner(System.in);
        int updatedHp = 50;
        int updatedAttack = 60;
        int updatedSpecialAttack = 70;

        dms.addPokemon();

        dms.updatePokemon();

        Pokemon updated = dms.getPokemonList().get(0);

        assertEquals(updatedHp, updated.getHp());
        assertEquals(updatedAttack, updated.getAttack());
        assertEquals(updatedSpecialAttack, updated.getSpecialAttack());
    }

    @Test
    void comparingPokemonSuccess() {
        String simulatedInput = "Squirtle\n7\n44\n48\n50\nJigglypuff\n39\n115\n45\n40\n7\n39\n";
        InputStream is = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(is);
        dms.scanner = new Scanner(System.in);
        String firstPokemonName = "Squirtle";

        dms.addPokemon();
        dms.addPokemon();

        Pokemon stronger = dms.comparePokemon();

        assertEquals(firstPokemonName, stronger.getName());
    }

    @Test
    void loadingDataFromFileSuccess() {
        String fileName = "./pokemon-data.txt";
        String simulatedInput = "./pokemon-data.txt";
        InputStream is = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(is);
        dms.scanner = new Scanner(System.in);
        dms.loadDataFromFile("./pokemon-data.txt");

        System.out.println(dms.getPokemonList().get(0).getName());

        assertFalse(dms.getPokemonList().isEmpty());
    }
}

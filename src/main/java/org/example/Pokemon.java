package org.example;

/**
 * Pokémon class represents a Pokemon with attributes such as name, Pokedex number, HP, attack, and special attack power.
 */
class Pokemon {
    private String name;
    private int pokedexNumber;
    private int hp;
    private int attack;
    private int specialAttack;

    public void setName(String name) {
        this.name = name;
    }

    public void setPokedexNumber(int pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    // Constructor
    public Pokemon(String name, int pokedexNumber, int hp, int attack, int specialAttack) {
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.hp = hp;
        this.attack = attack;
        this.specialAttack = specialAttack;
    }

    // Getters and Setters
    public String getName() { return name; }
    public int getPokedexNumber() { return pokedexNumber; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getSpecialAttack() { return specialAttack; }

    public void setHp(int hp) { this.hp = hp; }
    public void setAttack(int attack) { this.attack = attack; }
    public void setSpecialAttack(int specialAttack) { this.specialAttack = specialAttack; }

    /**
     * Method: toString
     * Purpose: Returns a formatted string representation of the Pokémon object.
     * Return: String containing Pokémon details
     */
    @Override
    public String toString() {
        return "#" + pokedexNumber + " | " + name + " | HP: " + hp + " | Attack: " + attack + " | Special Attack: " + specialAttack;
    }
}
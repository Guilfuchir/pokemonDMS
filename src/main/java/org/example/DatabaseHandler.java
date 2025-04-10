package org.example;

import java.sql.*;

public class DatabaseHandler {
    public static Connection connection;

    /**
     * Establishes a connection to the MySQL database.
     *
     * @param serverName "localhost:3306".
     * @param databaseName "Pokemondb".
     * @param username "root".
     * @param password "Tacos4me".
     */
    public static void startDB(String serverName, String databaseName, String username, String password){
        String driverName = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://" + serverName + "/" + databaseName;


        try{
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

/**
 * Retrieves all Pokémon entries from the DMS.
 */
    public static ResultSet selectPokemon() {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM pokemon");
            ResultSet rs = stm.executeQuery();
            return rs;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new Pokémon to the database.
     *
     * @param pokeDex Pokémon's Pokedex number.
     * @param name Pokémon name.
     * @param hp Hit points.
     * @param attack Attack stat.
     * @param specialAtk Special attack stat.
     */
    public static void addPokemon(int pokeDex, String name, int hp, int attack, int specialAtk){
        try{
            PreparedStatement stm = connection.prepareStatement("INSERT INTO pokemon (PokedexNumber, PokemonName," +
                    " Hp, Attack, SpecialAttack) VALUES (?, ?, ?, ?, ?)");

            stm.setInt(1, pokeDex);
            stm.setString(2, name);
            stm.setInt(3, hp);
            stm.setInt(4, attack);
            stm.setInt(5, specialAtk);

            stm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a Pokémon from the DMS.
     *
     * @param pokeDex The Pokedex number of the Pokémon to delete.
     */
    public static void deletePokemon(int pokeDex){
        try{
            PreparedStatement stm = connection.prepareStatement("DELETE FROM pokemon WHERE PokedexNumber=?");
            stm.setInt(1, pokeDex);
            stm.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing Pokémon in the DMS.
     *
     * @param name Updated Pokémon name.
     * @param pokedex Updated Pokedex number.
     * @param hp Updated HP value.
     * @param attack Updated Attack value.
     * @param specialAttack Updated Special Attack value.
     * @param startingPokedex Original Pokedex number.
     */
    public static void updatePokemon(String name, int pokedex, int hp, int attack, int specialAttack, int startingPokedex){
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE pokemon SET PokedexNumber=?, PokemonName=?, " +
                    "Hp=?, Attack=?, SpecialAttack=? WHERE PokedexNumber=?");


            stm.setInt(1, pokedex);
            stm.setString(2, name);
            stm.setInt(3, hp);
            stm.setInt(4, attack);
            stm.setInt(5, specialAttack);
            stm.setInt(6, startingPokedex);

            stm.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Compares the Attack values of the two Pokémon.
     *
     * @param pokedex1 First Pokémon's Pokedex number.
     * @param pokedex2 Second Pokémon's Pokedex number.
     * @return The Pokedex number of the Pokémon with the higher Attack stat.
     */
    public static int comparePokemon(int pokedex1, int pokedex2){
        try{
            PreparedStatement stm1 = connection.prepareStatement("SELECT * FROM pokemon WHERE PokedexNumber=?");
            PreparedStatement stm2 = connection.prepareStatement("SELECT * FROM pokemon WHERE PokedexNumber=?");

            stm1.setInt(1, pokedex1);
            stm2.setInt(1, pokedex2);

            ResultSet rs1 = stm1.executeQuery();
            ResultSet rs2 = stm2.executeQuery();

            int attack1 = 0;
            int attack2 = 0;

            while (rs1.next()){
                attack1 = rs1.getInt("Attack");
            }
            while(rs2.next()){
                attack2 = rs2.getInt("Attack");
            }

            if(attack1 > attack2){
                return pokedex1;
            }else{
                return pokedex2;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }


}

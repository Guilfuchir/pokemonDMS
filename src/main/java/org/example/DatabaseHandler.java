package org.example;

import java.sql.*;

public class DatabaseHandler {
    public static Connection connection;


    public static void startDB(String serverName, String databaseName, String username, String password){
        String driverName = "com.mysql.jdbc.Driver";
//        String serverName = "localhost:3306";
//        String databaseName = "Pokemondb";
        String url = "jdbc:mysql://" + serverName + "/" + databaseName;
//        String username = "root";
//        String password = "Tacos4me";

        try{
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


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

    public static void deletePokemon(int pokeDex){
        try{
            PreparedStatement stm = connection.prepareStatement("DELETE FROM pokemon WHERE PokedexNumber=?");
            stm.setInt(1, pokeDex);
            stm.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

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

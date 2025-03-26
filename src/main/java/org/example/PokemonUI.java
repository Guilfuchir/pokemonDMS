package org.example;

import javax.sql.rowset.serial.SerialStruct;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.util.List;
/*
Reinaldo Guilfuchi
CEN3024C
3/12/2025
This class is being used to create the PokemonDMS UI
*
*/
public class PokemonUI {
    private PokemonDMS dms;
    private JFrame frame;
    private JTextArea displayArea;
    private JTextField nameField, pokedexField, hpField, attackField, specialAttackField;

    public PokemonUI() {
        // Creating frame
        dms = new PokemonDMS();
        frame = new JFrame("Pokemon DMS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Pokedex Number:"));
        pokedexField = new JTextField();
        inputPanel.add(pokedexField);

        inputPanel.add(new JLabel("HP:"));
        hpField = new JTextField();
        inputPanel.add(hpField);

        inputPanel.add(new JLabel("Attack:"));
        attackField = new JTextField();
        inputPanel.add(attackField);

        inputPanel.add(new JLabel("Special Attack:"));
        specialAttackField = new JTextField();
        inputPanel.add(specialAttackField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Creating button panel to house buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add Pokemon");
        addButton.addActionListener(e -> addPokemon());
        buttonPanel.add(addButton);

        JButton displayButton = new JButton("Display Pokemon");
        displayButton.addActionListener(e -> displayPokemon());
        buttonPanel.add(displayButton);

        // Remove button listener
        JButton removeButton = new JButton("Remove Pokemon");
        removeButton.addActionListener(e -> {
            int pokedex = Integer.parseInt(JOptionPane.showInputDialog("Enter Pokedex number of the Pokemon to remove:"));
            DatabaseHandler.deletePokemon(pokedex);
            displayPokemon();
        });
        buttonPanel.add(removeButton);

        // Update button listener
        JButton updateButton = new JButton("Update Pokemon");
        updateButton.addActionListener(e -> {
            try {
                int pokedexStart = Integer.parseInt(JOptionPane.showInputDialog("Enter Pokedex number of the Pokemon to update:"));
                String name = nameField.getText();
                int pokedex = Integer.parseInt(pokedexField.getText());
                int hp = Integer.parseInt(hpField.getText());
                int attack = Integer.parseInt(attackField.getText());
                int specialAttack = Integer.parseInt(specialAttackField.getText());

                DatabaseHandler.updatePokemon(name, pokedex, hp, attack, specialAttack, pokedexStart);
                JOptionPane.showMessageDialog(null, "Pokemon updated.");
                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
            }
        });
        buttonPanel.add(updateButton);

        // Compare button listener
        JButton compareButton = new JButton("Compare Pokemon");
        compareButton.addActionListener(e -> {
            int pokedex1 = Integer.parseInt(JOptionPane.showInputDialog("Enter first Pokedex number:"));
            int pokedex2 = Integer.parseInt(JOptionPane.showInputDialog("Enter second Pokedex number:"));

            int pokdexStronger = DatabaseHandler.comparePokemon(pokedex1, pokedex2);
            JOptionPane.showMessageDialog(null, "Pokemon with Pokedex " + pokdexStronger +
                    " is stronger.");
        });
        buttonPanel.add(compareButton);


        // Upload button listener
        JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                if(dms.loadDataFromFile(file.getPath())){
                    JOptionPane.showMessageDialog(null, "File loaded successfully");
                    displayPokemon();
                }else{
                    JOptionPane.showMessageDialog(null, "File failed to load");
                }
            }
        });
//        buttonPanel.add(uploadButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Adds pokemon to DMS
    private void addPokemon() {
        try {
            String name = nameField.getText();
            int pokedex = Integer.parseInt(pokedexField.getText());
            int hp = Integer.parseInt(hpField.getText());
            int attack = Integer.parseInt(attackField.getText());
            int specialAttack = Integer.parseInt(specialAttackField.getText());

            DatabaseHandler.addPokemon(pokedex, name, hp, attack, specialAttack);
            JOptionPane.showMessageDialog(null, "Pokemon added");
            displayPokemon();
            clearFields();
        } catch (NumberFormatException ex) {
            displayArea.setText("Invalid input! Please enter correct values.");
        }
    }

    // Displays all Pokémon
    private void displayPokemon() {
        try{
            ResultSet rs = DatabaseHandler.selectPokemon();
            StringBuilder sb = new StringBuilder();
            while(rs.next()){
                int pokedex = rs.getInt("PokedexNumber");
                String name = rs.getString("PokemonName");
                int Hp = rs.getInt("Hp");
                int attack = rs.getInt("attack");
                int specialAttack = rs.getInt("specialAttack");
                sb.append(name).append(" - Pokedex: ").append(pokedex)
                        .append(", HP: ").append(Hp).append(", Attack: ")
                        .append(attack).append(", Special Attack: ")
                        .append(specialAttack).append("\n");
            }
            displayArea.setText(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // clears all the text fields

    private void clearFields() {
        nameField.setText("");
        pokedexField.setText("");
        hpField.setText("");
        attackField.setText("");
        specialAttackField.setText("");
    }
}

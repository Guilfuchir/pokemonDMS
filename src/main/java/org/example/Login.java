package org.example;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    /**
     * Constructs the login window, where the user can input server information and connect to the DMS.
     */
    public Login() {
        setTitle("Database Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel serverLabel = new JLabel("Server:");
        JTextField serverField = new JTextField();

        JLabel dbLabel = new JLabel("Database Name:");
        JTextField dbField = new JTextField();

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(serverLabel);
        panel.add(serverField);
        panel.add(dbLabel);
        panel.add(dbField);
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel()); // Empty space
        panel.add(loginButton);

        /**
         * Handles the login actions when the "Login" button is pressed.
         */

        loginButton.addActionListener(e -> {
            String server = serverField.getText();
            String database = dbField.getText();
            String username = userField.getText();
            String password = new String(passField.getPassword());

            try{
                DatabaseHandler.startDB(server, database, username, password);
                if(DatabaseHandler.connection != null){
                    new PokemonUI();
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Connection failed. Please enter correct information");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });

        add(panel);
        setVisible(true);
    }
}

package pages;

import dal.admins.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class SignUpPage extends JFrame {
    private final AdminDAO adminDao = new AdminDAO();
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton signUpButton;

    public SignUpPage() {
        setTitle("Admin Sign Up");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Closes only this window
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        signUpButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(signUpButton, gbc);

        signUpButton.addActionListener(e -> handleSignUp());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleSignUp() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        boolean success = adminDao.createAdmin(username, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose(); // Close sign-up window
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create account. Username may already exist.");
        }
    }
}
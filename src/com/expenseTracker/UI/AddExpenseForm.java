package com.expenseTracker.UI;
import com.expenseTracker.DB.Expense;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class AddExpenseForm extends JFrame {
    private JTextField sourceField;
    private JTextField amountField;
    private JTextField dateField;
    private JButton addButton, backButton;

    public AddExpenseForm() {
        setTitle("Add Expense");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel sourceLabel = new JLabel("Source:");
        sourceField = new JTextField(15);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(15);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateField = new JTextField(15);

        addButton = new JButton("Add Expense");
        backButton = new JButton("Back to Expense");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(sourceLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(sourceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(addButton, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(backButton, gbc);

        add(mainPanel);

        addButton.addActionListener(new AddExpenseAction());

        backButton.addActionListener(e -> {
            new ExpenseForm().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private class AddExpenseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amount = amountField.getText();
            String source = sourceField.getText();
            String date = dateField.getText();

            if (amount.isEmpty() || source.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(AddExpenseForm.this,
                        "Source, Amount, and Date are required.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Expense.insertExpense(source, Double.parseDouble(amount), date);
                JOptionPane.showMessageDialog(AddExpenseForm.this,
                        "Expense added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                new ExpenseForm().setVisible(true);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddExpenseForm.this,
                        "Please enter a valid number for the amount.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(AddExpenseForm.this,
                        "Failed to add expense: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void clearFields() {
            sourceField.setText("");
            amountField.setText("");
            dateField.setText("");
        }
    }
}

package com.expenseTracker.UI;
import com.expenseTracker.Model.ExpenseModel;
import com.expenseTracker.DB.Expense;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseForm extends JFrame {
    private JTable expenseTable;
    private JButton backButton, addExpenseButton, deleteExpenseButton, editExpenseButton;

    public ExpenseForm() {
        setTitle("Expense");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        expenseTable = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Source", "Amount", "Date"});
        expenseTable.setModel(model);
        panel.add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        populateExpenseTable(model);

        backButton = new JButton("Back to Dashboard");
        addExpenseButton = new JButton("Add Expense");
        deleteExpenseButton = new JButton("Delete Expense");
        editExpenseButton = new JButton("Edit Expense");

        backButton.addActionListener(e -> {
            new DashboardForm().setVisible(true);
            dispose();
        });

        addExpenseButton.addActionListener(e -> {
            new AddExpenseForm().setVisible(true);
            dispose();
        });

        deleteExpenseButton.addActionListener(e -> deleteSelectedExpense(model));

        editExpenseButton.addActionListener(e -> editSelectedExpense(model));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(addExpenseButton);
        buttonPanel.add(editExpenseButton);
        buttonPanel.add(deleteExpenseButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        setVisible(true);
    }

    private void populateExpenseTable(DefaultTableModel model) {
        try {
            List<ExpenseModel> expenseList = Expense.getAllExpense();
            for (ExpenseModel expense : expenseList) {
                model.addRow(new Object[]{
                        expense.getId(),
                        expense.getSource(),
                        expense.getAmount(),
                        expense.getDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading expense data.");
            e.printStackTrace();
        }
    }

    private void deleteSelectedExpense(DefaultTableModel model) {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this record?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Expense.deleteExpenseById(id);
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Expense record deleted successfully.");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting expense record.");
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.");
        }
    }

    private void editSelectedExpense(DefaultTableModel model) {
        int selectedRow = expenseTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String source = (String) model.getValueAt(selectedRow, 1);
            double amount = (double) model.getValueAt(selectedRow, 2);
            String date = (String) model.getValueAt(selectedRow, 3);

            String newSource = JOptionPane.showInputDialog(this, "Edit Source:", source);
            String newAmountStr = JOptionPane.showInputDialog(this, "Edit Amount:", amount);
            String newDate = JOptionPane.showInputDialog(this, "Edit Date (yyyy-MM-dd):", date);

            if (newSource == null || newSource.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Source is required.");
                return;
            }

            if (newAmountStr == null || newAmountStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Amount is required.");
                return;
            }

            double newAmount;
            try {
                newAmount = Double.parseDouble(newAmountStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.");
                return;
            }

            if (newDate == null || newDate.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Date is required.");
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(newDate);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please enter a date in the format yyyy-MM-dd.");
                return;
            }

            try {
                Expense.updateExpenseById(id, newSource, newAmount, newDate);
                model.setValueAt(newSource, selectedRow, 1);
                model.setValueAt(newAmount, selectedRow, 2);
                model.setValueAt(newDate, selectedRow, 3);
                JOptionPane.showMessageDialog(this, "Expense record updated successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating expense record.");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record to edit.");
        }
    }

}

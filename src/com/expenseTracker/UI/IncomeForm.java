package com.expenseTracker.UI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.expenseTracker.DB.Income;
import com.expenseTracker.Model.IncomeModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class IncomeForm extends JFrame {
    private JTable incomeTable;
    private JButton backButton, addIncomeButton, deleteIncomeButton, editIncomeButton;

    public IncomeForm() {
        setTitle("Income");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        incomeTable = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Source", "Amount", "Date"});
        incomeTable.setModel(model);
        panel.add(new JScrollPane(incomeTable), BorderLayout.CENTER);

        populateIncomeTable(model);

        backButton = new JButton("Back to Dashboard");
        addIncomeButton = new JButton("Add Income");
        deleteIncomeButton = new JButton("Delete Income");
        editIncomeButton = new JButton("Edit Income");

        backButton.addActionListener(e -> {
            new DashboardForm().setVisible(true);
            dispose();
        });

        addIncomeButton.addActionListener(e -> {
            new AddIncomeForm().setVisible(true);
            dispose();
        });

        deleteIncomeButton.addActionListener(e -> deleteSelectedIncome(model));

        editIncomeButton.addActionListener(e -> editSelectedIncome(model));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(addIncomeButton);
        buttonPanel.add(editIncomeButton);
        buttonPanel.add(deleteIncomeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        setVisible(true);
    }

    private void populateIncomeTable(DefaultTableModel model) {
        try {
            List<IncomeModel> incomeList = Income.getAllIncome();
            for (IncomeModel income : incomeList) {
                model.addRow(new Object[]{
                        income.getId(),
                        income.getSource(),
                        income.getAmount(),
                        income.getDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading income data.");
            e.printStackTrace();
        }
    }

    private void deleteSelectedIncome(DefaultTableModel model) {
        int selectedRow = incomeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0); // Get the ID from the first column

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this record?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Income.deleteIncomeById(id);
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Income record deleted successfully.");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error deleting income record.");
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.");
        }
    }

    private void editSelectedIncome(DefaultTableModel model) {
        int selectedRow = incomeTable.getSelectedRow();
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
                Income.updateIncomeById(id, newSource, newAmount, newDate);

                model.setValueAt(newSource, selectedRow, 1);
                model.setValueAt(newAmount, selectedRow, 2);
                model.setValueAt(newDate, selectedRow, 3);

                JOptionPane.showMessageDialog(this, "Income record updated successfully.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating income record.");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record to edit.");
        }
    }

}

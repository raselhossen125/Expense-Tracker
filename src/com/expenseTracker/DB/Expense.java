package com.expenseTracker.DB;
import com.expenseTracker.Model.ExpenseModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    public static List<ExpenseModel> getAllExpense() throws SQLException {
        List<ExpenseModel> expenseList = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT * FROM expense";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String source = rs.getString("source");
            double amount = rs.getDouble("amount");
            String date = rs.getString("date");

            expenseList.add(new ExpenseModel(id, source, amount, date));
        }

        rs.close();
        ps.close();
        conn.close();

        return expenseList;
    }

    public static void insertExpense(String source, double amount, String date) throws SQLException {
        String sql = "INSERT INTO expense (source, amount, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, source);
            stmt.setDouble(2, amount);
            stmt.setString(3, date);
            stmt.executeUpdate();
        }
    }

    public static void updateExpenseById(int id, String newSource, double newAmount, String newDate) throws SQLException {
        String query = "UPDATE expense SET source = ?, amount = ?, date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newSource);
            stmt.setDouble(2, newAmount);
            stmt.setString(3, newDate);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public static void deleteExpenseById(int id) throws SQLException {
        String query = "DELETE FROM expense WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

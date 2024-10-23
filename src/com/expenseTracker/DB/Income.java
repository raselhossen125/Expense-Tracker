package com.expenseTracker.DB;
import com.expenseTracker.Model.IncomeModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Income {
    public static List<IncomeModel> getAllIncome() throws SQLException {
        List<IncomeModel> incomeList = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT * FROM income";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String source = rs.getString("source");
            double amount = rs.getDouble("amount");
            String date = rs.getString("date");

            incomeList.add(new IncomeModel(id, source, amount, date));
        }

        rs.close();
        ps.close();
        conn.close();

        return incomeList;
    }

    public static void insertIncome(String source, double amount, String date) throws SQLException {
        String sql = "INSERT INTO income (source, amount, date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, source);
            stmt.setDouble(2, amount);
            stmt.setString(3, date);
            stmt.executeUpdate();
        }
    }

    public static void updateIncomeById(int id, String newSource, double newAmount, String newDate) throws SQLException {
        String query = "UPDATE income SET source = ?, amount = ?, date = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newSource);
            stmt.setDouble(2, newAmount);
            stmt.setString(3, newDate);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public static void deleteIncomeById(int id) throws SQLException {
        String query = "DELETE FROM income WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

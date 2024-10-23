package com.expenseTracker.DB;
import java.sql.*;

public class Dashboard {
    public static double getTotalExpenses() {
        String query = "SELECT SUM(amount) AS total FROM expense";
        double totalExpenses = 0.0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalExpenses = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalExpenses;
    }

    public static double getTotalIncome() throws Exception {
        String query = "SELECT SUM(amount) AS total FROM income";
        double totalIncome = 0.0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalIncome = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalIncome;
    }
}

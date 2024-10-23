package com.expenseTracker.UI;
import com.expenseTracker.DB.Expense;
import com.expenseTracker.DB.Income;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ReportForm extends JFrame {

    private JLabel dailyIncomeLabel, dailyExpenseLabel, dailyBalanceLabel;
    private JLabel weeklyIncomeLabel, weeklyExpenseLabel, weeklyBalanceLabel;
    private JLabel monthlyIncomeLabel, monthlyExpenseLabel, monthlyBalanceLabel;
    private JLabel yearlyIncomeLabel, yearlyExpenseLabel, yearlyBalanceLabel;
    private JButton backButton;

    public ReportForm() {
        setTitle("Income & Expense Report");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel to hold the report data
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(4, 3));

        dailyIncomeLabel = new JLabel("Daily Income: ");
        dailyExpenseLabel = new JLabel("Daily Expense: ");
        dailyBalanceLabel = new JLabel("Daily Balance: ");

        weeklyIncomeLabel = new JLabel("Weekly Income: ");
        weeklyExpenseLabel = new JLabel("Weekly Expense: ");
        weeklyBalanceLabel = new JLabel("Weekly Balance: ");

        monthlyIncomeLabel = new JLabel("Monthly Income: ");
        monthlyExpenseLabel = new JLabel("Monthly Expense: ");
        monthlyBalanceLabel = new JLabel("Monthly Balance: ");

        yearlyIncomeLabel = new JLabel("Yearly Income: ");
        yearlyExpenseLabel = new JLabel("Yearly Expense: ");
        yearlyBalanceLabel = new JLabel("Yearly Balance: ");

        try {
            double dailyIncome = Income.getDailyTotalIncome();
            double dailyExpense = Expense.getDailyTotalExpense();
            double dailyBalance = dailyIncome - dailyExpense;

            double weeklyIncome = Income.getWeeklyTotalIncome();
            double weeklyExpense = Expense.getWeeklyTotalExpense();
            double weeklyBalance = weeklyIncome - weeklyExpense;

            double monthlyIncome = Income.getMonthlyTotalIncome();
            double monthlyExpense = Expense.getMonthlyTotalExpense();
            double monthlyBalance = monthlyIncome - monthlyExpense;

            double yearlyIncome = Income.getYearlyTotalIncome();
            double yearlyExpense = Expense.getYearlyTotalExpense();
            double yearlyBalance = yearlyIncome - yearlyExpense;

            dailyIncomeLabel.setText("Daily Income: " + dailyIncome);
            dailyExpenseLabel.setText("Daily Expense: " + dailyExpense);
            dailyBalanceLabel.setText("Daily Balance: " + dailyBalance);

            weeklyIncomeLabel.setText("Weekly Income: " + weeklyIncome);
            weeklyExpenseLabel.setText("Weekly Expense: " + weeklyExpense);
            weeklyBalanceLabel.setText("Weekly Balance: " + weeklyBalance);

            monthlyIncomeLabel.setText("Monthly Income: " + monthlyIncome);
            monthlyExpenseLabel.setText("Monthly Expense: " + monthlyExpense);
            monthlyBalanceLabel.setText("Monthly Balance: " + monthlyBalance);

            yearlyIncomeLabel.setText("Yearly Income: " + yearlyIncome);
            yearlyExpenseLabel.setText("Yearly Expense: " + yearlyExpense);
            yearlyBalanceLabel.setText("Yearly Balance: " + yearlyBalance);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching report data.");
            e.printStackTrace();
        }

        reportPanel.add(dailyIncomeLabel);
        reportPanel.add(dailyExpenseLabel);
        reportPanel.add(dailyBalanceLabel);

        reportPanel.add(weeklyIncomeLabel);
        reportPanel.add(weeklyExpenseLabel);
        reportPanel.add(weeklyBalanceLabel);

        reportPanel.add(monthlyIncomeLabel);
        reportPanel.add(monthlyExpenseLabel);
        reportPanel.add(monthlyBalanceLabel);

        reportPanel.add(yearlyIncomeLabel);
        reportPanel.add(yearlyExpenseLabel);
        reportPanel.add(yearlyBalanceLabel);

        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            new DashboardForm().setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(reportPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}

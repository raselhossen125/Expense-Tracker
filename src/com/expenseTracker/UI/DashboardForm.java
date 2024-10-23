package com.expenseTracker.UI;
import com.expenseTracker.DB.Dashboard;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class DashboardForm extends JFrame {
    private JLabel totalIncomeLabel;
    private JLabel totalExpensesLabel;
    private JLabel balanceLabel;
    private JButton incomeButton, expenseButton, reportButton;

    public DashboardForm() {
        setTitle("Dashboard Overview");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 2, 5, 5));

        totalIncomeLabel = new JLabel();
        totalExpensesLabel = new JLabel();
        balanceLabel = new JLabel();

        statsPanel.add(new JLabel("Total Income:"));
        statsPanel.add(totalIncomeLabel);
        statsPanel.add(new JLabel("Total Expenses:"));
        statsPanel.add(totalExpensesLabel);
        statsPanel.add(new JLabel("Balance:"));
        statsPanel.add(balanceLabel);

        add(statsPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        incomeButton = new JButton("Income");
        expenseButton = new JButton("Expense");
        reportButton = new JButton("Report");

        buttonPanel.add(incomeButton);
        buttonPanel.add(expenseButton);
        buttonPanel.add(reportButton);

        add(buttonPanel, BorderLayout.SOUTH);

        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new IncomeForm().setVisible(true);
                dispose();
            }
        });

        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExpenseForm().setVisible(true);
                dispose();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to show the report, implement your report logic here
                JOptionPane.showMessageDialog(DashboardForm.this,
                        "Report generation logic goes here.",
                        "Generate Report", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        updateDashboard();

        setVisible(true);
    }

    private void updateDashboard() {
        try {
            double totalIncome = Dashboard.getTotalIncome();
            double totalExpenses = Dashboard.getTotalExpenses();
            double balance = totalIncome - totalExpenses;

            DecimalFormat df = new DecimalFormat("0.00");
            totalIncomeLabel.setText("৳" + df.format(totalIncome));
            totalExpensesLabel.setText("৳" + df.format(totalExpenses));
            balanceLabel.setText("৳" + df.format(balance));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from the database.");
        }
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            double totalIncome = Dashboard.getTotalIncome();
            double totalExpenses = Dashboard.getTotalExpenses();
            dataset.addValue(totalIncome, "Income", "Total");
            dataset.addValue(totalExpenses, "Expenses", "Total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Financial Overview",
                "Category",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }
}

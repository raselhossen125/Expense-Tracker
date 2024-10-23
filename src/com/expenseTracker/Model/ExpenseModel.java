package com.expenseTracker.Model;

public class ExpenseModel {
    private int id;
    private String source;
    private double amount;
    private String date;

    public ExpenseModel(int id, String source, double amount, String date) {
        this.id = id;
        this.source = source;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

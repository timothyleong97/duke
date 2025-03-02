package expenses;

import commands.DukeException;

import java.util.ArrayList;

public class Expenses {
    private ExpensesStorage storage;
    public ArrayList<Item> credit;
    public ArrayList<Item> debit;
    public double balance = 0;
    public String initialisationErrorMessage;

    /**
     * Create an expenses object that stores credit and debit items and can access storage.
     */
    public Expenses() {
        storage = new ExpensesStorage("expenses.txt");
        try {
            ArrayList<ArrayList<Item>> lists = storage.load();
            credit = lists.get(0);
            debit = lists.get(1);
        } catch (DukeException e) {
            initialisationErrorMessage = e.getMessage();
            credit = new ArrayList<>();
            debit = new ArrayList<>();
        }
        for (Item i:credit) {
            balance += i.amount;
        }
        for (Item i:debit) {
            balance -= i.amount;
        }
    }

    /**
     * Add an item.
     * @param title the name of the item.
     * @param amount the amount.
     * @param isCredit whether this item was an income or expense.
     */
    public void add(String title, double amount, boolean isCredit) {
        if (isCredit) {
            balance += amount;
            credit.add(new Item(title, amount, true));
        } else {
            balance -= amount;
            debit.add(new Item(title, amount, false));
        }
    }

    /**
     * Remove an item.
     * @param index which index no.
     * @param isCredit whether the item is from the credit/debit column.
     * @throws DukeException thrown if index is out of range.
     */
    public void remove(int index, boolean isCredit) throws DukeException {
        try {
            if (isCredit) {
                balance -= credit.get(index).amount;
                credit.remove(index);
            } else {
                balance += debit.get(index).amount;
                debit.remove(index);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("You entered an index out of range!");
        }
    }

    public void save() throws DukeException {
        storage.save(credit,debit);
    }
}



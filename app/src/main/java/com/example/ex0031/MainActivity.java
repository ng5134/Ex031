package com.example.ex0031;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

/**
class that shows list of expences and monthly סיכום
 */
public class MainActivity extends AppCompatActivity {

    private ListView lvExpenses;
    private TextView tvMonthlyTotal;
    private ArrayList<String> displayList;
    private ArrayList<Integer> idList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvExpenses = findViewById(R.id.lvRecords);
        tvMonthlyTotal = findViewById(R.id.tvMonthlyTotal);
        dbHelper = new DatabaseHelper(this);

        lvExpenses.setOnItemLongClickListener((parent, view, position, id) -> {
            showActionDialog(position);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDisplay();
    }

    /**
     *data
     */
    private void refreshDisplay() {
        displayList = new ArrayList<>();
        idList = new ArrayList<>();
        double total = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Expense.TABLE_NAME, null, null, null, null, null, Expense.DATE + " DESC");

        String currentMonth = String.format("%04d-%02d", Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Expense.ID));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(Expense.DESC));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(Expense.AMOUNT));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(Expense.DATE));

                if (date.startsWith(currentMonth)) total += amount;

                displayList.add(desc + " - " + amount + "₪ (" + date + ")");
                idList.add(id);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        lvExpenses.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList));
        tvMonthlyTotal.setText("סך הכל לחודש זה: " + total + "₪");
    }

    private void showActionDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("ניהול הוצאה")
                .setItems(new String[]{"ערוך", "מחק"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(this, Add.class);
                        intent.putExtra("ID", idList.get(position));
                        startActivity(intent);
                    } else {
                        deleteItem(idList.get(position));
                    }
                }).show();
    }

    private void deleteItem(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Expense.TABLE_NAME, Expense.ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        refreshDisplay();
        Toast.makeText(this, "ההוצאה נמחקה", Toast.LENGTH_SHORT).show();
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) startActivity(new Intent(this, Add.class));
        else if (id == R.id.menu_search) startActivity(new Intent(this, Search.class));
        else if (id == R.id.menu_credits) startActivity(new Intent(this, Credits.class));
        return true;
    }
}
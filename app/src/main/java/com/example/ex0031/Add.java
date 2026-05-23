package com.example.ex0031;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class Add extends AppCompatActivity {

    private EditText etDesc, etAmount;
    private Spinner spCategory;
    private TextView tvDate;
    private DatabaseHelper dbHelper;
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etDesc = findViewById(R.id.et_description);
        etAmount = findViewById(R.id.et_amount_input);
        spCategory = findViewById(R.id.sp_category_selector);
        tvDate = findViewById(R.id.tv_date_display);
        dbHelper = new DatabaseHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        spCategory.setAdapter(adapter);
    }

    public void showDatePickerDialog(View view) {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (v, year, month, day) -> {
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
            tvDate.setText(selectedDate);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void saveExpense(View view) {
        String desc = etDesc.getText().toString();
        String amount = etAmount.getText().toString();

        if (desc.isEmpty() || amount.isEmpty() || selectedDate.isEmpty()) {
            Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Expense.DESC, desc);
        values.put(Expense.AMOUNT, Double.parseDouble(amount));
        values.put(Expense.CATEGORY, spCategory.getSelectedItem().toString());
        values.put(Expense.DATE, selectedDate);

        db.insert(Expense.TABLE_NAME, null, values);
        db.close();

        Toast.makeText(this, "ההוצאה נשמרה!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
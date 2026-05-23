package com.example.ex0031;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * class
 */
public class Search extends AppCompatActivity {

    private EditText etDesc, etMin, etMax;
    private Spinner spCategory, spSort;
    private ListView lvResults;
    private TextView tvSummary;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etDesc = findViewById(R.id.et_search_description);
        etMin = findViewById(R.id.et_min_price);
        etMax = findViewById(R.id.et_max_price);
        spCategory = findViewById(R.id.sp_search_category);
        spSort = findViewById(R.id.sp_sort_options);
        lvResults = findViewById(R.id.lv_search_results);
        tvSummary = findViewById(R.id.tv_search_summary);

        dbHelper = new DatabaseHelper(this);

        // spenners
        String[] cats = {"הכל", "אוכל", "בילויים", "בריאות", "לימודים", "אחר"};
        spCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cats));

        String[] sorts = {"תאריך יורד", "תאריך עולה", "סכום יורד", "סכום עולה"};
        spSort.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sorts));
    }

    public void performSearch(View view) {
        StringBuilder query = new StringBuilder();
        ArrayList<String> args = new ArrayList<>();

        // bulding an if
        if (!etDesc.getText().toString().isEmpty()) {
            query.append(Expense.DESC).append(" LIKE ?");
            args.add("%" + etDesc.getText().toString() + "%");
        }

        if (!etMin.getText().toString().isEmpty()) {
            if (query.length() > 0) query.append(" AND ");
            query.append(Expense.AMOUNT).append(" >= ?");
            args.add(etMin.getText().toString());
        }


        String selection = query.length() > 0 ? query.toString() : null;
        String[] selectionArgs = args.isEmpty() ? null : args.toArray(new String[0]);

        Log.d("SQL_DEBUG", "Executing Filter: " + (selection == null ? "SELECT ALL" : selection));

        refreshSearchResults(selection, selectionArgs);
    }

    private void refreshSearchResults(String selection, String[] args) {
        ArrayList<String> dataList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(Expense.TABLE_NAME, null, selection, args, null, null, null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String desc = c.getString(c.getColumnIndexOrThrow(Expense.DESC));
                dataList.add(desc);
                c.moveToNext();
            }
        }
        c.close();
        db.close();

        lvResults.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList));
        tvSummary.setText("נמצאו: " + dataList.size() + " פריטים");
    }
}
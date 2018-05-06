package com.example.irisreitsma.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateData();

//        // link listviews
//        ListView list = findViewById(R.id.listview);
//        //EntryDatabase db = EntryDatabase.getInstance(this);
//        //Cursor cursor = db.selectAll();
//        //EntryAdapter adapter = new EntryAdapter(this, cursor);
//        //list.setAdapter(adapter);
//        list.setOnItemClickListener(new ListClickListener());
//        list.setOnItemLongClickListener(new ListLongClickListener());
    }

    // shows entry if click
    private class ListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String mood = cursor.getString(cursor.getColumnIndex("mood"));
            String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));

            JournalEntry entry = new JournalEntry(title,content,mood,timestamp);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("clicked_entry", entry);
            startActivity(intent);
        }
    }

    // delete if long click
    private class ListLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
            int entry = cursor.getInt(cursor.getColumnIndex("_id"));
            db.delete(entry);

            updateData();

            return true;
        }
    }

    // when plus button is clicked go to other activity
    public void addClicked(View v) {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }

    // keeps data updated
    public void updateData() {
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();
        EntryAdapter adapter = new EntryAdapter(MainActivity.this, cursor);
        ListView list = findViewById(R.id.listview);
        list.setAdapter(adapter);
    }
}

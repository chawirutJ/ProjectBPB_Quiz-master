package com.example.hp.projectbpb_quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.projectbpb_quiz.adater.QuizListAdapter;
import com.example.hp.projectbpb_quiz.db.DatabaseHelper;
import com.example.hp.projectbpb_quiz.model.Quiz;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    private ListView mListVew;

    private final QuizData quizData = QuizData.getInstance();

    private QuizListAdapter mAdapter;
    private DatabaseHelper mHelper;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListVew = (ListView) findViewById(R.id.listView);

        quizData.quizDataList = new ArrayList<>();

        mHelper = new DatabaseHelper(this);
        mDatabase = mHelper.getWritableDatabase();

        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_NAME3,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME3));
            String picture = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE3));
            String detail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DETAIL3));
            quizData.quizDataList.add(new Quiz(name, picture, detail));
        }

        mAdapter = new QuizListAdapter(
                this,
                R.layout.item,
                quizData.quizDataList
        );

        mListVew.setAdapter(mAdapter);

        mListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Quiz animal = quizData.quizDataList.get(i);
                Toast.makeText(Main3Activity.this, animal.name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Main3Activity.this, QuizDataDetailsActivity.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }
}

package pl.aib.rhymantic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pl.aib.rhymantic.R;
import pl.aib.rhymantic.adapter.RhymesAdapter;
import pl.aib.rhymantic.model.WordResult;
import pl.aib.rhymantic.task.FetchRhymesTask;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;
    private TextView resultLabel;
    private EditText input;
    private Button submit;

    private RhymesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.list = findViewById(R.id.recyclerView);
        this.resultLabel = findViewById(R.id.resultLabel);
        this.input = findViewById(R.id.wordInput);
        this.submit = findViewById(R.id.submit);
        this.adapter = new RhymesAdapter();
        this.setUpList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.submit.setOnClickListener(this::onSubmit);
    }

    private void setUpList() {
        this.list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        this.list.setAdapter(this.adapter);
    }

    private void onSubmit(View submitter) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(submitter.getWindowToken(), 0);

            String word = this.input.getText().toString().trim();
            String value = URLEncoder.encode(word, StandardCharsets.UTF_8.toString());
            FetchRhymesTask task = new FetchRhymesTask();

            task.execute(value);
            List<WordResult> data = task.get();
            this.onDataFetched(word, data);
        } catch (ExecutionException | InterruptedException | UnsupportedEncodingException e) {
            this.adapter.setData(new ArrayList<>());
        }
    }

    private void onDataFetched(String word, List<WordResult> results) {
        this.adjustResultLabel(word, results);
        this.adapter.setData(results);
    }

    private void adjustResultLabel(String word, List<WordResult> results) {
        int count = results.size();
        int color = count > 0 ? R.color.success : R.color.danger;
        this.resultLabel.setTextColor(getApplicationContext().getColor(color));
        this.resultLabel.setText(getApplicationContext().getString(R.string.fetch_result, count, word));
    }
}
package com.dariapro.traincounting.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.activity.MainActivity;
import com.dariapro.traincounting.entity.Question;
import com.dariapro.traincounting.entity.Record;
import com.dariapro.traincounting.view.model.QuestionViewModel;
import com.dariapro.traincounting.view.model.RecordViewModel;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class ScoresFragment extends Fragment {

    public static final int REQUEST_EVENT = 1;

    private RecordViewModel recordViewModel = null;

    private List<Record> recordsMathQuestions = null;
    private List<Record> recordsMathExpressions = null;

    private View view;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.scores_fragment, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.scores_fragment, container,false);

        updateUI();
        updateView();

        return view;
    }

    private void updateUI(){
        if (recordsMathQuestions == null &&
                recordsMathExpressions == null) {
            initData();
        }
    }

    private void initData() {
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        recordViewModel.getRecordByType(0).observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                setRecordsMathQuestions(records);
            }
        });
        recordViewModel.getRecordByType(1).observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                setRecordsMathExpressions(records);
            }
        });
    }

    private void updateView() {
        int countRows = 0;
        TableLayout scoresTable = view.findViewById(R.id.scores_layout);
        if (recordsMathQuestions != null) {
            scoresTable.addView(createTitle(getContext()
                                .getString(R.string.math_questions_scores)), countRows);
            countRows++;
            if (recordsMathQuestions.isEmpty()) {
                scoresTable.addView(createTitle(getContext()
                        .getString(R.string.no_scores)), countRows);
                countRows++;
            }
            else {
                scoresTable.addView(createTitleRow(), countRows);
                countRows++;
                for (Record record : recordsMathQuestions) {
                    scoresTable.addView(createRow(record), countRows);
                    countRows++;
                }
            }
        }
        if (recordsMathExpressions != null) {
            scoresTable.addView(createTitle(getContext()
                    .getString(R.string.math_expressions_scores)), countRows);
            countRows++;
            if (recordsMathExpressions.isEmpty()) {
                scoresTable.addView(createTitle(getContext()
                        .getString(R.string.no_scores)), countRows);
                countRows++;
            }
            else {
                scoresTable.addView(createTitleRow(), countRows);
                countRows++;
                for (Record record : recordsMathExpressions) {
                    scoresTable.addView(createRow(record), countRows);
                    countRows++;
                }
            }
        }
    }

    private TableRow createTitle(String text) {
        TableRow titleRow = new TableRow(getContext());
        TableRow.LayoutParams titleLayoutParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        titleRow.setLayoutParams(titleLayoutParams);
        titleRow.addView(createTextView(text, getResources().getColor(R.color.colorRose),
                         25f));
        titleRow.setGravity(Gravity.CENTER);
        return titleRow;
    }

    private ImageView createIcon(int imageResource) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(imageResource);
        return imageView;
    }

    private TableRow createTitleRow() {
        TableRow row = new TableRow(getContext());
        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(layoutParams);
        row.addView(createTextView(getContext().getString(R.string.score_level),
                getResources().getColor(R.color.colorOcean), 20f));
        row.addView(createIcon(R.drawable.time_28));
        row.addView(createIcon(R.drawable.passed_28));
        return row;
    }

    private TableRow createRow(Record record) {
        TableRow row = new TableRow(getContext());
        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(layoutParams);
        row.addView(createTextView(String.valueOf(record.getLevel()),
                getResources().getColor(R.color.colorOcean), 20f));
        row.addView(createTextView(String.valueOf(record.getTime()),
                getResources().getColor(R.color.colorOcean), 20f));
        row.addView(createTextView(String.valueOf(record.getNumberOfQuestions()),
                getResources().getColor(R.color.colorOcean), 20f));
        return row;
    }

    private TextView createTextView(String text, int color, float size) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(color);
        textView.setTextSize(size);
        return textView;
    }

    private void setRecordsMathQuestions(List<Record> records) {
        this.recordsMathQuestions = records;
        if (recordsMathExpressions != null) {
            updateView();
        }
    }

    private void setRecordsMathExpressions(List<Record> records) {
        this.recordsMathExpressions = records;
        if (recordsMathQuestions != null) {
            updateView();
        }
    }
}

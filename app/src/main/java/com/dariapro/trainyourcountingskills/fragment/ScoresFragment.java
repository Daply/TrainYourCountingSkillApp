package com.dariapro.trainyourcountingskills.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Record;
import com.dariapro.trainyourcountingskills.viewmodel.RecordViewModel;

import java.util.List;

/**
 * @author Pleshchankova Daria
 *
 */
public class ScoresFragment extends Fragment {

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
        recordViewModel.getRecordsByTypeSortedByLevel(0).observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                setRecordsMathQuestions(records);
            }
        });
        recordViewModel.getRecordsByTypeSortedByLevel(1).observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                setRecordsMathExpressions(records);
            }
        });
    }

    private void updateView() {
        createScoresTable(R.id.questions_scores_layout, recordsMathQuestions);
        createScoresTable(R.id.expressions_scores_layout, recordsMathExpressions);
    }

    private void createScoresTable(int layoutId, List<Record> records) {
        int countRows = 0;
        TableLayout scoresTable = view.findViewById(layoutId);
        if (records != null) {
            if (records.isEmpty()) {
                scoresTable.addView(createNoScoresTitleRow(getContext()
                        .getString(R.string.no_scores)), countRows);
            }
            else {
                scoresTable.addView(createDataTitleRow(), countRows);
                countRows++;
                for (Record record : records) {
                    scoresTable.addView(createDataRow(record), countRows);
                    countRows++;
                }
            }
        }
    }

    private TableRow createNoScoresTitleRow(String text) {
        TableRow titleRow = createRow();
        titleRow.addView(createTextView(text, getResources().getColor(R.color.colorOcean),
                         20f, 0));
        titleRow.setGravity(Gravity.CENTER_HORIZONTAL);
        return titleRow;
    }

    private TableRow createDataTitleRow() {
        TableRow row = createRow();
        row.addView(createTextView(getContext().getString(R.string.score_level),
                getResources().getColor(R.color.colorOcean), 21f, R.drawable.cell_shape));
        row.addView(createIcon(R.drawable.time_28));
        row.addView(createIcon(R.drawable.passed_28));
        return row;
    }

    private TableRow createDataRow(Record record) {
        TableRow row = createRow();
        row.addView(createTextView(String.valueOf(record.getLevel()),
                getResources().getColor(R.color.colorWhite), 20f, R.drawable.cell_shape));
        row.addView(createTextView(String.valueOf(record.getTime()) + " min",
                getResources().getColor(R.color.colorWhite), 20f, R.drawable.cell_shape));
        row.addView(createTextView(String.valueOf(record.getNumberOfQuestions()) + " question(s)",
                getResources().getColor(R.color.colorWhite), 20f, R.drawable.cell_shape));
        row.setGravity(Gravity.CENTER_HORIZONTAL);
        return row;
    }

    private TableRow createRow() {
        TableRow row = new TableRow(getContext());
        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(layoutParams);
        return row;
    }

    private ImageView createIcon(int imageResource) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(imageResource);
        imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_shape));
        return imageView;
    }

    private TextView createTextView(String text, int color, float size, int borderDrawable) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(color);
        textView.setTextSize(size);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        if (borderDrawable != 0) {
            textView.setBackground(ContextCompat.getDrawable(getContext(), borderDrawable));
        }
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

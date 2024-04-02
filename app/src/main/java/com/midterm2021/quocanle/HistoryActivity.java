package com.midterm2021.quocanle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.midterm2021.quocanle.DAO.ActionDAO;
import com.midterm2021.quocanle.databaseUtil.AppDatabase;
import com.midterm2021.quocanle.databinding.ActivityHistoryBinding;
import com.midterm2021.quocanle.model.ActionExcute;
import com.midterm2021.quocanle.model.ActionExcuteAdapter;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private static HistoryActivity instance;
    private ActivityHistoryBinding binding;
    private ArrayList<ActionExcute> actionList;
    public ActionExcuteAdapter adapter;

    public HistoryActivity() {
        instance = this;
    }

    public static HistoryActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        ActionDAO actionDAO = appDatabase.actionDAO();

        actionList = new ArrayList<>();
        adapter = new ActionExcuteAdapter(actionList, this);
        binding.rvHistory.setAdapter(adapter);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(this));

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                actionList.addAll(actionDAO.getAll());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        for (ActionExcute action : actionList) {
                            Log.d("Debug", "action: " + action.getInput() + " " + action.getActionDo() + " " + action.getOutput());
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        menu.findItem(R.id.app_bar_back).setOnMenuItemClickListener(item -> {
            onBackPressed();
            return true;
        });
        menu.findItem(R.id.app_bar_delete).setOnMenuItemClickListener(item -> {
            AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
            ActionDAO actionDAO = appDatabase.actionDAO();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    actionDAO.deleteAll(actionList);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            actionList.clear();
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            });
            return true;
        });
        return true;
    }
}
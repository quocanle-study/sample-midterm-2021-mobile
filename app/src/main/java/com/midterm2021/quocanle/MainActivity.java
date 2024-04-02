package com.midterm2021.quocanle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.midterm2021.quocanle.DAO.ActionDAO;
import com.midterm2021.quocanle.databaseUtil.AppDatabase;
import com.midterm2021.quocanle.databinding.ActivityMainBinding;
import com.midterm2021.quocanle.model.ActionExcute;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private ActivityMainBinding binding;
    private static final String[] itemsSpiner = {"count-letter-digit", "remove-even", "count-upper-lower", "perfect-number"};

    public MainActivity() {
        instance = this;
    }

    public static Activity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,itemsSpiner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
//        binding.spinner.setOnItemSelectedListener(this);

        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        ActionDAO actionDAO = appDatabase.actionDAO();

        binding.btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etInput.getText().toString().isEmpty()) {
                    CharSequence text = "Please enter input!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    return;
                }
                try {
                    String input = binding.etInput.getText().toString();
                    String selected = binding.spinner.getSelectedItem().toString();
                    String result = "";
                    if (selected.equals("count-letter-digit")) {
                        result = countLetterDigit(input);
                    } else if (selected.equals("remove-even")) {
                        result = removeEven(input);
                    } else if (selected.equals("count-upper-lower")) {
                        result = countUpperLower(input);
                    } else if (selected.equals("perfect-number")) {
                        result = perfectNumber(input);
                    }
                    String finalResult = result;
                    binding.tvResult.setText(finalResult);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            actionDAO.insertAll(new ActionExcute(input, selected, finalResult));
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            private String perfectNumber(String input) {
                String[] numbers = input.split(", ");
                String result = "";
                for (String num : numbers) {
                    int n = Integer.parseInt(num);
                    int sum = 0;
                    for (int i = 1; i < n; i++) {
                        if (n % i == 0) {
                            sum += i;
                        }
                    }
                    if (sum == n) {
                        result += n + ", ";
                    }
                }
                if (!result.equals("")) {
                    result = result.substring(0, result.length() - 2);
                }
                return result;
            }

            private String countUpperLower(String input) {
                int upper = 0;
                int lower = 0;
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (Character.isUpperCase(c)) {
                        upper++;
                    } else if (Character.isLowerCase(c)) {
                        lower++;
                    }
                }
                return "Upper case: " + upper + "\nLower case: " + lower;
            }

            private String removeEven(String input) {
                String[] numbers = input.split(", ");
                String result = "";
                for (String num : numbers) {
                    int n = Integer.parseInt(num);
                    if (n % 2 != 0) {
                        result += n + ", ";
                    }
                }
                if (!result.equals("")) {
                    result = result.substring(0, result.length() - 2);
                }
                return result;
            }

            private String countLetterDigit(String input) {
                int letter = 0;
                int digit = 0;
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (Character.isLetter(c)) {
                        letter++;
                    } else if (Character.isDigit(c)) {
                        digit++;
                    }
                }
                return "Letter: " + letter + "\nDigit: " + digit;
            }
        });
    }
}
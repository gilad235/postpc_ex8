package huji.postpc.y2021.giladtal.roots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.Operation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public Holder holder = null;
    Adapter adapter = null;
    App myApp = null; //todo maybe holder should hold the app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton doneButton = findViewById(R.id.buttonCreateTodoItem);
        EditText rootCal = findViewById(R.id.editTextInsertTask);
        if (myApp==null){
            myApp = App.getInstance();
        }
        if (holder==null){
            holder = myApp.getDb();
        }
        adapter = new Adapter(holder);

        RecyclerView recyclerView = findViewById(R.id.recyclerTodoItemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        doneButton.setEnabled(false);

        holder.liveDataCal.observe(this, new Observer<List<Calculation>>() {
            @Override
            public void onChanged(List<Calculation> calculations) {
                adapter.notifyDataSetChanged();
            }
        });

        WorkManager workManager = myApp.getWorkManager();
        LiveData<List<WorkInfo>> liveData = workManager.getWorkInfosByTagLiveData("calRoot");
        liveData.observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfo) {
                for (WorkInfo worker:workInfo) {
                    Calculation tempcal = holder.findById(worker.getId().toString());
                    if (tempcal==null)
                    {
                        continue;
                    }
                    Data prog = worker.getProgress();
                    long cur = prog.getLong("current", 0);
                    Data outputData = worker.getOutputData();
                    if (worker.getState() == WorkInfo.State.SUCCEEDED) {
                        boolean prime = outputData.getBoolean("prime",true);
                        tempcal.isPrime=prime;
                        tempcal.setState("Done");
                        if (!prime){
                            tempcal.root1 = outputData.getLong("root1",0);
                            tempcal.root2 = outputData.getLong("root2",0);
                        }
                    }
                    else
                    {
                        tempcal.setState("In progress");
                        tempcal.progress=(int)prog.getLong("progress",0);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        rootCal.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void afterTextChanged(Editable s) {
                // text did change
                String temp = rootCal.getText().toString();
                try {
                    long newcal = Long.parseLong(temp);
                    doneButton.setEnabled(true);
                }
                catch (Exception e){
                    doneButton.setEnabled(false);
                }

            }
        });
        doneButton.setOnClickListener(v -> {
            holder.addNewInProgressItem(Long.parseLong(rootCal.getText().toString()));
            adapter.notifyDataSetChanged();
        });
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        EditText editTextUserInput = findViewById(R.id.editTextInsertTask);
//        FloatingActionButton buttonCalculateRoots = findViewById(R.id.buttonCreateTodoItem);
//        outState.putBoolean("editTextUserEnabaled",editTextUserInput.isEnabled());
//        String str = editTextUserInput.getText().toString();
//        outState.putString("editTextUserstr",str);
//        outState.putBoolean("buttonCalculateRoots",buttonCalculateRoots.isEnabled());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        EditText editTextUserInput = findViewById(R.id.editTextInsertTask);
//        FloatingActionButton buttonCalculateRoots = findViewById(R.id.buttonCreateTodoItem);
//        editTextUserInput.setText(savedInstanceState.getString("editTextUserstr"));
//        editTextUserInput.setEnabled(savedInstanceState.getBoolean("editTextUserEnabaled"));
//        buttonCalculateRoots.setEnabled(savedInstanceState.getBoolean("buttonCalculateRoots"));
    }
}

package huji.postpc.y2021.giladtal.roots;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;
import java.util.UUID;

public class App extends Application {
    private Holder db = null;



    WorkManager workManager=null;
    UUID reqId;
    public Holder getDb(){
        return db;
    }
    private static App instance = null;
    public static App getInstance() {
        return instance;
    }
    LiveData<WorkInfo> workInfoByLiveData;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (db==null){
            db = new Holder(this);
        }
        workManager = WorkManager.getInstance(this);
//        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(RootWorker.class).setInputData().build();//todo maybe change to builder
//        workManager.enqueueUniqueWork("former_req", ExistingWorkPolicy.KEEP,req);
//        reqId = req.getId();
//        workInfoByLiveData = db.workManager.getWorkInfoByIdLiveData(reqId);
//        workInfoByLiveData.observeForever(workInfo -> {
//
//        });
        //todo fix livedata
//        db.LiveDataTodo.observe(this, new Observer<List<Calculation>>() {
//            @Override
//            public void onChanged(List<Calculation> todoItems) {
//                //todo refresh UI
//
//            }
//        });
    }
    public WorkManager getWorkManager() {
        return workManager;
    }
}

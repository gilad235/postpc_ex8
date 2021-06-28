package huji.postpc.y2021.giladtal.roots;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RootWorker extends Worker {

    public RootWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        long rootToCal = getInputData().getLong("root", 0);
        long i;

        for (i = 2; i * i <= rootToCal; i++) {
            if (i % 20000 == 0) {
                setProgressAsync(new Data.Builder().putLong("total", rootToCal)
                        .putString("state","prog").putLong("progress",(i*i*100)/rootToCal).build());
            }
            if (rootToCal%i==0)
            {
                return Result.success(new Data.Builder().putLong("total", rootToCal)
                    .putString("state","done").putLong("root1", i).putBoolean("prime",false)
                    .putLong("root2", rootToCal/i).build());
            }
        }
        return Result.success(new Data.Builder().putLong("total", rootToCal).putBoolean("prime",true)
                .putString("state","done").build());

    }
}

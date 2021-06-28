package huji.postpc.y2021.giladtal.roots;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Holder {
    List<Calculation> all_items;
    private final Context context;
    private final SharedPreferences sp;
    private final MutableLiveData<List<Calculation>> mutableLiveDataCal = new MutableLiveData<>();
    public final LiveData<List<Calculation>> liveDataCal = mutableLiveDataCal;


    Holder(Context context){
        this.context=context;
        this.sp = context.getSharedPreferences("local_db_roots",context.MODE_PRIVATE);
        all_items = new ArrayList<Calculation>();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initialed_from_sp() throws ParseException {
        Set<String> keys = sp.getAll().keySet();
        for (String key: keys){
            String calSavedInstance = sp.getString(key,null);
            Calculation cal = new Calculation(calSavedInstance);
            if (cal!=null){
                all_items.add(cal);
            }
        }

    }
    public ArrayList<Calculation> getCopies(){
        return new ArrayList<>(all_items);
    }
    public @Nullable Calculation getByID(String id){
        if (id==null){return null;}
        for (Calculation calculationItem:all_items){
            if (calculationItem.getId().equals(id)){
                return calculationItem;
            }
        }
        return null;
    }

    public List<Calculation> getCurrentItems() {//todo edit the logic of the lists

        return all_items;
    }

    public void addNewInProgressItem(long root) {

        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(RootWorker.class).addTag("calRoot").
                setInputData(new Data.Builder().putLong("root",root).build()
        ).build();//todo maybe change to builder
        all_items.add(new Calculation(root,req.getId().toString()));
        App.getInstance().workManager.enqueue(req);
        mutableLiveDataCal.setValue(new ArrayList<>(all_items));
    }
    Calculation findById(String id)
    {
        for (Calculation cal:all_items) {
            if (cal.getId().equals(id)){
                return cal;
            }
        }
        return null;
    }

    public void markItemDone(Calculation item) {
//        String curstate = item.getState();
//        if (curstate.equals("In progress"))
//        {
//
//            item.switch_state();
//        }
    }

    public void markItemInProgress(Calculation item) {
//        String curstate = item.getState();
//        if (curstate.equals("Done"))
//        {
//            item.switch_state();
//        }
    }


    public void deleteItem(Calculation item) {
//        String curstate = item.getState();
        all_items.remove(item);
        App.getInstance().workManager.cancelWorkById(UUID.fromString(item.getId()));
        mutableLiveDataCal.setValue(new ArrayList<>(all_items));
    }
    public void edit(String id, String desc){
        Calculation item = getByID(id);
//        if (item!=null){
//            item.setDesc(desc);
//        }
    }

}

package huji.postpc.y2021.giladtal.roots;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.ParseException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public class Calculation implements Serializable{


    private String state; //done to in progress
    private String id = null;
    long rootToCal;
    long root1;
    long root2;
    int progress;
    boolean isPrime;

    public Calculation(String calSavedInstance) throws  ParseException{
        String[] data = calSavedInstance.split("@",3);
        //todo edit
        id = data[0];
        state = data[1];
        rootToCal = Integer.parseInt(data[2]);
    }
    public Calculation() {
        state = "In progress";
        id = UUID.randomUUID().toString();
    }
    Calculation(long d,String id)
    {
        this.id=id;
        state = "In progress";
        rootToCal = d;

    }

    public String itemToString(){
        return id+"@"+state+"@"+rootToCal;
    }
    public void switch_state()
    {
        if(state.equals("In progress"))
        {
            state="Done";

        }
        else state="In progress";
    }

    public String getState() {
        return state;
    }

    public long getRootToCal() {
        return rootToCal;
    }

    public String getId() {
        return id;
    }
    public void setRootToCal(long desc) {
        this.rootToCal = desc;
    }
    public void setState(String state) {
        this.state = state;
    }

}

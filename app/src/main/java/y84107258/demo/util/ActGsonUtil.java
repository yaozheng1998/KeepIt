package y84107258.demo.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import y84107258.demo.model.MyActivity;

public class ActGsonUtil {
    public static ArrayList<MyActivity> getActFromGson(String raw){
        ArrayList<MyActivity> result=new ArrayList<>();
        Gson gson=new Gson();
        ArrayList<MyActivity> acts=gson.fromJson(raw,new TypeToken<List<MyActivity>>(){}.getType());
        if (acts!=null) {
            for (MyActivity a : acts) {
                result.add(a);
            }
        }
        return result;
    }
}

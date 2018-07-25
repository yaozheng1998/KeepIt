package y84107258.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MyActivity {
    private String activityName;
    private String startTime;
    private String endTime;
    private String pic;

    public MyActivity(String name, String stime, String etime, String pic){
        this.activityName=name;
        this.startTime=stime;
        this.endTime=etime;
        this.pic=pic;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json=new JSONObject();
        json.put("name",activityName);
        json.put("start",startTime);
        json.put("end",endTime);
        json.put("pic",pic);
        return json;
    }
    public static MyActivity parseJSONString(String jsonString) throws JSONException {
        JSONObject jsonObject=new JSONObject(jsonString);
        String name=jsonObject.getString("name");
        String start=jsonObject.getString("start");
        String end=jsonObject.getString("end");
        String pic=jsonObject.getString("pic");
        return new MyActivity(name,start,end,pic);
    }
}

package y84107258.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MyActivity {
    private String activityName;
    private String startTime;
    private String endTime;
    private String pic;
    private String alarmTime;
    private String description;

    public MyActivity(String name, String stime, String etime, String pic,String atime,String des){
        this.activityName=name;
        this.startTime=stime;
        this.endTime=etime;
        this.pic=pic;
        this.alarmTime=atime;
        this.description=des;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json=new JSONObject();
        json.put("name",activityName);
        json.put("start",startTime);
        json.put("end",endTime);
        json.put("pic",pic);
        json.put("alarm",alarmTime);
        json.put("des",description);
        return json;
    }
    public static MyActivity parseJSONString(String jsonString) throws JSONException {
        JSONObject jsonObject=new JSONObject(jsonString);
        String name=jsonObject.getString("name");
        String start=jsonObject.getString("start");
        String end=jsonObject.getString("end");
        String pic=jsonObject.getString("pic");
        String alarm=jsonObject.getString("alarm");
        String des=jsonObject.getString("des");
        return new MyActivity(name,start,end,pic,alarm,des);
    }
}

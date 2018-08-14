package y84107258.demo.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.Date;

public class MyActivity extends DataSupport{
    private String activityName;
    private String activityDate;
    private boolean isChecked;
    private String startTime;
    private String endTime;
    private String pic;
    private String alarmTime;
    private String description;
    private String userId;


    public MyActivity(){

    }
    public MyActivity(String name,String activityDate, String stime, String etime, String pic,String atime,String des, String userId,boolean isChecked){
        this.activityName=name;
        this.activityDate=activityDate;
        this.startTime=stime;
        this.endTime=etime;
        this.pic=pic;
        this.alarmTime=atime;
        this.description=des;
        this.userId=userId;
        this.isChecked=isChecked;
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

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json=new JSONObject();
        json.put("name",activityName);
        json.put("date",activityDate);
        json.put("start",startTime);
        json.put("end",endTime);
        json.put("pic",pic);
        json.put("alarm",alarmTime);
        json.put("des",description);
        json.put("userid",userId);
        json.put("ischecked",isChecked);
        return json;
    }
    public static MyActivity parseJSONString(String jsonString) throws JSONException {
        JSONObject jsonObject=new JSONObject(jsonString);
        String name=jsonObject.getString("name");
        String date=jsonObject.getString("date");
        String start=jsonObject.getString("start");
        String end=jsonObject.getString("end");
        String pic=jsonObject.getString("pic");
        String alarm=jsonObject.getString("alarm");
        String des=jsonObject.getString("des");
        String userId=jsonObject.getString("userid");
        boolean isChecked=jsonObject.getBoolean("ischecked");
        return new MyActivity(name,date,start,end,pic,alarm,des,userId,isChecked);
    }
}

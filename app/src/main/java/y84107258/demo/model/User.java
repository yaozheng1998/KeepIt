package y84107258.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 使用sharedpreference存储自定义类型数据，可以使用json转化为string；
 */
public class User {
    private static final String JSON_USERNAME="username";
    private static final String JSON_PASSWORD="password";

    private String username;
    private String password;

    public User(String username,String password){
        this.username=username;
        this.password=password;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json=new JSONObject();
        json.put(JSON_USERNAME,username);
        json.put(JSON_PASSWORD,password);
        return json;
    }
    public static User parseJSONString(String jsonString) throws JSONException {
        JSONObject jsonObject=new JSONObject(jsonString);
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        return new User(username,password);
    }
}

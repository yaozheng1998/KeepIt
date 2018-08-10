package y84107258.demo.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

/**
 * 使用sharedpreference存储自定义类型数据，可以使用json转化为string；
 */
public class User extends DataSupport{
    private static final String JSON_USERNAME="username";
    private static final String JSON_PASSWORD="password";
    private static final String JSON_DES="des";

    private String username;
    private String password;
    private String personalDes;

    public User(String username,String password,String personalDes){
        this.username=username;
        this.password=password;
        this.personalDes=personalDes;
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

    public String getPersonalDes() {
        return personalDes;
    }

    public void setPersonalDes(String personalDes) {
        this.personalDes = personalDes;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json=new JSONObject();
        json.put(JSON_USERNAME,username);
        json.put(JSON_PASSWORD,password);
        json.put(JSON_DES,personalDes);
        return json;
    }
    public static User parseJSONString(String jsonString) throws JSONException {
        JSONObject jsonObject=new JSONObject(jsonString);
        String username=jsonObject.getString("username");
        String password=jsonObject.getString("password");
        String des=jsonObject.getString("des");
        return new User(username,password,des);
    }
}

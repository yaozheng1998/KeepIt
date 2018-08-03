package y84107258.demo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {
    private static SPManager spManager;
    private SharedPreferences sharedPreferences;
    public static String SHARED_PREFS_NAME = "APP";

    public static SPManager singleton(Context context) {
        if (spManager == null) {
            spManager = new SPManager(context, SHARED_PREFS_NAME);
        }
        return spManager;
    }

    private SPManager(Context context, String sharedPrefsName) {
        sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
    }

    public <T> void put(String key, T value) {
        put(new String[] { key }, new Object[] { value });
    }

    public <T> void put(String[] keys, T[] values) {
        if (values != null && keys != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            for (int i = 0; i < values.length; i++) {
                T value = values[i];
                int index = i;
                if (index >= keys.length) {
                    index = keys.length - 1;
                }
                String key = keys[index];
                Class<? extends Object> cls = value.getClass();
                if (cls == Integer.class || cls == int.class) {
                    edit.putInt(key, (Integer) value);
                } else if (cls == Boolean.class || cls == boolean.class) {
                    edit.putBoolean(key, (Boolean) value);
                } else if (cls == Float.class || cls == float.class) {
                    edit.putFloat(key, (Float) value);
                } else if (cls == Long.class || cls == long.class) {
                    edit.putLong(key, (Long) value);
                } else if (cls == String.class) {
                    edit.putString(key, (String) value);
                }
            }
            edit.commit();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defValue) {
        Object result = null;
        if (defValue != null && key != null) {
            Class<? extends Object> cls = defValue.getClass();
            if (cls == Integer.class || cls == int.class) {
                result = sharedPreferences.getInt(key, (Integer) defValue);
            } else if (cls == Boolean.class || cls == boolean.class) {
                result = sharedPreferences.getBoolean(key, (Boolean) defValue);
            } else if (cls == Float.class || cls == float.class) {
                result = sharedPreferences.getFloat(key, (Float) defValue);
            } else if (cls == Long.class || cls == long.class) {
                result = sharedPreferences.getLong(key, (Long) defValue);
            } else if (cls == String.class) {
                result = sharedPreferences.getString(key, (String) defValue);
            }
        }
        return (T) result;
    }

    public void clear() {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }

    public void clear(String key) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(key);
        edit.commit();
    }
}

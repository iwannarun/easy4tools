package com.luckin.stock.utils;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * 
 * 说明：JSON管理
 * 
 * 
 * @date 2015年3月25日
 *
 */
public class JSONTools {
	private static GsonBuilder gb = new GsonBuilder();
	private static Gson gson = null;
	static{
		gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
	    gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
	    gb.disableHtmlEscaping().serializeNulls();//.setPrettyPrinting()格式化
	    gson = gb.create();
	}
	public static Gson getGsonInstance(){
        return gson;
	}
	
	public static String toGsonString(Object obj) {
		return gson.toJson(obj);
	}
	
	public static String toGsonString(String[] excludeNames,Object obj){
		Gson gson = gb.setExclusionStrategies(new JSONKit(excludeNames)).create();
		return gson.toJson(obj);
	}
	
	public static Object parseGSONObject(String jsonString,Class<?> clazz){
		return gson.fromJson(jsonString,clazz);
	}
}

class DateSerializer implements JsonSerializer<Date> {
    public JsonElement serialize(Date src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTime());
    }
}

class DateDeserializer implements JsonDeserializer<java.util.Date> {
    public Date deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
    }
}
/**
 * Gson序列化对象排除属性 
 * 调用方法： String[] keys = { "id" }; 
 * Gson gson = new GsonBuilder().setExclusionStrategies(new JsonKit(keys)).create();
 */
class JSONKit implements ExclusionStrategy {
	String[] keys;

	public JSONKit(String[] keys) {
		this.keys = keys;
	}

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes arg0) {
		for (String key : keys) {
			if (key.equals(arg0.getName())) {
				return true;
			}
		}
		return false;
	}
}
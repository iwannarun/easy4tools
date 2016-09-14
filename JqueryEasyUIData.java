

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;

/**
 * 
 * 说明：针对Jquery EasyUI 数据格式的处理
 * 
 * @date 2015年4月9日
 *
 */
public class JqueryEasyUIData {

	private long total;
	private List<?> rows;
	private String moneyall;
	
	public static String init(Page<?> page){
		JqueryEasyUIData instance = new JqueryEasyUIData();
		instance.setRows(page.getResult());
		instance.setTotal(page.getTotal());
		return JSON.toJSONString(instance, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
	}
	public static String init(Page<?> page,String money){
		JqueryEasyUIData instance = new JqueryEasyUIData();
		instance.setRows(page.getResult());
		instance.setTotal(page.getTotal());
		instance.setMoneyall(money);
		return JSON.toJSONString(instance, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
	}
	
	public static String toJSON(Object o){
        return JSON.toJSONString(o, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public String getMoneyall() {
		return moneyall;
	}

	public void setMoneyall(String moneyall) {
		this.moneyall = moneyall;
	}
	
}

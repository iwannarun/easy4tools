import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.visitor.functions.Char;

public class StringTools {

	public static boolean isNotNull(String s) {
		return (s != null) ? true : false;
	}

	public static boolean isNotEmpty(String s) {
		if (isNotNull(s)) {
			return "".equals(s.trim()) ? false : true;
		}
		return false;
	}

	public static boolean isEmpty(String s) {
		return !isNotEmpty(s);
	}

	/**
	 * 去除网页文档中的标签,不包括&nbsp;
	 * 
	 * @param s
	 * @return
	 */
	public static String delHtmlTag(String s) {
		/** 定义script的正则表达式 **/
		String regScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";
		/** 定义style的正则表达式 **/
		String regStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";
		/** 定义HTML标签的正则表达式 **/
		String regHtml = "<[^>]+>";
		/** 定义空格回车换行符 **/
		String regSpace = "\\s*|\t|\r|\n";

		Pattern pScript = Pattern.compile(regScript, Pattern.CASE_INSENSITIVE);
		Matcher mScript = pScript.matcher(s);
		s = mScript.replaceAll("");

		Pattern pStyle = Pattern.compile(regStyle, Pattern.CASE_INSENSITIVE);
		Matcher mStyle = pStyle.matcher(s);
		s = mStyle.replaceAll("");

		Pattern pHtml = Pattern.compile(regHtml, Pattern.CASE_INSENSITIVE);
		Matcher mHtml = pHtml.matcher(s);
		s = mHtml.replaceAll("");

		Pattern pSpace = Pattern.compile(regSpace, Pattern.CASE_INSENSITIVE);
		Matcher mSpace = pSpace.matcher(s);
		s = mSpace.replaceAll("");
		return s.trim();
	}

	/**
	 * 去除网页文档中的标签,包括&nbsp;
	 * 
	 * @param s
	 * @return
	 */
	public static String delHtmlTagBlank(String s) {
		s = delHtmlTag(s);
		s = s.replaceAll("&nbsp;", "");
		return s;
	}

	public static String arrayToString(Object[] array, String slice,
			boolean serial) {
		if (array == null || array.length <= 0) {
			return "";
		}
		if (isEmpty(slice)) {
			return array.toString();
		} else {
			String s = "";
			for (Object o : array) {
				if (o == null) {
					if (serial) {
						s += slice + "null";
					}
				} else {
					s += slice + o.toString();
				}
			}
			return s.substring(1);
		}
	}

	/**
	 * 判断是否是邮箱
	 */
	public static boolean isEmail(String email) {
		if (!isNotEmpty(email)) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher isNum = pattern.matcher(email);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否是手机号格式
	 * 
	 * @param tele
	 * @return
	 */
	public static boolean isTele(String tele) {
		if (!isNotEmpty(tele)) {
			return false;
		}
		if (tele.length() != 11) {
			return false;
		}
		if (tele.substring(0, 3).equals("147")) {
			return true;
		}
		Pattern pattern = Pattern.compile("1[3|5|7|8|][0-9]{9}");
		Matcher isNum = pattern.matcher(tele);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 用正则表达式 判断是否数字
	 * 
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (!isNotEmpty(str)) {
			return false;
		} else {
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(str).matches();
		}
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @param negative
	 *            是否允许负数
	 * @param positive
	 *            是否允许正数
	 * @param isfloat
	 *            是否允许小数
	 * @return
	 */
	public static boolean isNumberic(String str, boolean negative,
			boolean positive, boolean isfloat) {
		// 判断是否为正整数 ^\\d+$
		// 判断是否为正小数 ^\\d+\\.\\d+$
		// 判断是否为负整数 -\\d+$
		// 判断是否为负小数 ^-\\d+\\.\\d+$

		if (!isNotEmpty(str)) {
			return false;
		}

		Pattern pattern = null;
		boolean flag = false;

		if (negative) {
			pattern = Pattern.compile("-\\d+$");
			flag = pattern.matcher(str).matches();
			if (flag) {
				return true;
			}
			if (isfloat) {
				pattern = Pattern.compile("^-\\d+\\.\\d+$");
				flag = pattern.matcher(str).matches();
				if (flag) {
					return true;
				}
			}
		}
		if (positive) {
			pattern = Pattern.compile("^\\d+$");
			flag = pattern.matcher(str).matches();
			if (flag) {
				return true;
			}
			if (isfloat) {
				pattern = Pattern.compile("^\\d+\\.\\d+$");
				flag = pattern.matcher(str).matches();
				if (flag) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 数字金额大写转换 要用到正则表达式
	 */
	public static String digitUppercase(double n) {

		String fraction[] = { "角", "分" };

		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

		String unit[][] = { { "元", "万", "亿" },

		{ "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";

		n = Math.abs(n);

		String s = "";

		for (int i = 0; i < fraction.length; i++) {

			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
					.replaceAll("(零.)+", "");

		}
		if (s.length() < 1) {
			s = "整";
		}

		int integerPart = (int) Math.floor(n);
		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
					+ s;
		}
		return head
				+ s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "")
						.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

	/**
	 * 校验是否含有非法字符 包含非法字符时会返回非法的字符
	 */
	/*
	 * public static String illegalChar(String str) { if (!isNotEmpty(str)) {
	 * return ""; } String[] IIIEGAL_CHAR = { "+", "#","%", "^", "<", ">", "[",
	 * "]", "{", "}", "/", "\\", "?", "&", "(", ")",";","'","," ,"\"","-","."};
	 * for (int i = 0; i < IIIEGAL_CHAR.length; i++) { int index =
	 * str.indexOf(IIIEGAL_CHAR[i]); if (index > -1) { return IIIEGAL_CHAR[i]; }
	 * } return ""; }
	 */
	/**
	 * 
	 *
	 * 描述:过滤所有特殊字符 包含非法字符时会返回非法的字符
	 *
	 * @author 郭达望
	 * @created 2015年6月18日 上午11:24:42
	 * @since v1.0.0
	 * @param str
	 * @return
	 * @return String
	 */
	public static String illegalChar(String str) {
		if (!isNotEmpty(str)) {
			return "";
		}
		String reg = "^[a-z|A-Z|\\u4e00-\\u9fa5|0-9|_|_]*$";
		if (!str.matches(reg)) {
			return "存在非法字符";
		}
		String[] IIIEGAL_CHAR = { "select", "insert", "update", "delete",
				"drop", "--", "'" };
		for (int i = 0; i < IIIEGAL_CHAR.length; i++) {
			int index = str.indexOf(IIIEGAL_CHAR[i]);
			if (index > -1) {
				return IIIEGAL_CHAR[i];
			}
		}
		return "";
	}

	/**
	 * 去除空格
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 去除换行和制表，不去除空格
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceTabEnter(String str) {
		// \n 回车
		// \t 水平制表符
		// \s 空格
		// \r 换行
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static boolean isIp(String ip) {
		if (isEmpty(ip))
			return false;
		Pattern p = Pattern
				.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
		Matcher m = p.matcher(ip);
		return m.matches();
	}

	/***
	 * 产生一个纯数字随机整数
	 * 
	 * @param point
	 *            整数位数
	 * @return
	 */
	public static String getRandom(int point) {
		String result = String.valueOf(Math.random());
		String f = "#####0";
		if (point > 0) {
			f = "";
			for (int i = 0; i < point - 1; i++) {
				f += "#";
			}
			f += "0";
		}
		BigDecimal rand = new BigDecimal(result);
		BigDecimal one = new BigDecimal(1);
		double d = rand.divide(one, point, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		DecimalFormat df = new DecimalFormat(f);
		String t = df.format(d * Math.pow(10, point));
		if (t.length() < point) {
			int l = point - t.length();
			for (int i = 0; i < l; i++) {
				t += "0";
			}
		}
		return t;
	}

	/**
	 * 身份证号合法性验证
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean isIdCard(String idCard) {
		return IdcardValidator.isValidatedAllIdcard(idCard);
	}

	/**
	 * 银行卡号简单的验证，只做全数字验证
	 * 
	 * @param s
	 * @return
	 */
	public static boolean validBankNum(String s) {
		boolean flag = isNumeric(s);
		if (!flag) {
			return false;
		}
		if (s.length() < 16 || s.length() > 19) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
//	public static double parseDouble(double d) {
//		DecimalFormat df = new DecimalFormat("#.00");
//		d = Double.parseDouble(df.format(d));
//		return d;
//	}

	public static String digtalToAbc(String str) {
		if (!isNotEmpty(str)) {
			str = CalendarTools.formatDateTime(new Date(), "mmssSS");
		}
		String result = "";
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if ('0' == c) {
				result += "a";
			} else if ('1' == c) {
				result += "b";
			} else if ('3' == c) {
				result += "c";
			} else if ('4' == c) {
				result += "d";
			} else if ('5' == c) {
				result += "e";
			} else if ('6' == c) {
				result += "f";
			} else if ('7' == c) {
				result += "g";
			} else if ('8' == c) {
				result += "h";
			} else if ('9' == c) {
				result += "i";
			}
		}
		if (result.length() < 10) {
			result += (getRandom(10 - result.length()));
		}
		return result;
	}

	public static int getChineseLength(String str) {
		str = str.trim();
		if (StringUtils.isBlank(str)) {
			return 0;
		}
		try {
			return str.getBytes("gbk").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 *
	 * 描述:验证中文名字,过滤"."
	 *
	 * @author 郭达望
	 * @created 2015年6月15日 下午4:02:12
	 * @since v1.0.0
	 * @param name
	 * @return
	 * @return boolean
	 */
	public static boolean isChineseName(String name) {
		int count = 0;
		if (name.contains(".") && !name.endsWith(".") && !name.startsWith(".")) {
			count = name.indexOf("..");
			if (count != -1) {
				return false;
			}
			if (name.contains(".")) {
				name = name.replace(".", "");
			}
		} else if (name.contains("•") && !name.endsWith("•")
				&& !name.startsWith("•")) {
			count = name.indexOf("••");
			if (count != -1) {
				return false;
			}
			if (name.contains("•")) {
				name = name.replace("•", "");
			}
		} else if (name.contains("·") && !name.endsWith("·")
				&& !name.startsWith("·")) {
			count = name.indexOf("··");
			if (count != -1) {
				return false;
			}
			if (name.contains("·")) {
				name = name.replace("·", "");
			}
		} else if (name.contains("●") && !name.endsWith("●")
				&& !name.startsWith("●")) {
			count = name.indexOf("●●");
			if (count != -1) {
				return false;
			}
			if (name.contains("●")) {
				name = name.replace("●", "");
			}
		} else if (name.contains("．") && !name.endsWith("．")
				&& !name.startsWith("．")) {
			count = name.indexOf("．．");
			if (count != -1) {
				return false;
			}
			if (name.contains("．")) {
				name = name.replace("．", "");
			}
		}
		Pattern pattern = Pattern
				.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,20}$");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			return true;
		}
		return false;

	}

	/**
	 * 
	 *
	 * 描述:默认昵称：ID*3+随机数（0-2）
	 *
	 * @author 郭达望
	 * @created 2015年6月16日 下午5:30:35
	 * @since v1.0.0
	 * @param id
	 * @return
	 * @return String
	 */
	public static String randomNickName(Integer id, Integer type) {
		int i = (int) (Math.random() * 3);
		int num = id * 3 + i;
		int j = type == null ? 0 : type;
		String str = "用户";
		String name = str + num;
		return name;
	}

	/**
	 * 
	 *
	 * 描述:验证字符串长度是否符合要求，一个汉字等于两个字符
	 *
	 * @author 郭达望
	 * @created 2015年9月30日 上午11:15:43
	 * @since v1.0.0
	 * @param strParameter要验证的字符串
	 * @param limitLength验证的长度
	 * @return
	 * @return boolean
	 */
	public static boolean validateStrByLength(String strParameter,
			int limitLength) {
		int temp_int = 0;
		byte[] b = strParameter.getBytes();

		for (int i = 0; i < b.length; i++) {

			if (b[i] >= 0) {
				temp_int = temp_int + 1;
			} else {
				temp_int = temp_int + 2;
				i = i + 2;
			}
		}
		if (temp_int > limitLength) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkURL(String url) {
		if(isEmpty(url)){ return false;}
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}
	
	/**
	 * 数字+字母验证
	 */
	public static boolean isNumbericLetter(String param){
		if(StringUtils.isEmpty(param)){
			return false;
		}
		
		for(int i=0; i< param.length() ; i++){
			char c = param.charAt(i);
			if(!((c>='0' && c<='9')||(c>='a' && c<='z')|| (c>='A' && c<='Z'))){
				return false;
			}
		}
		
		boolean numbericFlag = false;
		boolean letterFlag = false;
		for(int i=0; i< param.length() ; i++){
			char c = param.charAt(i);
			if((c>='0' && c<='9')){
				numbericFlag = true;
			}
			
			if((c>='a' && c<='z')|| (c>='A' && c<='Z')){
				letterFlag = true;
			}
		}
		
		return numbericFlag&&letterFlag;
	}
}

package com.luckin.stock.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.luckin.stock.crypt.Md5Encrypter;

/**
 * 
 * 说明：文件处理
 * 
 * @author zheng_zhi_rui@163com
 * @date 2015年3月25日
 *
 */
public class FileTools {
	public static final String WIN_FILEPATH = "D:/User/";
	public static final String LINUX_FILEPATH = "/data/webserver/stock/user";

	//"/opt/stock_server/user";

	public static String saveFile(MultipartFile multipartFile, String path, String fileName)
	        throws IllegalStateException, IOException {
		if (StringUtils.isNotBlank(fileName)) {
			File file = new File(path, fileName);
			if (!file.exists()) {
				file.mkdirs();
			}
			multipartFile.transferTo(file);
			return file.getPath();
		}
		return "";
	}

	public static String saveFile(MultipartFile multipartFile, String fileName) throws IllegalStateException,
	        IOException {
		int index = fileName.lastIndexOf(".");
		String extensions = fileName.substring(index);
		String newName = fileName.substring(0, index) + CalendarTools.formatDateTime(new Date(), "yyyyMMddHHmmss");
		newName = Md5Encrypter.MD5(newName) + extensions;
		return FileTools.saveFile(multipartFile, FileTools.LINUX_FILEPATH, newName);
	}

	public static String copyFile(String orgFilePath, String newFilePath) throws IOException {
		FileInputStream input = new FileInputStream(orgFilePath);
		FileChannel fiCh = input.getChannel();
		String ouF = newFilePath;
		FileOutputStream output = new FileOutputStream(ouF);
		FileChannel fcout = output.getChannel();
		fiCh.transferTo(0, fiCh.size(), fcout);
		fcout.close();
		output.close();
		fiCh.close();
		input.close();

		return "";
	}

	public static String getEncodingFileName(String fileName, String userAgent) {
		String name = null;
		try {
			name = URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			name = fileName;
		}
		if (StringUtils.isNotEmpty(userAgent)) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.indexOf("opera") != -1) {
				name = "filename*=UTF-8''" + name;
			} else if (userAgent.indexOf("msie") != -1 ||
			    (userAgent.indexOf("rv:") != -1 && userAgent.indexOf("firefox") == -1)) {
				name = "filename=\"" + name + "\"";
			} else if (userAgent.indexOf("mozilla") != -1) {
				try {
					name = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
				} catch (UnsupportedEncodingException e) {
					name = "filename=\"" + name + "\"";
				}
			} else {
				name = "\"filename=" + name + "\"";
			}
		} else {
			name = "\"filename=" + name + "\"";
		}
		return name;
	}

}

package com.xyhmo.commom.utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 文件操作类
 * 
 * 
 */
public class FileUtil {
	private Logger logger = Logger.getLogger(FileUtil.class);
	/**
	 * 读取properties文件
	 * 
	 * @param file
	 *            文件路径，如"/config.properties"
	 * @return
	 */
	public Properties getProperties(String file) {
		String CONFIG_FILE_LOCATION = file;
		InputStream in = null;
		Properties props = null;
		try {
			logger.info("Reading " + CONFIG_FILE_LOCATION);
			in = FileUtil.class.getResourceAsStream(CONFIG_FILE_LOCATION);
			props = new Properties();
			props.load(in);
			logger.info("Read " + CONFIG_FILE_LOCATION + " success");
		} catch (FileNotFoundException e) {
			logger.error("Can not find " + CONFIG_FILE_LOCATION, e);
		} catch (IOException e) {
			logger.error("Read " + CONFIG_FILE_LOCATION + " failed ！", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					logger.error("Close " + CONFIG_FILE_LOCATION + " failed ！",
							e);
				}
			}
		}
		return props;
	}
	/**
	 * 根据的指定的字节数取得KB或M的记数
	 * 如果按KB单位计算，等于new Double(java.lang.Math.ceil(iappsize/1024)).longValue()
	 * 如果按M单位计算，保留4舍五入两位小数。
	 * @param size
	 * @return
	 */   
	public static String getMkbSize(Long iappsize){
		Long rsK=new Double(Math.ceil(iappsize/1024)).longValue();//KB 向上补1个kb
		DecimalFormat text=new DecimalFormat("#.00");//４舍五入
		Double rsM=new Double(text.format((rsK+0.0)/1024));//如果是M 可以是两位小数为Kb
		if(iappsize<1024){
			//amsAppbean.setAppSize("1KB");//大小
			return "1KB";
		}else if(rsK<1024){
			//amsAppbean.setAppSize(rsK+"KB");//大小 1024Kb以下
			return rsK+"KB";
		}else{
			//amsAppbean.setAppSize(rsM+"M");//M
			return rsM+"M";
		}
	}
}

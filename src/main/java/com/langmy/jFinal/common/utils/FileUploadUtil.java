package com.langmy.jFinal.common.utils;

import com.langmy.jFinal.common.AppConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Created by shouke on 16/7/1.
 */
public class FileUploadUtil {
	/**
	 * 只有在AppConstants中有声明的路径才可以上传
	 * @param paramPath
	 * @return
	 */
	public static boolean checkSecurityPath(String paramPath){
		if (StringUtils.isBlank(paramPath)) {
			return false;
		}
		//过滤未在配置中的上传路径
		switch (paramPath){
			case AppConstants.UPLOAD_DIR_AVATAR:
			case AppConstants.UPLOAD_DIR_EDITOR:
			case AppConstants.UPLOAD_DIR_LABEL:
				return true;
			default: return false;
		}
	}

	/**
	 * 使用UUID,生成文件名
	 * @param extension
	 * @return
	 */
	public static String randomFileName(String extension){
		return UUID.randomUUID().toString().replaceAll("-", "").concat(".").concat(extension);
	}
}

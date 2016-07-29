package com.shoukeplus.jFinal.common.utils;

import com.jfinal.upload.UploadFile;
import com.shoukeplus.jFinal.common.AppConstants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by shouke on 16/7/1.
 */
public class FileUploadUtil {
	/**
	 * 只有在AppConstants中有声明的路径才可以上传
	 *
	 * @param paramPath
	 * @return
	 */
	public static boolean checkSecurityPath(String paramPath) {
		if (StringUtils.isBlank(paramPath)) {
			return false;
		}
		//过滤未在配置中的上传路径
		switch (paramPath) {
			case AppConstants.UPLOAD_DIR_AVATAR:
			case AppConstants.UPLOAD_DIR_EDITOR:
			case AppConstants.UPLOAD_DIR_LABEL:
			case AppConstants.UPLOAD_DIR_LINK:
			case AppConstants.UPLOAD_DIR_CONTENT:
			case AppConstants.UPLOAD_DIR_ROLLIMAGES:
				return true;
			default:
				return false;
		}
	}

	public static String upload(String paramPath, List<UploadFile> uploadFiles) {
		if (!FileUploadUtil.checkSecurityPath(paramPath)) {
			throw new RuntimeException(AppConstants.UPLOAD_ERROR_MESSAGE);
		}

		//按天来创建文件夹
		String dateFolder = "/" + DateUtil.formatDate(DateUtil.getCurrentDateTime()) + "/";
		String relativePath = "/" + paramPath + dateFolder;
		String destFolder = AppConstants.UPLOAD_DIR + relativePath;

		File destFile = new File(destFolder);
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		String imgFile = "";
		//wangEditor只支持单图片上传,因此此处跳过多个的处理;
		for (UploadFile uf : uploadFiles) {
			//System.out.println(uploadFile.getOriginalFileName());//图片原来的名字
			//System.out.println(uploadFile.getFileName());//图片保存到服务器的名字
			String newName = FileUploadUtil.randomFileName(FilenameUtils.getExtension(uf.getOriginalFileName()));
			uf.getFile().renameTo(new File(destFolder + newName));
			//imgFile = AppConstants.IMG_HOSTURL + relativePath + newName;
			imgFile = relativePath + newName;
			break;
		}
		return imgFile;
	}

	/**
	 * 使用UUID,生成文件名
	 *
	 * @param extension
	 * @return
	 */
	public static String randomFileName(String extension) {
		return UUID.randomUUID().toString().replaceAll("-", "").concat(".").concat(extension);
	}
}

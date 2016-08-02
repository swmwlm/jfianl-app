package com.shoukeplus.jFinal.render;

import com.jfinal.core.JFinal;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.render.RenderFactory;

import javax.servlet.ServletContext;
import java.io.*;

/**
 * 文件下载(用于解决“无法自定义下载文件名”和“中文乱码”问题)
 */
public class MyFileRender extends Render {

    private File file;
    private String fileName;
    private ServletContext servletContext;

    public MyFileRender(File file, String fileName) {
        this.file = file;
        this.fileName = fileName;
        this.servletContext = JFinal.me().getServletContext();
    }

    @Override
    public void render() {

        if (file == null || !file.isFile() || file.length() > Integer.MAX_VALUE) {
            RenderFactory.me().getErrorRender(404).setContext(request, response).render();
            return;
        }
        //源码中的代码
        //response.addHeader("Content-disposition", "attachment; filename=" + file.getName());
        //修改后的代码 解决中文乱码问题
        try {
            //response.addHeader("Content-disposition",
            //        "attachment; filename=" + new String(fileName.contains(".") ? fileName.getBytes("GBK") : file.getName().getBytes("GBK"), "ISO8859-1"));
            response.addHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"),"ISO8859-1"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String contentType = servletContext.getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setContentLength((int) file.length());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            for (int n = -1; (n = inputStream.read(buffer)) != -1; ) {
                outputStream.write(buffer, 0, n);
            }
            outputStream.flush();
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

package io.renren.modules.sys.service.small.csjk;

import com.alibaba.fastjson.JSONObject;

import com.google.gson.JsonObject;
import io.renren.modules.sys.domain.BasicResultObject;

import io.renren.modules.sys.domain.BasicTagState;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Component("csjkUploadFileService")
public class UploadFileService {

    //region uploadVideo
    public String uploadVideo(HttpServletRequest req,
                              MultipartFile[] files, String dirName) {
        BasicResultObject resultObj = new BasicResultObject();
        //多文件上传
        if (files != null && files.length >= 1) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                String url = "./smallUploadSource/" + dirName;
                String fileName = files[0].getOriginalFilename();
                String fileSuffix = "mp4";// fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()); /*获取扩展名*/
                if (files[0].getSize() > 0) {
                    fis = (FileInputStream) files[0].getInputStream();
                    //创建输出文件对象
                    String uid = UUID.randomUUID().toString();
                    fos = new FileOutputStream(new File(url + "/" + uid + "." + fileSuffix));
                    //拷贝文件到输出文件对象
                    FileCopyUtils.copy(fis, fos);
                    Map<String,Object> map = new HashMap<>();
                    map.put("fileName","/"+dirName+"/"+uid+"."+fileSuffix);
                    map.put("time",this.getMp4Time(new File(url+"/"+uid+"."+fileSuffix)));
                    resultObj.Result = map;

                    //ImageZipUtils.zipImageFile(new File(url+"/" + uid + "." + fileSuffix),new File(url+"/" + uid + "." + fileSuffix),142,0,0.7f);
                } else {
                    resultObj.Message = "空文件";
                }
            } catch (Exception e) {
                resultObj.Message = e.getMessage();
                e.printStackTrace();
                System.out.println("uploadAudio Exceprion：" + JSONObject.toJSONString(resultObj));

            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    resultObj.Message = e.getMessage();
                    e.printStackTrace();
                    System.out.println("uploadAudio Exceprion：" + JSONObject.toJSONString(resultObj));
                }
            }
        }

        return JSONObject.toJSONString(resultObj);
    }
    //endregion

    // 获取时长 (jave library removed, returns 0)
    public long getMp4Time(File file) {
        return 0;
    }
    //endregion

    //region 获取视频一帧的截图
    public String getProcessImg(String veido_path,int frame) {
        BasicResultObject obj = new BasicResultObject();
        Properties prop = System.getProperties();
        String fileName = "/longHealthImage/"+UUID.randomUUID().toString()+".jpg";
        String ffmpeg_path = "./bin/ffmpeg.exe";
        String filePath = "./smallUploadSource"+fileName;
        String os = prop.getProperty("os.name");
        if (os != null && !os.toLowerCase().contains("win")) {
            ffmpeg_path = "ffmpeg";
            filePath = "/opt/app/server/smallUploadSource"+fileName;
            veido_path = "/opt/app/server/smallUploadSource"+veido_path;
        }else{
            veido_path = "./smallUploadSource"+veido_path;
        }

        File file = new File(veido_path);
        if (!file.exists()) {
            obj.Tag=BasicTagState.TagFailure;
            System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
            return JSONObject.toJSONString(obj);
        }

        List<String> commands = new java.util.ArrayList<>();
        commands.add(ffmpeg_path);
        commands.add("-i");
        commands.add(veido_path);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add(String.valueOf(frame));//这个参数是设置截取视频多少秒时的画面
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        commands.add("1920x1080");//宽X高
        commands.add(filePath);
        System.out.println(ffmpeg_path + " i " + veido_path + " -y -f image2 -ss "+String.valueOf(frame)+" -t 0.001 -s 1920x1080 "+filePath );
        try {
            System.out.println(new Date().getTime());
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            //Thread.sleep(3000);
            System.out.println("截取成功");
            System.out.println(new Date().getTime());
            obj.Result = fileName;
            //return JSONObject.toJSONString(obj);
        } catch (Exception e) {
            obj.Tag=BasicTagState.TagFailure;
            obj.Message = "报异常了";
            System.out.println("getProcessImg Exception:"+e.getMessage());
            e.printStackTrace();
            //return JSONObject.toJSONString(obj);
        }
        return JSONObject.toJSONString(obj);
    }
    //endregion

}

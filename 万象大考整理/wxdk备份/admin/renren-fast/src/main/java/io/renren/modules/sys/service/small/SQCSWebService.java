package io.renren.modules.sys.service.small;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.SQCSWebDao;
import io.renren.modules.sys.domain.BasicResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class SQCSWebService {
    @Autowired
    private SQCSWebDao sqcsWebDao;

    //region titleList

    public String titleList(String keyWords, int pageIndex, int pageSize ){
        BasicResultObject obj = sqcsWebDao.titleList(keyWords,pageIndex,pageSize);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region createTitle
    public String createTitle(String type,
                              String titleName,
                              String titleDescription,
                              String titleImageUrl,
                              String subjectStr,
                              String answerStr,
                              String optionStr,
                              String titleInitRead){
        BasicResultObject obj = sqcsWebDao.createTitle(type,titleName,titleDescription,titleImageUrl,subjectStr,answerStr,optionStr,titleInitRead);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region updateTitleById
    public String updateTitleById( int id,
                                   String type,
                                   String titleName,
                                   String titleDescription,
                                   String titleImageUrl,
                                   String subjectStr,
                                   String answerStr,
                                   String optionStr,
                                   String sortId,
                                   String titleInitRead){
       BasicResultObject obj = sqcsWebDao.updateTitleById(id,type,titleName,titleDescription,titleImageUrl,subjectStr,answerStr,optionStr,sortId,titleInitRead);
       return JSONObject.toJSONString(obj);
    }
    //endregion

    //region delTitleById
    public String delTitleById(int id){
        BasicResultObject obj = sqcsWebDao.delTitleById(id);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region getTitleById
    public String getTitleById(int id){
        BasicResultObject obj = sqcsWebDao.getTitleById(id);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region uploadImage
    public String uploadImage(MultipartFile[] files){

        //java.net.URL url = TestTitleService.class .getProtectionDomain().getCodeSource().getLocation();
        BasicResultObject resultObj = new BasicResultObject();
        //多文件上传
        if (files != null && files.length >= 1) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                String url = "./upload";
                String fileName = files[0].getOriginalFilename();
                String fileSuffix ="jpg";// fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()); /*获取扩展名*/
                if (files[0].getSize() > 0) {
                    fis = (FileInputStream) files[0].getInputStream();
                    //创建输出文件对象
                    String uid = UUID.randomUUID().toString();
                    fos = new FileOutputStream(new File(url+"/" + uid + "." + fileSuffix));
                    //拷贝文件到输出文件对象
                    FileCopyUtils.copy(fis, fos);
                    resultObj.Result =  uid + "." + fileSuffix;

                    //ImageZipUtils.zipImageFile(new File(url+"/" + uid + "." + fileSuffix),new File(url+"/" + uid + "." + fileSuffix),142,0,0.7f);
                } else {
                    resultObj.Message = "空文件";
                }
            } catch (Exception e) {
                resultObj.Message = e.getMessage();
                e.printStackTrace();
                // logger.info("uploadAudio Exceprion：" + JSONObject.toJSONString(resultObj));
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if(fos != null){
                        fos.close();
                    }
                } catch (IOException e) {
                    resultObj.Message = e.getMessage();
                    e.printStackTrace();
                    // logger.info("uploadAudio Exception：" + JSONObject.toJSONString(resultObj));
                }
            }
        }

        return JSONObject.toJSONString(resultObj);
    }
    //endregion
}

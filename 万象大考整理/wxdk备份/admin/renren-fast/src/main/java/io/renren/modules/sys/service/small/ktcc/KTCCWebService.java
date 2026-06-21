package io.renren.modules.sys.service.small.ktcc;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.ktcc.KTCCWebDao;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.small.ktcc.KTCCOption;
import io.renren.modules.sys.domain.small.ktcc.KTCCSubject;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Service
public class KTCCWebService {

    @Autowired
    private KTCCWebDao ktccWebDao;

    //region getSubjectList

    public String getSubjectList(int pageIndex, int pageSize, String keyWords, int type , int status, int modeType){

        BasicResultObject obj = ktccWebDao.getSubjectList(pageIndex,pageSize,keyWords,type,status,modeType);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region addSubject
    public String addSubject(String answer,String image,String audioName,String promptWord,String optionStr,String type,String status ,int modeType, int sortId,String description){
        BasicResultObject obj = ktccWebDao.addSubject(answer,image,audioName,promptWord,optionStr,type,status,modeType,sortId,description);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region updateSubjectById
    public String updateSubjectById(int id,String answer,String image,String audioName,String promptWord,String optionStr,String type,String status,String sortId,int modeType,String description){
        BasicResultObject obj = ktccWebDao.updateSubjectById(id,answer,image,audioName,promptWord,optionStr,type,status,sortId,modeType,description);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region delSubejctById
    public String delSubejctById(String ids){
        BasicResultObject obj = ktccWebDao.delSubejctById(ids);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region updateSubjectStatus
    public String updateSubjectStatus(String ids,int status){
        BasicResultObject obj = ktccWebDao.updateSubjectStatus(ids,status);
        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region getSubjectById
    public String getSubjectById(int Id){
        BasicResultObject obj = ktccWebDao.getSubjectById(Id);
        return  JSONObject.toJSONString(obj);
    }
    //endregion

    //region uploadImage
    public String uploadImage(HttpServletRequest req,
                              MultipartFile[] files){
        BasicResultObject resultObj = new BasicResultObject();
        //多文件上传
        if (files != null && files.length >= 1) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                String url = "./lookGuessImage";
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
                System.out.println("uploadAudio Exceprion：" + JSONObject.toJSONString(resultObj));

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
                    System.out.println("uploadAudio Exceprion：" + JSONObject.toJSONString(resultObj));
                }
            }
        }

        return JSONObject.toJSONString(resultObj);
    }
    //endregion

    //region uploadAudio
    public String uploadAudio(HttpServletRequest req,
                              MultipartFile[] files){
        BasicResultObject resultObj = new BasicResultObject();
        //多文件上传
        if (files != null && files.length >= 1) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                String url = "./lookGuessAudio";
                String fileName = files[0].getOriginalFilename();
                String fileSuffix ="mp3";// fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()); /*获取扩展名*/
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
                System.out.println("uploadAudio Exceprion：" + JSONObject.toJSONString(resultObj));

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
                    System.out.println("uploadAudio Exceprion：" + JSONObject.toJSONString(resultObj));
                }
            }
        }

        return JSONObject.toJSONString(resultObj);
    }
    //endregion

    //region saveSort
    public String saveSort(String ids , int pageIndex,int pageSize){
        return JSONObject.toJSONString(ktccWebDao.saveSort(ids,pageIndex,pageSize));
    }
    //endregion

    //region getSubjectRatio
    public String getSubjectRatio(int pageIndex, int pageSize,int orderType,String keyWords){
        return JSONObject.toJSONString(ktccWebDao.getSubjectRatio(pageIndex,pageSize,orderType,keyWords));
    }
    //endregion

    //region uploadExcel
    public String uploadExcel(HttpServletRequest req,
                              MultipartFile[] files){
        List<KTCCSubject> subjectList = this.analysisExcel(files);
        return JSONObject.toJSONString(ktccWebDao.uploadExcel(subjectList));
    }

    public List<KTCCSubject> analysisExcel(MultipartFile[] files){
        BasicResultObject resultObj = new BasicResultObject();
        List<KTCCSubject> subjectList = new ArrayList<>();
        try {
            String textFileName = files[0].getOriginalFilename();
            MultipartFile file = files[0];
            /*if(textFileName.endsWith(".doc")){ //判断文件格式
                InputStream fis = file.getInputStream();
                WordExtractor wordExtractor = new WordExtractor(fis);//使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
                int i=1;
                for(String words : wordExtractor.getParagraphText()){//获取段落内容

                    System.out.println("DOC文档，第（"+i+"）段内容"+words);
                    i++;
                }
                fis.close();
            }*/
            if(textFileName.endsWith(".docx")){
                String uid = UUID.randomUUID().toString();
                File uFile = new File(uid+".docx");//创建一个临时文件
                if(!uFile.exists()){
                    uFile.createNewFile();
                }
                FileCopyUtils.copy(file.getBytes(), uFile);//复制文件内容
                OPCPackage opcPackage = POIXMLDocument.openPackage(uid+".docx");//包含所有POI OOXML文档类的通用功能，打开一个文件包。
                XWPFDocument document = new XWPFDocument(opcPackage);//使用XWPF组件XWPFDocument类获取文档内容
                List<XWPFParagraph> paras = document.getParagraphs();
                int i=1;
                int readIndex = 1;
                String description = "";
                KTCCSubject subject = new KTCCSubject();
                List<KTCCOption> optionList = new ArrayList<>();

                for(XWPFParagraph paragraph : paras){
                    String words = paragraph.getText().trim();
                    List<XWPFRun> runsLists = paragraph.getRuns();//获取段楼中的句列表
                    /*内容红色就是答案*/
                    if( words.trim().length() > 0 && runsLists != null && runsLists.get(0).getColor()!=null && runsLists.get(0).getColor().equals("FF0000") ){
                        subject.setAnswer(words);
                    }
                    words = words.trim().length() == 0 ? "??????????" : words;
                    if(i == 1){ // 第一行题目
                        subject.setName(words);
                    }else if(readIndex > 1 && readIndex < 6){
                        KTCCOption option = new KTCCOption();
                        if(words.equals(subject.getAnswer())){
                            subject.setOptionSortId(readIndex - 1);
                        }
                        option.setSortId(readIndex - 1);
                        option.setName(words);
                        optionList.add(option);

                    }else if(readIndex >= 6){
                        String lastStr = words.substring(words.length()-1,words.length());
                        if(lastStr.equals("?") || lastStr.equals("？")){
                            subject.setDescription(description);
                            subject.setOptionList(optionList);
                            subjectList.add(subject);
                            if(words.equals("??????????")){break;}
                            optionList = new ArrayList<>();
                            subject = new KTCCSubject();
                            subject.setName(words);
                            description = "";
                            readIndex = 1;
                        }else{
                            description += words;
                        }
                    }
                    if(paras.size() == i){ // 最后一行
                        subject.setDescription(description);
                        subject.setOptionList(optionList);
                        subjectList.add(subject);
                    }
                    System.out.println("DOC文档，第（"+i+"）段内容"+words);
                    readIndex ++ ;
                    i++;
                }

                uFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectList;
    }

    //endregion


}

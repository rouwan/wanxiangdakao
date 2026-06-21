package io.renren.modules.sys.service.small.wxdk;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.dao.small.wxdk.*;
import io.renren.modules.sys.domain.BasicResultObject;
import io.renren.modules.sys.domain.BasicTagState;
import io.renren.modules.sys.domain.small.wxdk.Subject;
import io.renren.modules.sys.domain.small.wxdk.SubjectOption;
import io.renren.modules.sys.domain.small.wxdk.WeekSubject;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Component
public class UploadFileService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private SubjectTypeDao subjectTypeDao;
    @Autowired
    private SubjectOptionDao subjectOptionDao;
    @Autowired
    private WeekSubjectDao weekSubjectDao;
    @Autowired
    private WeekTitleDao weekTitleDao;
    @Autowired
    private SubjectRatioService subjectRatioService;
    @Autowired
    private UserSubjectService userSubjectService;

    String typeName = ""; //解析题目类型
    String titleName = "";//标题名称
    int createUserId = 0;//用户出题人id
    //region uploadImage
    public String uploadImage(HttpServletRequest req,
                              MultipartFile[] files,String dirName){
        BasicResultObject resultObj = new BasicResultObject();
        //多文件上传
        if (files != null && files.length >= 1) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                String url = "./smallUploadSource/"+dirName;
                String fileName = files[0].getOriginalFilename();
                String fileSuffix ="jpg";// fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()); /*获取扩展名*/
                if (files[0].getSize() > 0) {
                    fis = (FileInputStream) files[0].getInputStream();
                    //创建输出文件对象
                    String uid = UUID.randomUUID().toString();
                    fos = new FileOutputStream(new File(url+"/" + uid + "." + fileSuffix));
                    //拷贝文件到输出文件对象
                    FileCopyUtils.copy(fis, fos);
                    resultObj.Result =  "/"+dirName+"/"+uid + "." + fileSuffix;

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
                String url = "./smallUploadSource/vientianeTestAudio";
                String fileName = files[0].getOriginalFilename();
                String fileSuffix ="mp3";// fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length()); /*获取扩展名*/
                if (files[0].getSize() > 0) {
                    fis = (FileInputStream) files[0].getInputStream();
                    //创建输出文件对象
                    String uid = UUID.randomUUID().toString();
                    fos = new FileOutputStream(new File(url+"/" + uid + "." + fileSuffix));
                    //拷贝文件到输出文件对象
                    FileCopyUtils.copy(fis, fos);
                    resultObj.Result =  "/vientianeTestAudio/"+uid + "." + fileSuffix;

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

    //region uploadExcel
    public String uploadExcel(HttpServletRequest req,
                              MultipartFile[] files){
        BasicResultObject obj = new BasicResultObject();
        List<Subject> subjectList = this.analysisExcel(files);
        String existsSubjectName = "";
        if(!subjectTypeDao.isExistsByName(typeName)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "题目类型不存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        int typeId =  Integer.parseInt(subjectTypeDao.getSubjectTypeByName(typeName).get("id").toString());
        /*for(int i=0;i<subjectList.size();i++){
            String title = subjectList.get(i).getPromptWord();
            if(subjectDao.isExistsByTitle(title)){
                existsSubjectName += title + ",";
            }
        }
        if(existsSubjectName.length() > 0 ){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "题目【"+existsSubjectName+"】已存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }*/
        Map<String, Object> userMap = null;
        if(createUserId > 0){
            userMap = userDao.getUserInfoById(createUserId);
        }
        for(int i=0;i<subjectList.size();i++){
            Subject subject = subjectList.get(i);
            if(userMap != null){
                subject.setCreateUserUrl(userMap.get("headImgUrl").toString());
                subject.setCreateUserName(userMap.get("nickname").toString());
            }
            subject.setType(1);
            //subject.setModeType(3);
            subject.setStatus(0);
            subject.setSortId(subjectDao.getMaxSortId()+1);
            subject.setTypeId(typeId); //相当于没有选择

            int subjectId = subjectDao.insert(subject);
            List<SubjectOption> subjectOptionList = subject.getSubjectOptionList();
            for(int j =0;j<subjectOptionList.size();j++){
                subjectOptionList.get(j).setSubjectId(subjectId);
                subjectOptionList.get(j).setSortId(j+1);
                subjectOptionDao.insert(subjectOptionList.get(j));
            }
            /*
             *修改初始化的正确率
             * 困难 1:0%~32% 24/30
             * 一般 2:33%~66% 15/30
             * 简单 3:67%~100% 6/30
             * */
            if(subject.getLevelType() > 0){
                int rightNum = 240;
                int totalNum = 300;
                int errorNUm = 60;
                if(subject.getLevelType() == 2){
                    rightNum = 150;
                    errorNUm = 150;
                }else if(subject.getLevelType() == 3){
                    rightNum = 0;
                    errorNUm = 300;
                    totalNum = 300;
                }
                subjectRatioService.update(subjectId,totalNum,rightNum,errorNUm);
            }

            //如果用户文字题，能显示在用户手机列表
            if(createUserId > 0) {
                userSubjectService.insert(createUserId,subjectId);
            }
        }
        return JSONObject.toJSONString(obj);
    }

    public List<Subject>  analysisExcel(MultipartFile[] files){
        BasicResultObject resultObj = new BasicResultObject();
        List<Subject> subjectList = new ArrayList<>();
        try {
            String textFileName = files[0].getOriginalFilename();
            //林欣欣近代史题58题_林欣欣_历史.docx
            //林欣欣近代史题58题_文字题_林欣欣_历史.docx
            //林欣欣近代史题58题_判断题_林欣欣_待定.docx
            //林欣欣成语题10题_成语题_林欣欣_成语.xls
            //林欣欣单词题10题_单词题_林欣欣_待定.xls
            //林欣欣诗歌题_五言标题上_林欣欣_待定.xls
            //林欣欣诗歌题_五言标题下_林欣欣_待定.xls
            //林欣欣诗歌题_七言标题上_林欣欣_待定.xls
            //林欣欣诗歌题_七言标题下_林欣欣_待定.xls
            //林欣欣文字题_外包文字题_林欣欣_待定.xls
            //林欣欣文字题_用户文字题_用户ID_待定.xls
            String subjectType = textFileName.split("_")[1];//题目类型
            String createUserName = textFileName.split("_")[2]; //出题人
            typeName = textFileName.split("_")[3].split("\\.")[0];
            createUserId = 0;
            if(subjectType.equals("用户文字题")){
                createUserId = Integer.parseInt(textFileName.split("_")[2]);
            }
            MultipartFile file = files[0];
            //region 解析判断题
            if(subjectType.equals("判断题")){
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
                    Subject subject = new Subject();
                    List<SubjectOption> optionList = new ArrayList<>();
                    subject.setModeType(4);
                    for(XWPFParagraph paragraph : paras){
                        String words = paragraph.getText().replaceAll(" ","");
                        List<XWPFRun> runsLists = paragraph.getRuns();//获取段楼中的句列表
                        /*内容红色就是答案*/
                        if( words.trim().length() > 0 && runsLists != null && runsLists.get(0).getColor()!=null && runsLists.get(0).getColor().equals("FF0000") ){
                            subject.setAnswer(words);
                        }
                        words = words.trim().length() == 0 ? "??????????" : words;
                        if(i == 1){ // 第一行题目
                            subject.setPromptWord(words.replaceAll("\\?","。").replaceAll("？","。"));
                        }else if(readIndex > 1 && readIndex < 4){
                            SubjectOption option = new SubjectOption();
                            if(words.equals(subject.getAnswer())){
                                subject.setOptionSortId(readIndex - 1);
                            }
                            option.setSortId(readIndex - 1);
                            option.setOption(words);
                            optionList.add(option);

                        }else if(readIndex >= 4){
                            int levelType = 0;
                            if(readIndex == 4){
                                String[] levelTypeArr = {"简单","一般","困难"};
                                for(int j =0 ;j<levelTypeArr.length;j++) {
                                    if(levelTypeArr[j].equals(words)){
                                        levelType = j+1;
                                        break;
                                    }
                                }
                                if(levelType > 0){
                                    subject.setLevelType(levelType);
                                }
                            }
                            if(levelType <= 0){
                                String lastStr = words.substring(words.length()-1,words.length());
                                if(lastStr.equals("?") || lastStr.equals("？")){
                                    subject.setDescription(description);
                                    subject.setSubjectOptionList(optionList);
                                    subject.setCreateUserName(createUserName);
                                    subjectList.add(subject);
                                    if(words.equals("??????????")){break;}
                                    optionList = new ArrayList<>();
                                    subject = new Subject();
                                    subject.setModeType(4);
                                    subject.setCreateUserName(createUserName);
                                    subject.setPromptWord(words.replaceAll("\\?","。").replaceAll("？","。"));
                                    description = "";
                                    readIndex = 1;
                                }else{
                                    description += words;
                                }
                            }
                        }
                        if(paras.size() == i){ // 最后一行
                            subject.setDescription(description);
                            subject.setSubjectOptionList(optionList);
                            subjectList.add(subject);
                        }
                        System.out.println("DOC文档，第（"+i+"）段内容"+words);
                        readIndex ++ ;
                        i++;
                    }

                    uFile.delete();
                }
            }
            //endregion

            //region 解析成语题
            else if(subjectType.equals("成语题")){
                return this.readIdiomExcel(file,createUserName);
            }
            //endregion

            //region 解析单词题
            else if(subjectType.equals("单词题")){
                return this.readWordExcel(file,createUserName);
            }
            //endregion

            //region 诗词题
            else if(subjectType.equals("五言标题上") || subjectType.equals("五言标题下") || subjectType.equals("七言标题上") || subjectType.equals("七言标题下")){
                int modeType = 7;
                if(subjectType.equals("五言标题上")){
                    modeType = 7;
                }else if(subjectType.equals("五言标题下")){
                    modeType = 8;
                }else if(subjectType.equals("七言标题上")){
                    modeType = 9;
                }else if(subjectType.equals("七言标题下")){
                    modeType = 10;
                }
                return this.readSiciExcel(file,createUserName,modeType);
            }
            //endregion

            //region 解析用户文字题,外包文字题
            else if(subjectType.equals("用户文字题") || subjectType.equals("外包文字题")){
                return this.readUserSubjectExcel(file,createUserName);
            }
            //endregion

            //region 解析文字题
            else{
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
                    Subject subject = new Subject();
                    List<SubjectOption> optionList = new ArrayList<>();
                    subject.setModeType(3);
                    for(XWPFParagraph paragraph : paras){
                        String words = paragraph.getText().replaceAll(" ","");
                        List<XWPFRun> runsLists = paragraph.getRuns();//获取段楼中的句列表
                        /*内容红色就是答案*/
                        if( words.trim().length() > 0 && runsLists != null && runsLists.get(0).getColor()!=null && runsLists.get(0).getColor().equals("FF0000") ){
                            subject.setAnswer(words);
                        }
                        words = words.trim().length() == 0 ? "??????????" : words;
                        if(i == 1){ // 第一行题目
                            subject.setPromptWord(words);
                        }else if(readIndex > 1 && readIndex < 6){
                            SubjectOption option = new SubjectOption();
                            if(words.equals(subject.getAnswer())){
                                subject.setOptionSortId(readIndex - 1);
                            }
                            option.setSortId(readIndex - 1);
                            option.setOption(words);
                            optionList.add(option);

                        }else if(readIndex >= 6){
                            int levelType = 0;
                            if(readIndex == 6){
                                String[] levelTypeArr = {"简单","一般","困难"};
                                for(int j =0 ;j<levelTypeArr.length;j++) {
                                    if(levelTypeArr[j].equals(words)){
                                        levelType = j+1;
                                        break;
                                    }
                                }
                                if(levelType > 0){
                                    subject.setLevelType(levelType);
                                }
                            }
                            if(levelType <= 0){
                                String lastStr = words.substring(words.length()-1,words.length());
                                if(lastStr.equals("?") || lastStr.equals("？")){
                                    subject.setDescription(description);
                                    subject.setSubjectOptionList(optionList);
                                    subject.setCreateUserName(createUserName);
                                    subjectList.add(subject);
                                    if(words.equals("??????????")){break;}
                                    optionList = new ArrayList<>();
                                    subject = new Subject();
                                    subject.setModeType(3);
                                    subject.setCreateUserName(createUserName);
                                    subject.setPromptWord(words);
                                    description = "";
                                    readIndex = 1;
                                }else{
                                    description += words;
                                }
                            }
                        }
                        if(paras.size() == i){ // 最后一行
                            subject.setDescription(description);
                            subject.setSubjectOptionList(optionList);
                            subjectList.add(subject);
                        }
                        System.out.println("DOC文档，第（"+i+"）段内容"+words);
                        readIndex ++ ;
                        i++;
                    }

                    uFile.delete();
                }
            }
            //endregion


        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectList;
    }

    //region 解析成语题
    public List<Subject> readIdiomExcel(MultipartFile file,String createUserName){
        List<Subject> subjectList = new ArrayList<>();
        try {
            // 创建输入流，读取Excel
            InputStream is = file.getInputStream();
            // Workbook类
            Workbook workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            //获得当前sheet的开始行
            int firstRowNum  = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环除了第一行的所有行
            System.out.println(file.getOriginalFilename()+" 正在导入。。。。。");
            System.out.println(file.getOriginalFilename()+" 总共 : "+(lastRowNum+1)+" 条记录");
            for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if(row == null){
                    continue;
                }
                Subject subject = new Subject();
                //读取列的数据
                String name = row.getCell(0).getStringCellValue().replaceAll(" ","");if(name.equals("")){continue;}
                String promptWord = row.getCell(1).getStringCellValue().replaceAll(" ","");
                String description = row.getCell(3).getStringCellValue();
                String optionStr = row.getCell(2).getStringCellValue();
                String[] optionArr = optionStr.split("，");
                ArrayList<String> newOptionList = new ArrayList<>();
                String answer = name.substring(promptWord.indexOf("_"),promptWord.indexOf("_") + 1);
                newOptionList.add(answer);
                for(int i = 0;i<optionArr.length;i++){
                    String option = optionArr[i].replaceAll("\\[","").replaceAll("]","").replaceAll(" ","");
                    newOptionList.add(option);
                }
                Collections.shuffle(newOptionList);
                List<SubjectOption> subjectOptionList = new ArrayList<>();
                int optionSortId = 0;
                for(int i = 0;i<newOptionList.size();i++){
                    String option = newOptionList.get(i);
                    SubjectOption subjectOption = new SubjectOption(0,0,option,i+1);
                    if(answer.equals(option)){
                        optionSortId = i+1;
                    }
                    subject.setAnswer(answer);
                    subjectOptionList.add(subjectOption);
                    //阿党相为 阿党_为
                }
                subject.setStatus(0);
                subject.setModeType(5);
                subject.setCreateUserName(createUserName);
                subject.setLevelType(1);//默认简单
                subject.setOptionSortId(optionSortId);
                subject.setPromptWord(promptWord);
                subject.setDescription(description);
                subject.setSubjectOptionList(subjectOptionList);

                subjectList.add(subject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getOriginalFilename()+" 完成导入。。。。。");
        return subjectList;
    }
    //endregion

    //region 解析单词题

    public List<Subject> readWordExcel(MultipartFile file,String createUserName){
        List<Subject> subjectList = new ArrayList<>();
        try {
            // 创建输入流，读取Excel
            InputStream is = file.getInputStream();
            // Workbook类
            Workbook workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            //获得当前sheet的开始行
            int firstRowNum  = sheet.getFirstRowNum()+1;
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环除了第一行的所有行
            System.out.println(file.getOriginalFilename()+" 正在导入。。。。。");
            System.out.println(file.getOriginalFilename()+" 总共 : "+(lastRowNum+1)+" 条记录");
            for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if(row == null){
                    continue;
                }
                Subject subject = new Subject();
                //读取列的数据
                String promptWord = row.getCell(0).getStringCellValue().replaceAll(" ","");if(promptWord.equals("")){ continue; }
                String typName = row.getCell(1).getStringCellValue().replaceAll(" ","");
                String answer = row.getCell(2).getStringCellValue().replaceAll(" ","");
                //释义 name，typName，answer
                String description = promptWord+"，"+typName+"，"+answer;
                ArrayList<String> newOptionList = new ArrayList<>();
                newOptionList.add(answer);
                newOptionList.addAll(matchWordBySheet(sheet,firstRowNum,typName,answer));
                Collections.shuffle(newOptionList);
                List<SubjectOption> subjectOptionList = new ArrayList<>();
                int optionSortId = 0;
                for(int i = 0;i<newOptionList.size();i++){
                    String option = newOptionList.get(i);
                    SubjectOption subjectOption = new SubjectOption(0,0,option,i+1);
                    if(answer.equals(option)){
                        optionSortId = i+1;
                    }
                    subject.setAnswer(answer);
                    subjectOptionList.add(subjectOption);
                    //阿党相为 阿党_为
                }
                subject.setStatus(0);
                subject.setModeType(6);
                subject.setCreateUserName(createUserName);
                subject.setLevelType(1);//默认简单
                subject.setOptionSortId(optionSortId);
                subject.setPromptWord(promptWord);
                subject.setDescription(description);
                subject.setSubjectOptionList(subjectOptionList);

                subjectList.add(subject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getOriginalFilename()+" 完成导入。。。。。");
        return subjectList;
    }

    //region 筛选词性一样的数据
    public List<String> matchWordBySheet(Sheet sheet,int startIndex,String typName,String answer){
        //获得当前sheet的结束行
        List<String> optionList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        for(int rowNum = startIndex;rowNum <= lastRowNum;rowNum++){
            //获得当前行
            Row row = sheet.getRow(rowNum);
            String typName2 = row.getCell(1).getStringCellValue().replaceAll(" ","");
            String answer2 = row.getCell(2).getStringCellValue().replaceAll(" ","");
            if(typName.equals(typName2) && !answer.equals(answer2)){
                optionList.add(answer2);
            }
        }
        Collections.shuffle(optionList);
        if(optionList.size() < 3){
            //选项补充完整
            int len = 3 - optionList.size();
            for(int i =0;i<len;i++){
                char randomChar = (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
                optionList.add(String.valueOf(randomChar));
            }
        }
        return optionList.subList(0,3);
    }
    //endregion

    //endregion

    //region 诗词导入
    public List<Subject> readSiciExcel(MultipartFile file,String createUserName,int modeType){
        List<Subject> subjectList = new ArrayList<>();
        try {
            // 创建输入流，读取Excel
            InputStream is = file.getInputStream();
            // Workbook类
            Workbook workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            //获得当前sheet的开始行
            int firstRowNum  = sheet.getFirstRowNum()+1;
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环除了第一行的所有行
            System.out.println(file.getOriginalFilename()+" 正在导入。。。。。");
            System.out.println(file.getOriginalFilename()+" 总共 : "+(lastRowNum+1)+" 条记录");
            for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if(row == null){
                    continue;
                }
                Subject subject = new Subject();
                //读取列的数据
                String promptWord = row.getCell(2).getStringCellValue().replaceAll(" ","");if(promptWord.equals("")){ continue; }
                //String typName = row.getCell(1).getStringCellValue().replaceAll(" ","");
                String answer = row.getCell(3).getStringCellValue().replaceAll(" ","");
                String option2 = row.getCell(5).getStringCellValue().replaceAll(" ","");
                String option3 = row.getCell(6).getStringCellValue().replaceAll(" ","");
                String option4 = row.getCell(7).getStringCellValue().replaceAll(" ","");
                String description = row.getCell(8).getStringCellValue().replaceAll(" ","");
                int levelType = row.getCell(4).getStringCellValue().replaceAll(" ","").equals("简单")? 1 : 3;
                List<SubjectOption> subjectOptionList = new ArrayList<>();
                subjectOptionList.add(new SubjectOption(0,0,answer,1));
                subjectOptionList.add(new SubjectOption(0,0,option2,2));
                subjectOptionList.add(new SubjectOption(0,0,option3,3));
                subjectOptionList.add(new SubjectOption(0,0,option4,4));
                Collections.shuffle(subjectOptionList);
                int optionSortId = 0;
                for(int i = 0;i<subjectOptionList.size();i++){
                    String option = subjectOptionList.get(i).getOption();
                    if(answer.equals(option)){
                        optionSortId = i+1;
                    }
                }
                subject.setAnswer(answer);
                subject.setStatus(0);
                subject.setModeType(modeType);
                subject.setCreateUserName(createUserName);
                subject.setLevelType(levelType);//默认简单
                subject.setOptionSortId(optionSortId);
                subject.setPromptWord(promptWord);
                subject.setDescription(description);
                subject.setSubjectOptionList(subjectOptionList);
                subjectList.add(subject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getOriginalFilename()+" 完成导入。。。。。");
        return subjectList;
    }
    //endregion

    //region 解析用户文字题,外包文字题
    public  List<Subject> readUserSubjectExcel(MultipartFile file,String createUserName){
        List<Subject> subjectList = new ArrayList<>();
        try {
            // 创建输入流，读取Excel
            InputStream is = file.getInputStream();
            // Workbook类
            Workbook workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            //获得当前sheet的开始行
            int firstRowNum  = sheet.getFirstRowNum()+1;
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环除了第一行的所有行
            System.out.println(file.getOriginalFilename()+" 正在导入。。。。。");
            System.out.println(file.getOriginalFilename()+" 总共 : "+(lastRowNum+1)+" 条记录");
            for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if(row == null){
                    continue;
                }
                Subject subject = new Subject();
                //读取列的数据
                String promptWord = row.getCell(1).toString().replaceAll(" ","");if(promptWord.equals("")){ continue; }
                //String typName = row.getCell(1).getStringCellValue().replaceAll(" ","");
                String answer = row.getCell(2).toString().replaceAll(" ","");
                String option2 = row.getCell(3).toString().replaceAll(" ","");
                String option3 = row.getCell(4).toString().replaceAll(" ","");
                String option4 = row.getCell(5).toString().replaceAll(" ","");
                String description = row.getCell(6).toString().replaceAll(" ","");
                //int levelType = row.getCell(2).getStringCellValue().replaceAll(" ","").equals("简单")? 1 : 3;
                List<SubjectOption> subjectOptionList = new ArrayList<>();
                subjectOptionList.add(new SubjectOption(0,0,answer,1));
                subjectOptionList.add(new SubjectOption(0,0,option2,2));
                subjectOptionList.add(new SubjectOption(0,0,option3,3));
                subjectOptionList.add(new SubjectOption(0,0,option4,4));
                Collections.shuffle(subjectOptionList);
                int optionSortId = 0;
                for(int i = 0;i<subjectOptionList.size();i++){
                    String option = subjectOptionList.get(i).getOption();
                    if(answer.equals(option)){
                        optionSortId = i+1;
                    }
                }
                subject.setAnswer(answer);
                subject.setStatus(0);
                subject.setModeType(3);
                subject.setCreateUserName(createUserName);
                subject.setLevelType(3);//默认困难
                subject.setOptionSortId(optionSortId);
                subject.setPromptWord(promptWord);
                subject.setDescription(description);
                subject.setSubjectOptionList(subjectOptionList);
                subjectList.add(subject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getOriginalFilename()+" 完成导入。。。。。");
        return subjectList;
    }
    //endregion

    //endregion

    //region uploadWeekExcel
    public String uploadWeekExcel(HttpServletRequest req,
                              MultipartFile[] files){
        BasicResultObject obj = new BasicResultObject();
        List<Subject> subjectList = this.analysisWeekExcel(files);
        String existsSubjectName = "";
        if(!subjectTypeDao.isExistsByName(typeName)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "题目类型不存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        if(!weekTitleDao.isExistsByName(titleName)){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "封面小标题不存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        int titleId = Integer.parseInt(weekTitleDao.getInfoBySmallName(titleName).get("id").toString());
        int typeId =  Integer.parseInt(subjectTypeDao.getSubjectTypeByName(typeName).get("id").toString());
        for(int i=0;i<subjectList.size();i++){
            String title = subjectList.get(i).getPromptWord();
            if(subjectDao.isExistsByTitle(title)){
                existsSubjectName += title + ",";
            }
        }
        if(existsSubjectName.length() > 0 ){
            obj.Tag = BasicTagState.TagFailure;
            obj.Message = "题目【"+existsSubjectName+"】已存在，请重新填写！";
            return JSONObject.toJSONString(obj);
        }
        for(int i=0;i<subjectList.size();i++){
            Subject subject = subjectList.get(i);
            subject.setType(1);
            subject.setModeType(3);
            subject.setStatus(0);
            subject.setSortId(subjectDao.getMaxSortId()+1);
            subject.setTypeId(typeId); //相当于没有选择
            int subjectId = subjectDao.insert(subjectList.get(i));
            List<SubjectOption> subjectOptionList = subjectList.get(i).getSubjectOptionList();
            for(int j =0;j<subjectOptionList.size();j++){
                subjectOptionList.get(j).setSubjectId(subjectId);
                subjectOptionList.get(j).setSortId(j+1);
                subjectOptionDao.insert(subjectOptionList.get(j));
            }
            int maxSortId2 = weekSubjectDao.getMaxSortId()+1;
            weekSubjectDao.insert(new WeekSubject(0,subjectId,titleId,maxSortId2));
            /*
             *修改初始化的正确率
             * 困难 1:0%~32% 24/30
             * 一般 2:33%~66% 15/30
             * 简单 3:67%~100% 6/30
             * */
            if(subject.getLevelType() > 0){
                int rightNum = 24;
                int totalNum = 30;
                int errorNUm = 6;
                if(subject.getLevelType() == 2){
                    rightNum = 15;
                    errorNUm = 15;
                }else if(subject.getLevelType() == 3){
                    rightNum = 18;
                    errorNUm = 72;
                    totalNum = 90;
                }
                subjectRatioService.update(subjectId,totalNum,rightNum,errorNUm);
            }
        }
        return JSONObject.toJSONString(obj);
    }

    public List<Subject> analysisWeekExcel(MultipartFile[] files){
        BasicResultObject resultObj = new BasicResultObject();
        List<Subject> subjectList = new ArrayList<>();
        try {
            String textFileName = files[0].getOriginalFilename();
            //林欣欣近代史题58题_林欣欣_历史_节日大盘点.docx
            String createUserName = textFileName.split("_")[1]; // 出题人
            typeName = textFileName.split("_")[2];
            titleName = textFileName.split("_")[3].split("\\.")[0];
            MultipartFile file = files[0];
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
                Subject subject = new Subject();
                List<SubjectOption> optionList = new ArrayList<>();

                for(XWPFParagraph paragraph : paras){
                    String words = paragraph.getText().replaceAll(" ","");
                    List<XWPFRun> runsLists = paragraph.getRuns();//获取段楼中的句列表
                    /*内容红色就是答案*/
                    if( words.trim().length() > 0 && runsLists != null && runsLists.get(0).getColor()!=null && runsLists.get(0).getColor().equals("FF0000") ){
                        subject.setAnswer(words);
                    }
                    words = words.trim().length() == 0 ? "??????????" : words;
                    if(i == 1){ // 第一行题目
                        subject.setPromptWord(words);
                    }else if(readIndex > 1 && readIndex < 6){
                        SubjectOption option = new SubjectOption();
                        if(words.equals(subject.getAnswer())){
                            subject.setOptionSortId(readIndex - 1);
                        }
                        option.setSortId(readIndex - 1);
                        option.setOption(words);
                        optionList.add(option);

                    }else if(readIndex >= 6){
                        int levelType = 0;
                        if(readIndex == 6){
                            String[] levelTypeArr = {"简单","一般","困难"};
                            for(int j =0 ;j<levelTypeArr.length;j++) {
                                if(levelTypeArr[j].equals(words)){
                                    levelType = j+1;
                                    break;
                                }
                            }
                            if(levelType > 0){
                                subject.setLevelType(levelType);
                            }
                        }
                        if(levelType <= 0) {
                            String lastStr = words.substring(words.length() - 1, words.length());
                            if (lastStr.equals("?") || lastStr.equals("？")) {
                                subject.setDescription(description);
                                subject.setSubjectOptionList(optionList);
                                subject.setCreateUserName(createUserName);
                                subjectList.add(subject);
                                if (words.equals("??????????")) {
                                    break;
                                }
                                optionList = new ArrayList<>();
                                subject = new Subject();
                                subject.setPromptWord(words);
                                description = "";
                                readIndex = 1;
                            } else {
                                description += words;
                            }
                        }
                    }
                    if(paras.size() == i){ // 最后一行
                        subject.setDescription(description);
                        subject.setSubjectOptionList(optionList);
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

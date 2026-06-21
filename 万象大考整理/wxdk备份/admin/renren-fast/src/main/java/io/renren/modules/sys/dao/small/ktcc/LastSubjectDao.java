package io.renren.modules.sys.dao.small.ktcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LastSubjectDao {
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;


    public List<Map<String,Object>> getSubjectUse(){
        StringBuilder sb = new StringBuilder();
        int totalCountSubject = subjectDao.getTotalCountByStatus(1);
        double totalIndex =  Math.ceil(totalCountSubject/100);
        String tableName = "t"+ new Date().getTime();
        sb.append("CREATE TABLE  "+tableName+" AS ");
        sb.append("SELECT (SELECT COUNT(1) FROM ktcc_subject WHERE flag <3 AND STATUS =1 ) AS totalNum,");
        sb.append("(SELECT COUNT(1) FROM ktcc_user  ) AS totalUserNum,");
        int beginCardNumber = 0,endCardNumber = 100;
        for(int i = 0 ;i < totalIndex ; i++){
            if(i==0){
                sb.append("SUM(CASE WHEN "+beginCardNumber+"<=cardNumber AND cardNumber<="+endCardNumber+" THEN 1 ELSE 0 END) AS `"+beginCardNumber+"-"+endCardNumber+"`,");
            }else if(i == (totalIndex - 1)){
                sb.append("SUM(CASE WHEN "+beginCardNumber+"<=cardNumber AND cardNumber<="+endCardNumber+" THEN 1 ELSE 0 END) AS `"+(beginCardNumber+1)+"-"+endCardNumber+"` ");
            }
            else{
                sb.append("SUM(CASE WHEN "+beginCardNumber+"<=cardNumber AND cardNumber<="+endCardNumber+" THEN 1 ELSE 0 END) AS `"+(beginCardNumber+1)+"-"+endCardNumber+"`,");
            }
            beginCardNumber += 100;
            endCardNumber += 100;
        }
        /*sb.append("SUM(CASE WHEN 0<=cardNumber AND cardNumber<=100 THEN 1 ELSE 0 END) AS `0-100`, ");
        sb.append("SUM(CASE WHEN 100<cardNumber AND cardNumber<=200 THEN 1 ELSE 0 END) AS `101-200`,");
        sb.append("SUM(CASE WHEN 200<cardNumber AND cardNumber<=300 THEN 1 ELSE 0 END) AS `201-300`,");
        sb.append("SUM(CASE WHEN 300<cardNumber AND cardNumber<=400 THEN 1 ELSE 0 END) AS `301-400`,");
        sb.append("SUM(CASE WHEN 400<cardNumber AND cardNumber<=500 THEN 1 ELSE 0 END) AS `401-500`,");
        sb.append("SUM(CASE WHEN 500<cardNumber AND cardNumber<=600 THEN 1 ELSE 0 END) AS `501-600` ");*/
        sb.append("FROM `ktcc_lastsubject`; ");
        jdbcOperations.update(sb.toString(),new HashMap<>());
        sb.setLength(0);
        sb.append("SELECT a.cardNumber,totalNum,totalUserNum,userNum,CONVERT(userNum/totalUserNum,DECIMAL(18,2)) AS userRatio FROM ( ");
        beginCardNumber = 0 ;
        endCardNumber = 100;
        for(int i = 0 ;i < totalIndex ; i++){
            if(i==0){
                sb.append(" SELECT '"+beginCardNumber+"-"+endCardNumber+"' AS cardNumber,totalNum,totalUserNum,`"+beginCardNumber+"-"+endCardNumber+"` AS userNum FROM  "+tableName);
                sb.append(" UNION ALL ");
            }
            else if(i == (totalIndex - 1)){
                sb.append(" SELECT '"+beginCardNumber+"-"+endCardNumber+"' AS cardNumber,totalNum,totalUserNum,`"+(beginCardNumber+1)+"-"+endCardNumber+"` AS userNum FROM  "+tableName + " ) a;");

            }
            else{
                sb.append(" SELECT '"+beginCardNumber+"-"+endCardNumber+"' AS cardNumber,totalNum,totalUserNum,`"+(beginCardNumber+1)+"-"+endCardNumber+"` AS userNum FROM  "+tableName);
                sb.append(" UNION ALL ");
            }
            beginCardNumber += 100;
            endCardNumber += 100;
        }
        /*sb.append("SELECT '0-100' AS cardNumber,totalNum,totalUserNum,`0-100` AS userNum FROM  "+tableName);
        sb.append(" UNION ALL");
        sb.append(" SELECT '101-200' AS cardNumber,totalNum,totalUserNum,`101-200` AS userNum FROM "+tableName);
        sb.append(" UNION ALL");
        sb.append(" SELECT '201-300' AS cardNumber,totalNum,totalUserNum,`201-300` AS userNum FROM "+tableName);
        sb.append(" UNION ALL");
        sb.append(" SELECT '301-400' AS cardNumber,totalNum,totalUserNum,`301-400` AS userNum FROM "+tableName);
        sb.append(" UNION ALL");
        sb.append(" SELECT '401-500' AS cardNumber,totalNum,totalUserNum,`401-500` AS userNum FROM "+tableName);
        sb.append(" UNION ALL");
        sb.append(" SELECT '501-600' AS cardNumber,totalNum,totalUserNum,`501-600` AS userNum FROM "+tableName+" ) a;");*/
        List<Map<String,Object>> resultMap = jdbcOperations.queryForList(sb.toString(),new HashMap<>());
        sb.setLength(0);
        sb.append(" DROP TABLE "+tableName+";");
        jdbcOperations.update(sb.toString(),new HashMap<>());
        return resultMap;
    }
}

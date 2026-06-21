package com.example.vientianetest.dao.subject;

import com.example.vientianetest.dao.DataConfigDao;
import org.apache.poi.util.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SubjectWeightDao {
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private DataConfigDao dataConfigDao;

    public Map<String, Map<String, Map<String, Object>>> getSubjectWeight() {
        List<Map<String, Object>> listMap = jdbcOperations.queryForList("SELECT `subjectSortId`,`type`,`value`,sortId FROM `wxdk_subjectweight` ORDER BY subjectSortId,sortId", new HashMap<>());
        int len = listMap.size();
        int subjectSortLen = 0;
        int index = 0;
        String lastType = "" ;
        Map<String, Map<String, Map<String, Object>>> resultMap = new HashMap<>();
        for (int i = 0; i < len; i++) {
            Map<String, Object> map = listMap.get(i);
            String subjectSortId = map.get("subjectSortId").toString();
            String type = map.get("type").toString();
            int value = Integer.parseInt(map.get("value").toString());
            if (resultMap.get(subjectSortId) == null) {
                index = 0;
                subjectSortLen = this.getTotalSubjectWeightByType(Integer.parseInt(subjectSortId));
                resultMap.put(subjectSortId, new HashMap<>());
            }
            if (resultMap.get(subjectSortId).get(type) == null) {
                resultMap.get(subjectSortId).put(type, new HashMap<>());
            }
            int minRatio = 0;
            int maxRatio = 100;
            if (index == 0) {
                minRatio = 0;
                maxRatio = (int) (((double) value / subjectSortLen) * 100);
            } else if (index > 0) {
                int valueSub = Integer.parseInt(resultMap.get(subjectSortId).get(lastType).get("maxRatio").toString());
                minRatio = valueSub + 1;
                maxRatio = index == (subjectSortLen - 1) ? 100 : valueSub + (int) (((double) value / subjectSortLen) * 100);
            }
            lastType = type;
            resultMap.get(subjectSortId).get(type).put("minRatio", minRatio);
            resultMap.get(subjectSortId).get(type).put("maxRatio", maxRatio);
            index++;
        }
        return resultMap;
    }

    public int getTotalSubjectWeightByType( int subjectSortId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("subjectSortId",subjectSortId);
        return jdbcOperations.queryForObject("SELECT sum(value) FROM `wxdk_subjectweight` where subjectSortId = :subjectSortId",paramMap,Integer.class);
    }
}

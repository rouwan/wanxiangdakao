package com.example.vientianetest.service.user;

import com.example.vientianetest.domain.Subject;
import com.example.vientianetest.domain.User;
import com.example.vientianetest.redis.UserRankSubjectRecordRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRankSubjectRecordService {
    @Autowired
    private UserRankSubjectRecordRedis userRankSubjectRecordRedis;

    public void update(List<User> userList, List<Subject> subjectList){
        if(userList != null) {
            String subjectIds = "";
            for (int i = 0; i < subjectList.size(); i++) {
                if (i == subjectList.size() - 1) {
                    subjectIds += subjectList.get(i).getId() + ";";
                } else {
                    subjectIds += subjectList.get(i).getId() + ",";
                }
            }
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getBoot()) {
                    continue;
                }
                userRankSubjectRecordRedis.addUserSubjectRecord(userList.get(i).getUser().getUserId(), subjectIds);
            }
        }
    }
}

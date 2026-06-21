package com.example.vientianetest.timer;

import com.example.common.utils.DateUtils;
import com.example.vientianetest.dao.ClearDataTimeDao;
import com.example.vientianetest.redis.subject.SubjectRedis;
import com.example.vientianetest.service.WebSocketRankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.vientianetest.template.HitCatTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;


@Component
@EnableScheduling
public class ScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;
    @Autowired
    private ClearDataTimeDao clearDataTimeDao;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    @Autowired
    private WebSocketRankService webSocketRankService;
    @Autowired
    private SubjectRedis subjectRedis;
    @Autowired
    private HitCatTemplate hitCatTemplate;

    private String KEY_PACKAGE_RANK = "server:rank:paragraphList";
    private String KEY_PACKAGE_COMBAT = "server:rank:combatList";
    private String KEY_PACKAGE_CONTINUEWIN = "server:rank:continueWinList";
    private String KEY_PACKAGE_ROOMWIN = "server:rank:roomWinList";

    //每个月1号->凌晨两点 清空排行榜
    @Scheduled(cron  = "0 0 0 1 * *")
    public void clearRankList() throws InterruptedException {
        try{
            logger.info("清空排行榜 Scheduled 开始");
            Set<Object> objectSet = zSetOperations.range(KEY_PACKAGE_RANK,0,-1);
            for(Object object : objectSet){
                int userId = ((Integer) object).intValue();
                zSetOperations.add(KEY_PACKAGE_RANK, userId, 0);
            }
            webSocketRankService.initUserParagraph();//有玩家在玩排位,初始化段位
            logger.info("清空排行榜 Scheduled 结束");
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.info("清空排行榜异常：",ex.getMessage());
        }
    }

    //每天在规定时间执行 清空战斗力 和 连胜纪录
    @Scheduled(cron  = "0 0 2 * * *")
    public void clearCombatList() throws InterruptedException {
        try{
            if(clearDataTimeDao.isArriveClearTimeByType(1)){
                logger.info("每天在规定时间执行 Scheduled 开始");
                jdbcOperations.update("UPDATE wxdk_usercombat SET combat = 0, modifyTime=NOW() ",new HashMap<>());
                jdbcOperations.update("UPDATE wxdk_usercombatdetail SET combat = 0, modifyTime=NOW() ",new HashMap<>());
                Set<Object> objectSet = zSetOperations.range(KEY_PACKAGE_COMBAT,0,-1);
                for(Object object : objectSet){
                    int userId = ((Integer) object).intValue();
                    zSetOperations.add(KEY_PACKAGE_COMBAT, userId, 0);
                }
                //清空连胜纪录
                jdbcOperations.update("UPDATE wxdk_usergamestatisrank SET continueWinNum = 0, continueLoseNum = 0, maxContinueWinNum = 0,modifyTime= NOW() ",new HashMap<>());
                Set<Object> continueWinObjectSet = zSetOperations.range(KEY_PACKAGE_CONTINUEWIN,0,-1);
                for(Object object : continueWinObjectSet){
                    int userId = ((Integer) object).intValue();
                    zSetOperations.add(KEY_PACKAGE_CONTINUEWIN, userId, 0);
                }
                logger.info("每天在规定时间执行 Scheduled 结束");
            }
            //清空用户的每天战斗力
            jdbcOperations.update("TRUNCATE TABLE `wxdk_usercombatday`;",new HashMap<>());
            //每周一凌晨2点清空群对战胜场
            int week = DateUtils.dayForWeek(new Date());
            if(week == 2) {//周天返回是1
                jdbcOperations.update("CALL p_event_wxdk_sendRoomGameTopEmail()",new HashMap<>());
                Set<Object> roomWinObjectSet = zSetOperations.range(KEY_PACKAGE_ROOMWIN,0,-1);
                for(Object object : roomWinObjectSet){
                    int userId = ((Integer) object).intValue();
                    zSetOperations.add(KEY_PACKAGE_ROOMWIN, userId, 0);
                }
            }
            //更新redis的题库
            subjectRedis.initSubject();
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.info("每天在规定时间执行异常：",ex.getMessage());
        }
    }

    //5点发撸猫消息模板
    @Scheduled(cron  = "0 0 17 * * *")
    public void sendTemplate() throws InterruptedException {
        try {
            hitCatTemplate.sendTemplate();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("发送模板 异常：", ex.getMessage());
        }
    }
}

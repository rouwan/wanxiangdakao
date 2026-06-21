package com.example.vientianetest.service.robot;

import com.alibaba.fastjson.JSONObject;
import com.example.vientianetest.dao.GameRankDao;
import com.example.vientianetest.dao.robot.*;
import com.example.vientianetest.domain.*;
import com.example.vientianetest.redis.RobotDayRedis;
import com.example.vientianetest.service.SubjectRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//机器人默认游戏
@Component
public class RobotSimulateGameService {
    @Autowired
    private RobotDayRedis robotDayRedis;
    @Autowired
    private RobotDao robotDao;
    @Autowired
    private RobotGameRankDao robotGameRankDao;
    @Autowired
    private RobotDayRecordDao robotDayRecordDao;
    @Autowired
    private SubjectRatioService subjectRatioService;
    @Autowired
    private RobotDayRecordService robotDayRecordService;
    @Autowired
    private RobotParagraphService robotParagraphService;
    @Autowired
    private RobotGameStatisRankService robotGameStatisRankService;


    public Game match(){
        synchronized (RobotSimulateGameService.class){
            int firstCount = robotDayRecordDao.getFirstCount();
            List<User> userList = new ArrayList<>();
            if(firstCount > 0 ){
                if(firstCount >= 2){
                    userList.addAll(robotDao.getFirstRandRobotList());
                }else if(firstCount == 1){
                    System.out.println("还剩一条数据");
                    userList.addAll(robotDao.getFirstRandRobotList());
                    userList.add(robotDao.getRandRobot2(0));
                }
                for(int i=0;i<userList.size();i++){
                    robotDayRecordService.updateFistCounterByUserId(userList.get(i).getUser().getUserId(),1);
                }
            } else{
                //没有的话，就正常跑
                int afterCount = robotDayRecordDao.getAfterCount();
                if(afterCount >= 2){
                    userList.addAll(robotDao.getAfterRandRobotList());
                }else if(afterCount == 1){
                    System.out.println("还剩一条数据");
                    userList.addAll(robotDao.getAfterRandRobotList());
                    userList.add(robotDao.getRandRobot2(0));
                }
                for(int i=0;i<userList.size();i++){
                    robotDayRecordService.updateAfterCounterByUserId(userList.get(i).getUser().getUserId(),1);
                }
            }
            if(userList.size() < 2){
                robotDayRedis.update(1);
                return null;
            }
            //User user = userList.get(0).getAbility().getWinRatio() > userList.get(1).getAbility().getWinRatio() ? userList.get(1) : userList.get(0);
            List<Subject> subjectList = subjectRatioService.getRandSubjectByRightRatio(userList,null,"");//全部类型
            int gameId = robotGameRankDao.insert(userList.get(0).getUser().getUserId(),userList.get(1).getUser().getUserId(),Game.STATUS_NEW);
            Game game = new Game();
            game.setId(gameId);
            game.setStatus(Game.STATUS_NEW);
            game.setUserList(userList);
            game.setSubjectList(subjectList);
            return game;
        }
    }

    public void start(Game game){
        game.setStatus(Game.STATUS_PLAYING);
    }

    public void answer(Game game){
        for(int i =0;i<5;i++){
            for(int j =0;j<game.getUserList().size();j++){
                User user = game.getUserList().get(j);
                Subject subject = game.getSubjectList().get(i);
                int option = user.getRobotRandomOption(subject);
                Answer answer = new Answer();
                answer.setUid(user.getUser().getUserId());
                answer.setGameId(game.getId());
                answer.setIndex(user.getIndex());
                answer.setAnswer(option);
                answer.setBoot(true);
                //超时处理
                user.onAnswer(game,answer);
            }
        }
    }

    public void end(Game game){
        game.setStatus(Game.STATUS_END);
        game.endGame(0); //选出获胜者
        robotGameStatisRankService.update(game); //统计用户比赛次数
        robotGameRankDao.update(game);
        //加段位和星星
        robotParagraphService.update(game);
    }

    public void game() {

        if(robotDayRedis.isTodayFinish()){
            return;
        }
        //第一步 匹配
        Game game = this.match();
        if (game == null) {
            return;
        }
        //第二步 开始游戏
        this.start(game);
        //第三步 答题
        this.answer(game);
        //第四步 结束
        this.end(game);

    }

}

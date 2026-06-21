package com.example.vientianetest.timer;//package com.example.lookguess.timer;

import com.example.vientianetest.service.WebSocketRankService;
import com.example.vientianetest.service.WebSocketRoomService;
import com.example.vientianetest.service.robot.RobotSimulateGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 游戏主调度引擎：用 Java Timer 实现的轻量级任务轮询器。
 *
 * 定时器每 1 秒扫描一次任务表，判断是否有任务达到执行间隔，
 * 若有则在独立线程中执行。这是驱动匹配、Bot 答题、超时判定的核心。
 *
 * 关键任务及间隔：
 *   排位_匹配玩家     1s  → runMatch()    匹配池轮询
 *   排位_自动游戏     1s  → runRobot()    Bot 自动答题
 *   排位_判断超时     1s  → runTimeOut()  答题超时推进到下一题
 *   房间_机器人自动答题 1s  → runRobot()    房间 Bot 答题
 *   房间_匹配玩家     1s  → runMatch()    房间匹配
 *   排位/房间_心跳超时  10s → runKeepTimeOut() 120s 无心跳踢人
 *   修改题目正确率     3s  → runSubjectRatio() 批量更新正确率
 */
@Component
public class TaskTimer {
    private static final Logger logger = LoggerFactory.getLogger(TaskTimer.class);
    private Map<String, TaskItem> taskMap = new HashMap<>();
    //private Map<String, TaskItem> RankTaskMap = new HashMap<>();
    @Autowired
    private WebSocketRankService webSocketRankService;
    @Autowired
    private WebSocketRoomService webSocketRoomService;
    @Autowired
    private RobotSimulateGameService robotSimulateGameService;
    private void addTasks() {
        this.addTask(new TaskItem("修改题目的正确率", 3, () -> {
            webSocketRankService.runSubjectRatio();
        }));
        this.addTask(new TaskItem("排位_匹配玩家", 1, () -> {
            webSocketRankService.runMatch();
        }));
        this.addTask(new TaskItem("排位_自动游戏", 1, () -> {
            webSocketRankService.runRobot();
        }));
        this.addTask(new TaskItem("排位_判断超时", 1, () -> {
            webSocketRankService.runTimeOut();
        }));
        this.addTask(new TaskItem("房间_判断超时", 1, () -> {
            webSocketRoomService.runTimeOut();
        }));
        /*this.addTask(new TaskItem("房间_群发大厅消息", 1, () -> {
            webSocketRoomService.runSendAll();
        }));*/
        this.addTask(new TaskItem("房间_机器人自动答题", 1, () -> {
            webSocketRoomService.runRobot();
        }));
        this.addTask(new TaskItem("房间_匹配玩家", 1, () -> {
            webSocketRoomService.runMatch();
        }));
        this.addTask(new TaskItem("房间_用户心跳包超时", 10, () -> {
            webSocketRoomService.runKeepTimeOut();
        }));
        this.addTask(new TaskItem("排位_用户心跳包超时", 10, () -> {
            webSocketRankService.runKeepTimeOut();
        }));
        /*this.addTask(new TaskItem("模拟机器人匹配", 1, () -> {
            robotSimulateGameService.game();
        }));*/
    }


    public void addTask(TaskItem taskItem) {
        taskMap.put(taskItem.getName(), taskItem);
    }
    @PostConstruct
    public void start() {
        addTasks();
        //addRankTasks();
        System.out.println("开始任务");
        new Timer("scheduler").schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Date now = new Date();
                    for (String name : taskMap.keySet()) {
                        TaskItem taskItem = (TaskItem) taskMap.get(name);
                        if (!taskItem.isRunning() && (now.getTime() - taskItem.getPreviousTime().getTime()) / 1000 >= taskItem.getInterval()) {
                            //logger.debug("开始任务: {}", name);
                            taskItem.setRunning(true);
                            taskItem.setPreviousTime(now);
                            Thread t = new Thread(() -> {
                                try {
                                    taskMap.get(name).getTask().execute();
                                } catch (Throwable ex) {
                                    logger.debug("任务出错, task:{}", name);
                                    ex.printStackTrace();
                                    logger.debug("任务出错, task:{} ex:{}", name, ex);
                                    //System.out.println("任务出错, task:"+name+" t:"+t+"");
                                } finally {
                                    taskItem.setRunning(false);
                                    //logger.debug("完成任务: {}", name);
                                }
                            });
                            t.setName(name);
                            t.start();
                            t = null;
                        }
                    }
                } catch (Throwable t) {
                    logger.error("定时任务发生异常, t:{}", t);
                }
            }
        }, 10000, 1000);

    }
}



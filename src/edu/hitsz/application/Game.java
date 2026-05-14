package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dao.Grade;
import edu.hitsz.dao.GradeDaoImpl;
import edu.hitsz.supply.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractSupply> supplies;

    //Boss 敌机生成阈值
    private int bossTriggerScore = 500;
    //Boss 敌机个数(不能超过1）
    private int bossEnemyCount = 0;
    // 记录boss出现的代数
    private  int bossLevel = 1;
    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //英雄机和敌机射击周期
    protected double shootCycle = 20;
    private int shootCounter = 0;

    //当前玩家分数
    private int score = 0;

    //当前玩家名称
    private String playerName = "testPlayerName";

    //游戏结束标志
    private boolean gameOverFlag = false;

    //三种难度的数据文件
    String easyPath = "D:/AircraftWar-base1.0/easy.txt";
    String commonPath = "D:/AircraftWar-base1.0/common.txt";
    String difficultPath = "D:/AircraftWar-base1.0/difficult.txt";

    public Game() {
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        supplies = new LinkedList<>();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                enemySpawnCounter++;
                if (enemySpawnCounter >=enemySpawnCycle) {
                    enemySpawnCounter = 0;

                    //当游戏分数达到一定值时，产生Boss敌机
                    if ((score > (bossLevel * bossTriggerScore)) && (bossEnemyCount == 0)){
                        BossEnemyCreator aircraftFactory = new BossEnemyCreator();
                        bossEnemyCount = 1;
                        int randomSpeedX = (Math.random() > 0.5) ? 2 : -2;
                        enemyAircrafts.add(aircraftFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                randomSpeedX,
                                0,
                                100));
                        bossLevel += 1;
                    }

                    // 利用随机因子实现敌机的随机产生
                    double rand =Math.random();
                    // 产生王牌敌机
                    if (enemyAircrafts.size() < enemyMaxNumber && rand <= 0.1) {
                        HeroEnemyCreator aircraftFactory = new HeroEnemyCreator();
                        int randomSpeedX = (Math.random() > 0.5) ? 2 : -2;
                        enemyAircrafts.add(aircraftFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.HERO_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                randomSpeedX,
                                12,
                                45));
                    }
                    // 产生精锐敌机
                    else  if (enemyAircrafts.size() < enemyMaxNumber && rand <= 0.2) {
                        AdvancedEnemyCreator aircraftFactory = new AdvancedEnemyCreator();
                        int randomSpeedX = (Math.random() > 0.5) ? 1 : -1;
                        enemyAircrafts.add(aircraftFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                randomSpeedX,
                                12,
                                40));
                    }
                    // 产生精英敌机
                    else if (enemyAircrafts.size() < enemyMaxNumber && rand <= 0.5){
                        EliteEnemyCreator aircraftFactory = new EliteEnemyCreator();
                        enemyAircrafts.add(aircraftFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                0,
                                12,
                                30));

                    }

                    // 产生普通敌机
                 else  if (enemyAircrafts.size() < enemyMaxNumber && rand < 0.8) {
                       MobEnemyCreator aircraftFactory = new MobEnemyCreator();
                        enemyAircrafts.add(aircraftFactory.createEnemy ( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                                0,
                                10,
                                30)

                        );
                    }



                }

                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 道具移动
                suppliesMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();

            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            //英雄机射击
            heroBullets.addAll(heroAircraft.shoot());
            //  敌机射击
            for (AbstractAircraft enemy : enemyAircrafts) {
                    enemyBullets.addAll(enemy.shoot());
                }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }
private void suppliesMoveAction(){
        for (AbstractSupply abstractSupply : supplies ){
            abstractSupply.forward();
        }
}

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        //  敌机子弹攻击英雄机
        for (BaseBullet bullet_2 : enemyBullets){
            if (bullet_2.notValid()){
                continue;
            }
            if (heroAircraft.notValid()){
                continue;
            }
            if (heroAircraft.crash(bullet_2)){
                heroAircraft.decreaseHp(bullet_2.getPower());
                bullet_2.vanish();
            }
        }


        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        if (enemyAircraft instanceof MobEnemy) {
                            score += 10;
                        }else if (enemyAircraft instanceof  EliteEnemy) {
                            score += 20;

                            // 精锐敌机被击落后有80%的概率掉落道具
                            if (Math.random() <= 0.8) {
                                double rand_2 = Math.random();
                                if (rand_2 <= 0.5) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("BloodSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                } else if (rand_2 > 0.5 && rand_2 <= 0.9) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FireSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                } else {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FirePlusSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                }

                            }

                        } else if (enemyAircraft instanceof AdvancedEnemy){
                            score += 30;
                            // 精英敌机被击落后有90%的概率掉落道具
                            if (Math.random() <= 0.9){
                                double rand_3 = Math.random();
                                if (rand_3 <= 0.4) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("BloodSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                } else if (rand_3 > 0.4 && rand_3 <= 0.7) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FireSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                } else if(rand_3 > 0.7 && rand_3 <=0.9){
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FirePlusSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                }else {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("BombSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                }

                            }
                        }else if (enemyAircraft instanceof HeroEnemy){
                            score += 40;
                            // 精英敌机被击落后有95%的概率掉落道具
                            if (Math.random() <= 0.95){
                                double rand_4 = Math.random();
                                if (rand_4 <= 0.3) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("BloodSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                } else if (rand_4 > 0.3 && rand_4 <= 0.6) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FireSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                } else if(rand_4 > 0.6 && rand_4 <=0.7){
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FirePlusSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                }else if(rand_4 >0.7 && rand_4<= 0.9){
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("BombSupply", enemyAircraft.getLocationX(), enemyAircraft.getLocationY()));
                                }else {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FreezeSupply" ,enemyAircraft.getLocationX(), enemyAircraft.getLocationY() ));
                                }

                            }
                        }else if (enemyAircraft instanceof BossEnemy){
                            score += 80;
                            bossEnemyCount = 0;
                            // boss敌机被击落后随机掉落三种道具
                            for (int i = 0;i < 3 ; i++){
                                double rand_5 = Math.random();
                                int offset = (i - 1)*50;
                                if (rand_5 <= 0.3) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("BloodSupply", enemyAircraft.getLocationX()+offset, enemyAircraft.getLocationY()));
                                } else if (rand_5 > 0.3 && rand_5 <= 0.6) {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FireSupply", enemyAircraft.getLocationX()+offset, enemyAircraft.getLocationY()));
                                } else if(rand_5 > 0.6 && rand_5 <=0.7){
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FirePlusSupply", enemyAircraft.getLocationX()+offset, enemyAircraft.getLocationY()));
                                }else if(rand_5 >0.7 && rand_5 <= 0.9){
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("BombSupply", enemyAircraft.getLocationX()+offset, enemyAircraft.getLocationY()));
                                }else {
                                    SimpleFactory supplyFactory = new SimpleFactory();
                                    supplies.add(supplyFactory.createSupplies("FreezeSupply" ,enemyAircraft.getLocationX()+offset, enemyAircraft.getLocationY() ));
                                }

                            }
                        }


                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        //我方获得道具，道具生效
        for (AbstractSupply supply : supplies ){

            if (heroAircraft.crash(supply)){
               supply.Effect(heroAircraft,enemyAircrafts,enemyBullets);
               supply.vanish();
                }
        }


    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        supplies.removeIf(AbstractSupply::notValid);


    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0 && !gameOverFlag) {
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over!");
            Main.cardLayout.show(Main.cardPanel, "SCORE_UI");
            String inputName = JOptionPane.showInputDialog(this, "游戏结束，你的得分为 " + score + "。\n请输入玩家名字：");
            if (inputName != null && !inputName.trim().isEmpty()) {
                this.playerName = inputName;
            } else {
                this.playerName = "Anonymous"; // 如果用户不输入或者点取消，给个默认名
            }
            // 写入并且打印排行榜
            saveScoreToFile(easyPath,score,playerName);
        }
    };

    /**
     在游戏结束后，打印属于该难度的排行榜
     */
    private void saveScoreToFile(String path , int score ,String playerName){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = now.format(formatter);
        Grade grade = new Grade(playerName ,time,score);
        GradeDaoImpl gradeDaoImpl = new GradeDaoImpl(path);
        gradeDaoImpl.doAdd(grade);
        List<Grade> grades = new LinkedList<>();
        grades = gradeDaoImpl.getAllGrades();
        gradeDaoImpl.outGrade(grades);

    }

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        //  绘制道具
        paintImageWithPositionRevised(g,supplies);


        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}

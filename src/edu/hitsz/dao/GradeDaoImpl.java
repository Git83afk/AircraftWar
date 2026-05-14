package edu.hitsz.dao;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
public class GradeDaoImpl implements  GradeDAO{
    //操作文件的路径
    private String filePath;

    public GradeDaoImpl (String filePath){
        this.filePath = filePath;
    }

    @Override
    public  List<Grade> getAllGrades(){
        List<Grade> grades = new LinkedList<Grade>();
        File file = new File(filePath);

        //若文件不存在，直接返回空列表
        if(!file.exists()){
            return grades;
        }

        try(
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader reader = new BufferedReader(isr);

        ){
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");
                if (data.length == 3){
                    String name = data[0];
                    int score = Integer.parseInt(data[1]);
                    String time = data[2];
                    grades.add(new Grade(name , time , score));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return grades;
    }

    @Override
    public void doAdd(Grade grade){
        String line = grade.getPlayerName() + "," + grade.getScore() + "," + grade.getTime() + "\n";
        try{
            Files.writeString(Paths.get(filePath),line, StandardCharsets.UTF_8,StandardOpenOption.CREATE ,StandardOpenOption.APPEND);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void doDelete(String playerName , String time){
        int i =0;
    //先将所有数据读出来
    List<Grade> grades = getAllGrades();
    boolean removed = grades.removeIf(g->g.getPlayerName().equals(playerName) && g.getTime().equals(time));
    if (removed){
        StringBuilder sb = new StringBuilder();
        for (Grade g : grades){
            sb.append(g.getPlayerName()).append(",")
                    .append(g.getScore()).append(",")
                    .append(g.getTime()).append("\n");
        }
        try {
            Files.writeString(Paths.get(filePath),sb.toString(),StandardCharsets.UTF_8);
            System.out.println("删除成功");
        }catch (IOException e){
            e.printStackTrace();
        }
    } else {
        System.out.println("未找到匹配的记录，无需删除");
    }

    }

    @Override
    public List<Grade> findGrade(String playerName){
        List<Grade> grades = getAllGrades();
        List<Grade> playerGrades = new LinkedList<Grade>();
        for(Grade g : grades){
            if (g.getPlayerName().equals(playerName)){
              playerGrades.add(g);
            }
        }
            return playerGrades;

    }

    @Override
    public void outGrade (List<Grade> grades) {

        System.out.println("******************************\n\t\t得分排行榜\n******************************");
        if (grades == null || grades.isEmpty()) {
            System.out.println("没有找到记录");
            return;
        }
        grades.sort((g1,g2) -> Integer.compare(g2.getScore(), g1.getScore()));

        for (int i = 0 ; i < grades.size() ; i++){
            Grade g = grades.get(i);
            System.out.println("第" + (i+1) + "名" +g.getPlayerName() + "," + g.getScore() + "," + g.getTime());
        }
        }
}

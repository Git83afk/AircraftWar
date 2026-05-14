package edu.hitsz.dao;
import java.util.List;
public interface GradeDAO {
    public List<Grade> getAllGrades();
    public void doAdd (Grade grade);
    public void doDelete(String playerName ,String time);
    public List<Grade> findGrade(String playerName);
    public void outGrade (List<Grade> grades);
}

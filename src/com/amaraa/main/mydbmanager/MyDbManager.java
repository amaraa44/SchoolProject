package com.amaraa.main.mydbmanager;

import java.sql.*;
import java.util.ArrayList;


public class MyDbManager {

    static final String DB_URL = "jdbc:h2:~/test";
//    static final String DB_URL = "jdbc:h2:tcp://localhost//E://Projects//Game/h2db";

    static final String USER = "sa";
    static final String PASSWORD = "";

    public MyDbManager() {

        executeSQL("CREATE TABLE IF NOT EXISTS SCORES(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, SCORE INT NOT NULL)");

    }

    /**
     * This method will execute an sql code.
     * <p>If you want to create a new table you need to use this.</p>
     * Or if you want to something else just use this.
     *
     * @param sql SQL code
     */
    public static void executeSQL(String sql) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            statement.executeUpdate(sql);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addScore(int score){
        executeSQL("INSERT INTO SCORES VALUES(null, " + score + ");");
    }
    public static ArrayList<String> selectScores(){
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER,PASSWORD);
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = statement.executeQuery("SELECT TOP 10 * FROM SCORES ORDER BY SCORE DESC");

            ArrayList<String> scores = new ArrayList<>();
            while (resultSet.next()){
                String score = resultSet.getString("SCORE");
//                System.out.println(score);
                scores.add(score);
            }
            return scores;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

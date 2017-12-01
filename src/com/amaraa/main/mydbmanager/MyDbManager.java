package com.amaraa.main.mydbmanager;

import java.sql.*;


/**
 * MyDbManager.executeSQL("INSERT INTO USERS VALUES(null, 'nev');");
 * MyDbManager.insertInToUsers("ize");
 * MyDbManager.deleteFromUsers("nev");
 * MyDbManager.deleteFromUsers(2);
 * MyDbManager.updateNameInUsers(3,"nev");
 * MyDbManager.selectFromUsers(10);
 * System.out.println(MyDbManager.getUsersName(1));
 */
public class MyDbManager {

    static final String DB_URL = "jdbc:h2:tcp://localhost//E://Projects//Game/h2db";

    static final String USER = "sa";
    static final String PASSWORD = "";


    //TODO: SQL TABLE_NAME -- CONSTRUCTOR

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
    //TODO: SLECT FROM SCORES AND RETURN THE SCORE IN AN ARRAYLIST
    public static void selectScores(int i){
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM SCORES");

            int j = 0;
            while (resultSet.next() && j != i) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("NAME");
                System.out.println("ID: " + id + " NAME: " + name);
                i = i + 1;
            }

            resultSet.close();
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * This method will insert a new user to the USERS table.
//     *
//     * @param name What you want to add into the USERS table as NAME.
//     */
//    public static void insertInToUsers(String name) {
//        executeSQL("INSERT INTO USERS VALUES(null, '" + name + "');");
//    }
//
//    /**
//     * This will delete the users based on name.
//     * This method will delete ALL users from the USERS table, who's name equals with the param.
//     *
//     * @param name What you want to delete from the USERS table.
//     */
//    public static void deleteFromUsers(String name) {
//        executeSQL("DELETE FROM USERS WHERE NAME='" + name + "';");
//    }
//
//    /**
//     * This will delete the user based on id.
//     * This method will delete a user from the USERS table.
//     * This will delete the
//     */
//    public static void deleteFromUsers(int id) {
//        executeSQL("DELETE FROM USERS WHERE ID=" + id + ";");
//    }
//
//    /**
//     * This method will update the name in the USERS table.
//     *
//     * @param id   The users id in the table.
//     * @param name The users name in the table.
//     */
//    public static void updateNameInUsers(int id, String name) {
//        executeSQL("UPDATE USERS SET NAME='" + name + "' WHERE ID=" + id + ";");
//    }
//
//
//    /**
//     * This method will display the users name and id.
//     *
//     * @param i How many column want you to display.
//     */
//    public static void selectFromUsers(int i) {
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
//
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
//
//            int j = 0;
//            while (resultSet.next() && j != i) {
//                String id = resultSet.getString("ID");
//                String name = resultSet.getString("NAME");
//                System.out.println("ID: " + id + " NAME: " + name);
//                i = i + 1;
//            }
//
//            resultSet.close();
//            connection.close();
//            statement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * This method will get back us a name from the USERS table.
//     *
//     * @param id The id of name. What you want to get back.
//     * @return If on the id no available data it will return by null. If there is data it will return by the name.
//     */
//    public static String getUsersName(int id) {
//        String name = null;
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
//
//            ResultSet resultSet = statement.executeQuery("SELECT NAME FROM USERS WHERE ID=" + id);
//            while (resultSet.next()) {
//                name = resultSet.getString("NAME");
//            }
//
//            resultSet.close();
//            statement.close();
//            connection.close();
//
//            return name;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return name;
//    }

}

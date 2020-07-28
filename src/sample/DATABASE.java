package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DATABASE {
    public static Connection connection = null;

//    private static String DBName = "hello.db";
    private static String dataBaseName = "vocab.db";
    private String connectionString = "jdbc:sqlite:C:\\Users\\Rana Marsad\\IdeaProjects\\sample\\"+dataBaseName;

    public DATABASE() throws SQLException {
        Connection connection = DriverManager.getConnection(connectionString);
        Statement statement = connection.createStatement();

//        statement.execute("drop table if exists users");
//        statement.execute("drop table if exists words");

        statement.execute("CREATE table if not exists users ( " +
                "firstName varchar(15) not NULL, " +
                "lastName varchar(15) not null, " +
                "userName varchar(15) not null, " +
                "passwrd varchar(15) not null )");


        statement.execute("CREATE table if not EXISTS words ( " +
                "word varchar(20) not NULL," +
                "meaning varchar(20) not null, " +
                "synonym varchar(20) not null, " +
                "antonym varchar(20) not NULL, " +
                "userName varchar(15) not null," +
                "passwrd varchar(15) not null, " +
                "localDateTime TEXT not NULL)");

    }

    public static String getDBName() {
        return dataBaseName;
    }

    public void setDBName(String DBName) {
        this.dataBaseName = DBName;
    }


    public static DATABASE getInstance() throws SQLException {
        return new DATABASE();
    }

    public Connection getConnection(){

        try{
            if(connection == null){
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(connectionString);
            }else{
                connection.close();
                connection = DriverManager.getConnection(connectionString);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }

}

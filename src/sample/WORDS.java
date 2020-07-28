package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class WORDS {

    private String word;
    private String meaning;
    private String synonym;
    private String antonym;

    public WORDS(String word, String meaning, String synonym, String antonym) {
        this.word = word;
        this.meaning = meaning;
        this.synonym = synonym;
        this.antonym = antonym;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getAntonym() {
        return antonym;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    @Override
    public String toString() {
        return word + "  " + meaning + "  " + synonym + "  " + antonym;
    }

    //    public static void createTable() throws SQLException{
//
//        Connection connection = DATABASE.getInstance().getConnection();
//
//        Statement statement = connection.createStatement();
//
//        statement.execute("DROP TABLE IF EXISTS hello");
//
//        statement.execute(" create table if not exists hello (word text , meaning text , synonym text , antonym text)");
//
//    }

//    public void insertWord() throws SQLException {
//        Connection connection = DATABASE.getInstance().getConnection();
//        Statement statement = connection.createStatement();
//        statement.execute("DROP TABLE IF EXITS myData.db");
//        statement.execute("CREATE TABLE IF NOT EXITS W(WORD TEXT)  ");
//        statement.execute("insert into W values('hello')");
//        connection.close();
//    }

}

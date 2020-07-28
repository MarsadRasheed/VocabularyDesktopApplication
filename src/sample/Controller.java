package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static sample.WelcomeScreen.getAuthenticName;
import static sample.WelcomeScreen.getAuthenticPassword;


public class Controller implements Initializable {

    DATABASE database = new DATABASE();
    Connection connection = database.getConnection();
    Statement statement = connection.createStatement();

    @FXML
    private TextField wordField;
    @FXML
    private TextField meaningField;
    @FXML
    private TextField synonymField;
    @FXML
    private TextField antonymField;
    @FXML
    private Button insertButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView<WORDS> dataTable;
    @FXML
    private TableColumn<WORDS, String> wordColumn;
    @FXML
    private TableColumn<WORDS, String> meaningColumn;
    @FXML
    private TableColumn<WORDS, String> synonymColumn;
    @FXML
    private TableColumn<WORDS, String> antonymColumn;
    @FXML
    private Button showButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label wordLabel;
    @FXML
    private Label meaningLabel;
    @FXML
    private Label synonymLabel;
    @FXML
    private Label antonymLabel;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button logOutButton;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button recordButton;
    @FXML
    private Label choiceDetailsLabel;

    String userName = getAuthenticName();
    String password = getAuthenticPassword();

    public Controller() throws SQLException {
    }

    @FXML
    void InsertButton(ActionEvent event) throws SQLException {

        String word = wordField.getText().toString();
        String meaning = meaningField.getText().toString();
        String synonym = synonymField.getText().toString();
        String antonym = antonymField.getText().toString();
        String localDateTime = getLocalDateTime();

        statement.execute("Insert into words values ('" + word + "', '" + meaning + "', '" + synonym + "', '" + antonym + "' , '" + userName + "' , '"
                + password + "' , ' " + localDateTime + "' )");

        wordField.setText("");
        meaningField.setText("");
        synonymField.setText("");
        antonymField.setText("");
    }

    @FXML
    void deleteButtton(ActionEvent event) throws SQLException {
        String word = wordField.getText().toString();
        String meaning = wordField.getText().toString();
        String synonym = synonymField.getText().toString();
        String antonym = antonymField.getText().toString();

        statement.execute("Delete from words where word = " + "'" + word + "'"
                + " or meaning = " + "'" + meaning + "'"
                + " or synonym = " + "'" + synonym + "'"
                + " or antonym = " + "'" + antonym + "'"
                + " and userName = " + "'" + userName + "'"
                + " and passwrd = " + "'" + password + "'"
        );

        wordField.setText("");
        meaningField.setText("");
        synonymField.setText("");
        antonymField.setText("");
    }

    @FXML
    void updateButton(ActionEvent event) throws SQLException {
        String word = wordField.getText().toString();
        String meaning = meaningField.getText().toString();
        String synonym = synonymField.getText().toString();
        String antonym = antonymField.getText().toString();
        statement.execute("update words set word = " + "'" + word + "'"
                + " , meaning = " + "'" + meaning + "'"
                + " , synonym = " + "'" + synonym + "'"
                + " , antonym = " + "'" + antonym + "'"
                + " where word = " + "'" + word + "'"
                + " or meaning = " + "'" + meaning + "'"
                + " or synonym = " + "'" + synonym + "'"
                + " or antonym = " + "'" + antonym + "'"
                + " and userName = " + "'" + userName + "'"
                + " and passwrd = " + "'" + password + "'"
        );

        wordField.setText("");
        meaningField.setText("");
        synonymField.setText("");
        antonymField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setItems(getWords());

        wordColumn.setCellValueFactory(new PropertyValueFactory<WORDS, String>("word"));
        meaningColumn.setCellValueFactory(new PropertyValueFactory<WORDS, String>("meaning"));
        synonymColumn.setCellValueFactory(new PropertyValueFactory<WORDS, String>("synonym"));
        antonymColumn.setCellValueFactory(new PropertyValueFactory<WORDS, String>("antonym"));

        String query = "Select * from words where userName = " + "'" + userName + "'" + " and passwrd = " + "'" + password + "'";
        dataTable.setItems(showData(query));

    }

    public ObservableList showData(String query) {

        Statement statement = null;

        ObservableList<WORDS> list = FXCollections.observableArrayList();
        WORDS words = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!resultSet.next()) {
                    break;
                } else {
                    words = new WORDS(resultSet.getString("word"), resultSet.getString("meaning"), resultSet.getString("synonym"), resultSet.getString("antonym"));
                    list.add(words);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list.sorted();
    }

    @FXML
    void onResetButtonPressed(ActionEvent event) {
        choiceBox.setItems(getWords());

        wordField.clear();
        meaningField.clear();
        synonymField.clear();
        antonymField.clear();
        searchField.clear();

    }

    @FXML
    void onShowButttonPressed(ActionEvent event) {
        String query = "Select * from words where userName = " + "'" + userName + "'" + " and passwrd = " + "'" + password + "'";
        setDataTable(query);
    }

    @FXML
    void onSearchButtonPressed(ActionEvent event) {
        String searchString = searchField.getText().toString();
        String query = "Select * from words where words.word LIKE " + "'%" + searchString + "%'"
                + " OR words.meaning LIKE " + "'%" + searchString + "%'"
                + " OR words.synonym LIKE " + "'%" + searchString + "%'"
                + " OR words.antonym LIKE " + "'%" + searchString + "%'"
                + " and words.userName = " + "'" + userName + "'"
                + " and words.passwrd = " + "'" + password + "'";
        setDataTable(query);
    }

    public void setDataTable(String query) {
        dataTable.setItems(showData(query));
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        WORDS word = dataTable.getSelectionModel().getSelectedItem();
        if (word != null) {
            wordField.setText(word.getWord().toString());
            meaningField.setText(word.getMeaning().toString());
            synonymField.setText(word.getSynonym().toString());
            antonymField.setText(word.getAntonym().toString());
        }
    }

    @FXML
    void onLogOutButtonPressed(ActionEvent event) throws IOException {

        Parent welcomeParent = FXMLLoader.load(getClass().getResource("welcomeScreen.fxml"));
        Scene welcomScene = new Scene(welcomeParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(welcomScene);
        window.show();

    }

    @FXML
    void onRecordButtonPressed(ActionEvent event) {
        String date = choiceBox.getSelectionModel().getSelectedItem().toString();
        String query = "Select * from words where localDateTime = '" + date + "' and userName = '" + getAuthenticName() + "' and passwrd = '" + getAuthenticPassword() + "'";
        dataTable.setItems(showData(query));
    }

    public String getLocalDateTime() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String localDateTime = dateFormat.format(date);
        return localDateTime;
    }

    public ObservableList<String> getWords() {

        ObservableList<String> list = FXCollections.observableArrayList();
        String date = "";
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery("Select words.localDateTime from words where userName = '" + getAuthenticName() + "' and passwrd = '" + getAuthenticPassword() + "'");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while (true) {
            try {
                if (!resultSet.next()) {
                    break;
                } else {
                    date = resultSet.getString("localDateTime");
                    if (!list.contains(date)) {
                        list.add(date);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }
}

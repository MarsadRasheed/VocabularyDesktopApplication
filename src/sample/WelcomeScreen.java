package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class WelcomeScreen implements Initializable {

    DATABASE database = new DATABASE();
    Connection connection = database.getConnection();
    Statement statement = connection.createStatement();

    @FXML
    private Text welcomeText;
    @FXML
    private Button logInButton;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label userNameLabel;
    @FXML
    private Hyperlink signUpLink;

    public static String name = "";
    public static String pass = "";

    public WelcomeScreen() throws SQLException {
    }

    @FXML
    void onSignUpPressed(ActionEvent event) throws IOException {

        Parent logInParent = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        Scene logInScene = new Scene(logInParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(logInScene);
        window.show();
    }

    @FXML
    void onLogInButtonPressed(ActionEvent event) throws IOException, SQLException {

        String userName = userNameField.getText().toString();
        String password = passwordField.getText().toString();

        if (isExists(userName, password)) {

            name = userName;
            pass = password;

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();

        } else {
            System.out.println("Error opening,UserName or password is incorrect");
            userNameField.clear();
            passwordField.clear();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public boolean isExists(String userName, String password) {
        Users users;
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("Select * from users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!resultSet.next()) {
                    break;
                } else {
                    users = new Users(resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("userName"), resultSet.getString("passwrd"));
                    if (isEqual(users, userName, password)) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isEqual(Users user, String name, String password) {
        if (user.getUserName().equals(name) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public static String getAuthenticName() {
        return name;
    }

    public static String getAuthenticPassword() {
        return pass;
    }

}



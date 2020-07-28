package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class LogIn implements Initializable {

    DATABASE database = new DATABASE();
    Connection connection = database.getConnection();
    Statement statement = connection.createStatement();

    @FXML
    private Button logInButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField rePasswordField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;

    public LogIn() throws SQLException {
    }

    @FXML
    void onSignUpButtonPressed(ActionEvent event) throws SQLException {

        String firstName = firstNameField.getText().toString();
        String lastName = lastNameField.getText().toString();
        String userName = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        String rePassword = rePasswordField.getText().toString();

        if (!isExists(firstName, lastName, userName)) {
            if (isEqual(password, rePassword)) {
                statement.execute("Insert into users values ('" + firstName + "', '" + lastName + "', '" + userName + "', '" + password + "' )");
            } else {
                System.out.println("Passwords doesn't match.");
            }
        } else {
            System.out.println("This user already exists. Kindly change user details.");
        }
    }

    @FXML
    void onLogInButtonPressed(ActionEvent event) throws IOException {

        Parent welcomeParent = FXMLLoader.load(getClass().getResource("welcomeScreen.fxml"));
        Scene welcomeScene = new Scene(welcomeParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(welcomeScene);
        window.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private boolean isExists(String fName, String lName, String uName) {
        Users user;
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
                    user = new Users(resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("userName"), resultSet.getString("passwrd"));
                    if (isTrue(user, fName, lName, uName)) {
                        statement.close();
                        connection.close();
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public boolean isTrue(Users user, String fName, String lName, String uName) {
        if (user.getFirstName().equals(fName) && user.getLastName().equals(lName) && user.getUserName().equals(uName)) {
            return true;
        }
        return false;
    }

    public boolean isEqual(String first, String second) {
        if (first.equals(second)) {
            return true;
        }
        return false;
    }

}

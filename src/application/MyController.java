package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MyController implements Initializable{

	public File selectedFile = null;
	
	@FXML
    private TextField usernameField;

    @FXML
    private AnchorPane mainPanel;
    
    @FXML
    private Button browseBtn;

    @FXML
    private Button continueBtn;

    @FXML
    private TextField profileLinkField;
    
    @FXML
    private ImageView usernameTick;

    @FXML
    private ImageView profileLinkTick;

    @FXML
    private ImageView htmlFIleTick;
    
    @FXML
    private ImageView usernameX;

    @FXML
    private ImageView htmlFileX;

    @FXML
    private ImageView profileLinkX;
    
    @FXML
    private TextField fileNameField;
    
    @FXML
    void browseBtnPressed(ActionEvent event) {
    	
    	FileChooser fc = new FileChooser();
    	this.selectedFile = fc.showOpenDialog(null);
    	
    	if(selectedFile != null) {
    		fileNameField.setStyle("-fx-border-color: #DEDEE4;");
    		browseBtn.setStyle("-fx-border-color: #DEDEE4;");
    		fileNameField.setText(selectedFile.getName());
    	}
    	
    }
    
    @FXML
    void continueBtnPressed(ActionEvent event) throws IOException {
    	
    	String username = usernameField.getText();
    	String profileLink = profileLinkField.getText();
    	
    	if(username.equals(""))
    		usernameField.setStyle("-fx-border-color: #CF6E00;");
    	
    	if(profileLink.equals(""))
    		profileLinkField.setStyle("-fx-border-color: #CF6E00;");
    	
    	if(selectedFile == null) {
    		fileNameField.setStyle("-fx-border-color: #CF6E00;");
    		browseBtn.setStyle("-fx-border-color: #CF6E00;");
    	}
    	
    	//Changing screen
    	if(!(username.equals("") || profileLink.equals("") || selectedFile == null)) {
    		LoadData data = new LoadData(profileLink, username);
    		FileManager.writeDataFile(data.getData());
    		FileManager.writeUserData(new User(username, profileLink, selectedFile));
	    	Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
	    	Scene newScene = new Scene(parent);
	    	Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	app_stage.hide();
	    	app_stage.setScene(newScene);
	    	app_stage.show();
    	}
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		fileNameField.setEditable(false);
		
		usernameField.textProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			if(usernameField.getText().length() > 3) {
    				usernameField.setStyle("-fx-border-color: #DEDEE4;");
    			}
    		}
    	});
    	
    	profileLinkField.textProperty().addListener(new ChangeListener<String>() {
    		@Override
    		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			if(profileLinkField.getText().length() > 3) {
    				profileLinkField.setStyle("-fx-border-color: #DEDEE4;");
    			}
    		}
    	});
		
	}
}

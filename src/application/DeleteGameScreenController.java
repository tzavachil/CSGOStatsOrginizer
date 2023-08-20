package application;

import java.awt.Toolkit;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class DeleteGameScreenController implements Initializable {

    @FXML
    private TableView<Match> gamesTable;

    @FXML
    private Button deleteGameBtn;

    @FXML
    private Button confirmBtn;
    
    @FXML
    private Label selectedMatchLabel;
    
    private ArrayList<Match> myList;
    private static ObservableList<Match> itemList = FXCollections.observableArrayList();
    
    private static ObservableList<Match> returnableList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	
		myList = MainScreenController.getMyList();
    	
    	itemList.removeAll(itemList);
    	returnableList.removeAll(returnableList);
    	
		for(Match match : myList) {
			itemList.add(match);
			returnableList.add(match);
		}
		
		if(gamesTable.getSelectionModel().getSelectedItems().isEmpty())
			deleteGameBtn.setDisable(true);
		
		initTable();
		
	}
	
	//Games' Table Properties
	private void initTable() {
		
		gamesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		gamesTable.setEditable(false);
		gamesTable.setItems(itemList);
		TableColumn<Match, String> resultCol = new TableColumn<>("Result");
		resultCol.setMinWidth(95);
		resultCol.setCellValueFactory(new PropertyValueFactory<Match, String>("matchResultText"));
		TableColumn<Match, String> mapCol = new TableColumn<>("Map");
		mapCol.setMinWidth(135);
		mapCol.setCellValueFactory(new PropertyValueFactory<Match, String>("map"));
		TableColumn<Match, Date> dateCol = new TableColumn<>("Date");
		dateCol.setMinWidth(155);
		dateCol.setCellFactory(column -> {
		    TableCell<Match, Date> cell = new TableCell<Match, Date>() {
		        private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		        @Override
		        protected void updateItem(Date item, boolean empty) {
		            super.updateItem(item, empty);
		            if(empty) {
		                setText(null);
		            }
		            else {
		                setText(format.format(item));
		            }
		        }
		    };

		    return cell;
		});
		dateCol.setCellValueFactory(new PropertyValueFactory<Match, Date>("date"));
		TableColumn<Match, Integer> killsCol = new TableColumn<>("Kills");
		killsCol.setMinWidth(80);
		killsCol.setCellValueFactory(new PropertyValueFactory<Match, Integer>("kills"));
		TableColumn<Match, Integer> assistsCol = new TableColumn<>("Assists");
		assistsCol.setMinWidth(105);
		assistsCol.setCellValueFactory(new PropertyValueFactory<Match, Integer>("assists"));
		TableColumn<Match, Integer> deathsCol = new TableColumn<>("Deaths");
		deathsCol.setMinWidth(105);
		deathsCol.setCellValueFactory(new PropertyValueFactory<Match, Integer>("deaths"));
		TableColumn<Match, Integer> mvpsCol = new TableColumn<>("Mvps");
		mvpsCol.setMinWidth(90);
		mvpsCol.setCellValueFactory(new PropertyValueFactory<Match, Integer>("mvps"));
		TableColumn<Match, Integer> headCol = new TableColumn<>("Headshot %");
		headCol.setMinWidth(155);
		headCol.setCellValueFactory(new PropertyValueFactory<Match, Integer>("head"));
		TableColumn<Match, Integer> scoreCol = new TableColumn<>("Score");
		scoreCol.setMinWidth(95);
		scoreCol.setCellValueFactory(new PropertyValueFactory<Match, Integer>("score"));
		
		gamesTable.getColumns().addAll(resultCol, mapCol, dateCol, killsCol, assistsCol, 
				deathsCol, mvpsCol, headCol, scoreCol);
		dateCol.setSortType(TableColumn.SortType.DESCENDING);
		gamesTable.getSortOrder().add(dateCol);
		
		gamesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if(newSelection == null)
		    	deleteGameBtn.setDisable(true);
		    else
				deleteGameBtn.setDisable(false);
		});
		gamesTable.setOnMouseClicked((MouseEvent event) -> {
	        if(event.getButton().equals(MouseButton.PRIMARY)){
	            selectedMatchLabel.setText(gamesTable.getSelectionModel().getSelectedItems().size() + " matches is selected.");
	        }
	    });
	}
	
    @FXML
    void deleteBtnOnAction(ActionEvent event) {
    	ObservableList<Match> tempList = gamesTable.getSelectionModel().getSelectedItems();
    	itemList.removeAll(tempList);
    	selectedMatchLabel.setText(gamesTable.getSelectionModel().getSelectedItems().size() + " matches is selected.");
    }
    
    @FXML
    void confirmChanges(ActionEvent event) {
    	
    	Toolkit.getDefaultToolkit().beep();
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save the changes?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    	alert.showAndWait();
    	
    	Stage stage = (Stage) confirmBtn.getScene().getWindow();
    	
    	if (alert.getResult() == ButtonType.YES) {
    	    returnableList.removeAll(returnableList);
    		for(Match match : itemList)
    	    	returnableList.add(match);
    	    stage.close();
    	}
    	else if (alert.getResult() == ButtonType.NO) {
    		stage.close();
    	}
    }
    
    public static ObservableList<Match> returnList() {
    	return returnableList;
    }
	
}

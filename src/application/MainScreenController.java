package application;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainScreenController implements Initializable{
	
    @FXML
    private AreaChart<Category, Number> winsChart;
    
    @FXML
    private AreaChart<Category, Number> winsChartMonth;
    
    @FXML
    private AreaChart<Category, Number> winsChartYear;
    
    @FXML
    private TableView<Match> totalGamesTable;

    @FXML
    private TableView<Match> monthGamesTable;
    
    @FXML
    private TableView<Match> yearGamesTable;
    
    @FXML
    private CategoryAxis xAxisMonth;
    
    @FXML
    private NumberAxis yAxisMonth;
    
    @FXML
    private JFXComboBox<String> resultComboBox;
    
    @FXML
    private JFXToggleButton newMapToggleBtn;
    
    @FXML
    private TextField newMapTextField;
    
    @FXML
    private TextField deathsTextField;
    
    @FXML
    private TextField assistsTextField;
    
    @FXML
    private TextField headTextField;
    
    @FXML
    private TextField mvpsTextField;
    
    @FXML
    private TextField killsTextField;
    
    @FXML
    private TextField scoreTextField;
    
    @FXML
    private TextField pingTextField;
    
    @FXML
    private JFXComboBox<String> mapComboBox;
    
    @FXML
    private Button addGameBtn;
    
    @FXML
    private Button deleteGameBtn;
    
    private XYChart.Series series = new XYChart.Series();
    private int totalMatchCounter;
    private int totalResult;
    
    private XYChart.Series seriesMonth = new XYChart.Series();
	private int monthMatchCounter;
	private int monthResult;
	
	private XYChart.Series seriesYears = new XYChart.Series();
	private int yearMatchCounter;
	private int yearResult;
	
	private ObservableList<String> maps = FXCollections.observableArrayList();
	
	private ObservableList<Match> totalMatches = FXCollections.observableArrayList();
	private ObservableList<Match> monthMatches = FXCollections.observableArrayList();
	private ObservableList<Match> yearMatches = FXCollections.observableArrayList();
	
	private static ArrayList<Match> myList = FileManager.readDataFile();
	
	private Match lastMatch;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//text field listener for form validation
		textFieldColorize();
		
		//Total Games Chart
		initTotalGamesChart(myList);
		
		//Month's Games Chart	
		initMonthGamesChart(myList);
		
		//Year's Games Chart
		initYearGamesChart(myList);
		
		//Total Games Table properties
		totalGamesTable.setEditable(false);
		totalGamesTable.setItems(totalMatches);
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
		
		totalGamesTable.getColumns().addAll(resultCol, mapCol, dateCol, killsCol, assistsCol, 
				deathsCol, mvpsCol, headCol, scoreCol);
		dateCol.setSortType(TableColumn.SortType.DESCENDING);
		totalGamesTable.getSortOrder().add(dateCol);
		
		//Month Game Table properties
		monthGamesTable.setEditable(false);
		monthGamesTable.setItems(monthMatches);
		TableColumn<Match, String> resultCol2 = new TableColumn<>("Result");
		resultCol2.setMinWidth(95);
		resultCol2.setCellValueFactory(new PropertyValueFactory<Match, String>("matchResultText"));
		TableColumn<Match, String> mapCol2 = new TableColumn<>("Map");
		mapCol2.setMinWidth(135);
		mapCol2.setCellValueFactory(new PropertyValueFactory<Match, String>("map"));
		TableColumn<Match, Date> dateCol2 = new TableColumn<>("Date");
		dateCol2.setMinWidth(155);
		dateCol2.setCellFactory(column -> {
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
		dateCol2.setCellValueFactory(new PropertyValueFactory<Match, Date>("date"));
		TableColumn<Match, Integer> killsCol2 = new TableColumn<>("Kills");
		killsCol2.setMinWidth(80);
		killsCol2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("kills"));
		TableColumn<Match, Integer> assistsCol2 = new TableColumn<>("Assists");
		assistsCol2.setMinWidth(105);
		assistsCol2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("assists"));
		TableColumn<Match, Integer> deathsCol2 = new TableColumn<>("Deaths");
		deathsCol2.setMinWidth(105);
		deathsCol2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("deaths"));
		TableColumn<Match, Integer> mvpsCol2 = new TableColumn<>("Mvps");
		mvpsCol2.setMinWidth(90);
		mvpsCol2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("mvps"));
		TableColumn<Match, Integer> headCol2 = new TableColumn<>("Headshot %");
		headCol2.setMinWidth(155);
		headCol2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("head"));
		TableColumn<Match, Integer> scoreCol2 = new TableColumn<>("Score");
		scoreCol2.setMinWidth(95);
		scoreCol2.setCellValueFactory(new PropertyValueFactory<Match, Integer>("score"));
		
		monthGamesTable.getColumns().addAll(resultCol2, mapCol2, dateCol2, killsCol2, assistsCol2, 
				deathsCol2, mvpsCol2, headCol2, scoreCol2);
		dateCol2.setSortType(TableColumn.SortType.DESCENDING);
		monthGamesTable.getSortOrder().add(dateCol2);
		
		//Year Games Table properties
		yearGamesTable.setEditable(false);
		yearGamesTable.setItems(yearMatches);
		TableColumn<Match, String> resultCol3 = new TableColumn<>("Result");
		resultCol3.setMinWidth(95);
		resultCol3.setCellValueFactory(new PropertyValueFactory<Match, String>("matchResultText"));
		TableColumn<Match, String> mapCol3 = new TableColumn<>("Map");
		mapCol3.setMinWidth(135);
		mapCol3.setCellValueFactory(new PropertyValueFactory<Match, String>("map"));
		TableColumn<Match, Date> dateCol3 = new TableColumn<>("Date");
		dateCol3.setMinWidth(155);
		dateCol3.setCellFactory(column -> {
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
		dateCol3.setCellValueFactory(new PropertyValueFactory<Match, Date>("date"));
		TableColumn<Match, Integer> killsCol3 = new TableColumn<>("Kills");
		killsCol3.setMinWidth(80);
		killsCol3.setCellValueFactory(new PropertyValueFactory<Match, Integer>("kills"));
		TableColumn<Match, Integer> assistsCol3 = new TableColumn<>("Assists");
		assistsCol3.setMinWidth(105);
		assistsCol3.setCellValueFactory(new PropertyValueFactory<Match, Integer>("assists"));
		TableColumn<Match, Integer> deathsCol3 = new TableColumn<>("Deaths");
		deathsCol3.setMinWidth(105);
		deathsCol3.setCellValueFactory(new PropertyValueFactory<Match, Integer>("deaths"));
		TableColumn<Match, Integer> mvpsCol3 = new TableColumn<>("Mvps");
		mvpsCol3.setMinWidth(90);
		mvpsCol3.setCellValueFactory(new PropertyValueFactory<Match, Integer>("mvps"));
		TableColumn<Match, Integer> headCol3 = new TableColumn<>("Headshot %");
		headCol3.setMinWidth(155);
		headCol3.setCellValueFactory(new PropertyValueFactory<Match, Integer>("head"));
		TableColumn<Match, Integer> scoreCol3 = new TableColumn<>("Score");
		scoreCol3.setMinWidth(95);
		scoreCol3.setCellValueFactory(new PropertyValueFactory<Match, Integer>("score"));
		
		yearGamesTable.getColumns().addAll(resultCol3, mapCol3, dateCol3, killsCol3, assistsCol3, 
				deathsCol3, mvpsCol3, headCol3, scoreCol3);
		dateCol3.setSortType(TableColumn.SortType.DESCENDING);
		yearGamesTable.getSortOrder().add(dateCol3);
		
		//Result Combo Box properties
		ObservableList<String> results = FXCollections.observableArrayList(
				"Victory",
				"Defeat",
				"Tie");
		resultComboBox.setItems(results);
		resultComboBox.getSelectionModel().selectFirst();
		
		
		//Map Combo Box properties
		Collections.sort(maps);
		mapComboBox.setItems(maps);
		mapComboBox.getSelectionModel().selectFirst();
		
		//New Map Toggle Button properties
		newMapToggleBtn.selectedProperty().addListener(((observable, oldValue, newValue) -> {
			if(newValue) {
				mapComboBox.setDisable(true);
				newMapTextField.setDisable(false);
			}
			else {
				mapComboBox.setDisable(false);
				newMapTextField.setDisable(true);
			}
		}));
		
		winsChart.getData().addAll(series);
		winsChart.getXAxis().setTickLabelsVisible(false);
		winsChart.getXAxis().setTickMarkVisible(false);
		
		winsChartMonth.getData().addAll(seriesMonth);
		winsChartMonth.setStyle("-fx-create-symbols: true;");
		winsChartMonth.getXAxis().setTickLabelsVisible(false);
		winsChartMonth.getXAxis().setTickMarkVisible(false);
		
		winsChartYear.getData().addAll(seriesYears);
		winsChartYear.getXAxis().setTickLabelsVisible(false);
		winsChartYear.getXAxis().setTickMarkVisible(false);		
	}
	
	private void initTotalGamesChart(ArrayList<Match> list) {
		
		totalMatchCounter = 1;
		totalResult = 0;
		for(int i = list.size() - 1; i>=0; i--) {
			if(list.get(i).getMatchResult() == 2)
				totalResult -= 1;
			else
				totalResult += list.get(i).getMatchResult();
			series.getData().add(new XYChart.Data(list.get(i).getDateString(),totalResult));
			if(!maps.contains(list.get(i).getMap()))
				maps.add(list.get(i).getMap());
			totalMatches.add(list.get(i));
			list.get(i).resultInText();
			totalMatchCounter++;
		}
		lastMatch = myList.get(0);
	}
	
	private void initMonthGamesChart(ArrayList<Match> list) {
		
		monthMatchCounter = 0;
		monthResult = 0;
		int i = 0;
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		while(list.get(i).getDate().getMonth() == currentMonth) {
			monthMatchCounter++;
			i++;			
		}
		if(monthMatchCounter != 0) {
			for(int j = monthMatchCounter - 1; j>=0; j--) {
				if(list.get(j).getMatchResult() == 2)
					monthResult -= 1;
				else
					monthResult += list.get(j).getMatchResult();
				seriesMonth.getData().add(new XYChart.Data<>(list.get(j).getDateString(), monthResult));
				monthMatches.add(list.get(j));
			}
		}
	}
	
	private void initYearGamesChart(ArrayList<Match> list) {
				
		yearMatchCounter = 1;
		yearResult = 0;
		int i = 0;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		while(list.get(i).getDate().getYear() + 1900 == currentYear) {
			yearMatchCounter++;
			i++;
		}
		if(yearMatchCounter != 0) {
			for(int j = yearMatchCounter - 1; j>=0; j--) {
				if(list.get(j).getMatchResult() == 2)
					yearResult -= 1;
				else
					yearResult += list.get(j).getMatchResult();
				seriesYears.getData().add(new XYChart.Data<>(list.get(j).getDateString(), yearResult));
				yearMatches.add(list.get(j));				
			}
		}
	}
	
	//addGameBtn on Action
    @FXML
    void addingGame(ActionEvent event) {
    	
    	//collecting form's data
    	String matchResult = resultComboBox.getValue();
    	String map;
    	if(newMapToggleBtn.isSelected()) {
    		map = newMapTextField.getText();
    	}
    	else {
    		map = mapComboBox.getValue();
    	}
    	try {
	    	int ping = Integer.parseInt(pingTextField.getText());
	    	int kills = Integer.parseInt(killsTextField.getText());
	    	int assists = Integer.parseInt(assistsTextField.getText());
	    	int deaths = Integer.parseInt(deathsTextField.getText());
	    	int mvps = Integer.parseInt(mvpsTextField.getText());
	    	int head = Integer.parseInt(headTextField.getText());
	    	int score = Integer.parseInt(scoreTextField.getText());
	    	Date date = Calendar.getInstance().getTime();
	    	
	    	if(Pattern.matches("^[a-zA-Z]*$",newMapTextField.getText()) && 
	    			Integer.parseInt(headTextField.getText()) >= 0 && 
	    			Integer.parseInt(headTextField.getText()) <= 100) {
		    	//creating Match object with form's data
		    	Match newMatch = new Match(matchResult, map, date, ping, kills, assists, deaths, mvps, head, score);
		    	newMatch.resultInText();
		
		    	//adding new Match on charts
		    	if(seriesMonth.getData().isEmpty() || newMatch.getDate().getMonth() != lastMatch.getDate().getMonth()) {
		    		/*	refreshing Month's Games Chart and Table when
		    		 * 	games with different month is added
		    		 * */ 
		    		winsChartMonth.setVisible(false);
		    		seriesMonth.getData().removeAll(seriesMonth.getData());
		    		winsChartMonth.setVisible(true);
		    		monthGamesTable.setVisible(false);
		    		monthGamesTable.getItems().removeAll(monthGamesTable.getItems());
		    		monthGamesTable.setVisible(true);
		    		monthResult = 0;
		    	}
		    	if(seriesYears.getData().isEmpty() || newMatch.getDate().getYear() != lastMatch.getDate().getYear()) {
		    		/*	refreshing Year's Games Chart and Table when
		    		 * 	games with different year is added
		    		 * */ 
		    		winsChartYear.setVisible(false);
		    		seriesYears.getData().removeAll(seriesYears.getData());
		    		winsChartYear.setVisible(true);
		    		yearGamesTable.setVisible(false);
		    		yearGamesTable.getItems().removeAll(yearGamesTable.getItems());
		    		yearGamesTable.setVisible(true);
		    		yearResult = 0;
		    	}
		    	if(newMatch.getMatchResult() == 2) {
					totalResult -= 1;
					monthResult -= 1;
					yearResult -= 1;
		    	}
				else {
					totalResult += newMatch.getMatchResult();
					monthResult += newMatch.getMatchResult();
					yearResult += newMatch.getMatchResult();
				}
		    	lastMatch = newMatch;
		    	myList.add(0, newMatch); //save new games on .dat file and on close
				series.getData().add(new XYChart.Data(newMatch.getDateString(),totalResult));
				seriesMonth.getData().add(new XYChart.Data<>(newMatch.getDateString(), monthResult));
				seriesYears.getData().add(new XYChart.Data<>(newMatch.getDateString(), yearResult));
				
				//adding new Match on table views
				totalGamesTable.getItems().add(newMatch);
				totalGamesTable.sort();
				monthGamesTable.getItems().add(newMatch);
				monthGamesTable.sort();
				yearGamesTable.getItems().add(newMatch);
				yearGamesTable.sort();
		    	
				clearFormFields(); 
	    	}
	    	else {
	    		wrongFormValidation();
	    	}
    	} catch (NumberFormatException e) {
    		wrongFormValidation();
    	}
    }
    
    private void wrongFormValidation() {
    	System.out.println("Wrong from validation");
    	if(newMapToggleBtn.isSelected()) {
    		if(newMapTextField.getText().equals(""))
    			newMapTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
    									"	-fx-text-fill:  #6C0000;" + 
    									"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(killsTextField.getText().equals("")) {
    		killsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
									"	-fx-text-fill:  #6C0000;" + 
									"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(assistsTextField.getText().equals("")) {
    		assistsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
					"	-fx-text-fill:  #6C0000;" + 
					"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(deathsTextField.getText().equals("")) {
    		deathsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
					"	-fx-text-fill:  #6C0000;" + 
					"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(deathsTextField.getText().equals("")) {
    		deathsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
					"	-fx-text-fill:  #6C0000;" + 
					"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(mvpsTextField.getText().equals("")) {
    		mvpsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
					"	-fx-text-fill:  #6C0000;" + 
					"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(headTextField.getText().equals("")) {
    		headTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
					"	-fx-text-fill:  #6C0000;" + 
					"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(scoreTextField.getText().equals("")) {
    		scoreTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
					"	-fx-text-fill:  #6C0000;" + 
					"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    	if(pingTextField.getText().equals("")) {
    		pingTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
					"	-fx-text-fill:  #6C0000;" + 
					"	-fx-border-width: 0px 0px 2px 0px;");
    	}
    }
    
    private void textFieldColorize() {
    	
    	newMapTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	if(newMapToggleBtn.isSelected()) {
	    		if(Pattern.matches("^[a-zA-Z]*$",newMapTextField.getText())) {
			   		newMapTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
											"-fx-text-fill:  #3B3B3B;" + 
											"-fx-border-width: 0px 0px 1px 0px;");
		    	}
	    		else {
	    			newMapTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
											"	-fx-text-fill:  #6C0000;" + 
											"	-fx-border-width: 0px 0px 2px 0px;");
	    		}
	    	}
    		
    	});
    	killsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	try {
	    		Integer.parseInt(killsTextField.getText());
	    		killsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
										"-fx-text-fill:  #3B3B3B;" + 
										"-fx-border-width: 0px 0px 1px 0px;");
	    	} catch(NumberFormatException e) {
	    		killsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
										"	-fx-text-fill:  #6C0000;" + 
										"	-fx-border-width: 0px 0px 2px 0px;");
	    	}
    	});
    	assistsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	try {
	    		Integer.parseInt(assistsTextField.getText());
	    		assistsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
										"-fx-text-fill:  #3B3B3B;" + 
										"-fx-border-width: 0px 0px 1px 0px;");
	    	} catch(NumberFormatException e) {
	    		assistsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
										"	-fx-text-fill:  #6C0000;" + 
										"	-fx-border-width: 0px 0px 2px 0px;");
	    	}
    	});
    	deathsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	try {
	    		Integer.parseInt(deathsTextField.getText());
	    		deathsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
										"-fx-text-fill:  #3B3B3B;" + 
										"-fx-border-width: 0px 0px 1px 0px;");
	    	} catch(NumberFormatException e) {
	    		deathsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
										"	-fx-text-fill:  #6C0000;" + 
										"	-fx-border-width: 0px 0px 2px 0px;");
	    	}
    	});
    	mvpsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	try {
	    		Integer.parseInt(mvpsTextField.getText());
	    		mvpsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
										"-fx-text-fill:  #3B3B3B;" + 
										"-fx-border-width: 0px 0px 1px 0px;");
	    	} catch(NumberFormatException e) {
	    		mvpsTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
										"	-fx-text-fill:  #6C0000;" + 
										"	-fx-border-width: 0px 0px 2px 0px;");
	    	}
    	});
    	headTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	try {
	    		if(Integer.parseInt(headTextField.getText()) >= 0 && Integer.parseInt(headTextField.getText()) <= 100) {
		    		headTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
											"-fx-text-fill:  #3B3B3B;" + 
											"-fx-border-width: 0px 0px 1px 0px;");
	    		}
	    		else {
	    			headTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
							"	-fx-text-fill:  #6C0000;" + 
							"	-fx-border-width: 0px 0px 2px 0px;");
	    		}
	    	} catch(NumberFormatException e) {
	    		headTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
										"	-fx-text-fill:  #6C0000;" + 
										"	-fx-border-width: 0px 0px 2px 0px;");
	    	}
    	});
    	scoreTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	try {
	    		Integer.parseInt(scoreTextField.getText());
	    		scoreTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
										"-fx-text-fill:  #3B3B3B;" + 
										"-fx-border-width: 0px 0px 1px 0px;");
	    	} catch(NumberFormatException e) {
	    		scoreTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
										"	-fx-text-fill:  #6C0000;" + 
										"	-fx-border-width: 0px 0px 2px 0px;");
	    	}
    	});
    	pingTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    	try {
	    		Integer.parseInt(pingTextField.getText());
	    		pingTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
										"-fx-text-fill:  #3B3B3B;" + 
										"-fx-border-width: 0px 0px 1px 0px;");
	    	} catch(NumberFormatException e) {
	    		pingTextField.setStyle("	-fx-prompt-text-fill: #6C0000;" + 
										"	-fx-text-fill:  #6C0000;" + 
										"	-fx-border-width: 0px 0px 2px 0px;");
	    	}
    	});
    	
    }
    
    @FXML
    void deletingGame(ActionEvent event) throws IOException {
    	
    	Stage deleteWindow = new Stage();
    	deleteWindow.initModality(Modality.APPLICATION_MODAL);
    	//deleteWindow.initStyle(StageStyle.UNDECORATED);
    	
    	Parent parent = FXMLLoader.load(getClass().getResource("DeleteGameScreen.fxml"));
    	Scene newScene = new Scene(parent);
    	deleteWindow.setScene(newScene);
    	deleteWindow.showAndWait();
    	
    	ObservableList<Match> tempList = DeleteGameScreenController.returnList();
    	
    	if(myList.size() != tempList.size()) {
    		
    		//refreshing my list after deleting a game
    		myList.removeAll(myList);
    		for(Match match : tempList)
    			myList.add(match);
    		
    		//Total's Games Chart Refresh
    		winsChart.setVisible(false);
    		series.getData().removeAll(series.getData());
    		initTotalGamesChart(myList);
    		winsChart.setVisible(true);
    		
    		//Month's Games Chart Refresh
    		winsChartMonth.setVisible(false);
    		seriesMonth.getData().removeAll(seriesMonth.getData());
    		initMonthGamesChart(myList);
    		winsChartMonth.setVisible(true);
    		
    		//Year's Games Chart Refresh
    		winsChartYear.setVisible(false);
    		seriesYears.getData().removeAll(seriesYears.getData());
    		initYearGamesChart(myList);
    		winsChartYear.setVisible(true);
    		
    		
    		//Total's Games Table Refresh
    		totalMatches.removeAll(totalMatches);
    		for(Match match : tempList) 
    			totalMatches.add(match);
    		totalGamesTable.setItems(totalMatches);
    		totalGamesTable.refresh();
    		
    		//Month's Games Table Refresh
    		monthMatches.removeAll(monthMatches);
    		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    		int i = 0;
    		while(tempList.get(i).getDate().getMonth() == currentMonth) {
    			monthMatches.add(tempList.get(i));
    			i++;
    		}
    		monthGamesTable.setItems(monthMatches);
    		monthGamesTable.refresh();
    		
    		//Year's Games Table Refresh
    		yearMatches.removeAll(yearMatches);
    		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    		i = 0;
    		while(tempList.get(i).getDate().getYear() + 1900 == currentYear) {
    			yearMatches.add(tempList.get(i));
    			i++;
    		}
    		yearGamesTable.setItems(yearMatches);
    		yearGamesTable.refresh();
    		
    	}
    }
    
    private void clearFormFields() {
    	resultComboBox.getSelectionModel().selectFirst();
    	if(newMapToggleBtn.isSelected())
    		newMapTextField.setText("");
    	else
    		mapComboBox.getSelectionModel().selectFirst();
    	newMapToggleBtn.setSelected(false);
    	killsTextField.setText("");
    	assistsTextField.setText("");
    	deathsTextField.setText("");
    	mvpsTextField.setText("");
    	headTextField.setText("");
    	scoreTextField.setText("");	
    	pingTextField.setText("");
    	

   		newMapTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		killsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		assistsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		deathsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		mvpsTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		headTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		scoreTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		pingTextField.setStyle("-fx-prompt-text-fill: #ffffff;" + 
				"-fx-text-fill:  #3B3B3B;" + 
				"-fx-border-width: 0px 0px 1px 0px;");
   		
    }
    
    public static ArrayList<Match> getMyList(){
    	
    	return myList;
    }
    
    public static void shutdown() {
    	
    	FileManager.writeDataFile(myList);
        
    	Platform.exit();
        System.exit(0);
    }
}

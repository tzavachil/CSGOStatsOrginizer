package application;
	
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	
	private int games = 0;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		/*try {
			HBox root = new HBox();
			Scene scene = new Scene(root,1200,600);
			
			//Games Chart
			CategoryAxis xAxis = new CategoryAxis();
			xAxis.setLabel("Time");
			
			NumberAxis yAxis = new NumberAxis();
			yAxis.setLabel("Wins/Loses");
			
			//LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
			AreaChart<String, Number> lineChart = new AreaChart<String, Number>(xAxis, yAxis);
			lineChart.setTitle("Stats");
			
			XYChart.Series<String, Number> data = new XYChart.Series<String, Number>();		
			data.setName("Game Wins");
			LoadData matches = new LoadData("https://steamcommunity.com/id/tzavachil", "Tzavachil");
			for(int i = matches.getData().size()-1; i>=0; i--) {
				Match match = matches.getData().get(i);
				if(match.getMatchResult() == 1)
					games++;
				else if(match.getMatchResult() == 2)
					games--;
				data.getData().add(new XYChart.Data<String, Number>(match.getDate().toString(), games));
			}
			
			lineChart.setCreateSymbols(false);
			lineChart.setCursor(Cursor.CROSSHAIR);
			lineChart.getData().add(data);
			
			root.getChildren().addAll(lineChart);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		
		Parent parent;
		if(new File("data.dat").isFile())
			parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
		else
			parent = FXMLLoader.load(getClass().getResource("FirstTimeSetupWindow.fxml"));
		
		
		primaryStage.setScene(new Scene(parent));
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        MainScreenController.shutdown();
		    }
		});
	}
	
	public static void main(String[] args) {
		
		/*LoadData test = new LoadData("https://steamcommunity.com/id/tzavachil", "Tzavachil");
		
		FileManager.wirteDataFile(test.getData());
		
		//ArrayList<Match> test = FileManager.readDataFile();
		
		//System.out.println("Result \tMap \t\tDate \t\t\t\tPing \tKills \tAssists Deaths \tMvps \tHead \tScore");
		int count = 0;
		for(Match match : test.getData()) {
			//System.out.println(match);
			count ++;
		}
		System.out.println("Matches = " + count);
		*/
		
		launch(args);
		
	}
	
}

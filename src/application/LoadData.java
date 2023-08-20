package application;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LoadData {

	private ArrayList<Match> data;
	
	public LoadData(String profileLink, String username) {
	
		data = new ArrayList<Match>();
		
		File input = new File("C:\\Users\\tzava\\Desktop\\Κοινότητα Steam __ Counter-Strike_ Global Offensive __ Προσωπικά δεδομένα παιχνιδιού.html");
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			
			//Map Select
			Elements maps = doc.select(".csgo_scoreboard_inner_left td:contains(Competitive)");
						
			//Date Select
			Elements dates = doc.select(".csgo_scoreboard_inner_left td:contains(GMT)");
			
			//Find Match Results
			Elements results = doc.select(".csgo_scoreboard_inner_right");
			
			Elements matchScores = results.select(".csgo_scoreboard_score");
			
			System.out.println("Sizes: ");
			System.out.println("maps = " + maps.size());
			System.out.println("dates = " + dates.size());
			System.out.println("results = " + results.size());
			System.out.println("matchScores = " + matchScores.size());
			
			
			//Data Select
			Elements games = doc.select(".banchecker-profile:has(.linkTitle[href = " + profileLink + "]) td:not(.inner_name)");
			
			//Profile Link Error
			if(maps.size() != games.size()/7) {
				System.out.println("error");
				System.exit(0);
			}
			
			int dataSize = maps.size();
			
			int matchScore;
			boolean myTeam;
			String map;  
			Date date;   
			int ping;    
			int kills;  
			int assists; 
			int deaths;  
			int mvps;    
			int head;    
			int score;   
			
			int j = 0;
			for(int i=0; i<dataSize; i++) {
				map = translateMap(maps.get(i).text()); 
				date = translateDate(dates.get(i).text());
				
				ping = Integer.parseInt(games.get(i + j).text());   
				kills = Integer.parseInt(games.get(i + j + 1).text());   
				assists = Integer.parseInt(games.get(i + j + 2).text()); 
				deaths = Integer.parseInt(games.get(i + j + 3).text());  
				mvps = translateMvp(games.get(i + j + 4).text());    
				head = translateHead(games.get(i + j + 5).text());    
				score = Integer.parseInt(games.get(i + j + 6).text());
				
				myTeam = results.get(i).text().matches(".*?\\b" + username + "\\b.*?\\b" + matchScores.get(i).text()+ "\\b.*");
				matchScore = findMatchScore(matchScores.get(i).text(), myTeam);
				
				data.add(new Match(matchScore, map, date, ping, kills, assists, deaths, mvps, head, score));
				j+=6;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//team = true --> username on team1
	//return 0: tie, 1: victory, 2: defeat
	private int findMatchScore(String matchScore, boolean team) {
		int score;
		
		String[] scoreTable = matchScore.replaceAll(" " , "").split(":");
		int scoreTeam1 = Integer.parseInt(scoreTable[0]);
		int scoreTeam2 = Integer.parseInt(scoreTable[1]);
		if(scoreTeam1 == scoreTeam2)
			score = 0;
		else if(scoreTeam1 > scoreTeam2) {
			if(team)
				score = 1;
			else
				score = 2;
		}
		else {
			if(team)
				score = 2;
			else
				score = 1;
		}
		
		return score;
	}
	
	private String translateMap(String text) {
		return text.replace("Competitive ", "");
	}
	
	private Date translateDate(String text) {
				
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
		
		try {
			return formatter.parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private int translateMvp(String text) {
		
		if(text.equals("&nbsp;"))
			return 0;			
		else {
			String newText = text.replaceAll("\\D+","");
			if(newText.equals(""))
				return 1;
			else
				return Integer.parseInt(text.replaceAll("\\D+",""));
		}
			
	}
	
	private int translateHead(String text) {
		
		if(text.equals(""))
			return 0;
		else
			return Integer.parseInt(text.replace("%", ""));
	}
	
	public ArrayList<Match> getData(){
		return this.data;
	}
}

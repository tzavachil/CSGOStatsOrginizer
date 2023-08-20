package application;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int matchResult; //0: tie, 1: victory, 2: defeat
	private String matchResultText;
	private String map;
	private Date date;
	private int ping;
	private int kills;
	private int assists;
	private int deaths;
	private int mvps;
	private int head;
	private int score;
	
	public Match(int matchResult, String map, Date date, int ping, int kills, int assists, int deaths, int mvps, int head, int score) {
		this.matchResult = matchResult;
		this.map = map;
		this.date = date;
		this.ping = ping;
		this.kills = kills;
		this.assists = assists;
		this.deaths = deaths;
		this.mvps = mvps;
		this.head = head;
		this.score = score;
	}
	
	public Match(String matchResult, String map, Date date, int ping, int kills, int assists, int deaths, int mvps, int head, int score) {
		
		
		switch(matchResult) {
			case "Victory":
				this.matchResult = 1;
				break;
			case "Defeat":
				this.matchResult = 2;
				break;
			case "Tie":
				this.matchResult = 0;
				break;
		}
		this.map = map;
		this.date = date;
		this.ping = ping;
		this.kills = kills;
		this.assists = assists;
		this.deaths = deaths;
		this.mvps = mvps;
		this.head = head;
		this.score = score;
	}
	
	public String resultInText() {
		String resultText = "";
		
		switch(this.matchResult) {
		case 0:
			resultText = "Tie";
			break;
		case 1:
			resultText = "Victory";
			break;
		case 2:
			resultText = "Defeat";
			break;
		}
		
		this.matchResultText = resultText;
		return resultText;
	}
	
	public String toString() {
		
		String resultText = this.resultInText();
			
		
		return resultText + "\t" + map + "\t" + date + "\t" + ping + "\t" + kills + "\t" + assists + "\t" + deaths + "\t" + mvps + "\t" + head + "%\t" + score;
	}
	
	public String getDateString() {
		
		String year = Integer.toString(date.getYear() + 1900);
		String month = Integer.toString(date.getMonth() + 1);
		String days = Integer.toString(date.getDate());
		String hours = Integer.toString(date.getHours());
		String min = Integer.toString(date.getMinutes());
		String sec = Integer.toString(date.getSeconds());
		
		
		return year + " " + month + " " + days + " " + hours + " " + min + " " + sec;
	}
	
	//Getters and Setters
	public String getMatchResultText() {
		return this.matchResultText;
	}
	
	public int getMatchResult() {
		return this.matchResult;
	}
	
	public void setMatchResult(int matchResult){
		this.matchResult = matchResult;
	}
	
	public String getMap() {
		return map;
	}
	
	public void setMap(String map) {
		this.map = map;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getMvps() {
		return mvps;
	}

	public void setMvps(int mvps) {
		this.mvps = mvps;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}

package ch.zhaw.jasstafel;

public class PointManager {
	
	private int totalPointsTeam1;
	private int totalPointsTeam2;
	private int singlePointsTeam1;
	private int twentyPointsTeam1;
	private int fiftyPointsTeam1;
	private int hundredPointsTeam1;
	private int singlePointsTeam2;
	private int twentyPointsTeam2;
	private int fiftyPointsTeam2;
	private int hundredPointsTeam2;
	private static PointManager instance = new PointManager();
	
	private PointManager() {
		resetPoints();
	}
	
	public static PointManager getInstance() {
		return instance;
	}
	
	public void resetPoints() {
		totalPointsTeam1 = 0;
		totalPointsTeam2 = 0;
		singlePointsTeam1 = 0;
		twentyPointsTeam1 = 0;
		fiftyPointsTeam1 = 0;
		hundredPointsTeam1 = 0;
		singlePointsTeam2 = 0;
		twentyPointsTeam2 = 0;
		fiftyPointsTeam2 = 0;
		hundredPointsTeam2 = 0;
	}
	
	public void addPoints(int pointsTeam1, int pointsTeam2) {
		totalPointsTeam1 += pointsTeam1;
		pointsTeam1 = setHundredLineCount(pointsTeam1, 1);
		pointsTeam1 = setFiftyLineCount(pointsTeam1, 1);
		pointsTeam1 = setTwentyLineCount(pointsTeam1, 1);
		setSinglePoints(pointsTeam1, 1);
		totalPointsTeam2 += pointsTeam2;
		pointsTeam2 = setHundredLineCount(pointsTeam2, 2);
		pointsTeam2 = setFiftyLineCount(pointsTeam2, 2);
		pointsTeam2 = setTwentyLineCount(pointsTeam2, 2);
		setSinglePoints(pointsTeam2, 2);
	}
	
	public int getPoints(int team) {
		if (team == 1) {
			return totalPointsTeam1;
		}
		return totalPointsTeam2;
	}
	
	public int getHundredLineCount(int team) {
		if (team == 1) {
			return hundredPointsTeam1;
		}
		return hundredPointsTeam2;
	}
	
	public int getFiftyLineCount(int team) {
		if (team == 1) {
			return fiftyPointsTeam1;
		}
		return fiftyPointsTeam2;
	}
	
	public int getTwentyLineCount(int team) {
		if (team == 1) {
			return twentyPointsTeam1;
		}
		return twentyPointsTeam2;
	}
	
	public int getSinglePoints(int team) {
		if (team == 1) {
			return singlePointsTeam1;
		}
		return singlePointsTeam2;
	}
	
	private int setHundredLineCount(int points, int team) {
		while (points / 100 > 0) {
			points -= 100;
			if (team == 1) {
				hundredPointsTeam1++;
			} else {
				hundredPointsTeam2++;
			}
		}
		return points;
	}
	
	private int setFiftyLineCount(int points, int team) {
		while (points / 50 > 0) {
			points -= 50;
			if (team == 1) {
				fiftyPointsTeam1++;
			} else {
				fiftyPointsTeam2++;
			}
		}
		return points;
	}
	
	private int setTwentyLineCount(int points, int team) {
		while (points / 20 > 0) {
			points -= 20;
			if (team == 1) {
				twentyPointsTeam1++;
			} else {
				twentyPointsTeam2++;
			}
		}
		return points;
	}
	
	private void setSinglePoints(int points, int team) {
		if (team == 1) {
			singlePointsTeam1 += points;
			if (singlePointsTeam1 >= 20) {
				twentyPointsTeam1++;
				singlePointsTeam1 -= 20;
			}
		} else {
			singlePointsTeam2 += points;
			if (singlePointsTeam2 >= 20) {
				twentyPointsTeam2++;
				singlePointsTeam2 -= 20;
			}
		}
	}
	
}

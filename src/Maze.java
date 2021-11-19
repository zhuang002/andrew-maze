import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Maze {
	char[][] maze=null;
	char bChar = 0;
	char oChar = 0;
	char sChar = 0;
	char xChar = 0;
	Location start=null;
	Location exit = null;
	Random rand = new Random();

	public void loadMaze(File file) throws FileNotFoundException, ErrorInputException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(file);
		int row = sc.nextInt();
		int col = sc.nextInt();
		
		maze = new char[row][col];
		
		sc.nextLine();
		bChar = sc.nextLine().charAt(0);
		oChar = sc.nextLine().charAt(0);
		sChar = sc.nextLine().charAt(0);
		xChar = sc.nextLine().charAt(0);
		
		for (int i=0;i<row;i++) {
			String line = sc.nextLine();
			for (int j=0;j<col;j++) {
				maze[i][j] = toStandardChar(line.charAt(j));
				if (maze[i][j] == 'S') {
					start = new Location(i,j);
				} else if (maze[i][j] == 'X') {
					exit = new Location(i,j);
				}
			}
		}
		
	}

	private char toStandardChar(char c) throws ErrorInputException {
		// TODO Auto-generated method stub
		if (c == bChar) return 'B';
		if (c == oChar) return 'O';
		if (c == sChar) return 'S';
		if (c == xChar) return 'X';
		
		throw new ErrorInputException();
	}

	public void generateRandomMaze() {
		// TODO Auto-generated method stub
		// generate random size maze and fill with 'B'
		maze = new char[rand.nextInt(18)+3][rand.nextInt(18)+3];
		for (int i=0;i<maze.length;i++) {
			for (int j=0;j<maze[0].length;j++) {
				maze[i][j] = 'B';
			}
		}
		
		// generate a random start
		int r = rand.nextInt(maze.length-2)+1;
		int c = rand.nextInt(maze[0].length-2)+1;
		start = new Location(r,c);
		maze[r][c] = 'S';
		
		Location current = start;
		
		while (!isOnEdge(current)) {
			
			int dir = rand.nextInt(4);
			int steps = 0;
			switch(dir) {
			case 0:
				steps = rand.nextInt(current.row)+1;
				current = new Location(current.row-steps, current.col);
				break;
			case 1:
				steps = rand.nextInt(current.col)+1;
				current = new Location(current.row, current.col-steps);
				break;
			case 2:
				steps = rand.nextInt(maze.length-1-current.row)+1;
				current = new Location(current.row+steps,current.col);
				break;
			case 3:
				steps = rand.nextInt(maze[0].length-1-current.col)+1;
				current = new Location(current.row, current.col+steps);
				break;
			}
			maze[current.row][current.col] = 'O';
		}
		maze[current.row][current.col] = 'X';
		
	}

	private boolean isOnEdge(Location loc) {
		// TODO Auto-generated method stub
		return loc.col == 0 || loc.col == maze[0].length-1 || loc.row == 0 || loc.row == maze.length-1;
	}

	public ArrayList<Path> findPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	public char[][] getGid() {
		// TODO Auto-generated method stub
		return this.maze;
	}

}

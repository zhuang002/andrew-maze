import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Maze {
	char[][] maze = null;
	char bChar = 0;
	char oChar = 0;
	char sChar = 0;
	char xChar = 0;
	Location start = null;
	Location exit = null;
	Random rand = new Random();
	ArrayList<Location> visited=null;

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

		for (int i = 0; i < row; i++) {
			String line = sc.nextLine();
			for (int j = 0; j < col; j++) {
				maze[i][j] = toStandardChar(line.charAt(j));
				if (maze[i][j] == 'S') {
					start = new Location(i, j);
				} else if (maze[i][j] == 'X') {
					exit = new Location(i, j);
				}
			}
		}

	}

	private char toStandardChar(char c) throws ErrorInputException {
		// TODO Auto-generated method stub
		if (c == bChar)
			return 'B';
		if (c == oChar)
			return 'O';
		if (c == sChar)
			return 'S';
		if (c == xChar)
			return 'X';

		throw new ErrorInputException();
	}

	public void generateRandomMaze() {
		// TODO Auto-generated method stub
		// generate random size maze and fill with 'B'
		maze = new char[rand.nextInt(18) + 3][rand.nextInt(18) + 3];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				maze[i][j] = 'B';
			}
		}

		// generate a random start
		int r = rand.nextInt(maze.length - 2) + 1;
		int c = rand.nextInt(maze[0].length - 2) + 1;
		start = new Location(r, c);
		maze[r][c] = 'S';

		Location current = new Location(r, c);

		while (!isOnEdge(current)) {

			int dir = rand.nextInt(4);
			int steps = 0;
			switch (dir) {
			case 0:
				steps = rand.nextInt(current.row) + 1;
				for (int i = 1; i <= steps; i++) {
					current.row--;
					maze[current.row][current.col] = 'O';
				}
				break;
			case 1:
				steps = rand.nextInt(current.col) + 1;
				for (int i = 1; i <= steps; i++) {
					current.col--;
					maze[current.row][current.col] = 'O';
				}
				break;
			case 2:
				steps = rand.nextInt(maze.length - 1 - current.row) + 1;
				for (int i = 1; i <= steps; i++) {
					current.row++;
					maze[current.row][current.col] = 'O';
				}
				break;
			case 3:
				steps = rand.nextInt(maze[0].length - 1 - current.col) + 1;
				for (int i = 1; i <= steps; i++) {
					current.col++;
					maze[current.row][current.col] = 'O';
				}
				break;
			}

		}

		exit = new Location(current.row, current.col);

		int noCells = rand.nextInt((maze.length - 2) * (maze[0].length - 2));
		for (int i = 0; i < noCells; i++) {
			r = rand.nextInt(maze.length - 2) + 1;
			c = rand.nextInt(maze[0].length - 2) + 1;
			maze[r][c] = 'O';
		}

		maze[start.row][start.col] = 'S';
		maze[current.row][current.col] = 'X';

	}

	private boolean isOnEdge(Location loc) {
		// TODO Auto-generated method stub
		return loc.col == 0 || loc.col == maze[0].length - 1 || loc.row == 0 || loc.row == maze.length - 1;
	}

	public char[][] getGid() {
		// TODO Auto-generated method stub
		return this.maze;
	}

	public void checkBlocks() {
		// TODO Auto-generated method stub
		/*backUpMaze();
		markReachable(start);
		updateMaze();*/
		visited = new ArrayList<>();
		checkBlocks(start);
		maze[start.row][start.col]='S';
		
	}

	private void checkBlocks(Location location) {
		// TODO Auto-generated method stub
		char c=maze[location.row][location.col];
		if (c=='X' || c=='+')
			return;
		visited.add(location);
		ArrayList<Location> neighbours = getNeighbours(location);
		for (Location neighbour:neighbours) {
			checkBlocks(neighbour);
			c = maze[neighbour.row][neighbour.col];
			if (c=='+' || c=='X') {
				maze[location.row][location.col]='+';
			}
		}
		visited.remove(location);
		
	}




	private ArrayList<Location> getNeighbours(Location location) {
		// TODO Auto-generated method stub
		ArrayList<Location> al = new ArrayList<>();
		// check up
		if (location.row > 0) {
			Location neighbour = new Location(location.row - 1, location.col);
			char c=maze[neighbour.row][neighbour.col];
			if ((c=='O' || c=='X' || c=='+') && !visited.contains(neighbour))
				al.add(neighbour);
		}

		// check left
		if (location.col > 0) {

			Location neighbour = new Location(location.row, location.col - 1);
			char c=maze[neighbour.row][neighbour.col];
			if ((c=='O' || c=='X' || c=='+') && !visited.contains(neighbour))
				al.add(neighbour);

		}

		// check down
		if (location.row < maze.length - 1) {

			Location neighbour = new Location(location.row + 1, location.col);
			char c=maze[neighbour.row][neighbour.col];
			if ((c=='O' || c=='X' || c=='+') && !visited.contains(neighbour))
				al.add(neighbour);

		}
		// check left
		if (location.col < maze[0].length-1) {
			Location neighbour = new Location(location.row, location.col + 1);
			char c=maze[neighbour.row][neighbour.col];
			if ((c=='O' || c=='X' || c=='+') && !visited.contains(neighbour))
				al.add(neighbour);

		}
		return al;
	}

}

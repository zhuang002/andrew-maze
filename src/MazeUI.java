import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

public class MazeUI {

	private JFrame frame;
	JFileChooser fileChooser= new JFileChooser() ;
	JPanel gridPanel =null;
	Maze maze = new Maze();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeUI window = new MazeUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MazeUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Load");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						maze.loadMaze(fileChooser.getSelectedFile());
						char[][] grid = maze.getGid();
						displayGrid(grid);
					} catch (FileNotFoundException except) {
						JOptionPane.showMessageDialog(frame, "The file is not found.");
						
					} catch (ErrorInputException except) {
						JOptionPane.showMessageDialog(frame, "The content in the file is in bad format.");
					}
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Generate");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maze.generateRandomMaze();
				char[][] grid = maze.getGid();
				displayGrid(grid);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Run");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Find Path");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maze.checkBlocks();
				char[][] grid = maze.getGid();
				displayGrid(grid);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Clear");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JLabel lblNewLabel_message = new JLabel("Message");
		
		frame.getContentPane().add(lblNewLabel_message, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(20, 20, 0, 0));
		
		for (int i=0;i<400;i++) {
			JLabel lblNewLabel_1 = new JLabel("B");
			lblNewLabel_1.setOpaque(true);
			lblNewLabel_1.setBackground(Color.DARK_GRAY);
			lblNewLabel_1.setForeground(Color.white);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panel.add(lblNewLabel_1);
		}
		
		gridPanel = panel;
	}

	protected void displayGrid(char[][] grid) {
		// TODO Auto-generated method stub
		gridPanel.removeAll();
		gridPanel.setLayout(new GridLayout(grid.length,grid[0].length, 0, 0));
		for (int i=0;i<grid.length;i++) {
			for (int j=0;j<grid[0].length;j++) {
				char c = grid[i][j];
				JLabel label = null;
				
				switch(c) {
				case 'B':
					label = new JLabel(""+maze.bChar);
					label.setForeground(Color.white);
					label.setBackground(Color.darkGray);
					break;
					
				case 'O':
					label = new JLabel(""+maze.oChar);
					label.setForeground(Color.black);
					label.setBackground(Color.yellow);
					break;
				case 'S':
					label = new JLabel(""+maze.sChar);
					label.setForeground(Color.white);
					label.setBackground(Color.red);
					break;
				case 'X':
					label = new JLabel(""+maze.xChar);
					label.setForeground(Color.white);
					label.setBackground(Color.green);
					break;
				case '+':
					label = new JLabel(""+c);
					label.setForeground(Color.white);
					label.setBackground(Color.blue);
					break;
				default:
					break;
				}
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				label.setOpaque(true);
				gridPanel.add(label);
			}
		}
		gridPanel.revalidate();
	}

}

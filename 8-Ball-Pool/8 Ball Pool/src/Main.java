import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;


public class Main implements ActionListener {


	public static Level content;
	
//dimensi screen
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();
	public static final int HEIGHT = (int) screenSize.getHeight();

// Jfram
	private final JFrame splashScreen = new JFrame();
	private final JFrame mainScreen = new JFrame();
	private final JFrame playScreen = new JFrame();

	private Input input = new Input(playScreen);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				Main start = new Main();
			}
		});
	}
	
	public static JButton button(String name) {
		JButton newButton = new JButton(name);
		newButton.setFocusPainted(false);
		newButton.setFont(new Font("Arial", Font.BOLD, 26));
		newButton.setFocusable(false);
		newButton.setBackground(new Color(255, 255, 255));
		newButton.setMaximumSize(new Dimension(225, 255));
		return newButton;
	}

	public Main() {
		
		// setting jframe
		splashScreen.setSize(WIDTH, HEIGHT);
		splashScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainScreen.setSize(WIDTH, HEIGHT);
		playScreen.setVisible(false);

		mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainScreen.setUndecorated(true);
		mainScreen.setVisible(false);

		try {
			splashScreen.setContentPane(new JLabel(
					new ImageIcon(ImageIO.read(new File("8 Ball Pool/resource/Images/8 Ball Pool Splashscreen.jpg"))
							.getScaledInstance((int) WIDTH, (int) HEIGHT, Image.SCALE_SMOOTH))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		splashScreen.getContentPane().setLayout(new BorderLayout(5, 5));
		splashScreen.setUndecorated(true);
		splashScreen.setVisible(true);
		
		// label animasi buat splashscreen
		BlinkLabel anyKeyLabel = new BlinkLabel("TEKAN TOMBOL APA SAJA UNTUK MELANJUTKAN");
		anyKeyLabel.setForeground(Color.BLACK);
		anyKeyLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		splashScreen.getContentPane().add(anyKeyLabel, BorderLayout.PAGE_END);
		anyKeyLabel.startBlinking();

		// key apabila di tekan

		splashScreen.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				splashScreen.dispose();
				mainScreen.setVisible(true);
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		
		//main menu

		mainScreen.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//jpanel main menu
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(0, 0, 0, 0));
		menuPane.setPreferredSize(new Dimension((int) WIDTH / 3, (int) HEIGHT - (int) HEIGHT / 5));
		menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.Y_AXIS));
		menuPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));


		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 125);
		mainScreen.getContentPane().add(menuPane, c);
		c.insets = new Insets(0, 125, 0, 0);
		c.gridx = 1;

		JButton playButton = button("Play");
		playButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(Box.createRigidArea(new Dimension(0, 50)));
		menuPane.add(playButton);
		
		menuPane.getRootPane().setDefaultButton(playButton);
		
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startBalls();

			}
		});

		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		JButton loadButton=button("Load");//button for thomas
		loadButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(loadButton);
		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startBalls();
				content.loadGame("8 Ball Pool/resource/saveFile.txt");
			}
		});

		JButton exitButton = button("Exit");
		exitButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(exitButton);
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	public void startBalls() {
		playScreen.setUndecorated(true);
		playScreen.setVisible(true);
		playScreen.setSize((int) WIDTH, (int) HEIGHT);

		
		content = new Level();

		
		playScreen.setContentPane(content);
		playScreen.getGlassPane().setVisible(true);

		Timer timer = new Timer(20, this);
		timer.start();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		playScreen.repaint();
	}
}
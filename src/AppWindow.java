import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

/**
 * Application window. Holds the menu-bar etc.
 *
 * @author David Winter
 */
public class AppWindow extends JFrame implements ActionListener, ComponentListener {
	private GamePanel gamePanel;
	private ActionPerformedClass actionPerformed;
	private Color defaultTableColour = new Color(6, 120, 0);

	private JMenuItem savePlayer = new JMenuItem("Save Current Player");
	private JMenuItem openPlayer = new JMenuItem("Open Existing Player");

	private JMenuItem updatePlayerDetails;
	private JMenuItem dealAction;
	private JMenuItem hitAction;
	private JMenuItem doubleAction;
	private JMenuItem standAction;
	private JMenuItem oneChip;
	private JMenuItem fiveChip;
	private JMenuItem tenChip;
	private JMenuItem twentyFiveChip;
	private JMenuItem hundredChip;
	private JMenuItem windowTableColourMenu;
	private JMenuItem helpBlackjackRulesMenu;
	private JMenuItem helpAboutMenu;

	final int WIDTH = 600;
	final int HEIGHT = 500;

	public AppWindow() {
		super("Blackjack");

		configureWindow();
		setupMenus();
		setupKeyboardShortcuts();
		setupActionListeners();
		initializeComponents();

		setVisible(true);
	}

	private void configureWindow() {
		addComponentListener(this);

		Dimension windowSize = new Dimension(WIDTH, HEIGHT);
		setSize(windowSize);
		setLocationRelativeTo(null); // put game in centre of screen

		setBackground(defaultTableColour);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setupMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu playerMenu = new JMenu("Player");
		updatePlayerDetails = new JMenuItem("Update Player Details");
		playerMenu.add(updatePlayerDetails);
		playerMenu.addSeparator();
		playerMenu.add(savePlayer);
		playerMenu.add(openPlayer);
		menuBar.add(playerMenu);

		JMenu actionMenu = new JMenu("Actions");
		dealAction = new JMenuItem("Deal");
		hitAction = new JMenuItem("Hit");
		doubleAction = new JMenuItem("Double");
		standAction = new JMenuItem("Stand");
		actionMenu.add(dealAction);
		actionMenu.add(hitAction);
		actionMenu.add(doubleAction);
		actionMenu.add(standAction);
		menuBar.add(actionMenu);

		JMenu betMenu = new JMenu("Bet");
		oneChip = new JMenuItem("$1");
		fiveChip = new JMenuItem("$5");
		tenChip = new JMenuItem("$10");
		twentyFiveChip = new JMenuItem("$25");
		hundredChip = new JMenuItem("$100");
		betMenu.add(oneChip);
		betMenu.add(fiveChip);
		betMenu.add(tenChip);
		betMenu.add(twentyFiveChip);
		betMenu.add(hundredChip);
		menuBar.add(betMenu);

		JMenu windowMenu = new JMenu("Window");
		windowTableColourMenu = new JMenuItem("Change Table Colour");
		windowMenu.add(windowTableColourMenu);
		menuBar.add(windowMenu);

		JMenu helpMenu = new JMenu("Help");
		helpBlackjackRulesMenu = new JMenuItem("Blackjack Rules");
		helpAboutMenu = new JMenuItem("About Blackjack");
		helpMenu.add(helpBlackjackRulesMenu);
		helpMenu.addSeparator();
		helpMenu.add(helpAboutMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
	}

	private void setupKeyboardShortcuts() {
		updatePlayerDetails.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_U));
		savePlayer.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_S));
		openPlayer.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_O));
		dealAction.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_N));
		hitAction.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_C));
		doubleAction.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_D));
		standAction.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_F));
		oneChip.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_1));
		fiveChip.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_2));
		tenChip.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_3));
		twentyFiveChip.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_4));
		hundredChip.setAccelerator(createShortCut(java.awt.event.KeyEvent.VK_5));
	}

	private void setupActionListeners() {
		dealAction.addActionListener(this);
		hitAction.addActionListener(this);
		doubleAction.addActionListener(this);
		standAction.addActionListener(this);
		updatePlayerDetails.addActionListener(this);
		savePlayer.addActionListener(this);
		openPlayer.addActionListener(this);
		windowTableColourMenu.addActionListener(this);
		helpAboutMenu.addActionListener(this);
		oneChip.addActionListener(this);
		fiveChip.addActionListener(this);
		tenChip.addActionListener(this);
		twentyFiveChip.addActionListener(this);
		hundredChip.addActionListener(this);
	}

	private void initializeComponents() {
		gamePanel = new GamePanel();
		gamePanel.setBackground(defaultTableColour);
		add(gamePanel);
	}

	public KeyStroke createShortCut(int keyEvent) {
		return KeyStroke.getKeyStroke(keyEvent, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
	}

	public void componentResized(ComponentEvent e) {
		int currentWidth = getWidth();
		int currentHeight = getHeight();

		if (currentWidth < WIDTH) {
			currentWidth = WIDTH;
		}

		if (currentHeight < HEIGHT) {
			currentHeight = HEIGHT;
		}

		if (currentHeight < HEIGHT || currentHeight < WIDTH) {
			setSize(currentWidth, currentHeight);
		}
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}
}
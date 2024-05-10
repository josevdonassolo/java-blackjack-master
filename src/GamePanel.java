import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;

import Players.*;
import Cards.*;

public class GamePanel extends JPanel implements ActionListener {
	private Dealer dealer;
	private Player player;

	private GameTable table;
	private ActionPerformedClass actionPerformed;

	private JButton newGameButton = new JButton("Deal");
	private JButton hitButton = new JButton("Hit");
	private JButton doubleButton = new JButton("Double");
	private JButton standButton = new JButton("Stand");
	private JButton add1Chip = new JButton("1");
	private JButton add5Chip = new JButton("5");
	private JButton add10Chip = new JButton("10");
	private JButton add25Chip = new JButton("25");
	private JButton add100Chip = new JButton("100");
	private JButton clearBet = new JButton("Clear");

	private JLabel currentBet = new JLabel("Please set your bet...");
	private JLabel playerWallet = new JLabel("$999.99");
	private JLabel cardsLeft = new JLabel("Cards left...");
	private JLabel dealerSays = new JLabel("Dealer says...");

	public GamePanel() {
		this.setLayout(new BorderLayout());

		table = new GameTable();
		add(table, BorderLayout.CENTER);

		JPanel betPanel = new JPanel();
		betPanel.add(currentBet);
		betPanel.add(clearBet);
		betPanel.add(add1Chip);
		betPanel.add(add5Chip);
		betPanel.add(add10Chip);
		betPanel.add(add25Chip);
		betPanel.add(add100Chip);
		betPanel.add(playerWallet);

		JPanel dealerPanel = new JPanel();
		dealerPanel.add(dealerSays);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(newGameButton);
		optionsPanel.add(hitButton);
		optionsPanel.add(doubleButton);
		optionsPanel.add(standButton);
		optionsPanel.add(cardsLeft);

		JPanel bottomItems = new JPanel();
		bottomItems.setLayout(new GridLayout(0, 1));
		bottomItems.add(dealerPanel);
		bottomItems.add(betPanel);
		bottomItems.add(optionsPanel);
		add(bottomItems, BorderLayout.SOUTH);

		// opaque stuff
		// this.setBackground(new Color(6, 120, 0)); // now done in AppWindow.java
		betPanel.setOpaque(false);
		dealerPanel.setOpaque(false);
		optionsPanel.setOpaque(false);
		bottomItems.setOpaque(false);

		// add listeners to buttons
		newGameButton.addActionListener(this);
		hitButton.addActionListener(this);
		doubleButton.addActionListener(this);
		standButton.addActionListener(this);
		clearBet.addActionListener(this);
		add1Chip.addActionListener(this);
		add5Chip.addActionListener(this);
		add10Chip.addActionListener(this);
		add25Chip.addActionListener(this);
		add100Chip.addActionListener(this);

		// tool tips
		newGameButton.setToolTipText("Deal a new game.");
		hitButton.setToolTipText("Request another card.");
		doubleButton.setToolTipText("Double your bet, and receive another card.");
		standButton.setToolTipText("Stand with your card-hand.");
		clearBet.setToolTipText("Clear your current bet.");
		add1Chip.setToolTipText("Add a $1 chip to your current bet.");
		add5Chip.setToolTipText("Add a $5 chip to your current bet.");
		add10Chip.setToolTipText("Add a $10 chip to your current bet.");
		add25Chip.setToolTipText("Add a $25 chip to your current bet.");
		add100Chip.setToolTipText("Add a $100 chip to your current bet.");

		dealer = new Dealer();
		player = new Player("James Bond", 32, "Male");
		player.setWallet(100.00);

		updateValues();
	}

	public void newGame() {
		dealer.deal(player);
	}

	public void hit() {
		dealer.hit(player);
	}

	public void playDouble() {
		dealer.playDouble(player);
	}

	public void stand() {
		dealer.stand(player);
	}

	public void increaseBet(int amount) {
		dealer.acceptBetFrom(player, amount + player.getBet());
	}

	public void clearBet() {
		player.clearBet();
	}

	public void updateValues() {
		dealerSays.setText("<html><p align=\"center\"><font face=\"Serif\" color=\"white\" style=\"font-size: 20pt\">"
				+ dealer.says() + "</font></p></html>");

		doubleButton.setEnabled(!dealer.isGameOver() && dealer.canPlayerDouble(player));
		newGameButton.setEnabled(dealer.isGameOver() && player.betPlaced() && !player.isBankrupt());
		hitButton.setEnabled(!dealer.isGameOver());
		standButton.setEnabled(!dealer.isGameOver());

		clearBet.setEnabled(dealer.isGameOver() && player.betPlaced());
		add1Chip.setEnabled(dealer.isGameOver() && player.getWallet() >= 1.0);
		add5Chip.setEnabled(dealer.isGameOver() && player.getWallet() >= 5);
		add10Chip.setEnabled(dealer.isGameOver() && player.getWallet() >= 10);
		add25Chip.setEnabled(dealer.isGameOver() && player.getWallet() >= 25);
		add100Chip.setEnabled(dealer.isGameOver() && player.getWallet() >= 100);

		// redraw cards and totals
		table.update(dealer.getHand(), player.getHand(), dealer.areCardsFaceUp());
		table.setNames(dealer.getName(), player.getName());
		table.repaint();

		cardsLeft.setText(
				"Deck: " + dealer.cardsLeftInPack() + "/" + (dealer.CARD_PACKS * Cards.CardPack.CARDS_IN_PACK));

		if (player.isBankrupt()) {
			moreFunds();
		}

		// redraw bet
		currentBet.setText(Double.toString(player.getBet()));
		playerWallet.setText(Double.toString(player.getWallet()));
	}

	private void moreFunds() {
		int response = JOptionPane.showConfirmDialog(null,
				"Marshall Aid. One Hundred dollars. With the compliments of the USA.", "Out of funds",
				JOptionPane.YES_NO_OPTION);

		if (response == JOptionPane.YES_OPTION) {
			player.setWallet(100.00);
			updateValues();
		}
	}

	public void savePlayer() {
		if (dealer.isGameOver()) {
			JFileChooser playerSaveDialog = new JFileChooser("~");
			SimpleFileFilter fileFilter = new SimpleFileFilter(".ser", "(.ser) Serialised Files");
			playerSaveDialog.addChoosableFileFilter(fileFilter);
			int playerSaveResponse = playerSaveDialog.showSaveDialog(this);

			if (playerSaveResponse == playerSaveDialog.APPROVE_OPTION) {
				String filePath = playerSaveDialog.getSelectedFile().getAbsolutePath();

				try {
					ObjectOutputStream playerOut = new ObjectOutputStream(new FileOutputStream(filePath));
					playerOut.writeObject(player);
					playerOut.close();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Can't save a player while a game is in progress.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public void openPlayer() {
		if (dealer.isGameOver()) {
			JFileChooser playerOpenDialog = new JFileChooser("~");
			SimpleFileFilter fileFilter = new SimpleFileFilter(".ser", "(.ser) Serialised Files");
			playerOpenDialog.addChoosableFileFilter(fileFilter);
			int playerOpenResponse = playerOpenDialog.showOpenDialog(this);

			if (playerOpenResponse == playerOpenDialog.APPROVE_OPTION) {
				String filePath = playerOpenDialog.getSelectedFile().getAbsolutePath();

				try {
					ObjectInputStream playerIn = new ObjectInputStream(new FileInputStream(filePath));
					Player openedPlayer = (Player) playerIn.readObject();
					openedPlayer.hand = new PlayerCardHand();
					player = openedPlayer;
					playerIn.close();
					System.out.println(openedPlayer.getName());
				} catch (ClassNotFoundException e) {
					System.err.println(e);
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Can't open an existing player while a game is in progress.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updatePlayer() {
		PlayerDialog playerDetails = new PlayerDialog(null, "Player Details", true, player);
		playerDetails.setVisible(true);

		player = playerDetails.getPlayer();
	}
}
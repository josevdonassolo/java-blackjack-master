import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ActionPerformedClass implements ActionListener {
	private GamePanel gamePanel;
    @Override
    public void actionPerformed(ActionEvent evt) {
		String act = evt.getActionCommand();

		if (act.equals("$1")) {
			gamePanel.increaseBet(1);
		} else if (act.equals("$5")) {
			gamePanel.increaseBet(5);
		} else if (act.equals("$10")) {
			gamePanel.increaseBet(10);
		} else if (act.equals("$25")) {
			gamePanel.increaseBet(25);
		} else if (act.equals("$100")) {
			gamePanel.increaseBet(100);
		} else if (act.equals("Deal")) {
			gamePanel.newGame();
		} else if (act.equals("Hit")) {
			gamePanel.hit();
		} else if (act.equals("Double")) {
			gamePanel.playDouble();
		} else if (act.equals("Stand")) {
			gamePanel.stand();
		} else if (act.equals("Update Player Details")) {
			gamePanel.updatePlayer();
		} else if (act.equals("Save Current Player")) {
			gamePanel.savePlayer();
		} else if (act.equals("Open Existing Player")) {
			gamePanel.openPlayer();
		} else if (act.equals("Change Table Colour")) {
			Color tableColour = JColorChooser.showDialog(this, "Select Table Colour", defaultTableColour);
			this.setBackground(tableColour);
			gamePanel.setBackground(tableColour);
			gamePanel.repaint();
			this.repaint();
		} else if (act.equals("About Blackjack")) {
			String aboutText = "<html><p align=\"center\" style=\"padding-bottom: 10px;\">Written by David Winter &copy; 2006<br>Version 1.0</p><p align=\"center\" style=\"padding-bottom: 10px;\"><small>Become such an expert while developing this, <br>I won $1000 online in a game of Blackjack!</small></p><p align=\"center\">email: djw@davidwinter.me.uk<br>web: davidwinter.me.uk</p></html>";
			JOptionPane.showMessageDialog(this, aboutText, "About Blackjack", JOptionPane.PLAIN_MESSAGE);
		} else if (isBetEvent(act)) {
			increaseBet(Integer.parseInt(act));
		} else if (act.equals("Clear")) {
			System.out.println("clear bet");
			clearBet();
		}
		gamePanel.updateValues();
	}

    public boolean isBetEvent(String act) {
		return act.equals("1") || act.equals("5") || act.equals("10") || act.equals("25") || act.equals("100");
	}
}


	
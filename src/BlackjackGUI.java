import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Timer;

public class BlackjackGUI extends JFrame {
    
    // Game components
    private Deck deck;
    private Player player;
    private Player dealer;
    private int playerMoney = 100;
    private int currentBet = 0;
    private boolean gameInProgress = false;
    private boolean dealerRevealing = false;
    
    // GUI components
    private JPanel mainPanel;
    private JPanel dealerPanel;
    private JPanel playerPanel;
    private JPanel buttonPanel;
    private JPanel infoPanel;
    
    private JLabel dealerLabel;
    private JLabel playerLabel;
    private JLabel moneyLabel;
    private JLabel betLabel;
    private JLabel messageLabel;
    
    private JButton hitButton;
    private JButton standButton;
    private JButton newGameButton;
    private JButton betButton;
    
    private JTextField betField;
    
    public BlackjackGUI() {
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initializeGame();
        createGUI();
        updateDisplay();
    }
    
    private void initializeGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Player();
        deck.shuffle();
    }
    
    private void createGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(34, 139, 34)); // Dark green background
        
        // Dealer panel
        dealerPanel = new JPanel();
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
        dealerPanel.setBackground(new Color(34, 139, 34));
        dealerLabel = new JLabel("Dealer's Cards:");
        dealerLabel.setForeground(Color.WHITE);
        dealerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dealerPanel.add(dealerLabel);
        
        // Player panel
        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(new Color(34, 139, 34));
        playerLabel = new JLabel("Your Cards:");
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerPanel.add(playerLabel);
        
        // Info panel
        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.setBackground(new Color(34, 139, 34));
        
        moneyLabel = new JLabel("Money: $" + playerMoney);
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        betLabel = new JLabel("Bet: $" + currentBet);
        betLabel.setForeground(Color.WHITE);
        betLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        messageLabel = new JLabel("Place your bet and start a new game!");
        messageLabel.setForeground(Color.YELLOW);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        infoPanel.add(moneyLabel);
        infoPanel.add(betLabel);
        infoPanel.add(messageLabel);
        
        // Button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(34, 139, 34));
        
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");
        betButton = new JButton("Place Bet");
        
        betField = new JTextField(10);
        betField.setText("10");
        
        // Style buttons
        styleButton(hitButton);
        styleButton(standButton);
        styleButton(newGameButton);
        styleButton(betButton);
        
        // Add action listeners
        hitButton.addActionListener(e -> hit());
        standButton.addActionListener(e -> stand());
        newGameButton.addActionListener(e -> newGame());
        betButton.addActionListener(e -> placeBet());
        
        // Initially disable game buttons
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        
        buttonPanel.add(new JLabel("Bet: $"));
        buttonPanel.add(betField);
        buttonPanel.add(betButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        
        // Add panels to main panel
        mainPanel.add(dealerPanel, BorderLayout.NORTH);
        mainPanel.add(playerPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(mainPanel);
    }
    
    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    
    private void placeBet() {
        try {
            int bet = Integer.parseInt(betField.getText());
            if (bet <= 0 || bet > playerMoney) {
                JOptionPane.showMessageDialog(this, 
                    "Bet must be between $1 and $" + playerMoney, 
                    "Invalid Bet", JOptionPane.ERROR_MESSAGE);
                return;
            }
            currentBet = bet;
            updateDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid number", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void newGame() {
        if (currentBet == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please place a bet first!", 
                "No Bet", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Clear previous game
        player.clear();
        dealer.clear();
        
        // Deal initial cards
        player.add(deck.take());
        dealer.add(deck.take());
        player.add(deck.take());
        dealer.add(deck.take());
        
        gameInProgress = true;
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        newGameButton.setEnabled(false);
        betButton.setEnabled(false);
        
        updateDisplay();
        
        // Check for natural blackjack
        if (player.getHandTotal() == 21) {
            stand(); // Automatically stand on blackjack
        }
    }
    
    private void hit() {
        if (!gameInProgress) return;
        
        player.add(deck.take());
        updateDisplay();
        
        if (player.getHandTotal() > 21) {
            endGame("Bust! You went over 21. You lose!");
        }
    }
    
    private void stand() {
        if (!gameInProgress) return;
        
        // Disable buttons during dealer's turn
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        
        // First, reveal the hidden card with a delay
        dealerRevealing = true;
        messageLabel.setText("Dealer reveals hidden card...");
        updateDisplay(); // Show the reveal state
        
        Timer revealTimer = new Timer(1000, e -> {
            dealerRevealing = false;
            // Now start the dealer's turn
            dealerTurn();
        });
        revealTimer.setRepeats(false);
        revealTimer.start();
    }
    
    private void dealerTurn() {
        if (dealer.getHandTotal() <= 16) {
            // Dealer needs to hit
            dealer.add(deck.take());
            
            // Update display immediately
            updateDisplay();
            messageLabel.setText("Dealer draws a card...");
            
            // Add a delay and continue dealer's turn
            Timer timer = new Timer(1000, e -> dealerTurn());
            timer.setRepeats(false);
            timer.start();
        } else {
            // Dealer is done, determine winner
            determineWinner();
        }
    }
    
    private void determineWinner() {
        updateDisplay(); // Show final state
        
        // Determine winner
        int playerTotal = player.getHandTotal();
        int dealerTotal = dealer.getHandTotal();
        
        String result;
        if (dealerTotal > 21) {
            result = "Dealer busts! You win!";
            playerMoney += currentBet;
        } else if (playerTotal > dealerTotal) {
            result = "You win!";
            playerMoney += currentBet;
        } else if (playerTotal < dealerTotal) {
            result = "Dealer wins!";
            playerMoney -= currentBet;
        } else {
            result = "It's a tie!";
        }
        
        endGame(result);
    }
    
    private void endGame(String message) {
        gameInProgress = false;
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(true);
        betButton.setEnabled(true);
        
        messageLabel.setText(message);
        updateDisplay();
        
        if (playerMoney <= 0) {
            JOptionPane.showMessageDialog(this, 
                "You're out of money! Game over!", 
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
    private void updateDisplay() {
        // Update dealer cards
        dealerPanel.removeAll();
        dealerPanel.add(dealerLabel);
        
        if (dealer.totalCards() > 0) {
            // Show only the first card during the game
            Card firstCard = dealer.getCard(0);
            JLabel cardLabel = new JLabel(firstCard.toString());
            cardLabel.setForeground(Color.WHITE);
            cardLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            dealerPanel.add(cardLabel);
            
            if (gameInProgress && !hitButton.isEnabled() && !standButton.isEnabled() && !dealerRevealing) {
                // Dealer's turn - show all cards and running total
                for (int i = 1; i < dealer.totalCards(); i++) {
                    Card card = dealer.getCard(i);
                    JLabel additionalCardLabel = new JLabel(card.toString());
                    additionalCardLabel.setForeground(Color.WHITE);
                    additionalCardLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                    dealerPanel.add(additionalCardLabel);
                }
                
                JLabel totalLabel = new JLabel("Total: " + dealer.getHandTotal());
                totalLabel.setForeground(Color.YELLOW);
                totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
                dealerPanel.add(totalLabel);
            } else if (gameInProgress && dealerRevealing) {
                // Revealing phase - show all cards but don't show additional cards yet
                for (int i = 1; i < dealer.totalCards(); i++) {
                    Card card = dealer.getCard(i);
                    JLabel additionalCardLabel = new JLabel(card.toString());
                    additionalCardLabel.setForeground(Color.WHITE);
                    additionalCardLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                    dealerPanel.add(additionalCardLabel);
                }
                
                JLabel totalLabel = new JLabel("Total: " + dealer.getHandTotal());
                totalLabel.setForeground(Color.YELLOW);
                totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
                dealerPanel.add(totalLabel);
            } else if (gameInProgress) {
                // Player's turn - show only first card
                JLabel hiddenLabel = new JLabel("Hidden Card");
                hiddenLabel.setForeground(Color.GRAY);
                hiddenLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                dealerPanel.add(hiddenLabel);
                
                // Show only the first card's value
                JLabel totalLabel = new JLabel("Total: " + firstCard.getValue());
                totalLabel.setForeground(Color.YELLOW);
                totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
                dealerPanel.add(totalLabel);
            } else {
                // Game over - show all cards and final total
                for (int i = 1; i < dealer.totalCards(); i++) {
                    Card card = dealer.getCard(i);
                    JLabel additionalCardLabel = new JLabel(card.toString());
                    additionalCardLabel.setForeground(Color.WHITE);
                    additionalCardLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                    dealerPanel.add(additionalCardLabel);
                }
                
                JLabel totalLabel = new JLabel("Total: " + dealer.getHandTotal());
                totalLabel.setForeground(Color.YELLOW);
                totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
                dealerPanel.add(totalLabel);
            }
        }
        
        // Update player cards
        playerPanel.removeAll();
        playerPanel.add(playerLabel);
        
        if (player.totalCards() > 0) {
            for (int i = 0; i < player.totalCards(); i++) {
                Card card = player.getCard(i);
                JLabel cardLabel = new JLabel(card.toString());
                cardLabel.setForeground(Color.WHITE);
                cardLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                playerPanel.add(cardLabel);
            }
            
            JLabel totalLabel = new JLabel("Total: " + player.getHandTotal());
            totalLabel.setForeground(Color.YELLOW);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
            playerPanel.add(totalLabel);
        }
        
        // Update info
        moneyLabel.setText("Money: $" + playerMoney);
        betLabel.setText("Bet: $" + currentBet);
        
        // Refresh display
        dealerPanel.revalidate();
        dealerPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BlackjackGUI().setVisible(true);
        });
    }
} 
package game.obj;


import game.AsteroidsGame;
import game.AsteroidsGame.State;
import game.Keyboard;
import game.Obj;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

//Heads-up Display Class
//Kind of the Initializing Screen
//Shows basically everything like score, highest score, lives, start, credits, gameover and etc.


public class HUD extends Obj {
    
    //Sets the font
    private Font font = new Font("Impact", Font.PLAIN, 30);
    private Font font2 = new Font("Impact", Font.PLAIN, 20);

    //Text to be displayed 
    private String creditText = "© Programmed by UC CITCS 1-E";
    private String startText = "P U S H   S P A C E    T O   S T A R T";
    private String gameOverText = "G A M E  O V E R";
     
    //Some states
    private boolean titleState;
    private boolean gameOverState;
    private boolean allShotsDestroyed;
  
    //Start Time for gameOver
    private long gameOverStartTime;
    
    //Gets parameters of game from AsteroidsGame class
    public HUD(AsteroidsGame game) {
        super(game);
        createShipShape();
    }
    
    //Creates the ship lives shape
    private void createShipShape() {
        Polygon shipShape = new Polygon();
        shipShape.addPoint(0, -10);
        shipShape.addPoint(5, 10);
        shipShape.addPoint(-5, 10);
        shape = shipShape;
    }
    
    //Title will be changed to Playing state as soon as players press the space button 
    @Override
    public void updateTitle() {
        if (Keyboard.keyDown[KeyEvent.VK_SPACE]) {
            game.start(); //starts the game
        }
    }
    
    //Updates the high score and goes back to title screen when player reaches Game Over
    @Override
    public void updateGameOver() {
        allShotsDestroyed = game.checkAllObjectsDestroyed(Shot.class);
        if (!allShotsDestroyed) {
            gameOverStartTime = System.currentTimeMillis();
            return;
        }
        
        long gameOverTime = System.currentTimeMillis() - gameOverStartTime;
        if (gameOverTime > 10000) {
            game.updateHiscore();
            game.backToTitle();
        }
    }
    
    //Draw
    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.setColor(Color.WHITE);
        
       
        // draw score
        g.setFont(font);
        g.drawString("" + game.getScore(), 15, 35);

        // draw game over
        if (gameOverState && allShotsDestroyed) {
            int gameOverWidth = g.getFontMetrics().stringWidth(gameOverText);
            int gameOverX = (game.getWidth() - gameOverWidth) / 2;
            g.drawString(gameOverText, gameOverX, game.getHeight() / 2);
        }

        // draw hiscore
        g.setFont(font2);
        int hiscoreWidth = g.getFontMetrics().stringWidth("" + game.getHiscore());
        int hiscoreX = (game.getWidth() - hiscoreWidth) / 2;
        g.drawString("" + game.getHiscore(), hiscoreX, 30);
        
        // draw credit
        if (titleState) {
            int creditWidth = g.getFontMetrics().stringWidth(creditText);
            int creditX = (game.getWidth() - creditWidth) / 2;
            g.drawString(creditText, creditX, game.getHeight() - 50);
        }

        // push space to start
        if (titleState && (System.currentTimeMillis() / 300) % 2 == 0) {
            int startWidth = g.getFontMetrics().stringWidth(startText);
            int startX = (game.getWidth() - startWidth) / 2;
            g.drawString(startText, startX, game.getHeight() / 2);
        }

        // draw lives
        g.translate(20, 60);
        for (int l = 0; l < game.getLives(); l++) {
            g.draw(shape);
            g.translate(15, 00);
        }
        
        g.setTransform(at);
    }

    @Override
    public void StateChanged(State newState) {
        titleState = newState == State.TITLE;
        gameOverState = newState == State.GAME_OVER;
        if (gameOverState) {
            gameOverStartTime = System.currentTimeMillis();
            allShotsDestroyed = false;
        }
    }
    
}

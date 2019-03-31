package game.obj;

import game.AsteroidsGame;
import game.AsteroidsGame.State;
import game.Obj;

//Initializes the game for it to have a start time
public class Initializer extends Obj {
    
    private long startTime;
    
    //Gets parameters of game from AsteroidsGame Class
    public Initializer(AsteroidsGame game) {
        super(game);
        startTime = System.currentTimeMillis(); //Start time is from the System package currentTimeMillis();
    }

    //Shows the Title State for the start of the Game
    @Override
    public void updateInitializing() {
        if (System.currentTimeMillis() - startTime > 100) {
            game.setState(State.TITLE);
        }
    }
    
}

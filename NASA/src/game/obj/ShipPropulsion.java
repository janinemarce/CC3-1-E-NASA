package game.obj;

import game.AsteroidsGame;
import game.Obj;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class ShipPropulsion extends Obj {
    
    //When you move the ship forward this creates a shape called propulsion 
    //Think of it as the smoke that comes out of a rocketship when it blasts off hehe
    //This method is for design purposes to make the game look more attractive
    
    private Ship ship; 
    private boolean show;
    private Polygon propulsionShape = new Polygon(); //Creates a new polygon
    
    //Calls the AsteroidGame class
    public ShipPropulsion(AsteroidsGame game, Ship ship) {
        super(game);
        this.ship = ship;
        setShape();
    }
    
    //Creates the Propulsion Shape
    private void setShape() {
        propulsionShape.addPoint(-25, 0);
        propulsionShape.addPoint(-10, 3);
        propulsionShape.addPoint(-10, -3);
        shape = propulsionShape;
    }

    //This updates the look of the Ship and Propulsion when it Ship moving
    @Override
    public void update() {
        x = ship.x;
        y = ship.y;
        angle = ship.angle;
        visible = ship.visible;
        show = !show;
        propulsionShape.xpoints[0] = -25 + (int) (10 * Math.random());
    }
    
    //Draws the polygon if the ship is accelerating
    @Override
    public void draw(Graphics2D g) {
        if (ship.accelerating && show) {
            super.draw(g);
        }
    }
    
}

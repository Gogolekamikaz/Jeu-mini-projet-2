package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.Queue;

public class MazeLevel extends SuperPacmanArea{


    private SuperPacmanAutoGeneratedMazeBehavior behavior;
    private ArrayList<Ghost> areaGhostActors;
    private AreaGraph areaGraph;
    protected final static float cameraScaleFactor = 15.f;

    private int diamondCount;

    private static DiscreteCoordinates PLAYER_SPAWN_POSITION;


    @Override
    protected void createArea() {

        Gate[] gates = new Gate[2];
        gates[0] = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(5,1), this);
        gates[1] = new Gate(this, Orientation.LEFT, new DiscreteCoordinates(4,1), this);

        for (Gate gate : gates) {
            registerActor(gate);
        }
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem){
        behavior = new SuperPacmanAutoGeneratedMazeBehavior(window, 25, 25);
        if (super.begin(window, fileSystem, behavior)) {
            // Set the behavior map
            PLAYER_SPAWN_POSITION = behavior.getSpawnPoint();

            //createArea();
            return true;
        }
        return false;
    }

    @Override
    public String getTitle() {
        return "superpacman/MazeLevel";
    }

    public ArrayList<Ghost> getAreaGhostActors(){
        return areaGhostActors;
    }

    public Queue<Orientation> shortestPath(DiscreteCoordinates origine, DiscreteCoordinates arrivee){
        return(behavior.shortestPath(origine, arrivee));
    }

    public void scareCheck(SuperPacmanPlayer player){
        behavior.scareCheck(player);
    }

    public AreaGraph getAreaGraph(){return areaGraph;}

    public void addDiamond(){ diamondCount++; }

    public void removeDiamond(){ diamondCount--; }


    @Override
    public DiscreteCoordinates getSpawnPoint() {
        return behavior.getSpawnPoint();
    }

    @Override
    public boolean isOn() { return !isOff();}

    @Override
    public boolean isOff() { return diamondCount>0;}

    @Override
    public float getIntensity() {
        if(isOn()){
            return 1.f;
        } else {
            return 0.f;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Keyboard keyboard= this.getKeyboard();
        if(keyboard.get(Keyboard.P).isPressed()){
            suspend();
        }
    }

    @Override
    public void suspend(){
    }

}

package io.github.AngryBirdsCDAbhi.Levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ScreenAdapter;
import io.github.AngryBirdsCDAbhi.Birds.Bird;
import io.github.AngryBirdsCDAbhi.DynamicBodies;
import io.github.AngryBirdsCDAbhi.Piggies.Piggies;

import java.io.Serializable;
import java.util.ArrayList;

public class Level extends ScreenAdapter implements Serializable {
    private static final long serialVersionUID = 8L;

    private ArrayList<Bird> birds;
    private ArrayList<Piggies> piggies;
    private ArrayList<DynamicBodies> blocks;

    private int score;
    private float timeLeft;
    private boolean levelCompleted;

    // Constructor to save the current state of the level
    public Level(ArrayList<Bird> b,ArrayList<Piggies>p,ArrayList<DynamicBodies>bodies, int score, float timeLeft, boolean levelCompleted) {
        this.birds = b;
        this.piggies = p;
        this.blocks = bodies;
        this.score = score;
        this.timeLeft = timeLeft;
        this.levelCompleted = levelCompleted;
    }

    public ArrayList<Bird> getBirds() {
        return birds;
    }
    public ArrayList<Piggies> getPiggies() {
        return piggies;
    }
    public ArrayList<DynamicBodies> getBlocks() {
        return blocks;
    }

    public void setBirds(ArrayList<Bird> birds) {
        this.birds = birds;
    }

    public void setPiggies(ArrayList<Piggies> piggies) {
        this.piggies = piggies;
    }

    public void setBlocks(ArrayList<DynamicBodies> blocks) {
        this.blocks = blocks;
    }

    public int getScore() {
        return score;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }
}

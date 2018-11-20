package javaproject.entities.creatures;

import javaproject.assets.Asset;
import javaproject.main.Game;

import java.awt.*;

public class Crawler extends Creature{

    public Player target;
    private float damage;
    private double angle;
    private double diffrX,diffrY;

    public Crawler(Game game, float x, float y, Player player) {
        super(x, y, 64, 64);
        this.setHealth(30);
        this.setMovementSpeed(1f);
        target = player;
    }

    @Override
    public void tick() {
        chaseTarget();
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Asset.crawler,(int)x,(int)y,width,height,null);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void chaseTarget(){
        xMove = 0;
        yMove = 0;
        //this is the algorithm of chasing the players

        //first we have to find the distance between the players and the enemies
        diffrX = this.x-target.getX();
        diffrY = this.y-target.getY();

        //calculate the angle between the player and enemies
        angle = Math.atan2(diffrY,diffrX);

        //chase the player using the cos and sin
        //have to put - on Y because Y behave differently (increase while going down)
        yMove += -(movementSpeed*(float)Math.sin(angle));
        xMove += -(movementSpeed*(float)Math.cos(angle));
    }
}

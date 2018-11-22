package javaproject.states;

import javaproject.assets.Asset;
import javaproject.entities.creatures.*;
import javaproject.entities.object.Bullet;
import javaproject.entities.object.Wall;
import javaproject.inputs.BulletController;
import javaproject.main.Game;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


public class GameState extends State {

    public Player player;
    private BulletController bc;


    public int enemyCount = 5;
    //timer
    Timer timer;


    //create an array list that stores enemies,bullet
    private LinkedList<Creature> enemies = new LinkedList<Creature>();
    public ArrayList<Wall> walls = new ArrayList<>();
    public LinkedList<Bullet> bullets = new LinkedList<>();



    public GameState(Game game) {
        super(game);
        player = new Player(game, 800, 450);
        bc = new BulletController(game, player);
               //enemies

            //a holder for spawnSpotx
            /*int spawnX = spawnSpotX();
            if(enemyPicker == 1){
                //spawn walker
                enemies.add(new Walker(game,spawnX,spawnSpotY(spawnX),player));
            }

            else if(enemyPicker == 2){
                //spawn crawler
                enemies.add(new Crawler(game,spawnX,spawnSpotY(spawnX),player));
            }

            else{
                //spawn tanker
                enemies.add(new Tank(game,spawnX,spawnSpotY(spawnX),player));
            }*/

            //timer
            //This is going to spawn enemies every 2 second
            timer = new Timer();
            //for(int i = 0; i < enemyCount ;i++){
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        enemySpawn();
                    }
                }, 0,2000);
            //}


        //4 walls
        walls.add(new Wall(0, 0, 300, 100));//top left block
        walls.add(new Wall(0, 800, 300, 100));//bot left block
        walls.add(new Wall(1300, 0, 300, 100)); //top right block
        walls.add(new Wall(1300, 800, 300, 100));//bot right block*/

    }


    @Override
    public void tick() {
        //player tick
        player.tick();

        //enemy tick
        for (Creature enemy : enemies) {
            enemy.tick();
        }

        //bulletcontroller tick
        bc.tick();

        //walls tick
        for (Wall wall : walls) {
            wall.tick();
        }

        bulletCheckCollisions(bc.getBullets(),enemies);
        playerCheckCollisionsWithWall();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Asset.floor, 0, 0, 1600, 900, null);//floor*/
        player.render(g);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }

        bc.render(g);

        //walls render
        for (Wall wall : walls) {
            wall.render(g);
        }
        g.drawString("Player health:" + player.getHealth(), 1300, 50);

    }

    private void enemySpawn() {
            //pick random enemy, 3 is the maximum number and 1 is the minimum
            int enemyPicker = (int) (Math.random() * 3) + 1;

            //randomize 1 -3 to pick enemy spawn position
            int x =(int)(Math.random()*3)+1;

            //a holder for enemies spawn X position and Y position
            int spawnX,spawnY;
            switch(x){
                case 1:
                    spawnX =800;
                    int y = (int)(Math.random()*2) + 1;
                    switch(y){
                        case 1:
                            spawnY = 0;
                            break;
                        default:
                            spawnY = 900;
                            break;
                    }
                    break;

                case 2:
                    spawnX = 0;
                    spawnY = 450;
                    break;
                default:
                    spawnX = 1600;
                    spawnY = 450;
                    break;
            }

            if (enemyPicker == 1) {
                //spawn walker
                enemies.add(new Walker(game, spawnX,spawnY, player));
            } else if (enemyPicker == 2) {
                //spawn crawler
                enemies.add(new Crawler(game, spawnX,spawnY, player));
            } else {
                //spawn tanker
                enemies.add(new Tank(game, spawnX,spawnY, player));
            }
    }

     /*private int spawnSpotX(){
        //this return 1 - 3 randomize number
        int x = (int)(Math.random()*3)+1;
        int result = 0;

        if(x == 1){
            //return spawning spot
            result = 800;
        }

        else if(x == 2){
           result = 0;
        }

        else{
            result = 1600;
        }
        return result;

    }*/

    /*private int spawnSpotY(int spawnSpotX) {

        int result = 0;
        if (spawnSpotX == 800){
            //this return only two randomize number
            int y = (int)(Math.random()*2) +1;
            if( y == 1){
                result = 0;
            }
            else if (y == 2)
                result = 900;
        }
        else
            result = 450;

        return result;
    }*/

    //check bullet and enemy collision using rectangle intersect method
    private void bulletCheckCollisions(LinkedList<Bullet> bullets , LinkedList<Creature> enemies) {
        for (Bullet bullet : bullets) {
            Rectangle bulletRect = new Rectangle((int)bullet.getX(),(int)bullet.getY(),bullet.getWidth(),bullet.getHeight());
            Rectangle bulletBound = bulletRect.getBounds();

            for(Creature enemy : enemies){
                Rectangle enemyRect = new Rectangle((int)enemy.getX(),(int)enemy.getY(),enemy.getWidth(),enemy.getHeight());
                Rectangle enemyBound = enemyRect.getBounds();

                if(bulletBound.intersects(enemyBound)){
                    //Bullet hit enemy
                    bullet.setHitSomething(true);
                    enemy.hitByBullet();

                    //if enemy died
                    if(enemy.isAlive() == false){
                        enemies.remove(enemy);
                    }
                }
            }
        }
    }

    private void playerCheckCollisionsWithWall(){
        Rectangle playerRect = new Rectangle((int)player.getX(),(int)player.getY(),player.getWidth(),player.getHeight());
        Rectangle playerBound = playerRect.getBounds();

        for(Wall wall : walls){
            Rectangle wallRect = new Rectangle((int)wall.getX(),(int)wall.getY(),wall.getWidth(),wall.getHeight());
            Rectangle wallBound = wallRect.getBounds();

            if(playerBound.intersects(wallBound)){
                //player hit the wall
                System.out.println("Player hit the wall");
                break;
            }
            player.setMovementSpeed(2f);
        }
        player.setMovementSpeed(2f);
    }

    private void playerCheckCollisionWithEnemy(){
        
    }


}

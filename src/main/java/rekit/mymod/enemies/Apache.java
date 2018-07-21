package rekit.mymod.enemies;

import rekit.config.GameConf;
import rekit.core.GameGrid;
import rekit.logic.ILevelScene;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.GameElementFactory;
import rekit.logic.gameelements.entities.Player;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Frame;
import rekit.primitives.geometry.Vec;
import rekit.util.ReflectUtils;

@ReflectUtils.LoadMe
public class Apache extends Enemy {

    private static final Vec APACHE_SIZE_OUTER = new Vec(0.7, 0.7);
    private static final int POINTS = 80;
    private static final int WAIT_DURATION_MILLIS = 2500;
    private GameElement gameElement;
    private boolean canSpawn;
    private long sumTime;
    private final Thread waiter = new Thread(new Waiter());

    public Apache() {
        super();
    }

    public Apache(Vec startPos, GameElement gameElement) {
        super(startPos, new Vec(), APACHE_SIZE_OUTER);
        this.gameElement = gameElement;
        this.canSpawn = true;
        waiter.start();
    }

    /**
     * Drops a new enemy below the apache
     */
    private void drop() {
        Vec pos = this.getPos();
        pos = pos.addY(1);
        this.getScene().addGameElement(gameElement.create(pos));
    }

    @Override
    public void internalRender(GameGrid f) {
        int secs = (int) (this.sumTime / 100f);

        if (secs % 4 == 0) {
            f.drawImage(this.getPos(), this.getSize(), "roboindustries/flydrone_01.png", true, true, false, false);
        }
        if (secs % 4 == 1) {
            f.drawImage(this.getPos(), this.getSize(), "roboindustries/flydrone_02.png", true, true, false, false);
        }
        if (secs % 4 == 2) {
            f.drawImage(this.getPos(), this.getSize(), "roboindustries/flydrone_03.png", true, true, false, false);
        }
        if (secs % 4 == 3) {
            f.drawImage(this.getPos(), this.getSize(), "roboindustries/flydrone_04.png", true, true, false, false);
        }
    }

    @Override
    protected void innerLogicLoop() {
        // Do usual entity logic (applying velocity, ...)
        ILevelScene scene = this.getScene();
        Player player = scene.getPlayer();
        float speed = 5;
        float nx = -(this.getPos().x - player.getPos().x);
        float ny = 0;
        Vec vec = new Vec(nx, ny);

        vec = vec.scalar(speed / vec.norm());
        
        this.setVel(vec);
        if (canSpawn) {
            drop();
            canSpawn = false;
        }
        this.sumTime += this.deltaTime;
        super.innerLogicLoop();
    }

    @Override
    public void reactToCollision(GameElement element, Direction dir) {
        // Only continue if the element is hostile to the enemy
        // (meaning element is Player)
        if (!this.getTeam().isHostile(element.getTeam())) {
            return;
        }

        // If hit from above:
        if (dir == Direction.UP) {
            this.getScene().getPlayer().addPoints(POINTS);
            element.killBoost();
            this.addDamage(1);
        } else {
            // Touched dangerous side
            // Give player damage
            element.addDamage(1);
            this.destroy();
        }
    }

    @Override
    public void collidedWithSolid(Frame collision, Direction dir) {
        // standard behavior, that prevents clipping into other blocks
        super.collidedWithSolid(collision, dir);
        float x = getVel().x;
        if (dir == Direction.LEFT) {
        	this.setVel(getVel().setX(x < 0 ? 0 : x));
        }
        if (dir == Direction.RIGHT) {
        	this.setVel(getVel().setX(x > 0 ? 0 : x));
        }
        // upward push
        //this.setVel(Pizza.JUMP_VELOCITY);
    }

    @Override
    public Apache create(Vec startPos, String... options) {
    	if (options.length >= 1) {
    		return new Apache(startPos, GameElementFactory.getPrototype(options[0]));
    	}
    	GameConf.GAME_LOGGER.error("Error in Apache: Enemy to spawn not specified");
    	return null;
    }

    private class Waiter implements Runnable {

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(WAIT_DURATION_MILLIS);
                } catch (InterruptedException e) {
                }
                canSpawn = true;
            }
        }
    }
}

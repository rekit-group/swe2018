package rekit.mymod.enemies.missilelauncher;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.particles.ParticleSpawner;
import rekit.logic.gameelements.particles.ParticleSpawnerOption;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Frame;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;

public class Missile extends Enemy {

	private enum Dir {
		N, NE, E, SE, S, SW, W, NW
	};

    private float movementSpeed = 5;
    private static final Vec SIZE = new Vec(1,1);
    private static final RGBAColor COLOR = new RGBAColor(226,150,68,255);
    private static final String MISSILE_0 = "missilelauncher/missile_0.png";
    private static final String MISSILE_45 = "missilelauncher/missile_45.png";
    private static final String MISSILE_90 = "missilelauncher/missile_90.png";
    private ParticleSpawner ps;
    private Dir dir;
    private long sumTime;
    private boolean hasTargeted;
    private Vec moveDirection;

    public Missile() {
        super();
    }



    public Missile(Vec startPos){
        super(startPos,new Vec(0,0),SIZE);
        ps=new ParticleSpawner();
        ps = new ParticleSpawner();
        ps.angle = new ParticleSpawnerOption(0.0F,35.0F,0.0F,35.0F);
        ps.colorR = new ParticleSpawnerOption(200.0F,250.0F,-100.0F,5.0F);
        ps.colorG = new ParticleSpawnerOption(50.0F,150.0F,-100.0F,5.0F);
        ps.colorB = new ParticleSpawnerOption(0.0F,50.0F,-100.0F,5.0F);
        ps.colorA = new ParticleSpawnerOption(230.0F,250.0F,-120.0F,-200.0F);
    }


    @Override
    public Enemy create(Vec startPos, String... options) {
        return new Missile(startPos);
    }

    @Override
    protected void innerLogicLoop() {
        
        this.sumTime += this.deltaTime;
        
        if (this.sumTime > 1000) {
        	this.moveDirection = getDirection();
        	this.sumTime = 0;
        	this.hasTargeted = true;
        }
        if (!this.hasTargeted) {
        	this.moveDirection = new Vec(0, -movementSpeed);
        	this.dir = Dir.N;
        }
        this.setVel(this.moveDirection);
        
        spawnParticles();
        
        // Do usual entity logic (applying velocity, ...)
        super.innerLogicLoop();
    }

    @Override
    public void internalRender(GameGrid f) {
    	switch (this.dir) {
		case N:
			f.drawImage(getPos(), getSize(), MISSILE_0, true, false);
			break;
		case NW:
			f.drawImage(getPos(), getSize(), MISSILE_45, true, false);
			break;
		case W:
			f.drawImage(getPos(), getSize(), MISSILE_90, true, false);
			break;
		case S:
			f.drawImage(getPos(), getSize(), MISSILE_0, true, false, true, false);
			break;
		case SW:
			f.drawImage(getPos(), getSize(), MISSILE_45, true, false, true, false);
			break;
		case E:
			f.drawImage(getPos(), getSize(), MISSILE_90, true, false, false, true);
			break;
		case NE:
			f.drawImage(getPos(), getSize(), MISSILE_45, true, false, false, true);
			break;
		case SE:
			f.drawImage(getPos(), getSize(), MISSILE_45, true, false, true, true);
			break;
		default:
			break;
		}
    }



    private Vec getDirection(){
        //calculate position relative to player and normalize it
        Vec playerPos = this.getScene().getPlayer().getPos();
        float xMovement = playerPos.x-this.getPos().x;
        float yMovement = playerPos.y-this.getPos().y;
        float vectorLength=(float)Math.sqrt(xMovement*xMovement+yMovement*yMovement);
        xMovement=Math.round(xMovement/vectorLength)*movementSpeed;   //normalize and multiply with speed
        yMovement=Math.round(yMovement/vectorLength)*movementSpeed;
        this.dir = movement2Dir((int)xMovement, (int)yMovement);
        return new Vec(xMovement,yMovement);
    }

    @Override
    public void collidedWithSolid(Frame collision, Direction dir) {
        // standard behavior, that prevents clipping into other blocks
        super.collidedWithSolid(collision, dir);
        
        this.destroy();
    }

    @Override
    public void reactToCollision(GameElement element, Direction dir) {
    	if (element instanceof MissileLauncher) {
        	return;
        }

        if (!this.getTeam().isHostile(element.getTeam())) {
        	element.addDamage(1);
            element.destroy();
            this.destroy();
        } else {
            // Give player damage
            element.addDamage(1);
            // Kill the enemy itself
            this.destroy();
        }
    }


    private void spawnParticles(){
    	Vec v = getVel();
    	float vectorLength=(float)Math.sqrt(v.x * v.x + v.y * v.y);
        v.setX(v.x/vectorLength);
        v.setY(v.y/vectorLength);
        ps.spawn(getScene(),this.getPos().sub(v.multiply(new Vec(0.08, 0.08))));
    }
    
    private Dir movement2Dir(int dx, int dy) {
    	if (dx > 0) {
    		if (dy > 0) {
    			return Dir.SE;
    		} else if (dy == 0){
    			return Dir.E;
    		} else {
    			return Dir.NE;
    		}
    	} else if (dx == 0) {
    		if (dy > 0) {
    			return Dir.S;
    		} else {
    			return Dir.N;
    		}
    	} else {
    		if (dy > 0) {
    			return Dir.SW;
    		} else if (dy == 0){
    			return Dir.W;
    		} else {
    			return Dir.NW;
    		}
    	}
    }


}

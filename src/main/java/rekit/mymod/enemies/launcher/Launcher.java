package rekit.mymod.enemies.launcher;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.entities.Player;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Polygon;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

@LoadMe
public class Launcher extends Enemy {

  private static final int POINTS = 50;

  private long sumTime;
  private Vec center;

  private int lastShotSecond;
  private float barrelAngle;
  private Polygon baseBarrel, barrel;
  private Vec shootDir;
  protected RGBAColor barrelColor;

  protected int shootEverySecond;

  /**
   * Prototype constructor, used for dynamic instantiation.
   */
  @SuppressWarnings("unused")
  private Launcher() {
    super();
  }

  public Launcher(Vec startPos) {
    this.setPos(startPos);
    this.setSize(new Vec(1, 1));
    this.center = getPos().add(getSize().scalar(0.5f));
    
    Vec[] points = {new Vec(-.25, 0), new Vec(-.25f, .75f), new Vec(.25f, .75f), new Vec(.25f,0)};
    this.baseBarrel = new Polygon(this.getPos(), points);
    this.barrel = new Polygon(this.getPos(), points);
    this.barrelColor = new RGBAColor(200, 200, 200);

    // standard value
    shootEverySecond = 7;
  }

  @Override
  public void internalRender(GameGrid f) {
    this.sumTime += this.deltaTime;
    int secs = (int) (this.sumTime / 1000f);

    f.drawImage(this.getPos(), this.getSize(), "roboindustries/cannon_back.png", true, true, false, false);
    f.drawPolygon(this.barrel, this.barrelColor, true);
    f.drawImage(this.getPos(), this.getSize(), "roboindustries/cannon_front.png", true, true, false, false);
    f.drawImage(this.getPos(), this.getSize(), "roboindustries/cannon_front_shoot_0" + (1 + secs % 6) + ".png", true, true, false, false);
  }

  @Override
  public void innerLogicLoop() {
    this.sumTime += this.deltaTime;
    int secs = (int) (this.sumTime / 1000f);
    
    Player p = this.getScene().getPlayer();
    aim(p.getPos().add(p.getSize().scalar(0.5f)));

    if (lastShotSecond <= secs - shootEverySecond) {
      lastShotSecond = secs;
      // SHOOT!
      this.getScene().addGameElement(new Projectile(this.center, this.shootDir));
    }
  }

  private void aim(Vec aim) {
	
    this.barrelAngle = -center.getAngleTo(aim);
    this.barrel = this.baseBarrel.rotate(this.barrelAngle, this.getPos());
    
    this.shootDir = aim.sub(this.getPos());
    this.shootDir = shootDir.scalar(1 / shootDir.norm());
  }


  @Override
  public Launcher create(Vec startPos, String... options) {
    return new Launcher(startPos);
  }
  
  
  @Override
  public void reactToCollision(GameElement element, Direction dir) {
	// Only continue if the element is hostile to the enemy
	// (meaning element is Player)
	if (!this.getTeam().isHostile(element.getTeam())) {
		return;
	}

	// If hit 		
	// give the player points
	this.getScene().getPlayer().addPoints(Launcher.POINTS);
	// Let the player jump 
	element.killBoost();
	// kill the enemy
	this.addDamage(1);

	}

}


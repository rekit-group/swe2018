package rekit.mymod.enemies.launcher;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.Enemy;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Frame;
import rekit.primitives.geometry.Vec;

public class Projectile extends Enemy {

  private long sumTime;

  private Vec velocity;
  protected int speed;
  
  private static final Vec SIZE = new Vec(1, 1);

  /**
   * Prototype constructor, used for dynamic instantiation.
   */
  @SuppressWarnings("unused")
  private Projectile() {
    super();
  }

  public Projectile(Vec centerPos, Vec velocity) {
    this.setPos(centerPos.sub(SIZE.scalar(0.5f)));
    this.setSize(SIZE);
    this.speed = 3;
    this.velocity = velocity.scalar(this.speed);
  }

  @Override
  public void internalRender(GameGrid f) {
    this.sumTime += this.deltaTime;
    int i = (int) (4 * this.sumTime / 1000f);

    f.drawImage(this.getPos(), this.getSize(), "projectiles/cannonball_big_" + (1 + i % 4) + ".png", true, true, false, false);
  }
  

  @Override
  public void innerLogicLoop() {
    this.sumTime += this.deltaTime;

    this.setVel(this.velocity);
    super.innerLogicLoop();
  }

  @Override
  public Projectile create(Vec startPos, String... options) {
    return new Projectile(startPos, new Vec(0));
  }

  @Override
  public void reactToCollision(GameElement element, Direction dir) {
    // Only continue if the element is hostile to the enemy
    // (meaning element is Player)
    if (!this.getTeam().isHostile(element.getTeam())) {
      return;
    }

    // Give player damage
    element.addDamage(1);
    // Kill the enemy itself
    this.destroy();
  }

  @Override
  public void collidedWithSolid(Frame collision, Direction dir) {
    this.addDamage(1);
    this.destroy();
  }
}


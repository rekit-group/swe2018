package rekit.mymod.quiz;

import java.util.ArrayList;

import rekit.core.GameGrid;
import rekit.logic.gameelements.GameElement;
import rekit.logic.gameelements.type.DynamicInanimate;
import rekit.primitives.geometry.Direction;
import rekit.primitives.geometry.Vec;
import rekit.primitives.image.RGBAColor;
import rekit.util.ReflectUtils.LoadMe;

import rekit.mymod.MyModScene;
import rekit.mymod.enemies.Jumper;
import rekit.mymod.enemies.MonsterPizza;
import rekit.mymod.pickups.ExtraLife;
import rekit.mymod.pickups.GoodBoyCoin;

@LoadMe
public class AnswerBox extends DynamicInanimate {

	private Vec startPos;
	private ArrayList<BlockadeBox> blockadeBoxes;
	private boolean isCorrectAnswer = false;
	private float animValue = -2f;
	private boolean inJump = false;
	
	private AnswerBox() {
		super();
		blockadeBoxes = new ArrayList<BlockadeBox>();
		startPos = new Vec(0,0);
	}

	public AnswerBox(Vec startPos, ArrayList<BlockadeBox> blockadeBoxes, boolean isCorrectAnswer) {
        super(startPos, new Vec(1), new RGBAColor(0,0,0,1));
        this.startPos = startPos;
	    this.blockadeBoxes = blockadeBoxes;
	    this.isCorrectAnswer = isCorrectAnswer;
	}
	
	@Override
	public void internalRender(GameGrid f) {
		f.drawImage(this.getPos(), this.getSize(), "custom/question.png", true, true, false, false);
	}
	
	@Override
	public void logicLoop() {
		super.logicLoop();

        if(inJump){

            animValue += (deltaTime / 1000f) * 4;

			updateVelocity(animValue);

			if (getVel().y > 0) {
				//The Block is moving back down -> Explode and play particles
                explodeBlock();
			}


        }
    }
	
	@Override
	public void reactToCollision(GameElement element, Direction dir) {
		super.reactToCollision(element, dir);

		// Don't react to collisions if the block is already jumping
		if (inJump) { return; }

		if (Math.abs(dir.getAngle() - 3.14d) <  0.1f ) {

		    inJump = true;
        }
	}
	
	@Override
	public DynamicInanimate create(Vec startPos, String... options) {
		return new AnswerBox(startPos, blockadeBoxes, isCorrectAnswer);
	}
	
	@Override
	public Integer getZHint() {
		return 1;
	}

	private void updateVelocity(double animValue){

		this.setVel(new Vec(0, Math.pow(animValue, 3)));
		this.setPos(getPos().add(getVel().scalar(deltaTime / 1000f)));
	}

	private void explodeBlock() {

		// Destroy the answer block when it has been hit (but remember it's position so we can spawn particles there)
		Vec myOldPos = this.getPos();
		this.destroy();

        if (isCorrectAnswer) {
            // Right answer

            // Destroy blockade
            for (BlockadeBox box : blockadeBoxes) {
                box.destroy();
            }

            // 90% for normal coin 10% extra health
            if ((Math.random() * 10) > 1) {
                getScene().addGameElement(new GoodBoyCoin(myOldPos));
            } else {
                getScene().addGameElement(new ExtraLife(myOldPos));
            }

            // Spawn particle right answer effect
            getScene().addGameElement(new RightAnswerParticles(myOldPos));

        } else {

            // Wrong answer
        	
        	double random = Math.random() * 10;
        	// 30% for explosion 10% for monsterPizza 60% for jumper
        	if (random >= 7) {
                // Remove one of the players lives
                getScene().getPlayer().setLives(getScene().getPlayer().getLives() - 1);
                if (getScene().getPlayer().getLives() <= 0) {
                    // kill the player (for some reason that doesn't happen automatically)
                    getScene().getPlayer().addDamage(9999);
                }
                // Spawn wrong answer particles
                getScene().addGameElement(new WrongAnswerParticles(myOldPos));
                getScene().addGameElement(new WrongAnswerParticles(getScene().getPlayer().getPos()));
        	} 
        	else if (random < 7 && random >= 6) {
        		getScene().addGameElement(new MonsterPizza(new Vec(myOldPos.x -3, myOldPos.y)));
        		getScene().addGameElement(new MonsterPizza(new Vec(myOldPos.x -2, myOldPos.y)));
        		getScene().addGameElement(new MonsterPizza(new Vec(myOldPos.x -1, myOldPos.y)));
        		getScene().addGameElement(new MonsterPizza(myOldPos));
        		getScene().addGameElement(new MonsterPizza(new Vec(myOldPos.x +1, myOldPos.y)));
        		getScene().addGameElement(new MonsterPizza(new Vec(myOldPos.x +2, myOldPos.y)));
        		getScene().addGameElement(new MonsterPizza(new Vec(myOldPos.x +3, myOldPos.y)));
        	} 
        	
        	else if (random < 6 && random >= 5) {
        		getScene().addGameElement(new Jumper(new Vec(myOldPos.x -2, myOldPos.y)));
        		getScene().addGameElement(new Jumper(new Vec(myOldPos.x -1, myOldPos.y)));
        		getScene().addGameElement(new Jumper(myOldPos));
        		getScene().addGameElement(new Jumper(new Vec(myOldPos.x +1, myOldPos.y)));
        		getScene().addGameElement(new Jumper(new Vec(myOldPos.x +2, myOldPos.y)));
        	}
        	else {
        		getScene().addGameElement(new Jumper(myOldPos));
        	}
        }
	}
}

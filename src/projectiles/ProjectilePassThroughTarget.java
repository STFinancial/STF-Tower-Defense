package projectiles;

import java.util.HashSet;

import creeps.Creep;
import towers.Tower;
import utilities.GameConstants;
import utilities.TrigHelper;

public class ProjectilePassThroughTarget extends ProjectileBasic {
	private HashSet<Creep> passedThrough;
	private float passThroughRadius;
	
	public ProjectilePassThroughTarget(Tower parent, float passThroughRadius) {
		super(parent);
		passedThrough = new HashSet<Creep>();
		passThroughRadius = .2f;
	}
	
	@Override
	public Projectile clone() {
		ProjectilePassThroughTarget p = new ProjectilePassThroughTarget(parent, passThroughRadius);
		p.targetCreep = targetCreep;
		p.resistPenFlat = new float[GameConstants.NUM_DAMAGE_TYPES];
		p.resistPenPercent = new float[GameConstants.NUM_DAMAGE_TYPES];
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			p.resistPenFlat[i] = resistPenFlat[i];
			p.resistPenPercent[i] = resistPenPercent[i];
		}
		p.passThroughRadius = passThroughRadius;
		return p;
	}

	@Override
	public int update() {
		if (targetCreep != null) {
			if (targetCreep.isDead()) {
				dud = true;
				return -1;
			} else {
				targetAngle = TrigHelper.angleBetween(x, y, targetCreep.hitBox.x, targetCreep.hitBox.y);
			}
		} else {
			dud = true;
			return -1;
		}
		x -= Math.cos(targetAngle) * currentSpeed;
		y -= Math.sin(targetAngle) * currentSpeed;
		hitBox.x = x;
		hitBox.y = y;
		//TODO: Do we need a more sophisticated method of checking whether we "passed through" something?
		//TODO: We need to specify if the projectile can hit flying or not and correct these methods accordingly
		for (Creep c: guider.getCreepInRange(this, passThroughRange)) {
			if (!passedThrough.contains(c)) {
				c.addAllEffects(creepEffects);
				passedThrough.add(c);
			}
		}
		return 0;
	}

}

package projectiles;

import java.util.HashSet;

import creeps.Creep;
import towers.Tower;
import utilities.GameConstants;
import utilities.TrigHelper;

public class ProjectilePassThrough extends ProjectileBasic {
	private HashSet<Creep> passedThrough;
	private float passThroughRange;
	protected ProjectilePassThrough() {
		
	}
	
	public ProjectilePassThrough(Tower parent) {
		super(parent);
		passedThrough = new HashSet<Creep>();
		passThroughRange = .2f;
	}
	
	@Override
	public Projectile clone() {
		ProjectilePassThrough p = new ProjectilePassThrough();
		cloneStats(p);
		p.targetCreep = targetCreep;
		//this is only safe because we clone immediately before we fire
		p.resistPenFlat = new float[GameConstants.NUM_DAMAGE_TYPES];
		p.resistPenPercent = new float[GameConstants.NUM_DAMAGE_TYPES];
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			p.resistPenFlat[i] = resistPenFlat[i];
			p.resistPenPercent[i] = resistPenPercent[i];
		}
		p.passThroughRange = passThroughRange;
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
		for (Creep c: guider.getCreepInRange(this, passThroughRange)) {
			if (!passedThrough.contains(c)) {
				c.addAllEffects(creepEffects);
				passedThrough.add(c);
			}
		}
		return 0;
	}

}

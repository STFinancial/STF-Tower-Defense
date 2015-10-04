package projectiles;

import java.util.HashSet;

import creeps.Creep;
import towers.Tower;

//Hits all targets on the way to its target
public final class ProjectilePassThroughTarget extends ProjectileBasic {
	private HashSet<Creep> passedThrough;
	private float passThroughRadius;
	private boolean doesSplash;
	private int pulseTiming; //Does its pass through effects every "pulseTiming" ticks
	private int counter;
	
	private ProjectilePassThroughTarget(Tower parent, Projectile mold, float passThroughRadius, boolean doesSplash, int pulseTiming) {
		super(parent, mold);
		this.passedThrough = new HashSet<Creep>();
		this.passThroughRadius = passThroughRadius;
		this.doesSplash = doesSplash;
		this.pulseTiming = pulseTiming;
		this.counter = 0;
	}
	
	public ProjectilePassThroughTarget(Tower parent, float passThroughRadius, boolean doesSplash, int pulseTiming) {
		super(parent);
		this.passedThrough = new HashSet<Creep>();
		this.passThroughRadius = passThroughRadius;
		this.doesSplash = doesSplash;
		this.pulseTiming = pulseTiming;
		this.counter = 0;
	}
	
	@Override
	public Projectile clone() {
		return new ProjectilePassThroughTarget(parent, this, passThroughRadius, doesSplash, pulseTiming);
	}

	@Override
	public int update() {
		if (targetCreep != null) {
			if (targetCreep.isDead()) {
				dud = true;
				return -1;
			} else {
				updateAngle();
			}
		} else {
			dud = true;
			return -1;
		}
		x -= angleCos * currentSpeed;
		y -= angleSin * currentSpeed;
		hitBox.x = x;
		hitBox.y = y;
		//TODO: Do we need a more sophisticated method of checking whether we "passed through" something? (Depends how slow it is)
		if (++counter % pulseTiming == 0) { //Choosing not to pulse at first firing
			for (Creep c: guider.getCreepInRange(this, passThroughRadius, parent.hitsAir)) {
				//Have to avoid hitting the target creep twice, so we have to check for that, hopefully it won't add too much time to this call.
				if (!passedThrough.contains(c) || !c.equals(targetCreep)) {
					c.addAllEffects(creepEffects);
					passedThrough.add(c);
					if (doesSplash) {
						c.addAllEffects(splashEffects);
					}
				}
			}
		}
		return 0;
	}

}

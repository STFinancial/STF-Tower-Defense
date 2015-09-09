package towers;

import projectiles.ProjectileArea;
import projectiles.TargetsArea;
import utilities.Circle;
import utilities.GameConstants;
import levels.Level;
import maps.Tile;

public class TowerEarthWater extends Tower implements TargetsArea {
	private Circle placeToTarget;
	float areaRadius;
	
	public TowerEarthWater(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.EARTH_WATER, towerID);
		//TODO this is kind of a lazy piece of code, figure out a better default position for the target area
		areaRadius = 2f;
		setTargetArea(x, y);
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileArea(this, areaRadius);
		((ProjectileArea) baseProjectile).setTargetArea(placeToTarget.x, placeToTarget.y);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][2]) {
			((ProjectileArea) baseProjectile).doesSplash = true;
		}
	}

	@Override
	public boolean setTargetArea(float x, float y) {
		Circle t = new Circle(x, y, 0);
		if (t.intersects(targetArea)) {
			placeToTarget = new Circle(x, y, areaRadius);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int update() {
		currentAttackCoolDown--;
		if (currentAttackCoolDown < 1) {
			level.addProjectile(fireProjectile());
			attackCarryOver += 1 - currentAttackCoolDown;
			currentAttackCoolDown = attackCoolDown;
			if (attackCarryOver > 1) {
				attackCarryOver -= 1;
				currentAttackCoolDown--;
			}
			return 1;
		}
		return 0;
	}
	
	@Override
	protected void siphon(Tower from) {
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			this.damageArray[i] += (int) (from.damageArray[i] * this.siphonBonus);
			this.slowArray[i] += from.slowArray[i] * this.siphonBonus;
			this.slowDurationArray[i] += (int) ((from.slowDurationArray[i] * this.siphonBonus) / 2);
		}
		//TODO find a good equation for range siphoning
		this.range = Math.max(this.range, (this.range + from.range) / 2);//should I really max this?
		this.targetArea.radius = this.range;
		
		//TODO find a good equation for fire rate siphoning
		this.attackCoolDown = (int) ((from.attackCoolDown + this.attackCoolDown) / 2);
		boolean[][] progress = upgradeTracks[siphoningFrom.baseAttributeList.downgradeType.ordinal()];
		if (progress[0][3]) {
			this.damageSplash += from.damageSplash * .75f;
			this.effectSplash += from.effectSplash * .75f;
			this.splashRadius += from.splashRadius * .75f;
		} else {
			this.damageSplash += from.damageSplash * this.siphonBonus;
			this.effectSplash += from.effectSplash * this.siphonBonus;
			this.splashRadius += from.splashRadius * this.siphonBonus;
		}
	}

}

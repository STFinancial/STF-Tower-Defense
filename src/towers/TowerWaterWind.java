package towers;

import java.util.ArrayList;

import creeps.Creep;
import creeps.DamageType;
import levels.Level;
import maps.Tile;
import projectileeffects.Damage;
import projectileeffects.DamageOnHit;
import projectileeffects.ProjectileEffect;
import projectileeffects.Slow;
import projectileeffects.Snare;
import projectileeffects.ToughnessShred;
import projectileeffects.Wealth;
import projectiles.ProjectileBasic;
import projectiles.ProjectileRandom;
import utilities.GameConstants;

public final class TowerWaterWind extends Tower {
	float toughShredModifier;
	int toughShredDuration;
	int maxToughShredStacks;
	int toughnessWeight;
	
	int snareDuration;
	int snareWeight;
	
	int goldOnHitDuration;
	float goldOnHitModifier;
	int maxGOHStacks;
	int goldOnHitWeight;
	
	int damageOnHitDuration;
	float damageOnHitModifier;
	int maxDOHStacks;
	int damageOnHitWeight;
	
	public TowerWaterWind(Level level, Tile topLeftTile, TowerType type, int towerID) {
		super(level, topLeftTile, type, towerID);
		this.toughShredDuration = 0;
		this.toughShredModifier = 0;
		this.maxToughShredStacks = 0;
		this.toughnessWeight = 0;
		
		this.snareDuration = 0;
		this.snareWeight = 0;
		
		this.goldOnHitDuration = 0;
		this.goldOnHitModifier = 0;
		this.maxGOHStacks = 0;
		this.goldOnHitWeight = 0;
		
		this.damageOnHitDuration = 0;
		this.damageOnHitModifier = 0;
		this.maxDOHStacks = 0;
		this.damageOnHitWeight = 0;
	}

	@Override
	protected void adjustProjectileStats() {
		baseProjectile = new ProjectileRandom(this);
		ArrayList<ProjectileEffect> damages = new ArrayList<ProjectileEffect>(GameConstants.NUM_DAMAGE_TYPES);
		ArrayList<ProjectileEffect> slows = new ArrayList<ProjectileEffect>(GameConstants.NUM_DAMAGE_TYPES);
		for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
			damages.add(new Damage(damageArray[i], DamageType.values()[i], baseProjectile));
			slows.add(new Slow(slowDurationArray[i], slowArray[i], DamageType.values()[i], baseProjectile));
		}
		if (toughShredDuration != 0) {
			ArrayList<ProjectileEffect> toughShred = new ArrayList<ProjectileEffect>(1);
			ToughnessShred t = new ToughnessShred(toughShredDuration, toughShredModifier, DamageType.WIND, baseProjectile, false);
			t.setMaxStacks(maxToughShredStacks);
			toughShred.add(t);
			((ProjectileRandom) baseProjectile).addEffect(toughShred, toughnessWeight);
		}
		if (snareDuration != 0) {
			ArrayList<ProjectileEffect> snare = new ArrayList<ProjectileEffect>(1);
			Snare s = new Snare(snareDuration, DamageType.WIND, baseProjectile);
			snare.add(s);
			((ProjectileRandom) baseProjectile).addEffect(snare, snareWeight);
		}
		if (goldOnHitDuration != 0) {
			ArrayList<ProjectileEffect> GOH = new ArrayList<ProjectileEffect>(1);
			Wealth w = new Wealth(goldOnHitDuration, goldOnHitModifier, DamageType.WIND, baseProjectile, true, true);
			w.setMaxStacks(maxGOHStacks);
			GOH.add(w);
			((ProjectileRandom) baseProjectile).addEffect(GOH, goldOnHitWeight);
			
		}
		if (damageOnHitDuration != 0) {
			int sum = 0;
			for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
				sum += damageArray[i];
			}
			ArrayList<ProjectileEffect> DOH = new ArrayList<ProjectileEffect>(1);
			DamageOnHit d = new DamageOnHit(damageOnHitDuration, damageOnHitModifier * sum, DamageType.PHYSICAL, baseProjectile);
			d.setMaxStacks(maxDOHStacks);
			DOH.add(d);
			((ProjectileRandom) baseProjectile).addEffect(DOH, damageOnHitWeight);
		}
	}

	@Override
	public int update() {
		currentAttackCooldown--;
		if (currentAttackCooldown < 1) {
			Creep targetCreep = guider.findTargetCreep(this, hitsAir);
			if (targetCreep != null) {
				((ProjectileBasic) baseProjectile).setTargetCreep(targetCreep);
				level.addProjectile(fireProjectile());
				attackCarryOver += 1 - currentAttackCooldown;
				currentAttackCooldown = attackCooldown;
				if (attackCarryOver > 1) {
					attackCarryOver -= 1;
					currentAttackCooldown--;
				}
			}
			return 1;
		}
		return 0;
	}
}

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
	int toughnessWeight; //TODO: Should quality modify the weights? Are some more valuable than others?
	private float qShredModifier;
	private float qShredDuration;
	private float qShredStacks;
	
	int snareDuration;
	int snareWeight;
	private float qSnareDuration;
	
	int goldOnHitDuration;
	float goldOnHitModifier;
	int maxGOHStacks;
	int goldOnHitWeight;
	private float qGoldDuration;
	private float qGoldModifier;
	private float qGoldStacks;
	
	int damageOnHitDuration;
	float damageOnHitModifier;
	int maxDOHStacks;
	int damageOnHitWeight;
	private float qDamageDuration;
	private float qDamageModifier;
	private float qDamageStacks;
	
	public TowerWaterWind(Level level, Tile topLeftTile, int towerID) {
		super(level, topLeftTile, TowerType.WATER_WIND, towerID);
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

	@Override
	protected void adjustClassSpecificBaseStats() {
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
		
		this.qDamage = 0.05f;
		this.qSlow = 0.10f;
		this.qSlowDuration = 0.10f;
		this.qCooldown = 0.10f;
		this.qDamageSplash = 0.01f;
		this.qEffectSplash = 0.01f;
		this.qRadiusSplash = 0.02f;
		this.qRange = 0f;
		
		this.qShredModifier = 0.02f;
		this.qShredDuration = 1.5f;
		this.qShredStacks = 0.2f;
		this.qSnareDuration = 0.4f;
		this.qGoldDuration = 1.5f;
		this.qGoldModifier = 0.1f;
		this.qGoldStacks = 0.2f;
		this.qDamageDuration = 1f;
		this.qDamageModifier = 0.005f;
		this.qDamageStacks = 0.2f; //TODO: I wonder if we could make things stack two at a time?
	}

	@Override
	protected void adjustClassSpecificQuality() {
		toughShredModifier += qShredModifier * qLevel;
		toughShredDuration += (int) (qShredDuration * qLevel);
		maxToughShredStacks += (int) (qShredStacks * qLevel);
		snareDuration += (int) (qSnareDuration * qLevel);
		goldOnHitDuration += (int) (qGoldDuration * qLevel);
		goldOnHitModifier += qGoldModifier * qLevel;
		maxGOHStacks += (int) (qGoldStacks * qLevel);
		damageOnHitDuration += (int) (qDamageDuration * qLevel);
		damageOnHitModifier += (int) (qDamageModifier * qLevel);
		maxDOHStacks += (int) (qDamageStacks * qLevel);
	}
}

package projectiles;

import java.util.Iterator;
import java.util.LinkedList;

import creeps.DamageType;
import game.Game;
import game.GameEventType;
import projectileeffects.ProjectileEffect;
import towers.Tower;

public final class ProjectileManager {
	private static final ProjectileManager INSTANCE = new ProjectileManager();
	private LinkedList<Projectile> projectiles;
	private Game game;
	
	private ProjectileManager() { 
		projectiles = new LinkedList<Projectile>();
	}
	
	public static ProjectileManager getInstance() {
		return ProjectileManager.INSTANCE;
	}
	
	public void initialize(Game game) {
		this.projectiles = new LinkedList<Projectile>();
		this.game = game;
	}
	
	public void startRound(int roundNum) {
		projectiles.clear();
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
		game.newEvent(GameEventType.PROJECTILE_FIRED, p);
	}
	
	public void addProjectileEffect(boolean isSplash, Projectile p, ProjectileEffect effect) {
		if (isSplash) {
			p.addSpecificSplashEffect(effect);
		} else {
			p.addSpecificCreepEffect(effect);
		}
	}
	
	public void clearAllBasicEffects(Projectile p) {
		p.clearAllBasicEffects();
	}
	
	public void setResistPenPercent(Projectile p, DamageType type, float amount) { p.setResistPenPercent(type, amount); }
	public void setIgnoresShield(Projectile p, boolean ignoresShield) { p.setIgnoresShield(ignoresShield); }
	public void setShieldDrainModifier(Projectile p, float amount) { p.setShieldDrainModifier(amount); }
	public void setToughPenPercent(Projectile p, float amount) { p.setToughPenPercent(amount); }
	
	public Tower getParent(Projectile p) { return p.getParent(); }
	public float getResistPen(Projectile p, DamageType type, boolean isFlat) { return p.getResistPen(type, isFlat); }
	public float getShieldDrainModifier(Projectile p) { return p.getShieldDrainModifier(); }
	public float getToughPen(Projectile p, boolean isFlat) { return p.getToughPen(isFlat); }
	public boolean hitsAir(Projectile p) { return p.hitsAir(); }
	public boolean ignoresShield(Projectile p) { return p.ignoresShield(); }
	

	
	
	public void updateProjectiles() {
		Iterator<Projectile> i = projectiles.iterator();
		Projectile p;
		while (i.hasNext()) {
			p = i.next();
			p.update();
			//TODO: This doesn't account for duds, so we will be creating a detonation event even on a dud event
			if (p.isDone()) {
				p.detonate();
				i.remove();
				game.newEvent(GameEventType.PROJECTILE_DETONATED, p);
			}
		}
	}

	public float getX(Projectile projectile) {
		return projectile.x;
	}

	public float getY(Projectile projectile) {
		return projectile.y;
	}
}

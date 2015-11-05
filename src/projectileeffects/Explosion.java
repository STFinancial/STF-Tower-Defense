package projectileeffects;

import java.util.ArrayList;

import creeps.Creep;
import creeps.DamageType;
import projectiles.Projectile;
import projectiles.ProjectileGuider;
import utilities.Circle;

public final class Explosion extends ProjectileEffect {
	private float explosionRadius;
	private ArrayList<ProjectileEffect> explosionEffects;
	
	public Explosion(DamageType damageType, Projectile parent, float explosionRadius, ArrayList<ProjectileEffect> explosionEffects) {
		super(0, 0, 0, damageType, parent);
		this.explosionRadius = explosionRadius;
		this.explosionEffects = explosionEffects;
	}

	@Override
	public ProjectileEffect clone() {
		return new Explosion(damageType, parent, explosionRadius, explosionEffects);
	}

	@Override
	public void onApply() {
		applyEffect();
	}

	@Override
	protected void applyEffect() {
		applyEffect();
		for (Creep c: ProjectileGuider.getInstance().getCreepInRange(new Circle(creep.getX(), creep.getY(), explosionRadius), parent.hitsAir())) {
			c.addAllEffects(explosionEffects);
		}
	}

	@Override
	public void onExpire() {
		return;
	}

}

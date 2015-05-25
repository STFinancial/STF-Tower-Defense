package towers;

import creeps.ElementType;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;
import levels.Level;
import maps.Tile;

public class BasicAirTower extends Tower {

	public BasicAirTower(Level level, Tile topLeft){
		super(level, topLeft, 2, 2, 15, true, 3f); //TODO make this readable
		this.type = TowerType.AIR;
	}
	
	@Override
	Projectile fireProjectile() {
		Projectile p = new Projectile(this);
		ProjectileEffect effect = new Damage(6);
		effect.elementType = ElementType.FIRE;
		p.addEffect(effect);
		effect = new Slow(10, .05f);
		effect.elementType = ElementType.WATER;
		p.addEffect(effect);
		p.currentSpeed = p.speed = .20f;
		return p;
	}
}

package towers;

import creeps.ElementType;
import levels.Level;
import maps.Tile;
import projectiles.Damage;
import projectiles.Projectile;
import projectiles.ProjectileEffect;
import projectiles.Slow;

public class FireBoltTower extends Tower{
	
	public FireBoltTower(Level level, Tile topLeft){
		super(level, topLeft, 2, 2, 20, true, 8f); //TODO make this readable
	}
	
	@Override
	Projectile fireProjectile() {
		System.out.println("pew");
		Projectile p = new Projectile(this);
		ProjectileEffect effect = new Damage(8);
		effect.elementType = ElementType.FIRE;
		p.addEffect(effect);
		effect = new Slow(20, .5f);
		effect.elementType = ElementType.WATER;
		p.addEffect(effect);
		p.currentSpeed = p.speed = .2f;
		return p;
	}

}

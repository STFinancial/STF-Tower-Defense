package projectileeffects;

import creeps.DamageType;
import projectiles.Projectile;

public final class SuppressionDeathrattle extends Suppression implements Refreshable{

	//TODO: Modifier may have some meaning at a later date (minions coming out have less health, fewer of them, etc.)
	public SuppressionDeathrattle(int lifetime, float modifier, DamageType damageType, Projectile parent) {
		super(lifetime, modifier, damageType, parent);
	}

	@Override
	public ProjectileEffect clone() {
		return new SuppressionDeathrattle(lifetime, modifier, damageType, parent);
	}

	@Override
	protected void applyEffect() {
		creepManager.suppressDeathrattle(creep, damageType, modifier, lifetime);
	}

	@Override
	public void onExpire() {
		return;
	}
	
	@Override
	public void refresh() {
		counter = lifetime;
		applyEffect(); //TODO: This will need rework if we make use of the modifier field somehow
	}
//TODO: Currently commented because there can't be more than one on a creep at a time, but may need to change if we implement modifier
//	
//	@Override
//	public boolean equals(Object o) {
//		if (!(o instanceof ProjectileEffect)) {
//			return false;
//		}
//		ProjectileEffect p = (ProjectileEffect) o;
//		return  p.damageType == damageType &&
//				p.getClass() == getClass() &&
//				p.lifetime == lifetime &&
//				p.modifier == modifier &&
//				p.timing == timing; 
//		}
//	
//	//TODO: How will this interact with the other projectileEffect equals? Will it be ok?
//	@Override
//	public int hashCode() { //TODO: Optimization - Shorten this.
//		int result = 17;
//		result = 31 * result + damageType.ordinal();
//		result = 31 * result + getClass().hashCode();
//		result = 31 * result + (int) lifetime;
//		result = 31 * result + (int) modifier;
//		result = 31 * result + timing;
//		return result;
//	}
}

package creeps;

import projectileeffects.ProjectileEffect;

class CreepEffect {
	CreepEffect(ProjectileEffect p) {
		projectileEffect = p;
		lifetime = p.lifetime;
		timing = p.timing;
		counter = 0;
	}
	public ProjectileEffect projectileEffect;
	int lifetime;
	int timing;
	public int counter;
}

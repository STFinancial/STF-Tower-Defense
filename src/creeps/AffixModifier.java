package creeps;


/**
 * This class is attached to the baseProjectile of a Tower that has extra effects against a certain CreepType (e.g. double damage vs. monsters).
 * @author Tim
 */
public class AffixModifier {
	//TODO this leaves the problem of how to apply these
	//Somehow the applyEffect needs to get a hold of these
	//But currently a projectile effect cannot have a parent
	//But each projectile we fire off could have a reference to this
	//But how does the apply effect get access to that reference
	//im thinking it has to go into creep effect
	private class Multiplier {
		//currently one for damage and one for effect
		float[] mults = new float[2];
	}
	boolean ignoresArmor;
	boolean ignoresShield;
	
	private Multiplier[] multipliers;
	
	public AffixModifier() {
		multipliers = new Multiplier[CreepType.values().length];
		ignoresArmor = false;
		ignoresShield = false;
		for (int i = 0; i < CreepType.values().length; i++) {
			multipliers[i] = new Multiplier();
		}
	}
	
	//TODO
	public float getMultiplier(ProjectileEffect effect, CreepType type) {
		return 0;
	}
	
	public void setMultiplier(ProjectileEffect effect, CreepType type) {
		
	}
}

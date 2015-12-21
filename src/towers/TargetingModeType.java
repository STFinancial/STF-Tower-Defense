package towers;

import java.util.ArrayList;
import java.util.List;

import projectiles.*;

public enum TargetingModeType {
	//TODO: For NEW, when a tower switches to NEW we can begin maintaining queue of creep in range, or perhaps a map, of when we last hit that creep
	FIRST, LAST, HIGHEST_HEALTH, NEW, RANDOM, BLIND, ALWAYS;
	
	public static TargetingModeType getDefaultTargetingMode(Projectile p) {
		if (p instanceof ProjectileBasic || p instanceof ProjectileChain) {
			return FIRST;
		} else if (p instanceof ProjectileAOE || p instanceof ProjectileArea || p instanceof ProjectilePassThroughArea) {
			return BLIND;
		} else if (p instanceof ProjectileBeam) {
			return ALWAYS;
		} else {
			return BLIND;
		}
	}
	
	public static List<TargetingModeType> getTargetingModes(Projectile p) {
		ArrayList<TargetingModeType> modes = new ArrayList<TargetingModeType>();
		if (p instanceof ProjectileBasic) {
			modes.add(FIRST);
			modes.add(LAST);
			modes.add(HIGHEST_HEALTH);
			modes.add(NEW);
			modes.add(RANDOM);
		} else if (p instanceof ProjectileAOE || p instanceof ProjectileArea || p instanceof ProjectilePassThroughArea) {
			modes.add(BLIND);
			modes.add(ALWAYS);
		} else if (p instanceof ProjectileBeam) {
			modes.add(ALWAYS);
		} else {
			modes.add(BLIND);
		}
		return modes;
	}
}

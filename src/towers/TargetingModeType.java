package towers;

import java.util.ArrayList;
import java.util.List;

import projectiles.*;

public enum TargetingModeType {
	FIRST, LAST, HIGHEST_HEALTH, RANDOM, BLIND, ALWAYS;
	
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
		//TODO: Implement all targeting types then implement this method
		
		return modes;
	}
}

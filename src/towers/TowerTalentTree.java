package towers;

import creeps.DamageType;
import utilities.GameConstants;

class TowerTalentTree {
	//TODO: Changing up the design model here. Constants defined within the classes rather than game constants
	
	private static final int POINTS_PER_TIER = 3;
	private static final int NUM_REGIONS = 3;
	private static final int REGION_ZERO_CUTOFF = 1;
	private static final int REGION_ONE_CUTOFF = 2;
	
	static TowerTalentNode[] talentTree;
	
	TowerTalentTree() {
		talentTree = new TowerTalentNode[GameConstants.NUM_TOWER_TALENTS];
		initializeTree();
	}
	
	static void applyTalents(Tower t) {
		TowerTalentNode currentNode;
		for (int i = 0; i < GameConstants.NUM_TOWER_TALENTS; i++) {
			currentNode = talentTree[i];
			currentNode.operation.operate(t);
		}
	}
	
	
	private void initializeTree() {
		TowerTalentNode newNode;
		/* **Region ZERO** */
		newNode = new TowerTalentNode(0, 3, 0) {
			{
				name = "Damage";
				description = "Increases damage by 1%";
				operation = (Tower t) -> { 
					for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
						t.increaseDamage(DamageType.values()[i], 1 + (0.01f * t.getNumTalentPoints(nodeID)), false);
					} 
				};
			}
		};
		talentTree[newNode.nodeID] = newNode;
		/* **Region ONE** */
		newNode = new TowerTalentNode(1, 3, 0) {
			{
				name = "Slow";
				description = "Increases slows by 5%";
				operation = (Tower t) -> { 
					for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
						t.increaseSlow(DamageType.values()[i], 1 + (0.05f * t.getNumTalentPoints(nodeID)), false);
					} 
				};
			}
		};
		talentTree[newNode.nodeID] = newNode;
		/* **Region TWO** */
		newNode = new TowerTalentNode(2, 3, 0) {
			{
				name = "Cheap";
				description = "Reduces upgrade cost by 2%";
				operation = (Tower t) -> { 
					for (int i = 0; i < GameConstants.NUM_DAMAGE_TYPES; i++) {
						t.reduceUpgradeCost(1 + (0.02f * t.getNumTalentPoints(nodeID)));
					} 
				};
			}
		};
		talentTree[newNode.nodeID] = newNode;
	}
	
	private int getRegion(int nodeID) {
		if (nodeID < REGION_ZERO_CUTOFF) {
			return 0;
		} else if (nodeID < REGION_ONE_CUTOFF) {
			return 1;
		} else {
			return 2;
		}
	}
	
	private interface TowerTalentEffect {
		void operate(Tower t);
	}
	
	private class TowerTalentNode {
		int nodeID;
		int maxPoints;
		int tier;
		String name;
		String description;
		TowerTalentEffect operation;
		
		TowerTalentNode(int nodeID, int maxPoints, int tier) {
			this.nodeID = nodeID;
			this.maxPoints = maxPoints;
		}
	}
}

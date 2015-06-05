package utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import creeps.Creep;
import creeps.CreepType;
import creeps.DamageType;
import creeps.Wave;

public class CreepWaveGenerator {

	/*
	 * Creep waves have difficulty and length each wave is broken into "Chunks"
	 * which consist of multiple copies of the same creep The creeps stats are
	 * based off of difficulty and modified by their types
	 */

	public final static int BASE_HEALTH = 250;
	public final static int BASE_TOUGHNESS = 5;
	public final static int BASE_GOLD = 20;
	public final static int BASE_HEALTH_DAMAGE = 5;
	public final static float BASE_SPEED = .05f;

	public static float DEATH_RATTLE_PENALTY = .5f;
	public static float DEATH_RATTLE_CHUNK_SIZE_PENALTY = .5f;
	public static float DEATH_RATTLE_GOLD_PENALTY = .5f;

	public static float GIANT_HEALTH_BONUS = 2.5f;
	public static float GIANT_TOUGHNESS_BONUS = 2.5f;
	public static float GIANT_SPEED_PENALTY = .5f;
	public static float GIANT_CHUNK_SIZE_REDUCTION = .33f;

	public static float QUICK_HEALTH_PENALTY = .5f;
	public static float QUICK_TOUGHNESS_PENALTY = .25f;
	public static float QUICK_SPEED_BONUS = 1.6f;
	public static float QUICK_CHUNK_SIZE_INCREASE = 3f;

	public static float FLYING_HEALTH_PENALTY = .5f;
	public static float FLYING_SPEED_PENALTY = .7f;

	Random r = new Random();
	int numberOfWaves = 10;
	float baseDifficulty = 1f, linearDifficulty = .25f, exponentialDifficulty = 1.05f, difficulty;
	int startingWaveLength = 15, linearLengthIncrease = 2, waveLength;
	int minimumCreepPerChunk = 4, maximumCreepPerChunk = 8;
	boolean debug = false;

	// flying deathrattle, disruptor, Giant, quick, shielded, regen, juggernaut
	float[] typeChances = { .05f, .05f, 0f, .1f, .1f, 0f, 0f, 0f }; // Out of 1, matches indexs of creeps.CreepType
	float[] elementalChances = { .25f, .25f, .25f, .25f }; // Add to 1, matchesindex of creeps.ElementType

	public ArrayList<Wave> generateCreepWaves() {
		ArrayList<Wave> waves = new ArrayList<Wave>();
		difficulty = baseDifficulty;
		waveLength = startingWaveLength;
		for (int i = 0; i < numberOfWaves; i++) {
			if(debug)
				System.out.println("\nWave " + i);
			waves.add(generateWave(difficulty, waveLength));
			difficulty = (difficulty + linearDifficulty) * exponentialDifficulty;
			waveLength += linearLengthIncrease;
		}
		return waves;
	}

	public static void main(String[] args) {
		CreepWaveGenerator me = new CreepWaveGenerator();
		me.generateCreepWaves();
	}

	private Wave generateWave(float difficulty, int waveLength) {
		ArrayList<Creep> creeps = new ArrayList<Creep>();
		ArrayList<Integer> chunkSizes = new ArrayList<Integer>();
		int chunkSize;
		while (waveLength > 0) {
			if (waveLength > minimumCreepPerChunk + maximumCreepPerChunk) {
				chunkSize = nextInt(minimumCreepPerChunk, maximumCreepPerChunk);
			} else if (waveLength > maximumCreepPerChunk) {
				chunkSize = nextInt(minimumCreepPerChunk, waveLength - minimumCreepPerChunk);
			} else {
				chunkSize = waveLength;
			}
			waveLength -= chunkSize;
			creeps.addAll(generateChunk(difficulty, chunkSize, false));
			
			chunkSizes.add(chunkSize);
		}

		return null;
	}

	// Collection of the same creep
	private ArrayList<Creep> generateChunk(float difficulty, float chunkSize, boolean dr) {
		DamageType damageType = DamageType.LIGHT;
		HashSet<CreepType> creepTypes = new HashSet<CreepType>();
		ArrayList<Creep> chunk = new ArrayList<Creep>();
		ArrayList<Creep> deathRattle = null;
		float temp = r.nextFloat(), temp2 = 0;
		for (int i = 0; i < elementalChances.length; i++) {
			temp2 += elementalChances[i];
			if (temp <= temp2) {
				damageType = DamageType.values()[i];
				break;
			}
		}

		difficulty *= 1 + .1f * (.5f - r.nextFloat()); // up to 5% variation

		float speedFactor = .5f * (.5f - r.nextFloat()); // -25 to 25% uniform
		float toughnessFactor = -speedFactor * r.nextFloat();
		float healthFactor = -speedFactor - toughnessFactor;

		float health = (BASE_HEALTH * difficulty * (1 + healthFactor));
		float speed = BASE_SPEED * (1 + speedFactor);
		float toughness = (BASE_TOUGHNESS * difficulty * (1 + toughnessFactor));
		float healthCost = BASE_HEALTH_DAMAGE;
		float gold = (BASE_GOLD * difficulty);

		for (int i = 0; i < typeChances.length; i++) {
			if (r.nextFloat() < typeChances[i]) {
				switch (CreepType.values()[i]) {
				case DEATH_RATTLE:
					int drsize = nextInt(2,4);
					deathRattle = generateChunk(difficulty / drsize, drsize, true);
					health *= DEATH_RATTLE_PENALTY;
					toughness *= DEATH_RATTLE_PENALTY;
					chunkSize *= DEATH_RATTLE_CHUNK_SIZE_PENALTY;
					gold *= DEATH_RATTLE_CHUNK_SIZE_PENALTY;
					healthCost *= 1 / DEATH_RATTLE_CHUNK_SIZE_PENALTY;
					break;
				case DISRUPTOR:
					break;
				case FLYING:
					health *= FLYING_HEALTH_PENALTY;
					speed *= FLYING_SPEED_PENALTY;
					break;
				case GIANT:
					if (creepTypes.contains(CreepType.QUICK)) {
						continue;
					}
					health *= GIANT_HEALTH_BONUS;
					toughness *= GIANT_TOUGHNESS_BONUS;
					speed *= GIANT_SPEED_PENALTY;
					chunkSize *= GIANT_CHUNK_SIZE_REDUCTION;
					gold *= 1 / GIANT_CHUNK_SIZE_REDUCTION;
					healthCost *= 1 / GIANT_CHUNK_SIZE_REDUCTION;
					break;
				case JUGGERNAUT:
					break;
				case QUICK:
					if (creepTypes.contains(CreepType.GIANT)) {
						continue;
					}
					health *= QUICK_HEALTH_PENALTY;
					toughness *= QUICK_TOUGHNESS_PENALTY;
					speed *= QUICK_SPEED_BONUS;
					chunkSize *= QUICK_CHUNK_SIZE_INCREASE;
					gold *= 1 / QUICK_CHUNK_SIZE_INCREASE;
					healthCost *= 1 / QUICK_CHUNK_SIZE_INCREASE;
					break;
				case REGENERATING:
					break;
				case SHIELDED:
					break;
				default:
					break;
				}

				creepTypes.add(CreepType.values()[i]);
			}
		}
		Creep c = new Creep((int) health, speed, (int) toughness, (int) healthCost, (int) gold, damageType);
		for(CreepType type : creepTypes){
			c.addAffix(type);
		}
		if(deathRattle != null){
				c.children = deathRattle;
		}
		for (int i = 0; i < chunkSize; i++) {
			chunk.add(c.clone());
		}
		if(!dr){
			if(debug)
				System.out.println((int) chunkSize + " of " + c);
		}
		return chunk;
	}

	private int nextInt(int min, int max) {
		return r.nextInt(max - min) + min;
	}

}

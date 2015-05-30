package utilities;

import java.util.ArrayList;
import java.util.Random;

import creeps.Creep;
import creeps.CreepType;
import creeps.ElementType;
import creeps.Wave;

public class CreepWaveGenerator {

	/*
	 * Creep waves have difficulty and length each wave is broken into "Chunks"
	 * which consist of multiple copies of the same creep The creeps stats are
	 * based off of difficulty and modified by their types
	 */

	Random r = new Random();
	int numberOfWaves = 10;
	float baseDifficulty = 1f, linearDifficulty = .25f,
			exponentialDifficulty = 1.05f, difficulty;
	int startingWaveLength = 15, linearLengthIncrease = 2, waveLength;
	int minimumCreepPerChunk = 4, maximumCreepPerChunk = 8;
	
				//flying  deathrattle, disruptor, Giant, quick, shielded, regen, juggernaut
	float[] typeChances = {.05f, .05f, 0f, .1f, .1f, 0f, 0f, 0f}; //Out of 1, matches indexs of creeps.CreepType
	float[] elementalChances = {.25f, .25f, .25f, .25f}; //Add to 1, matchesindex of creeps.ElementType
	
	public ArrayList<Wave> generateCreepWaves() {
		ArrayList<Wave> waves = new ArrayList<Wave>();
		difficulty = baseDifficulty;
		waveLength = startingWaveLength;
		for (int i = 0; i < numberOfWaves; i++) {
			System.out.println("New wave size " + waveLength
					+ " with strength " + difficulty);
			waves.add(generateWave(difficulty, waveLength));
			difficulty = (difficulty + linearDifficulty)
					* exponentialDifficulty;
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
				chunkSize = nextInt(minimumCreepPerChunk, waveLength
						- minimumCreepPerChunk);
			} else {
				chunkSize = waveLength;
			}
			System.out.println(chunkSize);
			waveLength -= chunkSize;
			creeps.addAll(generateChunk(difficulty, chunkSize));
			chunkSizes.add(chunkSize);
		}

		return null;
	}

	//Collection of the same creep
	private ArrayList<Creep> generateChunk(float difficulty, int chunkSize) {
		ElementType elementType;
		ArrayList<CreepType> creepTypes = new ArrayList<CreepType>();
		
		float temp = r.nextFloat(), temp2 = 0;
		for(int i = 0; i < elementalChances.length; i++){
			temp2 += elementalChances[i];
			if(temp <= temp2){
				elementType = ElementType.values()[i];
				break;
			}
		}
		
		for(int i = 0 ; i < typeChances.length; i++){
			if(r.nextFloat() < typeChances[i]){
				creepTypes.add(CreepType.values()[i]);
				switch(CreepType.values()[i]){
				case DEATH_RATTLE:
					break;
				case DISRUPTOR:
					break;
				case FLYING:
					break;
				case GIANT:
					break;
				case JUGGERNAUT:
					break;
				case QUICK:
					break;
				case REGENERATING:
					break;
				case SHIELDED:
					break;
				default:
					break;
				}
			}
		}
		return null;
	}

	private int nextInt(int min, int max) {
		return r.nextInt(max - min) + min;
	}

}

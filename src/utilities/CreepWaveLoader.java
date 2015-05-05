package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import creeps.Creep;
import creeps.CreepType;
import creeps.ElementType;
import maps.Wave;

/*
 * Pass it absolute file location (Will adjust later), loads a creep wave for you
 */
public class CreepWaveLoader {

	public static ArrayList<Wave> getWaveFromCSV(String cvsFile) {

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int currentWave = 0;
		ArrayList<Wave> toReturn = new ArrayList<Wave>();
		int waveNumber, numberOfCreep, delayInterior, delayBefore, health, armor, healthCost, goldValue;
		float speed;
		ElementType elementType;
		try {

			br = new BufferedReader(new FileReader(cvsFile));
			br.readLine();
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] waveSegment = line.split(cvsSplitBy);

				waveNumber = Integer.parseInt(waveSegment[0]);
				numberOfCreep = Integer.parseInt(waveSegment[1]);
				delayInterior = Integer.parseInt(waveSegment[2]);
				delayBefore = Integer.parseInt(waveSegment[3]);
				health = Integer.parseInt(waveSegment[4]);
				armor = Integer.parseInt(waveSegment[5]);
				speed = Float.parseFloat(waveSegment[6]);
				healthCost = Integer.parseInt(waveSegment[7]);
				goldValue = Integer.parseInt(waveSegment[8]);
				elementType = ElementType.fromString(waveSegment[9]);

				if (waveNumber != currentWave) {
					currentWave = waveNumber;
					toReturn.add(new Wave());
				}

				for (int i = 0; i < numberOfCreep; i++) {
					Creep c = new Creep(health, armor, speed, healthCost, goldValue, elementType);
					for (int j = 10; j < waveSegment.length; j++) {
						System.out.println(waveSegment[j]);
						c.addAffix(CreepType.fromString(waveSegment[j]));
					}
					if (i == 0) {
						toReturn.get(waveNumber - 1).addCreep(c, delayBefore);
					} else {
						toReturn.get(waveNumber - 1).addCreep(c, delayInterior);
					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return toReturn;
	}

}

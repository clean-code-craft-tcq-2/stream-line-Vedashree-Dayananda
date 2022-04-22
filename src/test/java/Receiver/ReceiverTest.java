package Receiver;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;


public class ReceiverTest {

  List<Float> tempInputs = Arrays.asList(17f, 5f, 5f, 68f, 89f);
  List<Float> socInputs = Arrays.asList(25f, 85f, 15f, 3f, 56f);

  List<Float> expectedMinTemp = Arrays.asList(17f, 5f, 5f, 5f, 5f, 0.0f);
  List<Float> expectedMaxTemp = Arrays.asList(17f, 17f, 17f, 68f, 89f, 100.0f);

  List<Float> expectedMinSoc = Arrays.asList(25f, 25f, 15f, 3f, 3f, 0.0f);
  List<Float> expectedMaxSoc = Arrays.asList(25f, 85f, 85f, 85f, 85f, 99.0f);

  List<Float> expectedSMATemp = Arrays.asList(0.0f, 0.0f, 0.0f, 0.0f, 36.8f, 22.2f);
  List<Float> expectedSMASoc = Arrays.asList(0.0f, 0.0f, 0.0f, 0.0f, 36.8f, 39.4f);

  @Test
  public void testStatisticsInStream() {
    Receiver.resetStatistics();
    for (int index = 0; index < this.tempInputs.size(); index++) {
      Receiver.findStatisticsForValuesInStream(this.tempInputs.get(index), this.socInputs.get(index));

      assertEquals(this.expectedMinTemp.get(index), Receiver.minTempValue);
      assertEquals(this.expectedMaxTemp.get(index), Receiver.maxTempValue);
      assertEquals(this.expectedSMATemp.get(index), Receiver.simpleMovingAvgForTemp);

      assertEquals(this.expectedMinSoc.get(index), Receiver.minSocValue);
      assertEquals(this.expectedMaxSoc.get(index), Receiver.maxSocValue);
      assertEquals(this.expectedSMASoc.get(index), Receiver.simpleMovingAvgForSoc);
    }
  }

  @Test
  public void testReceiver() {
    File paramtersFile = new File("BatteryParameterData.txt");
    Receiver.resetStatistics();
    try {
      try (Scanner inputScanner = new Scanner(paramtersFile)) {
        for (int data = 0; data < Receiver.NO_OF_SENSOR_DATA; data++) {
          Float[] inputsFromConsole = Receiver.getInputsFromConsole(inputScanner);
          Receiver.findStatisticsForValuesInStream(inputsFromConsole[0], inputsFromConsole[1]);
          Receiver.printStatisticsToConsole();
        }
        assertEquals(this.expectedMinTemp.get(5), Receiver.minTempValue);
        assertEquals(this.expectedMaxTemp.get(5), Receiver.maxTempValue);
        assertEquals(this.expectedSMATemp.get(5), Receiver.simpleMovingAvgForTemp);

        assertEquals(this.expectedMinSoc.get(5), Receiver.minSocValue);
        assertEquals(this.expectedMaxSoc.get(5), Receiver.maxSocValue);
        assertEquals(this.expectedSMASoc.get(5), Receiver.simpleMovingAvgForSoc);
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}

package Receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

  @Test
  public void testFindSimpleMovingAvg() {

    List<Float> range = new ArrayList<>();
    range.add(25f);
    range.add(85f);
    range.add(15f);

    Float smaForLastValues = Receiver.findSimpleMovingAvg(range, Float.valueOf(34f));
    assertTrue(Float.compare(smaForLastValues, 0.0f) == 0);

    Float smaForLastValues1 = Receiver.findSimpleMovingAvg(range, 42f);
    assertTrue(Float.compare(smaForLastValues1, 40.2f) == 0);

    Float smaForLastValues2 = Receiver.findSimpleMovingAvg(range, Float.valueOf(34f));
    assertTrue(Float.compare(smaForLastValues2, 42.0f) == 0);

    range.add(78f);

    Float smaForLastValues3 = Receiver.findSimpleMovingAvg(range, Float.valueOf(84f));
    assertTrue(Float.compare(smaForLastValues3, 0.0f) == 0);
  }

  @Test
  public void testFindMinValue() {
    Float minValue = 35f;

    minValue = Receiver.findMinValue(minValue, Float.valueOf(23f));
    assertTrue(Float.compare(minValue, 23f) == 0);

    minValue = Receiver.findMinValue(minValue, Float.valueOf(73f));
    assertTrue(Float.compare(minValue, 23f) == 0);
  }

  @Test
  public void testFindMaxValue() {
    Float maxValue = 95f;

    maxValue = Receiver.findMaxValue(maxValue, Float.valueOf(105f));
    assertTrue(Float.compare(maxValue, 105f) == 0);

    maxValue = Receiver.findMaxValue(maxValue, Float.valueOf(73f));
    assertTrue(Float.compare(maxValue, 105f) == 0);
  }

  @Test
  public void testOperateOnRange() {
    List<Float> range = new ArrayList<>();
    range.add(25f);
    range.add(85f);
    range.add(15f);
    range.add(3f);

    Receiver.operateOnRange(range, Float.valueOf(56f));
    assertTrue(Float.compare(range.get(0), 25f) == 0);
    assertTrue(Float.compare(range.get(1), 85f) == 0);
    assertTrue(Float.compare(range.get(2), 15f) == 0);
    assertTrue(Float.compare(range.get(3), 3f) == 0);
    assertTrue(Float.compare(range.get(4), 56f) == 0);

    Receiver.operateOnRange(range, Float.valueOf(105f));
    assertTrue(Float.compare(range.get(0), 85f) == 0);
    assertTrue(Float.compare(range.get(1), 15f) == 0);
    assertTrue(Float.compare(range.get(2), 3f) == 0);
    assertTrue(Float.compare(range.get(3), 56f) == 0);
    assertTrue(Float.compare(range.get(4), 105f) == 0);

  }

}


package Receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Receiver {

  public static final int NO_OF_VALUES_IN_RANGE = 5;
  public static Float minTempValue;
  public static Float maxTempValue;

  public static Float minSocValue;
  public static Float maxSocValue;

  public static Float simpleMovingAvgForTemp;
  public static Float simpleMovingAvgForSoc;

  public static List<Float> simpleMovingAvgTempRange;
  public static List<Float> simpleMovingAvgSocRange;
  static final int NO_OF_SENSOR_DATA = 50;


  public static void main(final String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    resetStatistics();
    for (int data = 0; data < NO_OF_SENSOR_DATA; data++) {
      Float[] inputsFromConsole = getInputsFromConsole(inputScanner);
      findStatisticsForValuesInStream(inputsFromConsole[0], inputsFromConsole[1]);
      printStatisticsToConsole();
    }
    inputScanner.close();
  }

  public static void resetStatistics() {
    minTempValue = Float.MAX_VALUE;
    maxTempValue = Float.MIN_VALUE;

    minSocValue = Float.MAX_VALUE;
    maxSocValue = Float.MIN_VALUE;

    simpleMovingAvgForTemp = 0.0f;
    simpleMovingAvgForSoc = 0.0f;

    simpleMovingAvgTempRange = new ArrayList<>();
    simpleMovingAvgSocRange = new ArrayList<>();
  }

  public static void printStatisticsToConsole() {
    System.out.println("Statistics for Data in Stream:");
    System.out.println("Minimum Temperature and SOC: " + minTempValue + " , " + minSocValue);
    System.out.println("Maximum Temperature and SOC: " + maxTempValue + " , " + maxSocValue);
    System.out.println("SMA for Temperature and SOC: " + simpleMovingAvgForTemp + " , " + simpleMovingAvgForSoc);

  }

  public static Float[] getInputsFromConsole(final Scanner inputScanner) {
    String sensorDataInCsv = inputScanner.nextLine();
    String[] dataInStr = sensorDataInCsv.split(",");
    Float[] sensorData = { Float.parseFloat(dataInStr[0]), Float.parseFloat(dataInStr[1]) };
    return sensorData;
  }

  public static void findStatisticsForValuesInStream(final Float temp, final Float soc) {

    Receiver.minTempValue = findMinValue(temp, Receiver.minTempValue);
    Receiver.minSocValue = findMinValue(soc, Receiver.minSocValue);

    Receiver.maxTempValue = findMaxValue(temp, Receiver.maxTempValue);
    Receiver.maxSocValue = findMaxValue(soc, Receiver.maxSocValue);

    simpleMovingAvgForTemp = findSimpleMovingAvg(simpleMovingAvgTempRange, temp);
    simpleMovingAvgForSoc = findSimpleMovingAvg(simpleMovingAvgSocRange, soc);

  }

  public static Float findSimpleMovingAvg(final List<Float> range, final Float value) {
    operateOnRange(range, value);
    if (range.size() == NO_OF_VALUES_IN_RANGE) {
      Float sum = Float.valueOf(0.0f);

      for (int index = 0; index < range.size(); index++) {
        sum = sum + range.get(index);
      }
      return sum / NO_OF_VALUES_IN_RANGE;
    }
    return 0.0f;

  }

  public static void operateOnRange(final List<Float> range, final Float value) {
    range.add(value);
    if (range.size() > NO_OF_VALUES_IN_RANGE) {
      range.remove(0);
    }
  }

  public static Float findMinValue(final Float streamValue, final Float refMinValue) {
    Float minValue = refMinValue;
    if (streamValue < minValue) {
      minValue = streamValue;
    }
    return minValue;
  }

  public static Float findMaxValue(final Float streamValue, final Float refMaxValue) {
    Float maxValue = refMaxValue;
    if (streamValue > maxValue) {
      maxValue = streamValue;
    }
    return maxValue;
  }
}

package Receiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Receiver {

  private static final int NO_OF_VALUES_IN_RANGE = 5;
  public static Float minTempValue = Float.MAX_VALUE;
  public static Float maxTempValue = Float.MIN_VALUE;

  public static Float minSocValue = Float.MAX_VALUE;
  public static Float maxSocValue = Float.MIN_VALUE;

  public static Float simpleMovingAvgForTemp = Float.NaN;
  public static Float simpleMovingAvgForSoc = Float.NaN;

  public static List<Float> simpleMovingAvgTempRange = new ArrayList<>();
  public static List<Float> simpleMovingAvgSocRange = new ArrayList<>();
  private static final int NO_OF_SENSOR_DATA = 50;
  private static Scanner inputScanner = new Scanner(System.in);

  public static void main(final String[] args) {
    for (int data = 0; data < NO_OF_SENSOR_DATA; data++) {
      Float[] inputsFromConsole = getInputsFromConsole();
      findStatisticsForValuesInStream(inputsFromConsole[0], inputsFromConsole[1]);
      printStatisticsToConsole();
    }
    inputScanner.close();
  }

  private static void printStatisticsToConsole() {
    System.out.println("Statistics for Data in Stream:");
    System.out.println("Minimum Temperature and SOC: " + minTempValue + " , " + minSocValue);
    System.out.println("Maximum Temperature and SOC: " + maxTempValue + " , " + maxSocValue);
    System.out.println("SMA for Temperature and SOC: " + simpleMovingAvgForTemp + " , " + simpleMovingAvgForSoc);

  }

  private static Float[] getInputsFromConsole() {
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

  private static Float findSimpleMovingAvg(final List<Float> range, final Float value) {

    if (range.size() != NO_OF_VALUES_IN_RANGE) {
      return Float.NaN;
    }

    range.remove(0);
    range.add(value);

    Float sum = (float) 0;

    for (int index = 0; index < range.size(); index++) {
      sum = sum + range.get(index);
    }
    return sum / NO_OF_VALUES_IN_RANGE;
  }

  private static Float findMinValue(final Float streamValue, final Float refMinValue) {
    Float minValue = refMinValue;
    if (streamValue < minValue) {
      minValue = streamValue;
    }
    return minValue;
  }

  private static Float findMaxValue(final Float streamValue, final Float refMaxValue) {
    Float maxValue = refMaxValue;
    if (streamValue > maxValue) {
      maxValue = streamValue;
    }
    return maxValue;
  }
}
package Receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class ReceiverTest {

  List<Float> tempInputs = Arrays.asList(17f, 5f, 5f);
  List<Float> socInputs = Arrays.asList(25f, 85f, 15f);

  List<Float> expectedMinTemp = Arrays.asList(17f, 5f, 5f);
  List<Float> expectedMaxTemp = Arrays.asList(17f, 17f, 17f);
  List<Float> expectedSimpleMovingAvgTemp = Arrays.asList(Float.NaN, Float.NaN, Float.NaN);

  List<Float> expectedMinSoc = Arrays.asList(25f, 25f, 15f);
  List<Float> expectedMaxSoc = Arrays.asList(25f, 85f, 85f);
  List<Float> expectedSimpleMovingAvgSoc = Arrays.asList();

  @Test
  public void testStatisticsInStream() {

    for (int index = 0; index < this.tempInputs.size(); index++) {
      Receiver.findStatisticsForValuesInStream(this.tempInputs.get(index), this.socInputs.get(index));

      assertEquals(this.expectedMinTemp.get(index), Receiver.minTempValue);
      assertEquals(this.expectedMaxTemp.get(index), Receiver.maxTempValue);
      assertTrue(Receiver.simpleMovingAvgForTemp.isNaN());

      assertEquals(this.expectedMinSoc.get(index), Receiver.minSocValue);
      assertEquals(this.expectedMaxSoc.get(index), Receiver.maxSocValue);
      assertTrue(Receiver.simpleMovingAvgForSoc.isNaN());
    }
  }
}

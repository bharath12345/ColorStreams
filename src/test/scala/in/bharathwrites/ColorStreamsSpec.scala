package in.bharathwrites

import org.scalatest._

class ColorStreamsSpec extends FlatSpec with Matchers {

  import ColorStreams._

  "Test with example data" should "succeed" in {
    val channelOneArray = getData(Some("R1_1 R1_2 R1_3 B1_4 B1_8 G1_5"))
    val channelTwoArray = getData(Some("B2_6 B2_8 R2_9 G2_10 B2_7 R2_20"))

    channelOne = channelOneArray.toVector
    channelTwo = channelTwoArray.toVector

    compute(channelOne)

    val expectedResults = Vector(
      (channelOneArray(0), channelTwoArray(2)),
      (channelOneArray(3), channelTwoArray(0)),
      (channelOneArray(4), channelTwoArray(1)),
      (channelOneArray(5), channelTwoArray(3)),
      (channelOneArray(1), channelTwoArray(5))
    )

    expectedResults.foreach { er =>
      assert(result.contains(er))
    }
  }

  "Test with piecemeal example data" should "succeed" in {
    val channelOneArray = getData(Some("R1_1 R1_2 R1_3 B1_4 B1_8 G1_5"))
    val channelTwoArray = getData(Some("B2_6 B2_8 R2_9 G2_10 B2_7 R2_20"))

    for(i <- 0 until 6 ) {
      channelOne = Vector(channelOneArray(i))
      channelTwo = Vector(channelTwoArray(i))
      compute(channelOne)
    }

    val expectedResults = Vector(
      (channelOneArray(0), channelTwoArray(2)),
      (channelOneArray(3), channelTwoArray(0)),
      (channelOneArray(4), channelTwoArray(1)),
      (channelOneArray(5), channelTwoArray(3)),
      (channelOneArray(1), channelTwoArray(5))
    )

    expectedResults.foreach { er =>
      assert(result.contains(er))
    }
  }

}
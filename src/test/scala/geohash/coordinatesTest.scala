import coordinates._
import defs._
import org.scalatest.FlatSpec

/**
* LCoordinates testing
*/
class CoordinatesTest extends FlatSpec {

  	"A coordinate " should " not accept invalid inputs " in {
      assertThrows[IllegalArgumentException](new Coordinates(-91,0))
      assertThrows[IllegalArgumentException](new Coordinates(91,0))
      assertThrows[IllegalArgumentException](new Coordinates(0,-181))
      assertThrows[IllegalArgumentException](new Coordinates(0,181))
  	}

    "A coordinate " should " properly get its geohash encoding " in {
      assert(new Coordinates(0,0).GeoHash()    == "s00000000000")
      assert(new Coordinates(90,0).GeoHash()   == "upbpbpbpbpbp")
      assert(new Coordinates(-90,0).GeoHash()  == "h00000000000")
      assert(new Coordinates(0,-180).GeoHash() == "800000000000")
      assert(new Coordinates(0,180).GeoHash()  == "xbpbpbpbpbpb")
      assert(new Coordinates(45,0).GeoHash()   == "u00000000000")
      assert(new Coordinates(45,45).GeoHash()  == "v00000000000")
      assert(new Coordinates(-45,0).GeoHash()  == "k00000000000")
      assert(new Coordinates(0,-90).GeoHash()  == "d00000000000")
      assert(new Coordinates(0,90).GeoHash()   == "w00000000000")

      assert(new Coordinates(15,15).GeoHash()        == "s6dtm6dtm6dt")
      assert(new Coordinates(75.05,3.26).GeoHash()   == "uj66fnmvgw00")
      assert(new Coordinates(-78.53,0.59).GeoHash()  == "h403m2j9efzz")
      assert(new Coordinates(41.5,-159.4).GeoHash()  == "8rqshfb7nb9q")
    }

    "A coordinate geohash  encoding " should " be of size 12 " in {
      assert(new Coordinates(0,0).GeoHash().length          == defs.geohash_length)
      assert(new Coordinates(3,0).GeoHash().length          == defs.geohash_length)
      assert(new Coordinates(32,51).GeoHash().length        == defs.geohash_length)
      assert(new Coordinates(25.87,33.59).GeoHash().length  == defs.geohash_length)
      assert(new Coordinates(15.88,3.999).GeoHash().length  == defs.geohash_length)
    }
}

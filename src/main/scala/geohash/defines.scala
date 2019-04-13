package defs

/**
* Object holding some useful classes, definitions and aliases
*/
object defs {
	type Limits = (Double, Double)
	type Longitude          = Double
	type Latitude           = Double
	type GeoHash            = String
	type Unique_Identifier  = String
	class PartialSolution(val lat: Latitude, val long: Longitude, val geohash: GeoHash) {
		override def toString : String = {
			s"$lat, $long, $geohash"
		}
	}
	class Solution(val lat: Latitude, val long: Longitude, val geohash: GeoHash, val unique_id: Unique_Identifier) {
		override def toString : String = {
			s"$lat, $long, $geohash, $unique_id"
		}
	}
  	val  limitsLat: Limits = (-90.0, 90.0)
    val  limitsLon: Limits = (-180.0, 180.0)
    val  geohash_length = 12
	val  max_bits = geohash_length*5
}
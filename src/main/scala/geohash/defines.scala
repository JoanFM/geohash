package defs

/**
* Object holding some useful classes, definitions and aliases
*/
object defs {
	val  geohash_length    = 12
	val  max_bits          = geohash_length*5
	val  invalid_identifier= "INVALID"

	type Limits = (Double, Double)
	type Longitude          = Double
	type Latitude           = Double
	type GeoHash            = String

	type Unique_Prefix  = String
	class PartialSolution(val lat: Latitude, val long: Longitude, val geohash: GeoHash) {
		override def toString : String = {
			s"$lat, $long, $geohash"
		}
	}
	class Solution(val lat: Latitude, val long: Longitude, val geohash: GeoHash, val unique_prefix: Unique_Prefix) {
		override def toString : String = {
			s"$lat, $long, $geohash, $unique_prefix"
		}
	}
  	val  limitsLat: Limits = (-90.0, 90.0)
    val  limitsLon: Limits = (-180.0, 180.0)
}
package coordinates
import iterators._
import defs._

class Coordinates(val lat: defs.Latitude, val long: defs.Longitude) {
	/**
 	* Converts the coordinates into a bit representation stored in a Long because it is 64-bit long, enough to fit the 60 bit - long
 	* encoding of the coordinates.
 	*/
	private def toBitsRepresentation(): Long = {
		val limits_iterator = new LimitsIterator()
		/**
 		* Recursively split the coordinates into halfs in order to get the proper encoding
 		*/
		@annotation.tailrec
		def toBitsRepresentationRec(current_pos: Int, current_long_limits: defs.Limits, current_lat_limits: defs.Limits, conversion: Long): Long = {
			if (limits_iterator.hasNext(current_pos)) {
				val even             = (current_pos % 2 == 0)
				val value		     = if (even) long; else lat
				val limits           = if (even) current_long_limits; else current_lat_limits
				val positive_half    = (value >= (limits._1 + ((limits._2 - limits._1)/2.0)))
				val next_limits      = limits_iterator.next(current_pos, positive_half, current_long_limits, current_lat_limits)
				val next_long_limits = if (even) next_limits else current_long_limits
				val next_lat_limits  = if (even) current_lat_limits else next_limits
				val unit : Long 	 = 1
				val new_conversion   = if (positive_half) conversion | (unit << (defs.max_bits - current_pos - 1)) else conversion
				toBitsRepresentationRec(current_pos + 1, next_long_limits, next_lat_limits, new_conversion)
			} else {
				conversion
			}
		}
		toBitsRepresentationRec(0, defs.limitsLon, defs.limitsLat, 0x0)
 	}

	/**
 	* Converts a bit representation into a GeoHashMap
 	* @param bits_representation: Long : Bits representation of a coordinate with 
 	*/
	private def toGeoHash(bits_representation: Long): String = {
		val base32 = "0123456789bcdefghjkmnpqrstuvwxyz"
		@annotation.tailrec
		/**
 		* Recursively obtains the corresponding geohash of every 5-bit in the bit representation
 		* @param current_pos: Int		 : Current geohash character position being encoded
 		* @param current_geohash: String : Accumulates geohash already encoded
 		*/
		def toGeoHashRec(current_pos: Int, current_geohash: String): String = {
			if (current_pos < defs.geohash_length) {
				val all_ones: 	  Int = 31
				val base32_index: Int = all_ones & (bits_representation >>> (defs.max_bits - (current_pos + 1)*5)).toInt
				val nextChar          = base32(base32_index)
				toGeoHashRec(current_pos + 1, current_geohash + nextChar) 
			} else {
				current_geohash
			}
		}
		toGeoHashRec(0,"")
	} 

	def GeoHash(): String = {
		toGeoHash(toBitsRepresentation())
	}

	override def toString : String = {
		s"($lat, $long)"
	}
}
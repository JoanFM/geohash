package iterators

import defs._
/**
* Limits iterator abstract the business logic of switching alternatively between latitude and longitude limits
*/
class LimitsIterator {

	/**
	* LimitsTypeIterator abstract the business logic of getting new limits as the splitting of the limits goes on
	*/
	class LimitsTypeIterator {
		/**
 		* Takes the next limits values after a split according to the value given
 		* @param current: defs.Limits : Current limits that need to evolve
 		* @param option: Boolean      : Wether or not the second half should be taken for the next limits
 		*/
		def next(current: defs.Limits, option: Boolean): defs.Limits = {
			val new_limits = if (option) new defs.Limits(current._1 + ((current._2 - current._1)/2.0), current._2) else new defs.Limits(current._1, current._2 - ((current._2 - current._1)/2.0)) 
			new_limits
		}
	}

	val inner_iterator = new LimitsTypeIterator()

	/**
 	* Takes the next limits values after a split according to the value given
 	* @param position: Int   : Current position of the bit to obtain
 	* @param option: Boolean : Wether or not the second half should be taken for the next limits
 	* @param current_long: defs.Limits : Current limits corresponding to the longitude coordinate
 	* @param current_lat : defs.Limits : Current limits corresponding to the latitude coordinate
 	*/
	def next(position: Int, option: Boolean, current_long: defs.Limits, current_lat: defs.Limits): defs.Limits = {
		val new_limits = if (position % 2 == 0) inner_iterator.next(current_long, option) else inner_iterator.next(current_lat, option)
		new_limits
	}
	
	def hasNext(position: Int): Boolean = {position < defs.max_bits}
}

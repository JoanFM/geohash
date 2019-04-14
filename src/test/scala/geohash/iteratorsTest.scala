import iterators._
import defs._
import org.scalatest.FlatSpec

/**
* Limits iterator testing
*/
class LimitsIteratorTest extends FlatSpec {

  	"An iterator " should " not have next after max value " in {
    	assert(new LimitsIterator().hasNext(defs.max_bits) == false)
  	}

  	"An iterator " should " have next before max value " in {
    	assert(new LimitsIterator().hasNext(defs.max_bits - 1) == true)
  	}

  	"An iterator " should " split in half the corresponding limits" in {
    	assert(new LimitsIterator().next(0, true, defs.limitsLon, defs.limitsLat)   == (0, 180))
    	assert(new LimitsIterator().next(10, false, defs.limitsLon, defs.limitsLat) == (-180, 0))
    	assert(new LimitsIterator().next(1, true, defs.limitsLon, defs.limitsLat)   == (0, 90))
    	assert(new LimitsIterator().next(5, false, defs.limitsLon, defs.limitsLat)  == (-90, 0))
    	assert(new LimitsIterator().next(6, true, (22.5,45), defs.limitsLat)        == (33.75,45.0))
    	assert(new LimitsIterator().next(8, false, (-45,-22.5), defs.limitsLat)     == (-45, -33.75))
    	assert(new LimitsIterator().next(1, true, defs.limitsLon, (22.5,45))        == (33.75,45.0))
    	assert(new LimitsIterator().next(7, false, defs.limitsLon, (-45,-22.5))     == (-45, -33.75))
    	assert(new LimitsIterator().next(11, false, defs.limitsLon, (-45,-22.5))    == (-45, -33.75))
  	}

  	"An iterator " should " throw exception when given invalid input" in {
    	assertThrows[IllegalArgumentException](new LimitsIterator().next(6, true, (80,45), defs.limitsLat))
    	assertThrows[IllegalArgumentException](new LimitsIterator().next(8, false, (-40,-80), defs.limitsLat))
  	}
}

import coordinates._
import defs._
import com.rklaehn.radixtree._

object GeoHashApp extends App {
	/**
 	* Given latitude and longitudem obtain the partial solution which includes the geohash
 	* @param lat : defs.Latitude  : latitude coordinate
 	* @param long: defs.Longitude : longitude coordinate
 	*/
	def partialSolution(lat: defs.Latitude, long: defs.Longitude): defs.PartialSolution = {
		val coordinates	= new Coordinates(lat, long)
		new defs.PartialSolution(lat, long, coordinates.GeoHash())
	}

	/**
 	* Given a RadixTree (PrefixTree) returns a function that will obtain a Solution from a PartialSolution
 	* @param tree : RadixTree[String, String]  : Radix tree that have all the geohashes so that the unique identifier prefix can 
 	* be found
 	*/
	def finalSolution(tree: RadixTree[String, String]): (defs.PartialSolution => defs.Solution) = {
		/**
 		* Finds in the tree the shortest prefix that uniquely identifies a coordinate, for this purpose, starting by the
 		* longest substring of the string (itself), finds the last prefix that is unique in the tree 
 		*/
		(partial_solution: defs.PartialSolution) => {
 			@annotation.tailrec
			def finalSolutionRec(pos: Int, alternative: defs.Unique_Identifier): defs.Solution = {
				if (pos <= 0) {
					new defs.Solution(partial_solution.lat, partial_solution.long, partial_solution.geohash, alternative)
				}
				val prefix_to_find    = partial_solution.geohash.dropRight(partial_solution.geohash.length() - pos)
				val matching_prefixes = tree.filterPrefix(prefix_to_find).keys
				if (matching_prefixes.size > 1) {
					new defs.Solution(partial_solution.lat, partial_solution.long, partial_solution.geohash, alternative)
				} else {
					finalSolutionRec(pos - 1, prefix_to_find)
				}
			}
			val solution = finalSolutionRec(partial_solution.geohash.length, partial_solution.geohash)
     		solution
		}
	}

	/**
 	* Receive the input path of a file containing csv coordinates
 	*/
    def application(input_file_path: String) = {
    	val bufferedSource = io.Source.fromFile(input_file_path)
    	val partial_solutions = bufferedSource.getLines.drop(1).map(line => line.split(",").map(_.trim.toDouble) match {
    		case Array(lat: Double, long: Double) => partialSolution(lat, long)
    	})
    	val tuple_arrays = partial_solutions.toArray.map(x => (x , x.geohash -> x.geohash)).unzip
    	val tree = RadixTree(tuple_arrays._2: _*)
    	def final_solution_builder = finalSolution(tree)
    	val final_solutions = tuple_arrays._1.map(final_solution_builder)
    	println("lat,lng,geohash,uniq")
    	for (solution <- final_solutions) {
    		println(solution)
    	}
	    bufferedSource.close
    }

    if (args.length < 1) {
        println("Not enough params")
    }
    val filename = args(0)

	application(filename)
}

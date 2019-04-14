import geohash._
import com.rklaehn.radixtree._
import defs._
import org.scalatest.FlatSpec

/**
* LCoordinates testing
*/
class GeoHashTest extends FlatSpec {

  	"A solutions list " should " have the same lenght as input list " in {
      val coordinates_list : List[(Double, Double)] = List((41.388828145321, 2.1689976634898),(41.390743, 2.138067),(41.390853, 2.138177))
      val partial_solutions: List[defs.PartialSolution] = coordinates_list.map(pair => GeoHashApp.partialSolution(pair._1, pair._2)).flatten
      val tree                   = RadixTree(partial_solutions.map(x => x.geohash -> x.geohash): _*)
      def final_solution_builder = GeoHashApp.finalSolution(tree)
      val final_solutions        = partial_solutions.map(final_solution_builder)
      assert(partial_solutions.length == coordinates_list.length)
      assert(final_solutions.length   == coordinates_list.length)
  	}

    "A solutions list " should " have one less element than the lenght of the input list if an invalid coordinate is introduced" in {
      val coordinates_list : List[(Double, Double)] = List((41.388828145321, 2.1689976634898),(41.390743, 2.138067),(41.390853, 2.138177), (1000, 1000), (-1000, -1000))
      val partial_solutions: List[defs.PartialSolution] = coordinates_list.map(pair => GeoHashApp.partialSolution(pair._1, pair._2)).flatten
      val tree                   = RadixTree(partial_solutions.map(x => x.geohash -> x.geohash): _*)
      def final_solution_builder = GeoHashApp.finalSolution(tree)
      val final_solutions        = partial_solutions.map(final_solution_builder)
      assert(partial_solutions.length == coordinates_list.length - 2)
      assert(final_solutions.length   == coordinates_list.length - 2)
    }

  "Input from example " should " give proper solutions " in {
      val coordinates_list : List[(Double, Double)] = List((41.388828145321, 2.1689976634898),(41.390743, 2.138067),(41.390853, 2.138177))
      val partial_solutions: List[defs.PartialSolution] = coordinates_list.map(pair => GeoHashApp.partialSolution(pair._1, pair._2)).flatten
      val tree                   = RadixTree(partial_solutions.map(x => x.geohash -> x.geohash): _*)
      def final_solution_builder = GeoHashApp.finalSolution(tree)
      val final_solutions        = partial_solutions.map(final_solution_builder)
      assert(final_solutions{0}.geohash       == "sp3e3qe7mkcb")
      assert(final_solutions{0}.unique_prefix == "sp3e3")
      assert(final_solutions{1}.geohash       == "sp3e2wuys9dr")
      assert(final_solutions{1}.unique_prefix == "sp3e2wuy")
      assert(final_solutions{2}.geohash       == "sp3e2wuzpnhr")
      assert(final_solutions{2}.unique_prefix == "sp3e2wuz")
    }


}

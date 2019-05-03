# README GeoHashApp

    The GeoHashApp is the solution proposal to transform a set of latitude, longitude points to its geohash representation plus finding the shortest common prefix

#Bulding and Running:

    The project is built using sbt, and it has one dependency with com.rklaehn.radixtree library (https://index.scala-lang.org/rklaehn/radixtree/radixtree/0.5.0?target=_2.12)

    To run the application just use the command from the root of the directory: 
        - sbt "run filename" 
    where filename is the path of the input csv file containing the list of latitude, longitude coordinates to be geohashed. Invalid coordinates will be ignored in output and a Warning will be printed.

    To run the tests just use the command from the root of the directory:
        - sbt test

#Approach explanation:
    
    The application first implements the geohash encoding of the coordinates using a Long as a structure to hold the byte representation of a coordinate.
    This representation is obtained using bitwise operations that assign the unit bit to the desired bit position. From the 64-bits of a Long, just the 60 less significant ones are used.
    After the byte representation with the Long is obtained, the actual geohash is computed, by considering the LONG encoding in chunks of 5-bits to get its corresponding base32 character.

    In a second stage, a RadixTree is built with all the geohashes. This RadixTree is the most similar implementation of a Trie that I found (I am not sure if it is the most optimal) and also due to the lack of documentation I could not add elements on the fly (which would be a major optimization opportunity). 

    Once this RadixTree is completely built, the unique prefixes can be obtained as follows:
    Iteratively consider a substring with one char less of the original geohash until you find the first prefix that does not have the full geohash as the only entry with that prefix.
    Once this substring is found, the previous substring is the prefix that uniquely identifies the geohash in the set.
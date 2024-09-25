from __future__ import print_function

import sys
import pyspark.sql 


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Invalid Command Line Argument! Proper Format: SparkPubMed_1.py <input file> <output file>", file=sys.stderr)
        sys.exit(-1)


    input_file = sys.argv[1]
    output_file = sys.argv[2]

    ss = pyspark.sql.SparkSession\
        .builder\
        .appName("Task1")\
        .getOrCreate()
        

    texts = ss.read.text(input_file).rdd.map(lambda r: r[0])
    year = texts.map(lambda text: text.split("\t")).map(lambda token: (token[6], 1))
    total_per_year = year.reduceByKey(lambda x, y: x + y)
    total_per_year.map(lambda x: "%s\t%s" % (x[0], x[1])).saveAsTextFile(output_file)   

    ss.stop()
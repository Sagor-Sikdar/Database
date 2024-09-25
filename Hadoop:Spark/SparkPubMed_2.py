from __future__ import print_function
import sys
import pyspark.sql 

if __name__ == "__main__":

    if len(sys.argv) != 4:
        print("Invalid Command Line Arguments! Proper Format: SparkPubMed_2 <input_file> <year> <output_file>")
        sys.exit()

    input_file = sys.argv[1]
    year = sys.argv[2]
    output_file = sys.argv[3]

    ss = pyspark.sql.SparkSession\
        .builder\
        .appName("Task2")\
        .getOrCreate()

    texts = ss.read.option("delimiter", "\t").csv(input_file, header=False)

    extracted_text = texts.filter(texts._c6 == year)

    output = extracted_text.groupBy("_c7").count().sort("count", ascending=False)

    output.rdd.map(lambda x: "{0}\t{1}".format(x[0], x[1])).saveAsTextFile(output_file)

    ss.stop()

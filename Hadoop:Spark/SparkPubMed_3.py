from __future__ import print_function

import sys
import pyspark.sql 

def sort_group(group):
    sorted_group=sorted(group)
    return sorted_group

if __name__ == "__main__":

    if len(sys.argv) != 3:
        print("Invalid Command Line Argument. Propoer Format: SparkPubMed_3 <input_file> <output_file>")
        sys.exit()

    input_file = sys.argv[1]
    output_file = sys.argv[2]

    ss = pyspark.sql.SparkSession\
        .builder\
        .appName("Task3")\
        .getOrCreate()

   
    sc=ss.sparkContext

    texts = ss.read.text(input_file).rdd.map(lambda r: r[0])

    extracted_texts = texts.filter(lambda line: "Australia" in line or "USA" in line)

    authors_rdd = extracted_texts.map(lambda line: line.split("\t")).map(lambda token: (((token[3]+" "+token[2]),token[6]), 1))\
                        .reduceByKey(lambda x, y: x + y)

    groupby_rdd=authors_rdd.map(lambda x: (x[0][1],(x[0][0],x[1]))).groupByKey()

    top_rdd = groupby_rdd.mapValues(lambda x: sorted(x, key=lambda y: y[1], reverse=True))\
                         .mapValues(lambda x: [(y[0], y[1]) for y in x[:3]] + 
                                               [(y[0], y[1]) for y in x[3:] if y[1] == x[2][1]])

    top_rdd.map(lambda x: str(x[0]) + '->'+'\t' + ','.join([y[0] + '(' +str(y[1])+')' for y in x[1]]))\
               .saveAsTextFile(output_file)

    
    ss.stop()



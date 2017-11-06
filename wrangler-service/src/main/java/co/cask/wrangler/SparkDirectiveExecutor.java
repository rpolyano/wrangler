package co.cask.wrangler;

import co.cask.cdap.api.spark.AbstractSpark;
import co.cask.cdap.api.spark.JavaSparkExecutionContext;
import co.cask.cdap.api.spark.JavaSparkMain;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * Spark Program to execute directives
 */
public class SparkDirectiveExecutor extends AbstractSpark implements JavaSparkMain {

  @Override
  protected void configure() {
    setMainClass(SparkDirectiveExecutor.class);
  }

  @Override
  public void run(JavaSparkExecutionContext sec) throws Exception {
    JavaSparkContext sc = new JavaSparkContext();

    // read the dataset
    JavaPairRDD<LongWritable, Text> inputData = sec.<LongWritable, Text>fromDataset("data");

    JavaPairRDD<String, ExperimentStats> fieldData = inputData.flatMapToPair(
      new PairFlatMapFunction<Tuple2<LongWritable,Text>, String, ExperimentStats>() {
        @Override
        public Iterable<Tuple2<String, ExperimentStats>> call(Tuple2<LongWritable, Text> input) throws Exception {
          String[] splits = input._2().toString().split(",");
          List<Tuple2<String, ExperimentStats>> tupleList = new ArrayList<>();
          tupleList.add(new Tuple2<>("males", new ExperimentStats(splits[3])));
          tupleList.add(new Tuple2<>("females", new ExperimentStats(splits[4])));

          /*tupleList.add(new Tuple2<>(new Text("medianAge"), new Text(splits[2])));
          tupleList.add(new Tuple2<>(new Text("males"), new Text(splits[3])));
          tupleList.add(new Tuple2<>(new Text("females"), new Text(splits[4])));*/
          return tupleList;
        }
      });

    final JavaPairRDD<String, ExperimentStats> aggs = fieldData.reduceByKey(new Function2<ExperimentStats,
      ExperimentStats, ExperimentStats>() {
      @Override
      public ExperimentStats call(ExperimentStats s, ExperimentStats s2) throws Exception {
        double total = s.getTotal() + s2.getTotal();
        return new ExperimentStats(total);
      }
    });

    // write a total count to a table (that emits a metric we can validate in the test case)
    sec.<String, ExperimentStats>saveAsDataset(aggs, "totals");
  }
}

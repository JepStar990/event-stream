import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

object AdvancedDataProcessing {
  def main(args: Array[String]): Unit = {
    // Create Spark session
    val spark = SparkSession.builder
      .appName("Advanced Data Processing")
      .master("local[*]")
      .getOrCreate()

    // Read data from MinIO
    val inputPath = "s3a://event-data/"
    val df = spark.read
      .format("json")
      .option("inferSchema", "true")
      .load(inputPath)

    // Data Cleaning: Handle missing values
    val cleanedDF = df.na.fill(Map(
      "price" -> 0.0,
      "category_code" -> "unknown"
    ))

    // Data Type Conversion
    val normalizedDF = cleanedDF
      .withColumn("event_time", to_timestamp(col("event_time")))
      .withColumn("price", col("price").cast("double"))

    // Data Enrichment: Example join with a product dataset
    val productDF = spark.read
      .format("json")
      .option("inferSchema", "true")
      .load("s3a://event-data/products/")
    
    val enrichedDF = normalizedDF.join(productDF, Seq("product_id"), "left")

    // Derived Columns: Calculate discount percentage
    val transformedDF = enrichedDF
      .withColumn("discount_percentage", 
        when(col("original_price").isNotNull && col("original_price") > 0,
          ((col("original_price") - col("price")) / col("original_price")) * 100)
        .otherwise(0)
      )

    // Aggregation: Group by event type and category, calculate metrics
    val aggregatedDF = transformedDF
      .groupBy("event_type", "category_code")
      .agg(
        count("*").alias("event_count"),
        avg("price").alias("average_price"),
        max("price").alias("max_price"),
        min("price").alias("min_price")
      )

    // Window Functions: Calculate moving average of price
    val windowSpec = Window.partitionBy("category_code").orderBy("event_time").rowsBetween(-3, 3)
    val finalDF = aggregatedDF
      .withColumn("moving_avg_price", avg("price").over(windowSpec))

    // Show final transformed data
    finalDF.show()

    // Write processed data back to MinIO
    val outputPath = "s3a://event-data/processed/"
    finalDF.write
      .format("json")
      .mode("overwrite")
      .save(outputPath)

    // Stop Spark session
    spark.stop()
  }
}

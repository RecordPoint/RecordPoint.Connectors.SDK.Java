package com.recordpoint.examples.spark;

import com.recordpoint.connectors.sdk.json.JsonMapperException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("Starting Spark Example");

        SparkSession spark = SparkSession.builder()
                .appName("RecordPoint Spark Example")
                // Set if testing locally
                .config("spark.master", "local")
                // Required for TimestampType to use Instant
                // If not set, replace getInstant with getTimestamp().toInstant()
                .config("spark.sql.datetime.java8API.enabled", true)
                .getOrCreate();

        Dataset<Row> df = spark.read().option("header", true).csv("src/main/resources/books.csv");
        df.show();

        try {
            // Set the fields to use for each of the primary columns
            ItemSubmitter.getInstance().setAuthorColumn("author");
            ItemSubmitter.getInstance().setCreatedByColumn("author");
            ItemSubmitter.getInstance().setCreatedDateColumn("published");
            ItemSubmitter.getInstance().setLastModifiedColumn("published");
            ItemSubmitter.getInstance().setExternalIdColumn("id");
            ItemSubmitter.getInstance().setLastModifiedByColumn("author");
            ItemSubmitter.getInstance().setLocationColumn("location");
            ItemSubmitter.getInstance().setTitleColumn("title");

            // Whether to attach the data as a JSON file to the record
            ItemSubmitter.getInstance().setSubmitBinary(true);

            df.withColumn(
                    "published", df.col("published").cast(DataTypes.TimestampType)
            ).foreach(row -> {
                LOG.info("Submitting row {}", row);
                ItemSubmitter.getInstance().submitItem(row);
            });
        } catch (JsonMapperException exc) {
            LOG.error("Error parsing JSON configuration file", exc);
        } finally {
            try {
                ItemSubmitter.getInstance().close();
            } catch (JsonMapperException | IOException exc) {
                LOG.error("Error closing token manager", exc);
            }
        }

        LOG.info("Finished Spark Example");
    }
}

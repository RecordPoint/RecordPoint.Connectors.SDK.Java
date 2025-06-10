package com.recordpointcx.spark;

import com.recordpoint.connectors.sdk.auth.MsalTokenManager;
import com.recordpoint.connectors.sdk.auth.Token;
import com.recordpoint.connectors.sdk.auth.TokenManager;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {

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
            // Set the service settings
            ServiceSettings settings = ServiceSettings.Builder().build();
            TokenManager tokenManager = new MsalTokenManager(settings);
            ItemSubmitter.getInstance(settings, tokenManager);

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
                ItemSubmitter.getInstance().submitItem(row);
            });
        } catch (JsonMapperException exc) {
            System.err.println("Unable to read JSON configuration");
            System.err.println(exc.getMessage());
        } finally {
            try {
                ItemSubmitter.getInstance().close();
            } catch (JsonMapperException | IOException exc) {
                System.err.println("Error closing Spark session");
                System.err.println(exc.getMessage());
            }
        }
    }
}

package io.jenkins.plugins;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.BucketsApi;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.client.write.Point;

import java.time.Instant;
import java.util.List;

public class InfluxDB2Example {
    final String serverURL = "http://127.0.0.1:8086";
    final String token = "uEZ8SMi2TdIZCJ1tmEKnuqwXro_94YKAQzp9MhU7pwO4qr-FfTfbBTso_K1W6I_zj8j_o7NacgUmQ8GY71xHbw==";
    final String org = "my_org";
    final char[] password = "admin123".toCharArray();

    final InfluxDBClient influxDBClient = InfluxDBClientFactory.create(serverURL, token.toCharArray());

    public void createBucket() {
        // Create Bucket API instance
        BucketsApi bucketsApi = influxDBClient.getBucketsApi();
        
        // Get the organization
        Organization organization = influxDBClient.getOrganizationsApi().findOrganizations().get(0);

        // Create a new bucket
        Bucket bucket = new Bucket();
        bucket.setName("NOAA_water_database1");
        bucket.setOrgID(organization.getId());
        bucket.setRetentionRules(null);

        Bucket createdBucket = bucketsApi.createBucket(bucket);

        System.out.println("Bucket created with ID: " + createdBucket.getId());
    }

    public void writeData() {
        // Write Data
        String bucket = "NOAA_water_database1";

        // Write water level data
        Point waterLevelPoint = Point.measurement("water_level")
                .addTag("location", "coyote_creek")
                .addField("water_level", 5.0)
                .time(Instant.now(), WritePrecision.NS);

        influxDBClient.getWriteApiBlocking().writePoint(bucket, org, waterLevelPoint);

        // Write Hello world data
        Point helloWorldPoint = Point.measurement("greetings")
                .addTag("type", "message")
                .addField("content", "Hello world")
                .time(Instant.now(), WritePrecision.NS);

        influxDBClient.getWriteApiBlocking().writePoint(bucket, org, helloWorldPoint);

        System.out.println("Data written to InfluxDB");
    }

    public String retrieveMessage() {
        // Query to retrieve the message
        String query = "from(bucket: \"NOAA_water_database\") |> range(start: -1h) |> filter(fn: (r) => r._measurement == \"greetings\" and r._field == \"content\")";

        List<FluxTable> tables = influxDBClient.getQueryApi().query(query, org);
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                return record.getValueByKey("_value").toString();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        InfluxDB2Example example = new InfluxDB2Example();
        example.createBucket();
        example.writeData();
        String message = example.retrieveMessage();
        System.out.println("Retrieved message: " + message);
    }
}

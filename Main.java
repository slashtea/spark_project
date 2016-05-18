package simplestream;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;

import Utils.Util;
import twitter4j.TwitterFactory;
import twitter4j.auth.Authorization;
import twitter4j.conf.ConfigurationBuilder;
public class Main {

	public static void main(String[] args) {
		System.out.println("**********   START   **************"); 
        Util util_ = new Util();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("FWD7f4HJmPoqErPpZf0ZU2B3g")
		  .setOAuthConsumerSecret("Rs4GtAbPcyBbcwIDy1CxX8JsxyL5U7Q3FbtFsXbB7rKlX8rBZf")
		  .setOAuthAccessToken("4647906334-4vhUhFIPAHmF9FGY2U7ssg3g3SDIz51IweJWIMd")
		  .setOAuthAccessTokenSecret("MAQI6GaCA8vjApuxGP6hlGbmwVA5PhcBjOc6YNhfT9ynv");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Authorization twitterAuth = tf.getInstance().getAuthorization();
        // Spark
        SparkConf sparkConf = new SparkConf()
                .setAppName("Tweets sentiment analysis")
                .setMaster("local[2]");
        JavaStreamingContext sc = new JavaStreamingContext(sparkConf, new Duration(2000));
        TwitterUtils.createStream(sc, twitterAuth)
        .filter(s -> util_.check(s))
        .map(s -> util_.Evaluation(s))
        .print();
        
        sc.start();
        sc.awaitTermination();
    }

}

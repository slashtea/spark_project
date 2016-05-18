package Utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import twitter4j.Status;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;

public class Util implements Serializable{
	
	java.util.List countries = (java.util.List) Arrays.asList("Algeria","Bahrain","Egypt","Iraq","Jordan","Kuwait","Lebanon","Libya","Mauritania","Morocco","Oman",
			"Palestine","Qatar","Saudi Arabia","Somalia","Sudan","Syria","Tunisia","United Arab Emirates","Yemen");
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/test";
    static final String USER = "root";
	static final String PASS = "cloudera";
    static final String sql = "insert into tweets(text,lang,sentiment,country) values(?,?,?,?)";
	
	HashMap<String, String> map = new HashMap<String, String>();
	private static String NEGATIVE = "negative";
	private static String POSITIVE = "positive";
	private static String NEUTRAL = "neutral";
	AlchemyLanguageSer service;
	
	public Util(){
	}
	
	public String Evaluation(Status s){
		String tweetText = s.getText();
		Tweet tweet = new Tweet();
		tweet.setText(tweetText);
		try{
			tweet.setCountry(s.getPlace().getCountry());
		}catch(NullPointerException e){}
		service = new AlchemyLanguageSer();
		service.setApiKey("412b4190a52005c8cf98ea6d00122c7aa9f04bad");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put(AlchemyLanguage.TEXT, tweetText);
		try{
		DocumentSentiment sentiment = service.getSentiment(params).execute();
		tweet.setSentiment(sentiment.getSentiment().getType().toString());
		tweet.setLanguage(sentiment.getLanguage());
		save(tweet);
		}catch(Exception e){
		}
		return tweet.toString();
	}
	
	public boolean check(Status c){
		boolean result = false;
		try{
			if(countries.contains(c.getPlace().getCountry())) result = true;
		}catch(NullPointerException e){
			System.out.println("Error: " + e);
		}
		return result;
	}
	
	public void save(Tweet t){
        Connection conn = null;
        try{
           //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");

           //STEP 3: Open a connection
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           PreparedStatement preparedStatement =
                   conn.prepareStatement(sql);
           preparedStatement.setString(1, t.getText());
           preparedStatement.setString(2, t.getLanguage());
           preparedStatement.setString(3, t.getSentiment());
           preparedStatement.setString(4, t.getCountry());

           preparedStatement.execute();
        }catch(Exception e){
        	System.out.println("******"+e.getMessage());
        }
	}
	
	//VERSION 1
	/*public void execute() throws FileNotFoundException{
		Scanner scanner = new Scanner(new FileReader("AFINN-111.txt"));
		
		while(scanner.hasNextLine()){
			String[] columns = scanner.nextLine().split("\t");
			map.put(columns[0],columns[1]);
		}
	}
	
	public String Evaluate(String text){
		
		String[] arr = text.split(" ");
		int sum = 0;
		for(String word:arr){
			try{
				sum += Integer.parseInt(map.get(word));
			}catch(NumberFormatException e){
				
			}
		}
		if(sum>0) return POSITIVE;
		else if(sum<0) return NEGATIVE;
		else return NEUTRAL;
	}*/

}

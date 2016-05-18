package Utils;

import java.io.Serializable;

public class Tweet implements Serializable{
	
	private String text, sentiment, country, language;
	
	

	public Tweet() {
		text = "T Undefined";
		sentiment = "S Undefined";
		country = "C Undefined";
		language = "L Undefined";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "Tweet [text=" + text + ", sentiment=" + sentiment
				+ ", country=" + country + ", language=" + language + "]";
	}
	
	

}

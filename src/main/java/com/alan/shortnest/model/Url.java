package com.alan.shortnest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Url
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonProperty("original_url")
	private String originalUrl;

	@JsonProperty("shorten_url")
	private String shortenUrl;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getOriginalUrl()
	{
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl)
	{
		this.originalUrl = originalUrl;
	}

	public String getShortenUrl()
	{
		return shortenUrl;
	}

	public void setShortenUrl(String shortenUrl)
	{
		this.shortenUrl = shortenUrl;
	}

	public int getShortenUrlClicks()
	{
		return shortenUrlClicks;
	}

	public void setShortenUrlClicks(int shortenUrlClicks)
	{
		this.shortenUrlClicks = shortenUrlClicks;
	}

	@JsonProperty("shorten_url_clicks")
	private int shortenUrlClicks;

	public void generateShortUrl()
	{
		this.shortenUrl = generateHash(originalUrl);
	}

	private String generateHash(String url)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(url.getBytes(StandardCharsets.UTF_8));
			return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error generating shorten URL", e);
		}
	}
}

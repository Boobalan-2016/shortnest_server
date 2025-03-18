package com.alan.shortnest.service;

import io.micrometer.common.util.StringUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alan.shortnest.model.Url;
import com.alan.shortnest.repository.ShortNestRepository;

@Service
public class ShortNestService
{
	@Autowired
     private ShortNestRepository repository;

	public List<Url> getUrlStats()
	{
		return repository.findAll();
	}

	public Url createShortenUrl(Url originalUrl)
	{
		originalUrl.generateShortUrl();
		return repository.save(originalUrl);
	}

	public String getOriginalUrl(String shortUrl)
	{
		Url urlDetails = repository.findByShortenUrl(shortUrl);
		if(urlDetails != null)
		{
			urlDetails.setShortenUrlClicks(urlDetails.getShortenUrlClicks()+1);
		}
		repository.save(urlDetails);
		return urlDetails.getOriginalUrl();
	}

}

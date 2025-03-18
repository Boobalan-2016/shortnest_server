package com.alan.shortnest.controller;

import io.micrometer.common.util.StringUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.alan.shortnest.model.Url;
import com.alan.shortnest.service.ShortNestService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ShortNestController
{
	@Autowired
	private ShortNestService service;

	@GetMapping("/urlStats")
	@ResponseBody
	public ResponseEntity<List<Url>> getShortenUrlStats()
	{
		return new ResponseEntity<>(service.getUrlStats(),HttpStatus.OK);
	}

	@PostMapping("/shorten")
	@ResponseBody
	public ResponseEntity<?> getShortenUrl(@RequestBody Url originalUrl)
	{
		try
		{
			Url shortenUrl = service.createShortenUrl(originalUrl);
			return new ResponseEntity<>(shortenUrl, HttpStatus.CREATED);
		}
		catch(Exception ex)
		{
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{shortUrl}")
	@ResponseBody
	public ResponseEntity<RedirectView> redirectOriginalUrl(@PathVariable String shortUrl)
	{
		String originalUrl = service.getOriginalUrl(shortUrl);
		if(StringUtils.isNotEmpty(originalUrl))
		{
			RedirectView redirectView = new RedirectView(originalUrl);
			return ResponseEntity.ok(redirectView);
		}
		return ResponseEntity.notFound().build();
	}
}

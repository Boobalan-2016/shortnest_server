package com.alan.shortnest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alan.shortnest.model.Url;

@Repository
public interface ShortNestRepository extends JpaRepository<Url,Integer>
{
	Url findByShortenUrl(String shortenUrl);
}

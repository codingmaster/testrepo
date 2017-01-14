package com.springer.core.repository;

import com.springer.core.domain.Document;
import com.springer.core.domain.Watermark;
import org.springframework.stereotype.Repository;

@Repository
public interface WatermarkRepository extends BaseRepository<Watermark>
{
	Watermark findByDocument(Document document);
}

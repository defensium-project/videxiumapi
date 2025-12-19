package br.com.videxium.videxiumapi.util;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.videxium.videxiumapi.transfer.PageResponseTransfer;

public class PaginationUtil {
	
	public PaginationUtil() {}
	
	public static Pageable createPageRequest(int page, int size, String sortBy) {
		return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
	}
	
	public static Pageable createPageRequest(int page, int size) {
		return PageRequest.of(page, size);
	}
	
	public static <T,R> PageResponseTransfer<R> toPageResponse(Page<T> page, Function<T, R> mapper) {
		List<R> content = page.getContent().stream().map(mapper).toList();
		return new PageResponseTransfer<>(
				content, 
				page.getTotalElements(), 
				page.getTotalPages(), 
				page.getNumber(), 
				page.getSize());
	}
	
	public static <T,R> PageResponseTransfer<R> toPageResponse(Page<T> page, List<R> mappedContent) {
		return new PageResponseTransfer<>(
				mappedContent, 
				page.getTotalElements(), 
				page.getTotalPages(), 
				page.getNumber(), 
				page.getSize());
	}

}

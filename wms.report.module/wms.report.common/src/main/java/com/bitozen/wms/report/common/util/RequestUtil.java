package com.bitozen.wms.report.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @author EKSAD - Kahfi
 *
 */
@Component ("reportRequestUtil")
@PropertySource("classpath:wms.report.token.properties")
public class RequestUtil {

	private final String HEADER_KEY = "Authorization";

	@Value("${wms.report.token}")
	private String REPORT_TOKEN;

	public HttpEntity<String> getPreFormattedRequestWithToken() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.add(HEADER_KEY, REPORT_TOKEN);

		return new HttpEntity<>(httpHeaders);
	}
	
	public HttpEntity<String> getPreFormattedRequestWithTokenImage() {
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.IMAGE_JPEG);
		acceptableMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(acceptableMediaTypes);
		httpHeaders.add(HEADER_KEY, REPORT_TOKEN);

		return new HttpEntity<>(httpHeaders);
	}

}

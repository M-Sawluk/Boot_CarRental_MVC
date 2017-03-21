package com.michal.springboot.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michal.springboot.exception.RecaptchaServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class ReCaptchaService {

	private RestTemplate restTemplate;


	public ReCaptchaService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Value("${reCaptcha.url}")
	private String url;

	@Value("${reCaptcha.secret}")
	private String secret;


	public boolean isResponseValid(String response) {

		RecaptchaResponse recaptchaResponse;

		String capcza = new StringBuilder().append(url).append("?secret=").append(secret).append("&response=")
				.append(response).toString();

		try {

			recaptchaResponse = restTemplate.getForObject(capcza, RecaptchaResponse.class);

		} catch (RestClientException e) {

			throw new RecaptchaServiceException("Recaptcha API not available due to exception", e);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		return recaptchaResponse.success;
	}

	private static class RecaptchaResponse {

		@JsonProperty("success")
		private boolean success;

		@JsonProperty("error-codes")
		private List<String> errorCodes;

		@JsonProperty("challenge_ts")
		private String challenge_ts;

		@JsonProperty("hostname")
		private String hostname;

		@Override
		public String toString() {
			return "RecaptchaResponse [success=" + success + ", errorCodes=" + errorCodes + "]";
		}

	}

}

package com.lmg.digitization.digital.wallet.util;

import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmg.digitization.digital.wallet.properties.AppProperties;

@Component
public class ValidationUtils {

	@Autowired
	private AppProperties appProperties;

	public boolean isValidLanguage(String language) {
		return appProperties.getAllowedLanguage().contains(language);
	}

	public String getStoreConceptName(String code) {
		Optional<Entry<String, String>> conceptName = appProperties.getConcepts().entrySet().stream()
				.filter(x -> x.getKey().equals(code)).findFirst();
		return conceptName.isPresent() ? conceptName.get().getValue() : "";
	}

	public boolean isValidConceptCode(String code) {
		return appProperties.getConcepts().containsKey(code);
	}

}

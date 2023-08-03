package com.dnbn.back.common.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.dnbn.back.security.auth.MemberDetails;

@Configuration
public class AuditConfig implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.of("주비");
        }
		MemberDetails user = (MemberDetails)authentication.getPrincipal();
		return Optional.of(user.getNickname());
	}
}

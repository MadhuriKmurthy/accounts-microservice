package com.microservices.accounts;

import com.microservices.accounts.dto.AccountControlInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
				info = @Info(
								title = "Accounts Service API",
								version = "1.0",
								description = "API documentation for the Accounts Service",
								contact = @Contact(
												name = "Madhuri",
												email = "madk@example.com"
								),
								license = @License(
												name = "Apache 2.0",
												url = "http://www.apache.org/licenses/LICENSE-2.0.html"
								)
				)
)
@EnableConfigurationProperties(value = {AccountControlInfoDto.class})
public class AccountsApplication {

	public static void main(String[] args) {

		SpringApplication.run(AccountsApplication.class, args);
	}

}

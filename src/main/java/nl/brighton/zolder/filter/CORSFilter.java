package nl.brighton.zolder.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Order(1)
public class CORSFilter implements WebMvcConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedMethods("*");
    LOGGER.info(registry.toString());
  }
}
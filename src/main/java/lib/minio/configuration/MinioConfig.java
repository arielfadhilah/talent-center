package lib.minio.configuration;

import lib.minio.configuration.property.MinioProp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConfig {
  
    @Value("${application.minio.url}")
    private String minioUrl;

    @Value("${application.minio.username}")
    private String minioAccessKey;

    @Value("${application.minio.password}")
    private String minioSecretKey;

  @Bean
  public MinioClient minioClient(MinioProp props) {
    return MinioClient.builder()
        .endpoint(props.getUrl())
        .credentials(props.getUsername(), props.getPassword())
        .build();
  }
}

package com.team33.backend.image.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class S3Component {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

}

package com.goodworkalan.retry.mix;

import com.goodworkalan.mix.ProjectModule;
import com.goodworkalan.mix.builder.Builder;
import com.goodworkalan.mix.builder.JavaProject;

/**
 * Builds the project definition for Retry.
 *
 * @author Alan Gutierrez
 */
public class RetryProject implements ProjectModule {
    /**
     * Build the project definition for Retry.
     *
     * @param builder
     *          The project builder.
     */
    public void build(Builder builder) {
        builder
            .cookbook(JavaProject.class)
                .produces("com.github.bigeasy.retry/retry/0.1")
                .test()
                    .depends()
                        .include("org.testng/testng-jdk15/5.10")
                        .end()
                    .end()
                .end()
            .end();
    }
}

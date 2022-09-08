package com.issue;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@Controller
public class MyController {

  @Get("/user/{id}")
  @Secured(SecurityRule.IS_AUTHENTICATED)
  public HttpStatus get(Authentication principal, @PathVariable String id) {
    return HttpStatus.OK;
  }
}

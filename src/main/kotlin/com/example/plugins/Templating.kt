package com.example.plugins

import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.server.application.*
import io.ktor.server.freemarker.*

fun Application.configureTemplating() {
  install(FreeMarker) {
    templateLoader = ClassTemplateLoader(
      this::class.java.classLoader,
      "templates"
    )
    /*
    The templateLoader setting tells our
    application that FreeMarker templates
    will be located in the templates directory.
    */
    outputFormat = HTMLOutputFormat.INSTANCE
    /*
    The outputFormat setting helps convert
    control characters provided by the user
    to their corresponding HTML entities.
    This ensures that when one of our journal
    entries contains a String like <b>Hello</b>,
    it is actually printed as <b>Hello</b>,
    not Hello. This so-called escaping is
    an essential step in preventing XSS attacks.
    */
  }
}
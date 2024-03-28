package com.example.plugins

import com.example.modals.Article
import com.example.modals.articles
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {
  routing {
    staticResources("/content", "static")

    get("/") {
      call.respondRedirect("articles")
      /*
      The get("/") handler redirects all GET requests
      made to the / path to /articles.
      */
    }

    route("articles") {
      /*
      The route("articles") handler is used to group
      routes related to various actions: showing a list
      of articles, adding a new article, and so on.
      For example, a nested get function without a
      parameter responds to GET requests made to
      the /articles path, while get("new") responds to
      GET requests to /articles/new.
      */

      get {
        println("Show a list of articles")
        println(articles.size)
        // Show a list of articles
        call.respond(
          FreeMarkerContent(
            "index.ftl",
            mapOf("articles" to articles)
          )

          /*
            The call.respond function accepts the FreeMarkerContent
            object that represent content to be sent to the
            client.
            In our case, the FreeMarkerContent constructor
            accepts two parameters:
            - template is a name of a template loaded by the
              FreeMarker plugin. The index.ftl file doesn't
              exist yet, and we'll create it in the next
              chapter.
            - model is a data model to be passed during template
              rendering. In our case, we pass an already created
              list of articles in the articles template
              variable.
            */

          /*
            The FreeMarker plugin is configured to load templates
            located in the templates directory. First, create the
            templates directory inside resources.
            */
        )
      }
      get("new") {
        // Show a page with fields for creating a new article
        call.respond(FreeMarkerContent("new.ftl", model = null))
      }
      post {
        // Save an article
        val formParameters = call.receiveParameters()
        val title = formParameters.getOrFail("title")
        val body = formParameters.getOrFail("body")
        val newEntry = Article.newEntry(title, body)
        articles.add(newEntry)
        call.respondRedirect("/articles/${newEntry.id}")
        /*
          The call.receiveParameters function is used to receive form
          parameters and get their values. After saving a new article,
          call.respondRedirect is called to redirect to a page showing
          this article. Note that a URL path for a specific article
          contains an ID parameter whose value should be obtained at
          runtime. We'll take a look at how to handle path parameters
          in the next chapter.
         */
      }
      get("{id}") {
        // Show an article with a specific id
        val id = call.parameters.getOrFail<Int>("id").toInt()
        call.respond(
          FreeMarkerContent(
            "show.ftl",
            mapOf("article" to articles.find { it.id == id })
          )
          /*
            call.parameters is used to obtain the article ID passed
            in a URL path. To show the article with this ID, we
            need to find this article in a storage and pass it
            in the article template variable.
           */
        )
      }
      get("{id}/edit") {
        // Show a page with fields for editing an article
        val id = call.parameters.getOrFail<Int>("id").toInt()
        /*
          Similar to a route for showing an article, call.parameters
          is used to obtain the article identifier and find this
          article in a storage.
        */
        call.respond(
          FreeMarkerContent(
            "edit.ftl",
            mapOf("article" to articles.find { it.id == id })
          )
        )
      }
      post("{id}") {
        // Update or delete an article
        val id = call.parameters.getOrFail<Int>("id").toInt()
        val formParameters = call.receiveParameters()
        when (formParameters.getOrFail("_action")) {
          "update" -> {
            val index = articles.indexOfFirst { it.id == id }
            val title = formParameters.getOrFail("title")
            val body = formParameters.getOrFail("body")
            articles[index].title = title
            articles[index].body = body
            call.respondRedirect("/articles/${id}")
          }

          "delete" -> {
            articles.removeIf { it.id == id }
            call.respondRedirect("/articles")
          }
        }

      }
    }
  }
}

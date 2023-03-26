package vc

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {

  val getMainPage: HttpRequestBuilder = http("getMainPage")
    .get("/")
    .check(status is 200)

  val getCompanies: HttpRequestBuilder = http("getCompanies")
    .get("/companies")
    .queryParam("mode", "ajax")
    .check(status is 200)

  val getJob: HttpRequestBuilder = http("getJob")
    .get("/job")
    .queryParam("mode", "ajax")
    .check(status not 500)

  val subs: HttpRequestBuilder = http("getSubs")
    .get("/subs")
    .queryParam("mode", "ajax")
    .check(regex("""Ещё (\d+)&nbsp;подписок""").is("2006"))
    .check(regex("""Ещё (\d+)&nbsp;подписок""").saveAs("counter"))

  val search: HttpRequestBuilder = http("search #{counter}")
    .get("/search/v2/content/relevant")
    .queryParam("mode", "ajax")
    .queryParam("query", "#{word}")
    .check(status not 500)
    .check(regex("""l-page l-page--header-content-sidebar l-mb-12""").exists)

}

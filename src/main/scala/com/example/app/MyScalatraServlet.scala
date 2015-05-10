package com.example.app

import java.net.URL

import org.scalatra._
import org.scalatra.scalate.ScalateSupport

class MyScalatraServlet extends HatenanrssStack with ScalateSupport {

  notFound {
    "404 Not Found"
  }

  get("/rss") {
    params.get("rssUrl") match {
      case None => redirect(url("/"))
      case Some(rssUrl) =>
        try {
          val contents = xml.XML.load(new URL(rssUrl))

          val fixedContents = XmlUtil.fixLinkTag(contents)


          ActionResult(ResponseStatus(200), fixedContents, Map("Content-type" -> "text/xml"))
        } catch {
          case e: Throwable => halt(400, "Error:\n" + e.getMessage)
        }
    }
  }

  get("/") {
    contentType = "text/html"
    ssp("/index", "url" -> url("rss"))
  }
}

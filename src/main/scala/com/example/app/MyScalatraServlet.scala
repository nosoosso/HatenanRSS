package com.example.app

import java.net.URL
import java.nio.charset.Charset

import org.scalatra._
import org.scalatra.scalate.ScalateSupport
import org.scalatra.util.RicherString.stringToRicherString

class MyScalatraServlet extends HatenanrssStack with ScalateSupport {

  notFound {
    "404 Not Found"
  }

  get("/rss") {
    params.get("rssUrl") match {
      case None => redirect(url("/"))
      case Some(rssUrl) =>
        try {
          val encodedUrl = rssUrl.formEncode(Charset.forName("UTF-8"))
          val contents = xml.XML.load(new URL(encodedUrl))
          val fixedContents = XmlUtil.fixLinkTag(contents)

          Ok(fixedContents, Map("Content-type" -> "text/xml"))
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
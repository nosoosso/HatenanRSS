package com.example.app

import java.net.URL

import org.scalatra._

class MyScalatraServlet extends HatenanrssStack {

  notFound {
    "404 Not Found"
  }

  get("/rss") {
    params.get("rssUrl") match {
      case None => redirect(url("/"))
      case Some(rssUrl) =>
        try {
          val contents = xml.XML.load(new URL(rssUrl))

          if (contents.size > 1000)
            redirect("/")

          val fixedContents = XmlUtil.fixLinkTag(contents)


          ActionResult(ResponseStatus(200), fixedContents, Map("Content-type" -> "text/xml"))
        } catch {
          case e: Throwable => redirect("/")
        }
    }
  }

  get("/") {
    val action = url("rss")

    <html>
      <body>
        <h1>RSSのURLを入れてね</h1>
        <form action={action} method="get">
          <input type="text" name="rssUrl"/>
          <input type="submit" value="送信"/>
        </form>
      </body>
    </html>
  }
}

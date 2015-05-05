package com.example.app

import org.scalatra.test.specs2._
import org.specs2.mutable.Specification

/**
 * Created by nosoosso on 2015/04/27.
 */
class XmlUtilSpec extends Specification {
  "fixLinkTag" should {
    "replace the elements in link tag with Hatena Bookmark url" in {
      XmlUtil.fixLinkTag(sampleRss) must_== expected
    }
  }
  val sampleRss =
    <a>
      <b>
        <link>http://www.gizmode.jp</link>
      </b>
      <b>
        <link>http://www.yahoo.co.jp</link>
      </b>
    </a>

  val expected =
    <a>
      <b>
        <link>http://b.hatena.ne.jp/entry/www.gizmode.jp</link>
      </b>
      <b>
        <link>http://b.hatena.ne.jp/entry/www.yahoo.co.jp</link>
      </b>
    </a>
}
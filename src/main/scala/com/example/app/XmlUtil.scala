package com.example.app

import scala.xml.transform.{RewriteRule, RuleTransformer}
import scala.xml.{Elem, Node}

/**
 * Created by nosoosso on 2015/04/27.
 */
object XmlUtil {
  /**
   * 受け取ったXMLのlinkタグの内容をはてなブックマークのURLに変更する
   *
   * @param xml
   * @return
   */
  def fixLinkTag(xml: Node): Seq[Node] = {
    object replaceRule extends RewriteRule {
      override def transform(n: Node): Seq[Node] = n match {
        case e@Elem(prefix, "link", attr, scope, minEmpty, _*) =>
          <link>{fixUrl(e.text)}</link>
        case x => x
      }
    }
    object transformer extends RuleTransformer(replaceRule)

    transformer(xml)
  }

  private[this] def fixUrl(url: String): String = {

      if (url.startsWith("http://")) {
        val noProtocol = url.drop(7)
        "http://b.hatena.ne.jp/entry/" + noProtocol
      } else if (url.startsWith("https://")) {
        val noProtocol = url.drop(8)
        "http://b.hatena.ne.jp/entry/s/" + noProtocol
      }else{
        url
      }
  }
}

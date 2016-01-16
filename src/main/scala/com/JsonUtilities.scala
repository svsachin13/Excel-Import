package com

import org.json4s.{DefaultFormats, Extraction}
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._
/**
 * Created by sachin on 8/25/2015.
 * MyProject
 */
object JsonUtilities{
  implicit val formats = DefaultFormats

  implicit class CustomJson(jValue: JValue) {
    def asJson = compact(jValue)
  }
  implicit def convertToJValue(x: Any) = new CustomJson(Extraction.decompose(x))
}

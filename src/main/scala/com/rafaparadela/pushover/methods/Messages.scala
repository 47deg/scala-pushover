package com.rafaparadela.pushover.methods

import com.rafaparadela.pushover.HttpClient
import com.rafaparadela.pushover.Responses._
import com.rafaparadela.pushover.Priorities
import com.rafaparadela.pushover.Sounds

class Messages(httpClient: HttpClient, apiToken: String) {

  def post(
      user: String,
      message: String,
      device: Option[String] = None,
      title: Option[String] = None,
      url: Option[String] = None,
      url_title: Option[String] = None,
      priority: Option[Priorities.Value] = None,
      timestamp: Option[String] = None,
      sound: Option[Sounds.Value] = None): MessagePostResponse = {

    val requiredParams = Map("token" -> apiToken, "user" -> user, "message" -> message)

    val data = requiredParams ++
        device.map(x => Map("device" -> x)).getOrElse(Map.empty) ++
        title.map(x => Map("title" -> x)).getOrElse(Map.empty) ++
        url.map(x => Map("url" -> x)).getOrElse(Map.empty) ++
        url_title.map(x => Map("url_title" -> x)).getOrElse(Map.empty) ++
        priority.map(x => Map("priority" -> x.id.toString)).getOrElse(Map.empty) ++
        timestamp.map(x => Map("timestamp" -> x)).getOrElse(Map.empty) ++
        sound.map(x => Map("sound" -> x.toString)).getOrElse(Map.empty)


    println(data)

    val responseDict = httpClient.post("messages.json", data)

    MessagePostResponse(
      (responseDict \ "status").as[Int],
      (responseDict \ "request").as[String])
  }
}

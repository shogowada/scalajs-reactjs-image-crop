package io.github.shogowada.scalajs.reactjs.image.crop.example

import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.scalajs.reactjs.events.SyntheticEvent
import io.github.shogowada.scalajs.reactjs.image.crop.{Crop, ReactCrop}
import io.github.shogowada.scalajs.reactjs.{React, ReactDOM}
import org.scalajs.dom
import org.scalajs.dom.Event
import org.scalajs.dom.raw.{HTMLCanvasElement, HTMLImageElement}

import scala.scalajs.js.JSApp

object Main extends JSApp {
  override def main(): Unit = {
    val mountNode = dom.document.createElement("div")
    dom.document.body.appendChild(mountNode)
    ReactDOM.render(
      <(Example.reactClass).empty,
      mountNode
    )
  }
}

object Example {
  /*
  * Import ReactCrop._ to access ReactCrop component and its props.
  *
  * Also make sure to include node_modules/react-image-crop/dist/ReactCrop.css.
  * */
  import ReactCrop._

  case class State(
      crop: Crop,
      previewURL: Option[String]
  )

  lazy val InitialState = State(
    crop = new Crop {
      override val x = 20
      override val y = 20
      override val width = 60
      override val height = 60
      override val aspect = 1
    },
    previewURL = None
  )

  type Self = React.Self[Unit, State]

  lazy val reactClass: ReactClass = React.createClass[Unit, State](
    getInitialState = (self) => InitialState,
    render = (self) => {
      val url = "sample.jpg"
      <.div()(
        <.div()(
          <.ReactCrop(
            ^.src := url,
            ^.crop := self.state.crop,
            ^.keepSelection := true,
            ^.onChange := ((crop: Crop) => self.setState(_.copy(crop = crop))),
            ^.onImageLoaded := ((crop: Crop) => self.setState(_.copy(crop = crop)))
          )()
        ),
        <.div()(
          <.div()(
            <.button(^.onClick := onCropClick(self, url))("Crop")
          ),
          self.state.previewURL.map(previewURL =>
            <.img(^.src := previewURL)()
          )
        )
      )
    }
  )

  /*
  * react-image-crop extracts where to crop, but it leaves the actual cropping
  * to the user. This is one way of cropping.
  * */
  private def onCropClick(self: Self, url: String) =
    (event: SyntheticEvent) => {
      event.preventDefault()

      val crop = self.state.crop
      dom.console.log(crop)

      val image: HTMLImageElement = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
      image.onload = (event: Event) => {
        val imageWidth = image.naturalWidth
        val imageHeight = image.naturalHeight

        val x: Int = Math.floor((crop.x.get * imageWidth) / 100).toInt
        val y: Int = Math.floor((crop.y.get * imageHeight) / 100).toInt
        val width: Int = Math.floor((crop.width.get * imageWidth) / 100).toInt
        val height: Int = Math.floor((crop.height.get * imageHeight) / 100).toInt

        val canvas: HTMLCanvasElement = dom.document.createElement("canvas").asInstanceOf[HTMLCanvasElement]
        canvas.width = width
        canvas.height = height
        val context = canvas.getContext("2d")
        context.drawImage(image, x, y, width, height, 0, 0, width, height)

        val dataURL: String = canvas.toDataURL("image/jpeg")
        self.setState(_.copy(previewURL = Option(dataURL)))
      }
      image.src = url
    }
}

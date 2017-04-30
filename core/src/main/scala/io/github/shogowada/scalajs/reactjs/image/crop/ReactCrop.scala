package io.github.shogowada.scalajs.reactjs.image.crop

import io.github.shogowada.scalajs.reactjs.EventVirtualDOMAttributes.OnFormEventAttribute
import io.github.shogowada.scalajs.reactjs.VirtualDOM.VirtualDOMElements.ReactClassElementSpec
import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.statictags.{Attribute, AttributeSpec, BooleanAttributeSpec, IntegerAttributeSpec}
import org.scalajs.dom.html.Image

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, ScalaJSDefined}

@js.native
@JSImport("react-image-crop", JSImport.Default)
object NativeReactCrop extends ReactClass

@ScalaJSDefined
trait Crop extends js.Object {
  val x: js.UndefOr[Double] = js.undefined
  val y: js.UndefOr[Double] = js.undefined
  val width: js.UndefOr[Double] = js.undefined
  val height: js.UndefOr[Double] = js.undefined
  val aspect: js.UndefOr[Double] = js.undefined
}

object ReactCrop {

  implicit class ReactImageCropVirtualDOMElements(elements: VirtualDOMElements) {
    lazy val ReactCrop: ReactClassElementSpec = elements(NativeReactCrop)
  }

  object ReactImageCropVirtualDOMAttributes {

    import VirtualDOMAttributes.Type._

    type OnChange = js.Function1[Crop, _]
    type OnChangeWithPixelCrop = js.Function2[Crop, Crop, _]
    type OnImageLoadedWithoutImage = js.Function1[Crop, _]
    type OnImageLoaded = js.Function2[Crop, Image, _]
    type OnImageLoadedWithPixelCrop = js.Function3[Crop, Image, Crop, _]

    case class CropAttributeSpec(name: String) extends AttributeSpec {
      def :=(crop: Crop): Attribute[Crop] =
        Attribute(name = name, value = crop, AS_IS)
    }

    case class OnReactCropEventAttribute(name: String) extends AttributeSpec {
      def :=(onChange: (Crop) => _): Attribute[OnChange] =
        Attribute(name = name, value = onChange, AS_IS)
      def :=(onChange: (Crop, Crop) => _): Attribute[OnChangeWithPixelCrop] =
        Attribute(name = name, value = onChange, AS_IS)
    }

    case class OnReactCropImageLoadedAttribute(name: String) extends AttributeSpec {
      def :=(onImageLoaded: (Crop) => _): Attribute[OnImageLoadedWithoutImage] =
        Attribute(name = name, value = onImageLoaded, AS_IS)
      def :=(onImageLoaded: (Crop, Image) => _): Attribute[OnImageLoaded] =
        Attribute(name = name, value = onImageLoaded, AS_IS)
      def :=(onImageLoaded: (Crop, Image, Crop) => _): Attribute[OnImageLoadedWithPixelCrop] =
        Attribute(name = name, value = onImageLoaded, AS_IS)
    }
  }

  implicit class ReactImageCropVirtualDOMAttributes(attributes: VirtualDOMAttributes) {

    import ReactImageCropVirtualDOMAttributes._

    lazy val crop = CropAttributeSpec("crop")
    lazy val keepSelection = BooleanAttributeSpec("keepSelection")
    lazy val maxHeight = IntegerAttributeSpec("maxHeight")
    lazy val maxWidth = IntegerAttributeSpec("maxWidth")
    lazy val minHeight = IntegerAttributeSpec("minHeight")
    lazy val minWidth = IntegerAttributeSpec("minWidth")
    lazy val onComplete = OnReactCropEventAttribute("onComplete")
    lazy val onImageLoaded = OnReactCropImageLoadedAttribute("onImageLoaded")
  }

  implicit class ReactImageCropOnChangeAttribute(attribute: OnFormEventAttribute[_]) {

    import ReactImageCropVirtualDOMAttributes._

    def :=(onChange: OnChange): Attribute[OnChange] =
      Attribute(name = attribute.name, value = onChange, VirtualDOMAttributes.Type.AS_IS)
    def :=(onChange: OnChangeWithPixelCrop): Attribute[OnChangeWithPixelCrop] =
      Attribute(name = attribute.name, value = onChange, VirtualDOMAttributes.Type.AS_IS)
  }
}

package uz.mahmudxon.lodingcat

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator

class LoadingCat : View {
    //    private final static int PAW_WIDTH = 64;
    // private final static int PAW_LIGHT_WIDTH = 92;
    var drawingRect: Rect? = null
    var drawingRectF: RectF? = null
    var internalRectF: RectF? = null
    var internalLeftRectF: RectF? = null
    var internalRightRectF: RectF? = null
    var internalMiddleRecrF: RectF? = null
    var strokePaint: Paint? = null
    var fillPaint: Paint? = null
    var bodyPatint: Paint? = null
    var pawFill: Paint? = null
    var pawStroke: Paint? = null
    var pawLightStroke: Paint? = null
    var tailStroke: Paint? = null
    var mouthStroke: Paint? = null
    var rotateMatrix: Matrix? = null
    var bodyLength = 30f
    var alph = 30f
    var pointer = 0f
    var bodyColor = 0
    var strokeColor = 0
    var bodyLightColor = 0
    var bodyDarkColor = 0
    var path: Path? = null
    var point: Point? = null
    var foreheadMargin = 0
    var eyeMarging = 0
    var mouthMargin = 0

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        drawingRect = Rect()
        drawingRectF = RectF()
        internalRectF = RectF()
        internalLeftRectF = RectF()
        internalRightRectF = RectF()
        internalMiddleRecrF = RectF()
        bodyColor = ContextCompat.getColor(context, R.color.bodyColor)
        bodyLightColor = ContextCompat.getColor(context, R.color.bodyLightColor)
        strokeColor = ContextCompat.getColor(context, R.color.strokeColor)
        bodyDarkColor = ContextCompat.getColor(context, R.color.bodyDarkColor)
        strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        strokePaint!!.color = strokeColor
        strokePaint!!.style = Paint.Style.STROKE
        strokePaint!!.strokeCap = Paint.Cap.ROUND
        strokePaint!!.strokeJoin = Paint.Join.ROUND
        mouthStroke = Paint(Paint.ANTI_ALIAS_FLAG)
        mouthStroke!!.color = strokeColor
        mouthStroke!!.style = Paint.Style.STROKE
        mouthStroke!!.strokeCap = Paint.Cap.ROUND
        mouthStroke!!.strokeJoin = Paint.Join.ROUND
        fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        fillPaint!!.color = bodyColor
        pawStroke = Paint(Paint.ANTI_ALIAS_FLAG)
        pawStroke!!.color = strokeColor
        pawStroke!!.style = Paint.Style.STROKE
        pawStroke!!.strokeCap = Paint.Cap.ROUND
        pawStroke!!.strokeJoin = Paint.Join.ROUND
        pawFill = Paint(Paint.ANTI_ALIAS_FLAG)
        pawFill!!.color = bodyColor
        pawFill!!.style = Paint.Style.STROKE
        pawFill!!.strokeCap = Paint.Cap.ROUND
        pawFill!!.strokeJoin = Paint.Join.ROUND
        pawLightStroke = Paint(Paint.ANTI_ALIAS_FLAG)
        pawLightStroke!!.color = bodyLightColor
        pawLightStroke!!.style = Paint.Style.STROKE
        pawLightStroke!!.strokeCap = Paint.Cap.ROUND
        pawLightStroke!!.strokeJoin = Paint.Join.ROUND
        bodyPatint = Paint(Paint.ANTI_ALIAS_FLAG)
        bodyPatint!!.color = bodyColor
        bodyPatint!!.style = Paint.Style.STROKE
        bodyPatint!!.strokeCap = Paint.Cap.BUTT
        tailStroke = Paint(Paint.ANTI_ALIAS_FLAG)
        tailStroke!!.color = bodyDarkColor
        tailStroke!!.style = Paint.Style.STROKE
        tailStroke!!.strokeCap = Paint.Cap.ROUND
        tailStroke!!.strokeJoin = Paint.Join.ROUND
        rotateMatrix = Matrix()
        path = Path()
        point = Point()
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizeHalf = Math.min(measuredHeight, measuredWidth) shr 1
        val sizeQuad = (sizeHalf * 0.42f).toInt()
        val sizeQuadHalf = sizeQuad shr 1
        val cX = measuredWidth shr 1
        val cY = measuredHeight shr 1
        val padding = (strokePaint!!.strokeWidth * 0.5f + sizeQuad * 0.1f).toInt()
        val strokeHalf = pawStroke!!.strokeWidth / 2 - strokePaint!!.strokeWidth / 2
        val strokeWidth = Math.min(measuredHeight, measuredWidth) * 0.024f
        drawingRect!![cX - sizeHalf + padding, cY - sizeHalf + padding, cX + sizeHalf - padding] =
            cY + sizeHalf - padding
        drawingRectF!!.set(drawingRect!!)
        internalRectF!![(
                drawingRect!!.left + sizeQuad).toFloat(), (
                drawingRect!!.top + sizeQuad).toFloat(), (
                drawingRect!!.right - sizeQuad).toFloat()] = (
                drawingRect!!.bottom - sizeQuad
                ).toFloat()
        val earSize = (drawingRect!!.right - internalRectF!!.right).toInt() / 4
        internalLeftRectF!![internalRectF!!.left - strokeHalf, internalRectF!!.top - strokeHalf, internalRectF!!.right + strokeHalf] =
            internalRectF!!.bottom + strokeHalf
        internalRightRectF!![drawingRectF!!.left + strokeHalf, drawingRectF!!.top + strokeHalf, drawingRectF!!.right - strokeHalf] =
            drawingRectF!!.bottom - strokeHalf
        internalMiddleRecrF!![(
                drawingRect!!.left + sizeQuadHalf).toFloat(), (
                drawingRect!!.top + sizeQuadHalf).toFloat(), (
                drawingRect!!.right - sizeQuadHalf).toFloat()] = (
                drawingRect!!.bottom - sizeQuadHalf
                ).toFloat()
        pawStroke!!.strokeWidth = earSize.toFloat()
        pawFill!!.strokeWidth = earSize - strokeWidth
        tailStroke!!.strokeWidth = earSize - strokeWidth
        pawLightStroke!!.strokeWidth = sizeQuadHalf.toFloat()
        bodyPatint!!.strokeWidth = drawingRect!!.right - internalRectF!!.right - strokeWidth / 2
        strokePaint!!.strokeWidth = strokeWidth
        mouthStroke!!.strokeWidth = strokeWidth / 3
        foreheadMargin = (sizeHalf * Math.tan(Math.toRadians(5.0))).toInt()
        eyeMarging = (sizeHalf * Math.tan(Math.toRadians(9.0))).toInt()
        mouthMargin = foreheadMargin shl 1
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val pX = drawingRect!!.centerX()
        val pY = drawingRect!!.centerY()
        val sizeHalf = drawingRect!!.height() shr 1
        val internalSizeHalf = internalRectF!!.height().toInt() shr 1
        val leftSizeHalf = internalLeftRectF!!.height().toInt() shr 1
        val rightSizeHalf = internalRightRectF!!.height().toInt() shr 1
        val earSize = (drawingRectF!!.bottom - internalRectF!!.bottom).toInt() / 4
        val interanlAr =
            Math.asin(Math.sin(Math.toRadians(7.0) * sizeHalf / internalSizeHalf)).toFloat()
        val interanlA = Math.toDegrees(interanlAr.toDouble()).toFloat()
        val leftAr = Math.asin(Math.sin(Math.toRadians(7.0) * sizeHalf / leftSizeHalf)).toFloat()
        val leftA = Math.toDegrees(leftAr.toDouble()).toFloat()
        val rightAr = Math.asin(Math.sin(Math.toRadians(7.0) * sizeHalf / rightSizeHalf)).toFloat()
        val rightA = Math.toDegrees(rightAr.toDouble()).toFloat()
        bodyLength =
            interpolator.getInterpolation(if (pointer < 360) pointer / 360f else (720f - pointer) / 360f) * 180 + BODY_SMALLEST_LENGTH
        alph = interpolator.getInterpolation(pointer / 720f) * -720 + 50
        pointer += SPEED
        canvas.rotate(alph, pX.toFloat(), pY.toFloat())
        canvas.save()
        if (pointer > 720) pointer -= 720f
        rotateMatrix!!.setTranslate(0f, foreheadMargin.toFloat())
        point!![pX + sizeHalf - earSize] = pY

        //head
        path!!.arcTo(drawingRectF!!, HEAD_LENGTH.toFloat(), -HEAD_LENGTH.toFloat())
        transformPoint(point, rotateMatrix)
        path!!.lineTo(point!!.x.toFloat(), point!!.y.toFloat())
        point!![pX + internalSizeHalf + earSize] = pY
        transformPoint(point, rotateMatrix)
        path!!.lineTo(point!!.x.toFloat(), point!!.y.toFloat())
        path!!.arcTo(internalRectF!!, 0f, HEAD_LENGTH - 7 + interanlA)
        canvas.drawPath(path!!, strokePaint!!)
        path!!.close()
        canvas.drawPath(path!!, fillPaint!!)

        //tail
        canvas.drawArc(
            internalMiddleRecrF!!,
            HEAD_LENGTH + bodyLength - 30,
            40f,
            false,
            pawStroke!!
        )
        canvas.drawArc(
            internalMiddleRecrF!!,
            HEAD_LENGTH + bodyLength - 30,
            40f,
            false,
            tailStroke!!
        )
        //back paws
        canvas.drawArc(internalLeftRectF!!, HEAD_LENGTH.toFloat(), bodyLength, false, pawStroke!!)
        canvas.drawArc(internalRightRectF!!, HEAD_LENGTH.toFloat(), bodyLength, false, pawStroke!!)
        canvas.drawArc(
            internalLeftRectF!!,
            (HEAD_LENGTH - 10).toFloat(),
            bodyLength + 10,
            false,
            pawFill!!
        )
        canvas.drawArc(
            internalRightRectF!!,
            (HEAD_LENGTH - 10).toFloat(),
            bodyLength + 10,
            false,
            pawFill!!
        )

        //body
        canvas.drawArc(
            internalMiddleRecrF!!,
            (HEAD_LENGTH - 20).toFloat(),
            bodyLength + 10,
            false,
            bodyPatint!!
        )
        canvas.drawArc(
            internalMiddleRecrF!!,
            (HEAD_LENGTH - 20).toFloat(),
            bodyLength - 15,
            false,
            pawLightStroke!!
        )
        canvas.save()
        canvas.rotate(bodyLength + HEAD_LENGTH - 10, pX.toFloat(), pY.toFloat())
        val pawPadding =
            pawFill!!.strokeWidth + (pawStroke!!.strokeWidth - pawFill!!.strokeWidth) * 0.3f
        strokePaint!!.strokeWidth = strokePaint!!.strokeWidth / 2
        canvas.drawLine(
            pX + internalSizeHalf + pawPadding, pY.toFloat(),
            pX + sizeHalf - pawPadding, pY.toFloat(), strokePaint!!
        )
        strokePaint!!.strokeWidth = strokePaint!!.strokeWidth * 2
        canvas.restore()

        //face paws
        canvas.drawArc(internalLeftRectF!!, (HEAD_LENGTH - 17).toFloat(), 15f, false, pawStroke!!)
        canvas.drawArc(internalRightRectF!!, (HEAD_LENGTH - 17).toFloat(), 15f, false, pawStroke!!)
        canvas.drawArc(
            internalLeftRectF!!,
            (HEAD_LENGTH - 17 - 10).toFloat(),
            (15 + 10).toFloat(),
            false,
            pawFill!!
        )
        canvas.drawArc(
            internalRightRectF!!,
            (HEAD_LENGTH - 17 - 10).toFloat(),
            (15 + 10).toFloat(),
            false,
            pawFill!!
        )
        canvas.restore()
        canvas.save()
        canvas.translate(0f, eyeMarging.toFloat())
        canvas.rotate(3f, pX.toFloat(), pY.toFloat())
        var rightEyeX = pX + sizeHalf - earSize - strokePaint!!.strokeWidth / 4
        var leftEyeX = pX + internalSizeHalf + earSize - strokePaint!!.strokeWidth / 4
        var distanceEyeX = rightEyeX - leftEyeX
        val distanceEyeQuadX = (rightEyeX - leftEyeX) / 4
        var centerEyeX = leftEyeX + distanceEyeX / 2
        val earSizeHalf = earSize shr 1
        canvas.drawPoint(leftEyeX, pY.toFloat(), strokePaint!!)
        canvas.drawPoint(rightEyeX, pY.toFloat(), strokePaint!!)
        canvas.translate(0f, foreheadMargin.toFloat())
        //mouth
        path!!.reset()
        path!!.moveTo(leftEyeX + distanceEyeQuadX, pY.toFloat())
        path!!.quadTo(
            centerEyeX - distanceEyeQuadX / 2,
            pY + distanceEyeQuadX * 0.7f,
            centerEyeX,
            pY.toFloat()
        )
        path!!.quadTo(
            centerEyeX + distanceEyeQuadX / 2,
            pY + distanceEyeQuadX * 0.7f,
            rightEyeX - distanceEyeQuadX,
            pY.toFloat()
        )
        canvas.drawPath(path!!, mouthStroke!!)
        path!!.reset()
        canvas.restore()
        canvas.rotate(20f, pX.toFloat(), pY.toFloat())
        canvas.rotate(10f, centerEyeX, pY.toFloat())
        canvas.save()
        rightEyeX = (pX + sizeHalf - earSize).toFloat()
        leftEyeX = (pX + internalSizeHalf + earSize).toFloat()
        distanceEyeX = rightEyeX - leftEyeX
        centerEyeX = leftEyeX + distanceEyeX / 2
        for (i in 0..2) {
            canvas.restore()
            canvas.save()
            canvas.rotate((-10 * i).toFloat(), centerEyeX, pY.toFloat())
            canvas.drawLine(
                (pX + internalSizeHalf - earSizeHalf).toFloat(),
                pY.toFloat(),
                (pX + internalSizeHalf + earSizeHalf / 2).toFloat(),
                pY.toFloat(),
                mouthStroke!!
            )
            canvas.restore()
            canvas.save()
            canvas.rotate((10 * i - 30).toFloat(), centerEyeX, pY.toFloat())
            canvas.drawLine(
                (pX + sizeHalf - earSizeHalf / 2).toFloat(),
                pY.toFloat(),
                (pX + sizeHalf + earSizeHalf).toFloat(),
                pY.toFloat(),
                mouthStroke!!
            )
        }
        canvas.restore()
        // canvas.drawPath(path,strokePaint);
        invalidate()
        //canvas.drawCircle(pX, pY, sizeHalf, strokePaint);
    }

    fun transformPoint(point: Point?, matrix: Matrix?) {
        val pts = FloatArray(2)
        pts[0] = point!!.x.toFloat()
        pts[1] = point.y.toFloat()
        matrix!!.mapPoints(pts)
        point[pts[0].toInt()] = pts[1].toInt()
    }

    companion object {
        private const val HEAD_LENGTH = 60
        private const val BODY_SMALLEST_LENGTH = 30
        private const val SPEED = 4f
        private const val SPEED_HALF = SPEED / 2
        private val interpolator: Interpolator = AccelerateDecelerateInterpolator()
    }
}
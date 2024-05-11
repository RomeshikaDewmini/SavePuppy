package com.example.savepuppy

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class GameView(var c: Context, var gameTask: GameTask, private val sharedPreferences: SharedPreferences): View(c)
{
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var puppyPosition = 0
    private val otherStones =   ArrayList<HashMap<String,Any>>()

    var viewWidth = 0
    var viewHeight = 0

    init{
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if(time % 700 < 10 +speed){
            val map = HashMap<String,Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherStones.add(map)

        }
        time = time + 10 + speed
        val stoneWidth = 200
        val stoneHeight = 200
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.puppy, null)

        d.setBounds(
            puppyPosition* viewWidth / 3,
            viewHeight - 2 - stoneHeight,
            puppyPosition * viewWidth / 3 + stoneWidth,
            viewHeight - 2
        )

        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for(i in otherStones.indices){
            try {
                val stoneX = otherStones[i]["lane"] as Int * viewWidth / 3
                val stoneY = time - otherStones[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.stone, null)

                d2.setBounds(
                    stoneX + 25,
                    stoneY - stoneHeight,
                    stoneX + stoneWidth - 25,
                    stoneY
                )

                d2.draw(canvas)
                if (otherStones[i]["lane"] as Int == puppyPosition) {



                    if (stoneY > viewHeight - 2 - stoneHeight
                        && stoneY < viewHeight -2 ) {


                        gameTask.closeGame(score)
                    }
                }
                if (stoneY > viewHeight + stoneHeight) {
                    otherStones.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)


                    if (score > highScore) {
                        highScore = score
                    }
                }

            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score: $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed: $speed", 380f, 80f, myPaint!!)
        canvas.drawText("High Score: $highScore", 680f, 80f, myPaint!!)
        invalidate()





    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                if (x < viewWidth / 2) {
                    if (puppyPosition > 0) {
                        puppyPosition--
                    }
                }
                if (x > viewWidth / 2) {
                    if (puppyPosition < 2) {
                        puppyPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }


    companion object {
        private const val PREF_HIGH_SCORE_KEY = "high_score"
    }


}
package com.first75494.flyingfish;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFish extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth,canvasHeight;

    private int yellowX,yellowY,yellowSpeed = 13;
    private Paint yellowPaint = new Paint();

    private int greenX,greenY,greenSpeed = 15;
    private Paint greenPaint = new Paint();

    private int redX,redY,redSpeed = 20;
    private Paint redPaint = new Paint();

    private boolean touch = false;

    private Bitmap backgroundImage;
    private Paint scorePoint=new Paint();
    private Bitmap life[]=new Bitmap[2];

    private int score, lifeCounter;
    public FlyingFish(Context context) {
        super(context);


        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePoint.setColor(Color.WHITE);
        scorePoint.setTextSize(70);
        scorePoint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePoint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounter=3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight()*3;
        fishY = fishY + fishSpeed;

        if(fishY<minFishY){
            fishY = minFishY;
        }
        if(fishY>maxFishY){
            fishY = maxFishY;
        }
        fishSpeed=fishSpeed+2;
        if(touch){
            canvas.drawBitmap(fish[1], fishX, fishY,null);
            touch = false;
        }else{
            canvas.drawBitmap(fish[0], fishX, fishY,null);
        }


        yellowX = yellowX-yellowSpeed;

        if(hitBallChecker(yellowX,yellowY)){
            score += 10;
            yellowX = -100;
        }

        if(yellowX<0){
            yellowX = canvasWidth +21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY-minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);


        greenX = greenX-greenSpeed;

        if(hitBallChecker(greenX,greenY)){
            score += 15;
            greenX = -100;
        }

        if(greenX<0){
            greenX= canvasWidth +21;
            greenY = (int) Math.floor(Math.random() * (maxFishY-minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX,greenY,35,greenPaint);

        redX = redX-redSpeed;

        if(hitBallChecker(redX,redY)){
            redX = -100;
            lifeCounter--;
            if(lifeCounter == 0){
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();

                Intent gameOver = new Intent(getContext(),GameOverActivity.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOver.putExtra("Score",score);
                getContext().startActivity(gameOver);
            }
        }

        if(redX<0){
            redX = canvasWidth +21;
            redY = (int) Math.floor(Math.random() * (maxFishY-minFishY)) + minFishY;
        }
        canvas.drawCircle(redX,redY,30,redPaint);


        canvas.drawText("Score : " +score,20,90,scorePoint); //coordinates of Score.

        for(int i=0;i<3;i++){
            int x =(int) (450 + life[0].getWidth() * 1.2 * i);
            int y=30;

            if(i<lifeCounter){
                canvas.drawBitmap(life[0],x,y,null);  //To Display The Red Heart.
            }else{
                canvas.drawBitmap(life[1],x,y,null); //To Display Grey Heart When Fish Touches The Red Ball
            }
        }
    }

    public boolean hitBallChecker(int x,int y){
        if(fishX<x && x<(fishX + fish[0].getWidth()) && fishY<y && y<(fishY + fish[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;

            fishSpeed = -22;
        }

        return  true;
    }
}

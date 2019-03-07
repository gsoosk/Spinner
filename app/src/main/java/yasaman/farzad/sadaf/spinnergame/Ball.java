package yasaman.farzad.sadaf.spinnergame;

import android.util.Log;

public class Ball {
    private float x0;
    private float y0;
    private float x;
    private float y;
    private float vx0;
    private float vy0;
    private float v;
    private long t0;
    private float windowWidth;
    private float windowHeight;
    public Ball(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.t0 = Config.getTime();
        this.vx0 = 0;
        this.vy0 = 0;

    }
    public void setWindow(float windowWidth, float windowHeight)
    {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }
    public void gravityUpdate(float xGravity, float yGravity, float zGravity)
    {
        xGravity = -xGravity;
        long t = Config.getTime();
        float deltaT = (float) (t - t0) / 1000 ;

        float nx = xGravity * Config.M;
        float ny = yGravity * Config.M;

        float n_force = zGravity * Config.M;

        float friction_s, friction_m, friction_m_nx, friction_m_ny;


        friction_m = n_force * Config.M_K;
        friction_s = n_force * Config.M_S;

        friction_m_nx = (xGravity > 0) ?  - friction_m  : friction_m ;
        friction_m_ny = (yGravity > 0) ?  - friction_m :  friction_m;

        float ax, ay;
        ax = xGravity + (friction_m_nx / Config.M);
        ay = yGravity + (friction_m_ny / Config.M);


        if(vx0 != 0 || nx - friction_s > 0)
        {
            if(vx0 > 0 && ax < 0 && vx0 <0.1 || vx0 < 0 && ax > 0 && vx0 > -0.1)
             vx0 = 0;
            x = (float) (((0.5 * ax * deltaT * deltaT) + (vx0 * deltaT)) * Config.METER + x0) ;
            vx0 = ax * deltaT + vx0;

        }

        if(vy0 != 0 || ny - friction_s > 0)
        {
            if(vy0 > 0 && ax < 0 && vy0 <0.1 || vy0 < 0 && ax > 0 && vy0 > -0.1)
                vy0 = 0;
            y = (float) (((0.5 * ay * deltaT * deltaT) + (vy0 * deltaT)) * Config.METER + y0) ;
            vy0 = ay * deltaT + vy0;

        }

        if( x + Config.BALL_SIZE/2 > windowWidth/2 ||  x - Config.BALL_SIZE/2 < -windowWidth/2)
            vx0 = -vx0;
        if( y + Config.BALL_SIZE/2 > windowHeight/2 ||  y - Config.BALL_SIZE/2 < -windowHeight/2)
            vy0 = -vy0;

        x0 = x ;
        y0 = y;
        t0 = t;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVy0() {
        return vy0;
    }
}

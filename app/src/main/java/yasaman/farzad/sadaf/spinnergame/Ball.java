package yasaman.farzad.sadaf.spinnergame;

import android.util.Log;

public class Ball {
    private float x0;
    private float y0;
    private float x;
    private float y;
    private float vx0;
    private float vy0;
    private long t0;
    private float windowWidth;
    private float windowHeight;
    private float tethaX;
    private float tethaY;
    private float tethaZ;
    private long t;
    private boolean updated;

    public Ball(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.t0 = Config.getTime();
        this.vx0 = 0;
        this.vy0 = 0;
        this.tethaX = 0;
        this.tethaY = 0;
        this.tethaZ = 0;

    }
    public void setWindow(float windowWidth, float windowHeight)
    {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

    public void gyroUpdate(float xGyro, float yGyro, float zGyro)
    {
        t = Config.getTime();
        updated = true;
        float deltaT = (float) (t - t0) / 1000 ;
        float newTethaX = yGyro * deltaT + tethaX;
        float newTethaY = xGyro * deltaT + tethaY;
        float newTethaZ = zGyro * deltaT + tethaZ;

        float newXGravity = Config.G * (float)Math.sin(newTethaX);
        float newYGravity = Config.G * (float)Math.sin(newTethaY);
        float newZGravity = Config.G * (float)Math.cos(newTethaZ);

        tethaX = newTethaX;
        tethaY = newTethaY;
        tethaZ = newTethaZ;

        gravityUpdate( -newXGravity, newYGravity, newZGravity);

    }
    public void gravityUpdate(float xGravity, float yGravity, float zGravity)
    {
        xGravity *= Config.METER ;
        zGravity *= Config.METER ;
        yGravity *= Config.METER ;

        xGravity = -xGravity;
        if(!updated)
            t = Config.getTime();
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


        boolean canMoveX = false;
        if(vx0 < 5 && vx0 > -5)
        {
            if(Math.abs(nx)> Math.abs(friction_s))
            {
                canMoveX = true;
            }
        }
        else
        {
            canMoveX = true;
        }

        boolean canMoveY = false;
        if(vy0 < 5 && vy0 > -5)
        {
            if(Math.abs(ny)> Math.abs(friction_s))
            {
                canMoveY = true;
            }
        }
        else
        {
            canMoveY = true;
        }

        if(canMoveX)
        {
            float newX = (float) (((0.5 * ax * deltaT * deltaT) + (vx0 * deltaT)) + x0) ;
            if( newX + Config.BALL_SIZE/2 > windowWidth/2 ||  newX - Config.BALL_SIZE/2 < -windowWidth/2)
            {
                vx0 = 0;
            }
            else
            {
                x = newX ;
                vx0 = ax * deltaT + vx0;
            }
        }
        else
        {
            vx0 = 0;
        }

        if(canMoveY)
        {
            float newY = (float) (((0.5 * ay * deltaT * deltaT) + (vy0 * deltaT)) + y0) ;
            if( newY + Config.BALL_SIZE/2 > windowHeight/2 ||  newY - Config.BALL_SIZE/2 < -windowHeight/2)
            {
                vy0 = 0;
            }
            else
            {
                y = newY;
                vy0 = ay * deltaT + vy0;
            }

        }
        else
        {
            vy0 = 0;
        }

        x0 = x ;
        y0 = y;
        t0 = t;
        updated = false;
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

    public float getVx0() {
        return vx0;
    }
}

package com.mygdx.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.Components;
import com.mygdx.game.components.SteerableControlComponent;
import com.mygdx.game.utils.SteeringVelocity;
import com.mygdx.game.utils.graph.ConnectGridPoint;
import com.mygdx.game.utils.graph.PointGraph6;

public class RenderDebugSystem extends EntitySystem{

    private final float W = 10;
    private final float H = W * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private final OrthographicCamera camera = new OrthographicCamera(W,H);

    private final ShapeRenderer renderer = new ShapeRenderer();

    private final float [] localVertices = new float[12];
    private final float [] globalVertices = new float[12];

    private final float ZOOM_ACCELERATION = 1;
    private final float ZOOM_MAX_VELOCITY = 2;
    private final float MAX_ZOOM = 4;
    private final float MIN_ZOOM = 0.1f;

    private Entity player = null;

    private float zoomVelocity = 0;
    private float zoomDir = 0;

    Vector2 tmp = new Vector2();

    public RenderDebugSystem(Entity player){
        this();
        this.player = player;
    }

    public RenderDebugSystem(){
        createLocal();
    }

    private void createLocal(){
        float sin = Holder.r * (float)Math.sin(Math.PI/3);
        float cos =Holder.r * (float)Math.cos(Math.PI/3);


        localVertices[0] = cos;
        localVertices[1] = sin;

        localVertices[2] = -cos;
        localVertices[3] = sin;

        localVertices[4] = -Holder.r;
        localVertices[5] = 0;

        localVertices[6] = -cos;
        localVertices[7] = -sin;

        localVertices[8] = cos;
        localVertices[9] = -sin;

        localVertices[10] = Holder.r;
        localVertices[11] = 0;
    }

    private void setGlobalVertices(float x, float y){
        for (int i=0;i<globalVertices.length; i+=2){
            globalVertices[i] = localVertices[i] + x;
            globalVertices[i+1] = localVertices[i+1] + y;
        }
    }

    private void renderGridPoint(ConnectGridPoint point){
        Vector2 v = Holder.basis.getPos(point.x,point.y);
        setGlobalVertices(v.x,v.y);
        renderer.polygon(globalVertices);
    }

    private void renderConnections(ConnectGridPoint point){
        Vector2 v = Holder.basis.getPos(point.x,point.y);

        float x = v.x;
        float y = v.y;
        if (point.hasConnection(1)){
            v = Holder.basis.getPos(point.x,point.y+1);
            renderer.line(x,y,v.x,v.y);
        }
        if (point.hasConnection(2)){
            v = Holder.basis.getPos(point.x+1,point.y);
            renderer.line(x,y,v.x,v.y);

        }
        if (point.hasConnection(4)){
            v = Holder.basis.getPos(point.x+1,point.y-1);
            renderer.line(x,y,v.x,v.y);
        }
        if (point.hasConnection(8)){
            v = Holder.basis.getPos(point.x,point.y-1);
            renderer.line(x,y,v.x,v.y);
        }
        if (point.hasConnection(16)){
            v = Holder.basis.getPos(point.x-1,point.y);
            renderer.line(x,y,v.x,v.y);
        }
        if (point.hasConnection(32)){
            v = Holder.basis.getPos(point.x-1,point.y+1);
            renderer.line(x,y,v.x,v.y);
        }
    }

    private void renderWalls(ConnectGridPoint point){
        Vector2 v = Holder.basis.getPos(point.x,point.y);
        setGlobalVertices(v.x,v.y);

        if (!point.hasConnection(1)){
            renderer.line(globalVertices[0],globalVertices[1],globalVertices[2],globalVertices[3]);
        }
        if (!point.hasConnection(2)){
            renderer.line(globalVertices[0],globalVertices[1],globalVertices[10],globalVertices[11]);
        }
        if (!point.hasConnection(4)){
            renderer.line(globalVertices[10],globalVertices[11],globalVertices[8],globalVertices[9]);
        }
        if (!point.hasConnection(8)){
            renderer.line(globalVertices[8],globalVertices[9],globalVertices[6],globalVertices[7]);
        }
        if (!point.hasConnection(16)){
            renderer.line(globalVertices[6],globalVertices[7],globalVertices[4],globalVertices[5]);
        }
        if (!point.hasConnection(32)){
            renderer.line(globalVertices[4],globalVertices[5],globalVertices[2],globalVertices[3]);
        }

    }

    public void zoom(float scale){
        zoomDir = scale;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        ScreenUtils.clear(Color.BLACK);

        if (player!=null) {
            camera.position.set(Components.STEER.get(player).steerable.getPosition(),0);
        }
        if (zoomDir!=0){
            float zoom = camera.zoom + zoomDir * (zoomVelocity * deltaTime + ZOOM_ACCELERATION * deltaTime * deltaTime / 2);
            zoomVelocity += ZOOM_ACCELERATION * deltaTime;
            if (Math.abs(zoomVelocity) > ZOOM_MAX_VELOCITY){
                zoomVelocity = zoomVelocity>0 ? ZOOM_MAX_VELOCITY: -ZOOM_MAX_VELOCITY;
            }
            zoom = MathUtils.clamp(zoom,MIN_ZOOM,MAX_ZOOM);
            camera.zoom = zoom;
        }else{
            zoomVelocity = 0;
        }
        camera.update();

        box2DDebugRenderer.render(getEngine().getSystem(B2DSystem.class).getWorld(),camera.combined);
        PointGraph6 g = Holder.graph;
        if (g!=null){
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            for (ConnectGridPoint p: g.getNodes()) {
                renderer.setColor(Color.WHITE);
                //renderGridPoint(p);
                renderer.setColor(Color.FIREBRICK);
         //       renderConnections(p);
                renderer.setColor(Color.ROYAL);
                /*
                ROYAL
                CORAL
                PURPLE
                MAROON
                TEAL
                FIREBRICK
                 */
           //     renderWalls(p);
            }
            renderer.end();
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

}

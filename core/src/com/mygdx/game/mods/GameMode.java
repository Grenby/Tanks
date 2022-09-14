package com.mygdx.game.mods;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.mygdx.game.components.PhysicsComponent;
import com.mygdx.game.components.SteerableControlComponent;
import com.mygdx.game.components.SteerableComponent;
import com.mygdx.game.systems.B2DSystem;
import com.mygdx.game.systems.Holder;
import com.mygdx.game.systems.PlayerInputSystem;
import com.mygdx.game.systems.RenderDebugSystem;
import com.mygdx.game.utils.Basis;
import com.mygdx.game.utils.box2d.B2DBodyBuilder;
import com.mygdx.game.utils.box2d.B2DSteerable;
import com.mygdx.game.utils.graph.ConnectGridPoint;
import com.mygdx.game.utils.graph.DefConnection;
import com.mygdx.game.utils.graph.PointGraph6;
import com.mygdx.game.utils.graph.RegisterGraphToWorld;
import com.mygdx.game.utils.maze.MazeGenerator;

public class GameMode implements Screen {


    Engine engine = new Engine();

    void addUnits(World world){
        Body body;
        B2DBodyBuilder.BodyDefParam bodyDefParam;
        B2DBodyBuilder.FixtureDefParam fixtureDefParam;

        bodyDefParam = new B2DBodyBuilder.BodyDefParam()
                        .dynamicBody()
                        .setPos(0, 0);
        fixtureDefParam = new B2DBodyBuilder.FixtureDefParam()
                        .boxShape(0.2f,0.1f, 0, 0);

        body = B2DBodyBuilder.instance.bodyDef(world, bodyDefParam).addFixture(fixtureDefParam).build();

        SteerableControlComponent c = new SteerableControlComponent();

        Entity entity = new Entity()
                .add(new PhysicsComponent(body))
                .add(new SteerableComponent(new B2DSteerable(body)))
                .add(c);

        engine.addEntity(entity);
        engine.getSystem(RenderDebugSystem.class).setPlayer(entity);
        engine.getSystem(PlayerInputSystem.class).setPlayer(entity);
    }

    void drawRing(int x0, int y0, int r, ObjectSet<ConnectGridPoint> set){
        x0 -=r;
        set.add(new ConnectGridPoint(x0,y0));
        for (int i=0;i<r;i++){
            x0++;
            y0--;
            set.add(new ConnectGridPoint(x0,y0));
        }
        for (int i=0;i<r;i++){
            x0++;
            set.add(new ConnectGridPoint(x0,y0));
        }
        for (int i=0;i<r;i++){
            y0++;
            set.add(new ConnectGridPoint(x0,y0));
        }
        for (int i=0;i<r;i++){
            y0++;
            x0--;
            set.add(new ConnectGridPoint(x0,y0));
        }
        for (int i=0;i<r;i++){
            x0--;
            set.add(new ConnectGridPoint(x0,y0));
        }
        for (int i=0;i<r-1;i++){
            //x0--;
            y0--;
            set.add(new ConnectGridPoint(x0,y0));
        }
    }

    void input(){
        float move = 0;
        float rotate = 0;
        float zoom = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            move +=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rotate -=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move -=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            rotate +=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            zoom+=1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            zoom-=1;
        }

        PlayerInputSystem sys = engine.getSystem(PlayerInputSystem.class);
        sys.move(move);
        sys.rotate(rotate);
        engine.getSystem(RenderDebugSystem.class).zoom(zoom);
    }

    @Override
    public void show() {

        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new B2DSystem());
        engine.addSystem(new RenderDebugSystem());

        addUnits(engine.getSystem(B2DSystem.class).getWorld());


        ObjectSet<ConnectGridPoint> points = new ObjectSet<>();
        for (int i=1; i<20;i++){
            drawRing(0,0,i,points);
        }
        System.out.println(points.size);
        points.add(new ConnectGridPoint(0,0));
        //System.out.println(points.size);
        PointGraph6 graph6 = new PointGraph6(points){

            @Override
            public Array<Connection<ConnectGridPoint>> getConnections(ConnectGridPoint point) {
                for (Connection<ConnectGridPoint> c: connections) {
                    freeConnection.free((DefConnection<ConnectGridPoint>) c);
                }
                connections.clear();
                byte e = 1;
                for (int i = 0; i < 6; i++) {
                    DefConnection<ConnectGridPoint> c = freeConnection.obtain();
                    c.setFrom(point);
                    point2.set(point);
                    switch (e){
                        case 1: point2.add(0,1); break;
                        case 2: point2.add(1,0); break;
                        case 4: point2.add(1,-1); break;
                        case 8: point2.add(0,-1); break;
                        case 16: point2.add(-1,0); break;
                        case 32: point2.add(-1,1); break;
                    }
                    c.setTo(nodes.get(point2));
                    if (c.getToNode()!=null) {
                        connections.add(c);
                    } else{
                        freeConnection.free(c);
                    }
                    e<<=1;
                }
                return connections;
            }
        };
        PointGraph6 graph = new PointGraph6(points);
        new MazeGenerator<ConnectGridPoint>().generate(graph6,points.first(),points.size,graph);
        Holder.r = 1;
        Holder.graph = graph;
        Holder.basis = new Basis();
        float h = Holder.r * (float) Math.sqrt(3) / 2;
        Holder.basis.e1.set(2*h,0).rotateRad(MathUtils.PI/6);
        Holder.basis.e2.set(0,2*h);

        //System.out.println(points.get(new ConnectGridPoint(3,0)).getConnections());
        //System.out.println(points.get(new ConnectGridPoint(4,0)).getConnections());

        RegisterGraphToWorld registerGraphToWorld = new RegisterGraphToWorld();
        registerGraphToWorld.register6(engine.getSystem(B2DSystem.class).getWorld(),Holder.graph,Holder.basis);

        engine.createEntity();

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        input();
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

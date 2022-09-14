package com.mygdx.game.utils.graph;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.mygdx.game.systems.Holder;
import com.mygdx.game.utils.Basis;
import com.mygdx.game.utils.box2d.B2DBodyBuilder;

public class RegisterGraphToWorld {

    public ObjectMap<ConnectGridPoint, Integer> points = new ObjectMap<>();
    ObjectSet<ConnectGridPoint> set;

    Basis basis;
    ConnectGridPoint tmp = new ConnectGridPoint();

    private void addPoint(ConnectGridPoint point){
        int e = 1;
        for (int i=0;i<6;i++){
            if (!point.hasConnection(e)){
                points.put(point,points.get(point,0)|e);
                switch (e){
                    case 1:{
                        if (set.contains(tmp.set(point).add(0,1))){
                            points.put(tmp,points.get(tmp,0) | 8);
                        }
                        break;
                    }
                    case 2:{
                        if (set.contains(tmp.set(point).add(1,0))){
                            points.put(tmp,points.get(tmp,0) | 16);
                        }
                        break;
                    }
                    case 4:{
                        if (set.contains(tmp.set(point).add(1,-1))){
                            points.put(tmp,points.get(tmp,0) | 32);
                        }
                        break;
                    }
                    case 8:{
                        if (set.contains(tmp.set(point).add(0,-1))){
                            points.put(tmp,points.get(tmp,0) | 1);
                        }
                        break;
                    }
                    case 16:{
                        if (set.contains(tmp.set(point).add(-1,0))){
                            points.put(tmp,points.get(tmp,0) | 2);
                        }
                        break;
                    }
                    case 32:{
                        if (set.contains(tmp.set(point).add(-1,1))){
                            points.put(tmp,points.get(tmp,0) | 4);
                        }
                        break;
                    }
                }
            }
            e<<=1;
        }
    }

    private boolean needWall(ConnectGridPoint p, int dir){
        if (p.hasConnection(dir))
            return false;
        return (points.get(p,0) & dir) == 0;
    }

    void addPoint(ConnectGridPoint p,B2DBodyBuilder bb){
        float h = (float)Math.sqrt(3)/2*Holder.r;
        float w = 0.1f;
        Vector2 v = basis.getPos(p.x,p.y);
        if (needWall(p,1)){
            bb.addFixture(new B2DBodyBuilder.FixtureDefParam().boxShape(Holder.r,w,v.x,v.y+h,0));
        }
        if (needWall(p,2)){
            bb.addFixture(new B2DBodyBuilder.FixtureDefParam().boxShape(Holder.r,w,v.x+h*MathUtils.cos(MathUtils.PI/6),v.y+h * MathUtils.sin(MathUtils.PI/6),-(float) Math.PI/3));
        }
        if (needWall(p,4)){
            bb.addFixture(new B2DBodyBuilder.FixtureDefParam().boxShape(Holder.r,w,v.x+h*MathUtils.cos(MathUtils.PI/6),v.y-h * MathUtils.sin(MathUtils.PI/6),(float) Math.PI/3));
        }
        if (needWall(p,8)){
            bb.addFixture(new B2DBodyBuilder.FixtureDefParam().boxShape(Holder.r,w,v.x + 0,v.y -h,0));
        }
        if (needWall(p,16)){
            bb.addFixture(new B2DBodyBuilder.FixtureDefParam().boxShape(Holder.r,w,v.x-h*MathUtils.cos(MathUtils.PI/6),v.y-h * MathUtils.sin(MathUtils.PI/6),-(float) Math.PI/3));
        }
        if (needWall(p,32)){
            bb.addFixture(new B2DBodyBuilder.FixtureDefParam().boxShape(Holder.r,w,v.x-h*MathUtils.cos(MathUtils.PI/6),v.y+h * MathUtils.sin(MathUtils.PI/6),(float) Math.PI/3));
        }
        addPoint(p);
    }

    public void register6(World world, PointGraph6 graph, Basis basis){
        points.clear();

        B2DBodyBuilder.BodyDefParam bodyDefParam;

        bodyDefParam = new B2DBodyBuilder.BodyDefParam()
                .staticBody()
                .setPos(0, 0);
        this.set = graph.getNodes();
        this.basis =basis;
        B2DBodyBuilder bb = B2DBodyBuilder.instance.bodyDef(world, bodyDefParam);
        for (ConnectGridPoint p: set) {
            addPoint(p,bb);
        }
        bb.build();
    }

}

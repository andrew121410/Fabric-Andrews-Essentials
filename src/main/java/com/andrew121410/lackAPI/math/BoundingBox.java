package com.andrew121410.lackAPI.math;

public class BoundingBox {

    private Vector3 vectorDOWN;
    private Vector3 vectorUP;
    private Vector3 vectorDOWNMIN;
    private Vector3 vectorUPMAX;

    public BoundingBox(Vector3 vectorDOWN, Vector3 vectorUP) {
        this.vectorDOWN = vectorDOWN;
        this.vectorUP = vectorUP;

        this.vectorDOWNMIN = Vector3.getMinimum(vectorDOWN, vectorUP);
        this.vectorUPMAX = Vector3.getMaximum(vectorDOWN, vectorUP);
    }

    public boolean isInAABB(Vector3 vector) {
        return vector.isInAABB(vectorDOWNMIN, vectorUPMAX);
    }

    public Vector3 getMidpoint() {
        return vectorDOWN.getMidpoint(vectorUP);
    }

    public Vector3 getMidPointOnFloor() {
        Vector3 vector = getMidpoint();
        vector.setY(vectorDOWN.getY());
        return vector;
    }

    public BoundingBox add(Vector3 add) {
        vectorUP.add(add);
        vectorDOWN.add(add);

        vectorDOWNMIN = Vector3.getMinimum(vectorDOWN, vectorUP);
        vectorUPMAX = Vector3.getMaximum(vectorDOWN, vectorUP);
        return this;
    }

    public BoundingBox subtract(Vector3 subtract) {
        vectorUP.subtract(subtract);
        vectorDOWN.subtract(subtract);

        vectorDOWNMIN = Vector3.getMinimum(vectorDOWN, vectorUP);
        vectorUPMAX = Vector3.getMaximum(vectorDOWN, vectorUP);
        return this;
    }

    //GETTERS
    public Vector3 getVectorDOWN() {
        return vectorDOWN;
    }

    public Vector3 getVectorUP() {
        return vectorUP;
    }

    public Vector3 getVectorDOWNMIN() {
        return vectorDOWNMIN;
    }

    public Vector3 getVectorUPMAX() {
        return vectorUPMAX;
    }
}

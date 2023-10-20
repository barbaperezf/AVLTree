/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avltree;

/**
 * @author barbaperezf
 * El nodo usado en los árboles AVL
 * @param <T> 
 */
public class NodoAVL <T extends Comparable<T>>{
   //El dato guardado en el nodo
   private T elem;
   //Cada nodo tiene un padre y a lo más dos hijos
   private NodoAVL<T> papa, izq, der;
   //Factor de equilibrio = altura de hijo derecho - altura de hijo izquierdo. Entre 0 y |2|
   private int fe;
   
   //constructor
   public NodoAVL(T elem){
       this.elem=elem;
       this.fe=0;
       this.papa=null;
       this.izq=null;
       this.der=null;
   }
   
    //método auxiliar para conectar de un nodo existente otro nuevo (colgar a un nuevo hijo a un padre)
    public void cuelga(NodoAVL<T> nuevo){
        if (nuevo == null){
            return;
        }
        //si es menor o igual, cuelgan a la izquierda del padre
        if (nuevo.getElem().compareTo(elem)<=0){ 
            this.izq = nuevo;
        }
        //los mayores van a la derecha
        else{
            this.der = nuevo;
        }
        //conectar al nuevo con el padre
        nuevo.setPapa(this); 
    }
    public void cuelga(NodoAVL<T> nodo, char lado){
        if(nodo == null){
            return;
        }
        if(lado=='i'|| lado=='I'){
            this.izq=nodo;
            nodo.setPapa(this);
            return;
        }
        if(lado=='d'|| lado=='D'){
            this.der=nodo;
            nodo.setPapa(this);
            return;
        }
    }

    //Queremos saber el elemento y el factor de equilibrio de cada nodo
   @Override
    public String toString(){
        return "("+this.getElem()+" | fe: "+this.getFe()+")";
    }
    
    //getters
    public T getElem() {
        return elem;
    }

    public NodoAVL<T> getPapa() {
        return papa;
    }

    public NodoAVL<T> getIzq() {
        return izq;
    }

    public NodoAVL<T> getDer() {
        return der;
    }

    public int getFe() {
        return fe;
    }
    
    //setters

    public void setElem(T elem) {
        this.elem = elem;
    }

    public void setPapa(NodoAVL<T> papa) {
        this.papa = papa;
    }

    public void setIzq(NodoAVL<T> izq) {
        this.izq = izq;
    }

    public void setDer(NodoAVL<T> der) {
        this.der = der;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }
    
    

}

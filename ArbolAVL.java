/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avltree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author barbaperezf
 * Un árbol AVL es un Árbol de Búsqueda Binaria que se auto balancea
 * cuando la diferencia de altura entre sus dos hijos es de dos
 * Los árboles AVL tiene O(nlogn) para la búsqueda, insersión y eliminación
 * @param <T> 
 */

public class ArbolAVL  <T extends Comparable<T>> implements InterfaceArbolAVL<T> {
    //La raíz es el nodo principal del árbol
    //De él se derivan todos los otros nodos del árbol
    private NodoAVL raiz;
    //contador
    int cont;
    
    //constructor
    public ArbolAVL(){
        this.raiz = null;
        this.cont = 0;
    }
    
    
    @Override
    public int size() {
        return cont;
    }

    @Override
    public boolean isEmpty() {
        return cont==0;
    }
    //ALTURA
    //La altura de un árbol se calcula como max{altura(hijoDer), altura(hijoIzq)}+1
    public int calculaAltura(NodoAVL<T> actual){
        if(actual==null){
            return 0;
        }
        int alturaD = calculaAltura(actual.getDer()) + 1;
        int alturaI = calculaAltura(actual.getIzq()) + 1;
        return Math.max(alturaD, alturaI);
    }
    
    //Factor de Equilibrio
    public int calculaFe(NodoAVL<T> actual){
        NodoAVL<T> hijoD = actual.getDer();
        NodoAVL<T> hijoI = actual.getIzq();
        return calculaAltura(hijoD)-calculaAltura(hijoI);
    }
   

    //BUSCA
    @Override
    public NodoAVL<T> busca(T aBuscar){
        NodoAVL<T> actual = this.raiz;
        boolean termine = false;
        while(actual!=null && !termine){
            if(actual.getElem().compareTo(aBuscar)==0){
                termine=true;
            }else{
                if(aBuscar.compareTo(actual.getElem())<=0){
                    actual=actual.getIzq();
                }else{
                    actual=actual.getDer();
                }
            }
        }
        return actual;
    }

    //INSERTA
    @Override
    public void inserta(T dato) {
        if(dato!=null){
            NodoAVL <T> nuevo = new NodoAVL<T>(dato);
            cont++;
            //si está vacío
            if(raiz==null){
                raiz = nuevo;
                return;
            }
            //insertar como cualquier otro Árbol Binario de Búsqueda
            NodoAVL<T> actual = raiz;
            NodoAVL<T> papa = raiz;
            while(actual!=null){
                papa=actual;
                if(dato.compareTo(actual.getElem())<=0){
                    actual=actual.getIzq();
                }else{
                    actual=actual.getDer();
                }         
            }
            papa.cuelga(nuevo);
            actualizaFeInserta(nuevo);
        }
    }
    
    private void actualizaFeInserta(NodoAVL<T> actual){
        NodoAVL<T> papa = actual.getPapa();
        boolean termine = false;
        while (!termine && papa != null){
            papa.setFe(calculaFe(papa));
            if (papa.getFe() == 0) 
                termine = true;
            if (papa.getFe() == 2)
                if (actual.getFe() >= 0)
                    papa = rotaDD(papa);
                else
                    papa = rotaDI(papa);
            if (papa.getFe() == -2)
                if (actual.getFe() <= 0)
                    papa = rotaII(papa);
                else
                    papa = rotaID(papa);
            
            actual = papa;
            papa = papa.getPapa();
        }
    }   
    
    //BORRA
    @Override
    public T borra(T dato) {
        NodoAVL actual = busca(dato);
        //no está en el árbol
        if (actual==null){
            return null;
        }
        NodoAVL<T> papa = actual.getPapa();
        //Nodo que vamos a tener que checar
        NodoAVL<T> aBalancear;
        cont--;
        //Hay 3 casos que aparecen cuando borramos:
        //Caso 1: Es una hoja (no tiene hijos)
        if(actual.getIzq()==null && actual.getDer()==null){
            //Subcaso, es la raiz
            if(actual==raiz){
                raiz=null;
            }
            if(actual.getElem().compareTo(papa.getElem())<=0){
                papa.setIzq(null);
            }else{
                papa.setDer(null);
            }
            actual.setPapa(null);
            aBalancear=papa;
        }
        //Caso 2: Es una rama (tiene un hijo)
        if(actual.getIzq()==null || actual.getDer()==null){
            NodoAVL<T> hijo;
            if(actual.getIzq()==null){
                hijo=actual.getDer();
            }else{
                hijo=actual.getIzq();
            }
            if(actual==raiz){
                raiz=hijo;
            }else{
                papa.cuelga(hijo);
            }
            actual.setPapa(null);
            aBalancear=papa;
        }
        //Caso 3: Es un árbol (tiene dos hijos)
        else{
            //Buscamos el sucesor inorden. Una derecha y lo más a la izquierda
            NodoAVL<T> aux = actual.getDer();
            while(aux.getIzq()!=null){
                aux=aux.getIzq();
            }
            //Cambiamos los valores
            actual.setElem(aux.getElem());
            
            //Subcaso a) aux es el de la derecha del actual, es decir, el de la
                //derecha no tenía hijos izquierdos
            if(aux == actual.getDer()){
                aBalancear=actual;
                //subcaso a1) aux no tiene hijos
                if(aux.getDer()==null){
                    actual.setDer(null);
                    aux.setPapa(null);
                }
                //subcaso a2) aux tiene un hijo derecho
                else{
                    aux.getPapa().cuelga(aux.getDer(), 'd');
                    aux.setDer(null);
                    aux.setPapa(null);
                }
            }
            //Subcaso b) aux no es el de la derecha del actual, es decir, 
                //el de la derecha si tenía hijos izquierdos
            else{
                aBalancear=aux.getPapa();
                //subcaso b1) aux no tiene hijos
                if(aux.getDer()==null){
                    aux.getPapa().setIzq(null);
                    aux.setPapa(null);
                }
                //subcaso b2) aux tiene un hijo derecho
                else{
                    aux.getPapa().cuelga(aux.getDer(),'i');
                    aux.setPapa(null);
                }
            }    
        }
        //Balancear después de borrar}
        actualizaFeBorra(aBalancear);
        
        return dato;
    }
    
    private void actualizaFeBorra(NodoAVL<T> actual){
        NodoAVL<T> papa = actual.getPapa();
        boolean temine = false;
        while(papa!=raiz && !temine){
            papa.setFe(calculaFe(papa));
           if (papa.getFe() == 1 || papa.getFe() == -1)
                temine = true;
            if (papa.getFe() == 2)
                if (actual.getFe() >= 0)
                    papa = rotaDD(papa);
                else
                    papa = rotaDI(papa);
            
            if (papa.getFe() == -2)
                if (actual.getFe() <= 0)
                    papa = rotaII(papa);
                else
                    papa = rotaID(papa);
            
            actual = papa;
            papa = papa.getPapa();
        }
    }   
    

    
    //ROTATIONS
    //there are four types of rotations that help us keep the tree balanced 
    //we will use alpha, beta, gamma, A, B, C, D as auxiliary variables to rotate

    //Izquierda-Izquierda (II)
    /** Fe(actual) = -2, Fe(izq) = -1 or 0
    * 
    *               alfa                               beta
    *              /    \                            /       \ 
    *            beta     D         II ->        gamma        alfa
    *           /    \                          /     \      /     \ 
    *       gamma     C                        A       B    C       D
    *       /    \ 
    *      A      B
    * 
    */
    private NodoAVL<T> rotaII(NodoAVL<T> actual){
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getIzq();
        NodoAVL<T> C = beta.getDer();
        
        if (alfa == this.raiz){
            this.raiz = beta;
        }
        if (beta != this.raiz){
            if(alfa.getPapa().getIzq() == alfa){
                alfa.getPapa().cuelga(beta,'i');
            }else{
                alfa.getPapa().cuelga(beta,'d');
            }
        }else{
            beta.setPapa(null);
        }
        alfa.setPapa(beta);
        beta.setDer(alfa);
        alfa.setIzq(C);
        if (C != null){
            C.setPapa(alfa);
        }
        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));
        
        return beta;
    }
    //Izquierda-Derecha (ID)
    /** Fe(actual) = -2, Fe(izq) = 1
    * 
    *               alfa                              gamma
    *              /    \                            /       \ 
    *            beta    D          ID ->        beta        alfa
    *           /    \                          /    \      /     \ 
    *          A   gamma                       A      B    C       D
    *              /    \ 
    *             B      C
    * 
    */
    private NodoAVL<T> rotaID(NodoAVL<T> actual){
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getIzq();
        NodoAVL<T> gamma = beta.getDer();
        NodoAVL<T> B = gamma.getIzq();
        NodoAVL<T> C = gamma.getDer();
        
        if (alfa == this.raiz){
            this.raiz = gamma;
        }
        if (gamma != this.raiz){
            if(alfa.getPapa().getIzq() == alfa){
                alfa.getPapa().cuelga(gamma,'i');
            }else{
                alfa.getPapa().cuelga(gamma,'d');
            }
        }else{
            gamma.setPapa(null);
        }
        
        if (B != null){
            B.setPapa(beta);
        }
        beta.setDer(B);
        if (C != null){
            C.setPapa(alfa);
        }
        alfa.setIzq(C);
        alfa.setPapa(gamma);
        beta.setPapa(gamma);
        gamma.setIzq(beta);
        gamma.setDer(alfa);
                
        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));
        gamma.setFe(calculaFe(gamma));
        
        return gamma; 
    }

    //Derecha-Derecha (DD)
    /** Fe(actual) = 2, Fe(der) = 1 or 0
    * 
    *               alfa                               beta
    *              /    \                            /        \ 
    *             A     beta         DD ->        alfa        gamma
    *                  /    \                    /     \      /     \ 
    *                 B    gamma                A       B    C       D
    *                      /    \ 
    *                     C      D
    * 
    */
    private NodoAVL<T> rotaDD(NodoAVL<T> actual){
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getDer();
        NodoAVL<T> B = beta.getIzq();
        
        if (alfa == this.raiz){
            this.raiz = beta;
        }
        if (beta != this.raiz){
            if(alfa.getPapa().getIzq() == alfa){
                alfa.getPapa().cuelga(beta,'i');
            }else{
                alfa.getPapa().cuelga(beta,'d');
            }
        }else{
            beta.setPapa(null);
        }

        alfa.setPapa(beta);
        beta.setIzq(alfa);
        alfa.setDer(B);
        if (B != null){
            B.setPapa(alfa);
        }

        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));
        
        return beta;
    }
    //Derecha-Izquierda (DI)
    /** Fe(actual) = 2, Fe(izq) = -1
    * 
    *               alfa                               gamma
    *              /    \                            /        \ 
    *             A     beta         DI ->        alfa        beta
    *                  /    \                    /     \      /     \ 
    *               gamma    D                  A       B    C       D
    *               /    \ 
    *              B      C
    * 
    */
    private NodoAVL<T> rotaDI(NodoAVL<T> actual){
        NodoAVL<T> alfa = actual;
        NodoAVL<T> beta = alfa.getDer();
        NodoAVL<T> gamma = beta.getIzq();
        NodoAVL<T> B = gamma.getIzq();
        NodoAVL<T> C = gamma.getDer();
        
        if (alfa == this.raiz){
            this.raiz = gamma;
        }
        if (gamma != this.raiz){
            if(alfa.getPapa().getIzq() == alfa){
                alfa.getPapa().cuelga(gamma,'i');
            }else{
                alfa.getPapa().cuelga(gamma,'d');
            }
        }else{
            gamma.setPapa(null);
        }
        
        if (B != null){
            B.setPapa(alfa);
        }
        alfa.setDer(B);
        
        if (C != null){
            C.setPapa(beta);
        }
    
        beta.setIzq(C);
        gamma.setDer(beta);
        gamma.setIzq(alfa);
        alfa.setPapa(gamma);
        beta.setPapa(gamma);
        
        alfa.setFe(calculaFe(alfa));
        beta.setFe(calculaFe(beta));
        gamma.setFe(calculaFe(gamma));
        
        return gamma;
    }
  
    //getters
    public NodoAVL<T> getRoot() {
        return raiz;
    }

	

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(cont==0){
            sb.append("El árbol está vacío");
        }else{
            Queue<NodoAVL<T>> cola = new LinkedList<>();
            int contImp=0,i=0;
            cola.add(raiz);
            while(contImp<=cont && cola.peek()!=null){
                if(Math.pow(2, i) > contImp){
                    sb.append("\n");
                }else{
                    sb.append("       ");
                    i++;
                }
                sb.append(cola.peek());
                cola.add(cola.peek().getIzq());
                cola.add(cola.peek().getDer());
                cola.poll();
                contImp++;
            }            
        }
        return sb.toString();
    }
    
  
    
    
}

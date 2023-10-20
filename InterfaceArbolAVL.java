/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package avltree;

/**
 *
 * @author edi
 */
interface InterfaceArbolAVL<T extends Comparable <T>> {
    public int size();
    public boolean isEmpty();
    @Override
    public String toString();
    public NodoAVL<T> busca(T dato);
    public void inserta(T dato);
    public T borra(T dato);
    
}
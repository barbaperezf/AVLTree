/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package avltree;

/**
 *
 * @author barbaperezf
 */
public class Pruebas {
    public static void divisor(){
        System.out.println();
        for(int i=0; i<90; i++){
            System.out.print("-");
        }
        System.out.println();
    }
    public static void main(String[] args) throws Exception {
        ArbolAVL <Integer> p = new ArbolAVL();
        System.out.println(p);
        divisor();
        
        p.inserta(6);
        System.out.println(p);
        divisor();
        
        p.inserta(4);
        System.out.println(p);
        divisor();
        
        p.inserta(7);
        System.out.println(p);
        divisor();
        
         p.inserta(1);
        System.out.println(p);
        divisor();
        
        p.inserta(0);
        System.out.println(p);
        divisor();

    
    
    
    }
}

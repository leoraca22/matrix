/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    } 

    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
    
    public static double determinante(Matriz matriz)
    {
        double det;
        if(matriz.datos.length==2)
        {
            det=(matriz.datos[0][0]*matriz.datos[1][1])-(matriz.datos[1][0]*matriz.datos[0][1]);
            return det;
        }
        double suma=0;
        for(int i=0; i<matriz.datos.length; i++){  
        Matriz nm=new Matriz(matriz.datos.length-1,matriz.datos.length-1,false);
            for(int j=0; j<matriz.datos.length; j++){
                if(j!=i){
                    for(int k=1; k<matriz.datos.length; k++){
                    	int indice=-1;
	                    if(j<i) {
	                    	indice=j;
	                    } else if(j>i) {
	                    	indice=j-1;
	                    }
	                    nm.datos[indice][k-1]=matriz.datos[j][k];
                    }
                }
            }
            if(i%2==0) {
                suma+=matriz.datos[i][0] * determinante(nm);
            } else {
            	suma-=matriz.datos[i][0] * determinante(nm);
            }
        }
        return suma;
    }
    
    
    public static Matriz matrizInversa(Matriz matriz) {
        double det=1/determinante(matriz);
        Matriz nmatriz=matrizAdjunta(matriz);
        multiplicarMatriz(det,nmatriz);
        return nmatriz;
    }
}

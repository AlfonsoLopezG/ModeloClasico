/*
 Programa de un modelo clasico
 Hecho por: Juan Alejandro Calzoncit, Alfonso López, Juan Jaime Reyes.
 Fecha de inicio: 04/09/2021
 Ultima actualización: 16/09/2021
 */
package ModeloDeColas;
import java.util.Random;
import java.util.Scanner;
public class Principal {
	static Scanner Lector = new Scanner(System.in);
	public static void main(String[] args) {
		//Variables, vectores y matrices utilizadas.
		int i,j; //Controladores de ciclos.
		int Dif=0;//Diferencia para calcular Wt o Idt.
		int M; //Numero total de ordenes a procesar.
		int N; //Numero total de procesos a realizar en cada orden.
		int Et; //Media tiempos de llegada de las ordenes.
		int At[]; // Registro de tiempos de llegada de las ordenes.
		int Ts[]; //Tiempo total de cada orden.
		float Vt[]; //Varianza de los tiempos de proceso.
		int Wt[][]; //Tiempo de espera de procesos en cada orden.
		int St[][];//Tiempo de ejecución de un proceso en la orden.
		int T[][]; //Tiempo total de procesos por orden.
		int Idt[][];//Tiempo de ocio de procesos.
		
		//Captura del numero de ordenes que llegaron y el numero de procesos a ejecutar.
		System.out.println("Ingrese el numero de ordenes: ");
		M = Lector.nextInt();
		
		System.out.println("Ingrese el numero de procesos: ");
		N = Lector.nextInt();
		
		//Generando el tamaño de arreglos y matrices
		At = new int [M];
		Ts = new int[M];
		Vt = new float[N];
		Wt = new int [M][N];
		St = new int [M][N];
		T = new int [M][N];
		Idt = new int [M][N];
		
		//Creando objeto random
		Random ram= new Random();
		//Registrando la llegadas de cada orden (Rango del random 0-5)
		At[0]=0;
		for(i=1;i<M;i++) {
			At[i]= ram.nextInt(5)+1; //Numero random
		}
		
		//Generando los tiempos de ejecucion de proceso (Random)
		for(i=0;i<M;i++) {
			for(j=0;j<N;j++) {
				St[i][j]=ram.nextInt(7)+1;
			}
		}
		
		//Llenado de tiempos de ocio y Tiempo de proceso para la orden 1
		Idt[0][0]=0;
		for(i=1;i<N;i++) {
			Idt[0][i]=St[0][i-1]+ Idt[0][i-1];
		}
		
		//Llenando tiempos de espera y ocio
		Wt[0][0] = 0;
		Idt[0][0] = 0;
		for(i=1;i<N;i++) {//Primer pedido
			Wt[0][i]=0;
			Idt[0][i]=Idt[0][i-1]+St[0][i-1];
		}
		
		//Calculando tiempos de espera y ocio cuando i>1
		for(i=1;i<M;i++) {
			for(j=0;j<N;j++) {
				int x=0;
				int sum=0,res=At[i];
				if(j==0) {
					Dif=St[i-1][0]-At[i];
				}
				if(j==1) {
					Dif=(St[i-1][0]+St[i-1][1])-(At[i]+Wt[i][0]+St[i][0]);
				}
				if(j>1) {
					int n=j;
					for(int k=0;k<=j;k++) {
						sum+=St[i-1][k];
						if(k<n) {
						res+=Wt[i][k]+St[i][k];
						}
					}
					Dif=sum-res;
				}
				
		//Llenando ocio y espera
				if(Dif>x) {
					Wt[i][j] = Dif;
					Idt[i][j] = 0;
				}
				if(Dif==x) {
					Wt[i][j] = 0;
					Idt[i][j] = 0;
				}
				if(Dif<x) {
					Wt[i][j] = 0;
					Idt[i][j] = -Dif;
				}
			}
		}
		//Llenando los tiempos de proceso
		for(i=0;i<M;i++) {
			for(j=0;j<N;j++) {
				T[i][j]=Wt[i][j] +St[i][j];
			}
		}
		
		//Llenando los tiempos totales por pedido
		for(i=0;i<M;i++) {
			Ts[i]=0;
			for(j=0;j<N;j++) {
				Ts[i]+=T[i][j];
			}
		}
		
		//Mostrando los tiempos de llegada
		System.out.println("Tiempos de llegada: ");
		for(i=0;i<M;i++) {
			System.out.print(At[i] +" ");
		}
		
		//Mostrando los tiempos
		System.out.println("\nTiempos de ejecucion de procesos: ");
		for(i=0;i<M;i++) {
			for(j=0;j<N;j++) {
				System.out.print(St[i][j] +" ");
			}
		System.out.println();
		}
		
		//Mostrando matrices de ocio y espera
		System.out.println("Matriz de espera: ");
		for(i=0;i<M;i++) {
			for(j=0;j<N;j++){
				System.out.print(Wt[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("Matriz de ocio: ");
		for(i=0;i<M;i++) {
			for(j=0;j<N;j++){
				System.out.print(Idt[i][j] + " ");
			}
			System.out.println();
		}
		
		//Mostrando los tiempos por proceso en cada orden
		System.out.println("Tiempos de los procesos por cada orden: ");
		for(i=0;i<M;i++) {
			for(j=0;j<N;j++) {
				System.out.print(T[i][j] +" ");
			}
			System.out.println();
		}
		
		//Mostrando el tiempo total de cada pedido
		System.out.println("Tiempo total por pedido: ");
		for(i=0;i<M;i++) {
			System.out.print(Ts[i] +" ");
		}
		
	}
}

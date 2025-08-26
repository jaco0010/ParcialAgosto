package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Double> precios = new HashMap<>(); //Almacena los nombres de los productos  y sus precios
        List<String> productos = new ArrayList<>(); //Almacena los nombres de los productos en el orden en que se agregan.

        System.out.println("que vas a comprar hoy");
        int cantidad = pedirCantidad(sc);

        for (int i = 1; i <= cantidad; i++) {
            System.out.println();
            System.out.print("Producto " + i + ": ");
            String nombre = sc.nextLine().trim();

            System.out.print("¿Cuánto vale?");
            double precio = pedirPrecio(sc);

            precios.put(nombre, precio);
            productos.add(nombre);
        }

        double total = precios.values().stream().mapToDouble(Double::doubleValue).sum();
        double promedio = total / precios.size(); //Obtiene el número total de productos en el mapa

        String caro = productoExtremo(precios, true);
        String barato = productoExtremo(precios, false);

        long encimaMedia = precios.values().stream().filter(precio -> precio > promedio).count(); //Obtiene una colección de todos los valores (precios) del mapa precios


        System.out.println("Compraste: " + productos);
        System.out.println("Total a pagar: $" + total);
        System.out.println("Precio promedio: $" + promedio);
        System.out.println("Lo más caro: " + caro + " ($" + precios.get(caro) + ")"); //recupera los precios
        System.out.println("Lo más barato: " + barato + " ($" + precios.get(barato) + ")"); //recupera los precios
        System.out.println("Cantidad de productos más caros que el promedio: " + encimaMedia);
    }

    private static int pedirCantidad(Scanner sc) {
        while (true) {
            System.out.print("que productos llevas?");
            if (sc.hasNextInt()) { //verifica si el siguiente token puede convertirse en int sin avanzar el cursor
                int c = sc.nextInt();
                sc.nextLine(); //borra espacios
                if (c > 0) return c;
                System.out.println("digita un numero correcto");
            } else {
                System.out.println("Este numero no me sirve");
                sc.nextLine();
            }
        }
    }

    private static double pedirPrecio(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                double p = Double.parseDouble(line);
                if (p >= 0) return p;
                throw new IllegalArgumentException("El precio no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Este numero no me sirve");
            }
        }
    }


    private static double sumar(Map<String, Double> precios) {
        return precios.values().stream()//btiene una colección de los valores del mapa precios, que son los precios de los productos.
                .mapToDouble(Double::doubleValue)//onvierte cada objeto Double en un valor primitivo double
                .sum();//calcula la suma de todos los elementos
    }

    private static String productoExtremo(Map<String, Double> precios, boolean buscarMaximo) {
        String productoExtremo = null;
        double valorExtremo = buscarMaximo ? Double.MIN_VALUE : Double.MAX_VALUE;

        for (Map.Entry<String, Double> entry : precios.entrySet()) {
            double precio = entry.getValue();
            if ((buscarMaximo && precio > valorExtremo) || (!buscarMaximo && precio < valorExtremo)) {
                valorExtremo = precio;
                productoExtremo = entry.getKey();
            }
        }

        return productoExtremo;
    }



    private static long contarSobrePromedio(Map<String, Double> precios, double promedio) {
        return precios.values().stream() //convierte la colección de valores del mapa en un Stream.
                .filter(precio -> precio > promedio) //filtra los precios que son mayores al promedio
                .count();
    }
}

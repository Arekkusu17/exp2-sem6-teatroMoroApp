/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.appteatromoros6;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author arekkusu
 */
public class AppTeatroMoroS6 {
    // Representa una entrada vendida o reservada
    static class Ticket {
        //Variables de instancia
        String row;
        int seat;
        boolean isReserved; // true si es una reserva, false si es una compra

        Ticket(String row, int seat, boolean isReserved) {
            this.row = row;
            this.seat = seat;
            this.isReserved = isReserved;
        }

        String getRow() {
            return row;
        }

        void setRow(String row) {
            this.row = row;
        }
    
        int getSeat() {
            return seat;
        }

        void setSeat(int seat) {
            this.seat = seat;
        }
    
        boolean getIsReserved() {
            return isReserved;
        }
    
        void setIsReserved(boolean isReserved) {
            this.isReserved = isReserved;
        }
    }
    
    static class ReservationOrder {
        private static int countersReservedOrders = 1;
        private int id;
        private List<Ticket> ticketsReserved;
        private Date reserveDate;
    
        ReservationOrder(List<Ticket> ticketsReserved) {
            this.id = countersReservedOrders++;
            this.ticketsReserved = ticketsReserved;
            this.reserveDate = new Date();
        }
    
        int getId() {
            return id;
        }
    
        List<Ticket> getReservationTickets() {
            return ticketsReserved;
        }
    
        void showReserveSummary() {
            System.out.println("\n--- RESUMEN DE RESERVA #" + id + " ---");
            for (Ticket e : ticketsReserved) {
                System.out.println("Asiento: Fila " + e.getRow() + ", N° " + e.getSeat());
            }
            System.out.println("Fecha de reserva: " + reserveDate);
        }
    }

    static class BuyingOrder {
        private static int counter = 1;
        private int id;
        private String boughtDate;
        private List<Ticket> ticketsBought;
        
        // Fecha de compra
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
        BuyingOrder(List<Ticket> ticketsBought) {
            this.id = counter++;
            this.boughtDate = sdf.format(new Date());
            this.ticketsBought = ticketsBought;
            // Marcamos todas como compra (por si no estaba explícito)
            for (Ticket e : ticketsBought) {
                e.setIsReserved(false);
            }
        }
    
        int getId() {
            return id;
        }
        
        String getBoughtDate(){
            return boughtDate;
        }
    
        List<Ticket> getBoughtTickets() {
            return ticketsBought;
        }
    }

    
    // Variables estaticas
    static Scanner scanner = new Scanner(System.in);
    static List<Ticket> tickets = new ArrayList<>();
    static List<ReservationOrder> reserveOrders = new ArrayList<>();
    static List<BuyingOrder> soldOrders = new ArrayList<>();
    
    // Variables de estadística
    static int totalSoldTickets = 0;
    static double totalIncome = 0;
    static int totalReservedOrders = 0;
    static int totalBoughtOrders = 0;
    
    // Constantes
    final static String THEATER_NAME = "Teatro Moro";
    final static int THEATER_CAPACITY = 100; 
    final static double TICKET_PRICE = 10000;

    
    public static void main(String[] args) {
        boolean continue_running = true;
        
        System.out.println("=== Bienvenido al " + THEATER_NAME + " ===");
        System.out.println("Capacidad del teatro: " + THEATER_CAPACITY + " personas");
        System.out.println("Sistema de venta de entradas");

        while (continue_running) {
            showMenu();

            int menuOption = getValidOption(1, 5);

            switch (menuOption) {
                case 1:
                    reserveTickets();
                    break;
                case 2:
                    buyTickets();
                    break;
                case 3:
                    modifySoldOrder();
                    break;
                case 4:
                    printReceiptMenu();
                    break;
                case 5:
                    showSummary();
                    break;
                case 6:
                    continue_running = false;
                    System.out.println("Saliendo del sistema. ¡Gracias!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    static void showMenu() {
        System.out.println("\n=== MENÚ DE VENTA ===");
        System.out.println("1. Reservar entradas");
        System.out.println("2. Comprar entradas");
        System.out.println("3. Modificar una venta existente");
        System.out.println("4. Imprimir boleta");
        System.out.println("5. Ver estadísticas de ventas");
        System.out.println("6. Salir");
    }
    
    // ----------------------------
    // MÉTODOS (a completar luego)
    // ----------------------------

    static void reserveTickets() {
        System.out.println("\n--- RESERVAR ENTRADAS ---");
        System.out.print("¿Cuántas entradas desea reservar? ");
        int ticketQuantity = getValidNumberInRange(1, 20);  // Puedes ajustar el límite
    
        List<Ticket> ticketsToReserve = new ArrayList<>();
    
        for (int i = 1; i <= ticketQuantity; i++) {
            System.out.println("Reserva " + i + " de " + ticketQuantity);
            System.out.print("Ingrese la fila (A-J): ");
            String row = scanner.nextLine().toUpperCase();
    
            if (!row.matches("[A-J]")) {
                System.out.println("Fila inválida.");
                i--; // repetir este intento
                continue;
            }
    
            System.out.print("Ingrese el número de asiento (1-20): ");
            int seat = getValidNumberInRange(1, 20);
    
            if (!isAvailable(row, seat, ticketsToReserve)) {
                System.out.println("El asiento ya está reservado o comprado. Elija otro.");
                i--; // repetir este intento
            } else {
                ticketsToReserve.add(new Ticket(row, seat, true));
            }
        }
    
        ReservationOrder newOrder = new ReservationOrder(ticketsToReserve);
        reserveOrders.add(newOrder);
        tickets.addAll(ticketsToReserve);  // Para mantener control general de entradas
    
        totalReservedOrders++;

        System.out.println("Reserva realizada con éxito.");
        newOrder.showReserveSummary();    
    }
    

    static void buyTickets() {
        System.out.println("\n--- COMPRAR ENTRADAS ---");
        System.out.println("¿Desea comprar desde una reserva existente?");
        System.out.print("1. Sí\n2. No, comprar nuevas entradas\nSeleccione una opción: ");
        int buyOption = getValidNumberInRange(1, 2);

        if (buyOption == 1) {
            if (reserveOrders.isEmpty()) {
                System.out.println("No hay reservas disponibles para comprar.");
                return;
            }

            System.out.println("\nÓrdenes de reserva disponibles:");
            for (ReservationOrder order : reserveOrders) {
                System.out.println("ID: " + order.getId() + ", Asientos reservados: " + order.getReservationTickets().size());
            }

            System.out.print("Ingrese el ID de la reserva que desea comprar: ");
            int id = getValidNumberInRange(1, Integer.MAX_VALUE);

            ReservationOrder foundOrder = null;
            for (ReservationOrder order : reserveOrders) {
                if (order.getId() == id) {
                    foundOrder = order;
                    break;
                }
            }

            if (foundOrder == null) {
                System.out.println("Reserva no encontrada.");
                return;
            }

            for (Ticket e : foundOrder.getReservationTickets()) {
                e.setIsReserved(false);  // Se convierte en compra
            }
            
            // Crear una orden de compra
            BuyingOrder newBuyingOrder = new BuyingOrder(foundOrder.getReservationTickets());
            soldOrders.add(newBuyingOrder);  // Agregar la nueva orden de compra a la lista de ventas
            
            //Actualiza estadísticas
            totalBoughtOrders++;
            totalSoldTickets += foundOrder.getReservationTickets().size();
            totalIncome += foundOrder.getReservationTickets().size() * TICKET_PRICE;
            
            // Eliminar la orden de reserva
            reserveOrders.remove(foundOrder);
            totalReservedOrders--;
        
            // Mostrar mensaje de éxito
            System.out.println("Compra realizada con éxito para la reserva #" + id);
        
            // Imprimir la boleta automáticamente
            printReceipt(newBuyingOrder);  // Llamada para imprimir la boleta de la compra realizada
        } else {
            // Compra directa (sin reserva)
            System.out.print("¿Cuántas entradas desea comprar? ");
            int ticketQuantity = getValidNumberInRange(1, 20);
            List<Ticket> ticketsToBuy = new ArrayList<>();

            for (int i = 1; i <= ticketQuantity; i++) {
                System.out.println("Compra " + i + " de " + ticketQuantity);
                System.out.print("Ingrese la fila (A-J): ");
                String row = scanner.nextLine().toUpperCase();

                if (!row.matches("[A-J]")) {
                    System.out.println("Fila inválida.");
                    i--;
                    continue;
                }

                System.out.print("Ingrese el número de asiento (1-20): ");
                int seat = getValidNumberInRange(1, 20);

                if (!isAvailable(row, seat, ticketsToBuy)) {
                    System.out.println("El asiento ya está reservado o comprado.");
                    i--;
                } else {
                    ticketsToBuy.add(new Ticket(row, seat, false));
                }
            }

            BuyingOrder newOrder = new BuyingOrder(ticketsToBuy);
            soldOrders.add(newOrder);
            
            //Actualiza estadísticas
            totalBoughtOrders++;
            totalSoldTickets += ticketsToBuy.size();
            totalIncome += ticketsToBuy.size() * TICKET_PRICE;
            
            tickets.addAll(ticketsToBuy);
            printReceipt(newOrder);
            //System.out.println("Compra realizada con éxito.");
        }
    }

    static void modifySoldOrder() {
        System.out.println("\n--- MODIFICAR VENTA EXISTENTE ---");

        // Filtrar solo las órdenes de compra (no reservas)
        if (soldOrders.isEmpty()) {
            System.out.println("No hay ventas registradas para modificar.");
            return;
        }
    
        // Mostrar las órdenes de compra existentes
        for (int i = 0; i < soldOrders.size(); i++) {
            BuyingOrder order = soldOrders.get(i);
            System.out.println((i + 1) + ". ID: " + order.getId() + ", Entradas: " + order.getBoughtTickets().size());
        }
    
        System.out.print("Seleccione el número de la orden que desea modificar: ");
        int modifyOption = getValidNumberInRange(1, soldOrders.size());
        BuyingOrder selectedOrder = soldOrders.get(modifyOption - 1);
    
        System.out.println("\nEntradas en la orden #" + selectedOrder.getId() + ":");
        for (int i = 0; i < selectedOrder.getBoughtTickets().size(); i++) {
            Ticket e = selectedOrder.getBoughtTickets().get(i);
            System.out.println((i + 1) + ". Fila: " + e.getRow() + ", Asiento: " + e.getSeat());
        }
    
        System.out.println("¿Qué acción desea realizar?");
        System.out.println("1. Cambiar asiento");
        System.out.println("2. Eliminar asiento");
        int selectedAction = getValidNumberInRange(1, 2);
    
        if (selectedAction == 1) {
            System.out.print("Ingrese el número de la entrada a modificar: ");
            int selectedTicketIndex = getValidNumberInRange(1, selectedOrder.getBoughtTickets().size());
            Ticket selectedTicket = selectedOrder.getBoughtTickets().get(selectedTicketIndex - 1);
    
            System.out.print("Ingrese la nueva fila (A-J): ");
            String newRow = scanner.nextLine().toUpperCase();
    
            System.out.print("Ingrese el nuevo número de asiento (1-20): ");
            int newSeat = getValidNumberInRange(1, 20);
    
            if (!isAvailable(newRow, newSeat, null)) {
                System.out.println("El asiento ya está ocupado.");
            } else {
                selectedTicket.setRow(newRow);
                selectedTicket.setSeat(newSeat);
                System.out.println("Asiento actualizado con éxito.");
            }
    
        } else {
            System.out.print("Ingrese el número de la entrada a eliminar: ");
            int ticketIndexToEliminate = getValidNumberInRange(1, selectedOrder.getBoughtTickets().size());
            Ticket removedTicket = selectedOrder.getBoughtTickets().remove(ticketIndexToEliminate - 1);
            totalSoldTickets--;
            
            System.out.println("Entrada eliminada correctamente.");
        }
    }

    static void printReceiptMenu() {
        // Verificar si hay órdenes de compra disponibles
        if (soldOrders.isEmpty()) {
            System.out.println("No hay ventas registradas para imprimir boletas.");
            return;
        }
    
        // Mostrar las órdenes de compra
        System.out.println("\n--- ÓRDENES DE COMPRA ---");
        for (int i = 0; i < soldOrders.size(); i++) {
            BuyingOrder orden = soldOrders.get(i);
            System.out.println((i + 1) + ". ID: " + orden.getId());
        }
    
        // Selección de la orden
        System.out.print("Seleccione el número de la orden para imprimir la boleta: ");
        int printOption = getValidNumberInRange(1, soldOrders.size());
        BuyingOrder selectedOrder = soldOrders.get(printOption - 1);
    
        // Llamar a la función de impresión de boleta
        printReceipt(selectedOrder);
    }

    static void printReceipt(BuyingOrder order) {
        String boughtDate = order.getBoughtDate();

        // Mostrar encabezado de la boleta
        System.out.println("\n--- BOLETA DE COMPRA ---");
        System.out.println("Fecha de compra: " + boughtDate);
        System.out.println("ID de Orden: " + order.getId());
        System.out.println("------------------------------------------------");

        // Mostrar entradas de la orden
        double totalPrice = 0;
        for (Ticket e : order.getBoughtTickets()) {
            String ticketType = e.getIsReserved() ? "Reserva" : "Compra";
            totalPrice += TICKET_PRICE;

            System.out.println("Asiento: Fila " + e.getRow() + ", Asiento " + e.getSeat() + " - Tipo: " + ticketType + " - Precio: $" + TICKET_PRICE);
        }

        // Mostrar total
        System.out.println("------------------------------------------------");
        System.out.println("Total: $" + totalPrice);
        System.out.println("--- ¡Gracias por su compra! ---\n");
    }
    
    static void showSummary() {
    System.out.println("\n=== ESTADÍSTICAS DE VENTAS ===");
    System.out.println("Asientos vendidos: " + totalSoldTickets);
    System.out.println("Ingresos totales: $" + totalIncome);
    System.out.println("Órdenes de reserva: " + totalReservedOrders);
    System.out.println("Órdenes de compra: " + totalBoughtOrders);
    System.out.println("Capacidad en uso del teatro: " + (totalSoldTickets * 100 / THEATER_CAPACITY) + "%");

    }

    // ----------------------------
    // UTILIDADES
    // ----------------------------

    static int getValidOption(int min, int max) {
        int option = -1;
        boolean validOption = false;

        while (!validOption) {
            System.out.print("Seleccione una opción (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); 
                if (option >= min && option <= max) {
                    validOption = true;
                } else {
                    System.out.println("Opción fuera de rango.");
                }
            } else {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); 
            }
        }

        return option;
    }
    
    static int getValidNumberInRange(int min, int max) {
        while (!scanner.hasNextInt()) {
            System.out.print("Debe ingresar un número: ");
            scanner.nextLine();
        }
    
        int number = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
    
        if (number < min || number > max) {
            System.out.println("El número debe estar entre " + min + " y " + max + ".");
            return getValidNumberInRange(min, max);
        }
    
        return number;
    }

    static boolean isAvailable(String row, int seat, List<Ticket> ticketsOrdered) {
    // Revisar en la orden actual
    if (ticketsOrdered != null) {
        for (Ticket t : ticketsOrdered) {
            if (t.getRow().equalsIgnoreCase(row) && t.getSeat() == seat) {
                return false; // Ya fue elegido en esta orden
            }
        }
    }
    // Revisar en la lista global de asientos ocupados
    for (Ticket e : tickets) {
        if (e.getRow().equalsIgnoreCase(row) && e.getSeat() == seat) {
            return false; // Ya está ocupado globalmente
        }
    }


    return true; // Está disponible
    }

}

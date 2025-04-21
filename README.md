# Experiencia 2 Semana 6: Teatro Moro

*Aviso:* Las imagenes pueden no coincidir en la imagen versus su estado final, puesto que realicé unas mejoras luego de realizar distintos cambios

## Logica de Depuración para Validación y seleccion de asientos
![!row.matches("[A-J]") Breakpoint 1.1](images/row_matches_1.png)
![!row.matches("[A-J]") Breakpoint 1.2](images/row_matches_2.png)
Se utiliza este breakpoint para evidenciar que al ingresar una fila no válida, el flujo invita al usuario a intentar nuevamente e ingresar un valor aceptado.

![reserveOrders.add(newOrder) Breakpoint 2.1](images/reserveorders_add_1.png)
![reserveOrders.add(newOrder) Breakpoint 2.2](images/reserveorders_add_2.png)
Con este breakpoint, se puede verificar el estado inicial de reserveOrders y su la cantidad de elementos que contiene. Con ello, es posible revisar si la nueva orden de reserva se agrega correctamente.

![tickets.addAll(ticketsToReserve) 3.1](images/tickets_add_all_1.png)
![tickets.addAll(ticketsToReserve) 3.2](images/tickets_add_all_2.png)
Con este breakpoint, podemos revisar si todos los tickets(asientos) que fueron seleccionados en los pasos anteriores fueron agregados al listado total de los tickets en uso(sean reservas o compras): Hace posible verificar el cambio en la cantidad de elementos.

## Depuración para reservas que se transforman en compras
![e.setIsReserved(false) Breakpoint 4.1](images/setIsReserved_1.png)
![e.setIsReserved(false) Breakpoint 4.2](images/setIsReserved_2.png)
Con este breakpoint, se permite validar si se actualiza correctamente el estado isReserved al momento de transformar una reserva en compra.

![new BuyingOrder(foundOrder.getReservationTickets() Breakpoint 5.1](images/new_buying_order_1.png)
![new BuyingOrder(foundOrder.getReservationTickets() Breakpoint 5.2](images/new_buying_order_2.png)
En este punto, podemos validar que fue capturada correctamente la informacion que se utiliza para crear la nueva orden de compra y que esta se encuentra con las ultimas actualizaciones en sus tickets

![reserveOrders.remove(foundOrder) 6.1](images/reserveOrders_remove_1.png)
![reserveOrders.remove(foundOrder) 6.2](images/reserveOrders_remove_2.png)
En esta depuración, verificamos que reserveOrders elimina correctamente de su contenido la orden que fue transformada en compra.

## Depuración en proceso de imprimir boletas
![order.getBoughtDate() Breakpoint 7.1](images/bought_date_1.png)
![order.getBoughtDate() Breakpoint 7.1](images/bought_date_2.png)
Validamos que la variable boughtDate se inicializa y captura la información de origen correctamente

![e.getIsReserved() ? "Reserva" : "Compra" Breakpoint 1.1](images/getIsReserved_1.png)
![e.getIsReserved() ? "Reserva" : "Compra" Breakpoint 1.1](images/getIsReserved_1.png)
Visualizamos que la variable tipo obtiene y evalua correctamente para saber si es reserva o compra, información que se utiliza dentro del contenido de la boleta

Edit: La variable tipo, fue renombrada como ticketType

![totalPrice += price Breakpoint 1.1](images/total_price_1.png)
![totalPrice += price Breakpoint 1.1](images/total_price_2.png)
Validamos que se realiza correctamente la suma del valor de cada entrada, para luego imprimir en la boleta

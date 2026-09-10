package test;

import java.time.LocalDate;
import java.util.List;

import datos.Festival;
import datos.Item;
import datos.Pedido;
import negocio.FestivalABM;
import negocio.PedidoABM;

public class TestPedidosPorFechaYTipo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PedidoABM abm = new PedidoABM();
		FestivalABM festivalAbm = new FestivalABM();

		LocalDate fechaDesde = LocalDate.of(2026, 1, 1);
		LocalDate fechaHasta = LocalDate.of(2026, 12, 31);
		String tipoUnidad = "CamionComida";
		Festival festival = festivalAbm.traer(1);

		List<Pedido> lista = abm.traerPorFechaYTipoUnidad(fechaDesde, fechaHasta, tipoUnidad, festival);

		System.out.println("Pedidos entre " + fechaDesde + " y " + fechaHasta + " de tipo " + tipoUnidad
				+ " del festival " + festival.getNombre() + ":");
		for (Pedido p : lista) {
			System.out.println("Pedido " + p.getIdPedido() + " - fecha: " + p.getFecha());
			for (Item i : p.getItems()) {
				System.out.println("   Item: " + i.getCantidad() + " x " + i.getPlato().getNombre());
			}
		}

	}
}

package br.com.signer;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PrescriptionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNS_NAME = { "Paciente", "Data do Pedido", "Receituário" };

	private transient List<FileModel> tableItems;

	@Override
	public String getColumnName(int column) {
		return COLUMNS_NAME[column];
	}

	@Override
	public int getColumnCount() {
		return COLUMNS_NAME.length;
	}

	@Override
	public int getRowCount() {
		return this.tableItems.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		FileModel file = this.tableItems.get(row);

		switch (col) {
			case 0:
				return file.getPaciente();
			case 1:
				return file.getDataPedido();
			case 2:
				return file.getName().substring(file.getName().lastIndexOf("/") + 1);
			default:
				return null;
		}

	}

	public List<FileModel> getTableItems() {
		return tableItems;
	}

	public void setTableItems(List<FileModel> tableItems) {
		this.tableItems = tableItems;
	}

}

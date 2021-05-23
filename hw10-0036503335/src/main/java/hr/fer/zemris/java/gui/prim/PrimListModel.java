package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This class represent a prime number list which stores
 * prime numbers.
 * @author Tin Reiter
 * @version 1.0
 */
public class PrimListModel implements ListModel<Integer> {
	
	private List<Integer> primList;
	private List<ListDataListener> listeners;
	private int nextNumberToCheck;
	
	/**
	 * Creates a new prime number list.
	 */
	public PrimListModel() {
		this.primList = new ArrayList<>();
		this.listeners = new ArrayList<>();
		primList.add(1);
		this.nextNumberToCheck = 2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return primList.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getElementAt(int index) {
		return primList.get(index);
	}

	/**
	 * {@inheritDoc}n
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Adds the next prime number to this list.
	 */
	public void next() {
		int i = nextNumberToCheck;
		while (true) {
			if (isPrime(i)) {
				primList.add(i);
				nextNumberToCheck = ++i;
				notifyListeners();
				break;
			} else {
				i++;
			}
		}
	}
	
	/**
	 * Checks if the given number is a prime number.
	 * 
	 * @param number the number to check.
	 * @return true if the given number is a prime number,
	 *         false otherwise.
	 */
	private boolean isPrime(int number) {
		for (int i = 2; i * i <= nextNumberToCheck; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Notifies all listeners of this list that the list changed.
	 */
	private void notifyListeners() {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primList.size() - 1, primList.size() - 1);
		for (ListDataListener listener : listeners) {
			listener.intervalAdded(event);
		}
	}

}

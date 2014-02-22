package com.pointandframe.consult.util;

import java.util.ArrayList;
import java.util.List;

public class SimpleObservable implements IObservable {
	List<IObserver> observers;
	
	public SimpleObservable() {
		observers = new ArrayList<IObserver>();
	}

	@Override
	public void registerObserver(IObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(IObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (IObserver o : observers) {
			o.notify(this);
		}
	}

}

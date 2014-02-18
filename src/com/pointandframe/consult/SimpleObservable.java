package com.pointandframe.consult;

import java.util.ArrayList;
import java.util.List;

public class SimpleObservable implements Observable {
	List<Observer> observers;
	
	public SimpleObservable() {
		observers = new ArrayList<Observer>();
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.notify(this);
		}
	}

}

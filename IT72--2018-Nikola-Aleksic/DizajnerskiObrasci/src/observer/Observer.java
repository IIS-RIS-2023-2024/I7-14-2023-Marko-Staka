package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Observer {

	private boolean btnDelete;
	private boolean btnModify;

	private PropertyChangeSupport propertyChangeSupport;

	public Observer() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public void setBtnDelete(boolean btnDelete) {
		propertyChangeSupport.firePropertyChange("btnDelete", this.btnDelete, btnDelete);
		this.btnDelete = btnDelete;
	}

	public void setBtnModify(boolean btnModify) {
		propertyChangeSupport.firePropertyChange("btnModify", this.btnModify, btnModify);
		this.btnModify = btnModify;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

}
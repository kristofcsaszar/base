package hu.bme.mit.train.controller;

import com.google.common.collect.HashBasedTable;

import hu.bme.mit.train.interfaces.TrainController;
import com.google.common.collect.*;
import java.time.LocalDateTime;


public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private Table<LocalDateTime, String, Integer> dataTable = HashBasedTable.create();

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
		logData();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

	public Boolean hasLogs(){
		return !this.dataTable.isEmpty();
	}

	private void logData(){
		LocalDateTime currentTime = LocalDateTime.now();
		dataTable.put(currentTime, "joystickPosition", this.step);
		dataTable.put(currentTime, "referenceSpeed", this.referenceSpeed);
	}

}

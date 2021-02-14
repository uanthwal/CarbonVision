package com.dal.carbonfootprint.dashboard;

/**
 * Model to store User Vehicle details
 */
public class Vehcile {

    float fuelConsumption;
    String modelYear;
    String userId;
    String vehicleBrand;
    String ModelName;
    String vehicleType;
    String fuelType;

    /**
     * Default constructor
     */
    public Vehcile() {
    }

    /**
     * Parametrized constructors
     * @param fuelConsumption
     * @param modelYear
     * @param userId
     * @param vehicleBrand
     * @param modelName
     * @param vehicleType
     * @param fuelType
     */
    public Vehcile(float fuelConsumption, String modelYear, String userId, String vehicleBrand, String modelName, String vehicleType, String fuelType) {
        this.fuelConsumption = fuelConsumption;
        this.modelYear = modelYear;
        this.userId = userId;
        this.vehicleBrand = vehicleBrand;
        ModelName = modelName;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
    }

    public float getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(float fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}

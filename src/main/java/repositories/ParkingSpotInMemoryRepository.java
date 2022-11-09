package repositories;

import parkingspots.*;

import java.util.List;
import java.util.Optional;

public class ParkingSpotInMemoryRepository implements ParkingSpotRepository {
    private final List<ParkingSpot> parkingSpots;

    public ParkingSpotInMemoryRepository(List<ParkingSpot> parkingSpots){
        this.parkingSpots = parkingSpots;
    }

    @Override
    public Optional<ParkingSpot> findVehicleByPlateNumber(String plateNumber){
        return parkingSpots.stream()
                .filter(parkingSpot -> !parkingSpot.isEmpty() && parkingSpot.getVehicle().getPlateNumber().equals(plateNumber))
                .findFirst();
    }

    @Override
    public Optional<ParkingSpot> getEmptyParkingSpotWithoutElectricChargerOfType(ParkingSpotType type) {
        return parkingSpots.stream()
                .filter(parkingSpot -> parkingSpot.getParkingSpotType().equals(type) && parkingSpot.isEmpty() && !parkingSpot.hasElectricCharger())
                .findFirst();
    }

    @Override
    public Optional<ParkingSpot> getEmptyParkingSpotWithElectricChargerOfType(ParkingSpotType type){
        return parkingSpots.stream()
                .filter(parkingSpot -> parkingSpot.getParkingSpotType().equals(type) && parkingSpot.isEmpty() && parkingSpot.hasElectricCharger())
                .findFirst();
    }
}
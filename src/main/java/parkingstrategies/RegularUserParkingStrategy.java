package parkingstrategies;

import parkinglots.ParkingLotRepository;
import parkingspots.*;
import vehicles.Vehicle;
import vehicles.VehicleType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegularUserParkingStrategy implements ParkingStrategy {
    private static final Map<VehicleType, ParkingSpotType> fittingParkingSpots;

    static{
        fittingParkingSpots = new HashMap<>();
        fittingParkingSpots.put(VehicleType.MOTORCYCLE, ParkingSpotType.SMALL);
        fittingParkingSpots.put(VehicleType.CAR, ParkingSpotType.MEDIUM);
        fittingParkingSpots.put(VehicleType.TRUCK, ParkingSpotType.LARGE);
    }


    @Override
    public Optional<ParkingSpot> getParkingSpot(Vehicle vehicle, ParkingLotRepository repository){
        ParkingSpotType fittingParkingSpot = fittingParkingSpots.get(vehicle.getVehicleType());

        Optional<ParkingSpot> parkingSpotOptional = vehicle.isElectric() ? getParkingSpotWithElectricCharger(repository, fittingParkingSpot)
                                                                         : getParkingSpotWithoutElectricCharger(repository, fittingParkingSpot);

        if(parkingSpotOptional.isEmpty() && vehicle.isElectric()){
            parkingSpotOptional = getParkingSpotWithoutElectricCharger(repository, fittingParkingSpot);
        }

        return parkingSpotOptional;
    }

    private Optional<ParkingSpot> getParkingSpotWithElectricCharger(ParkingLotRepository parkingLot, ParkingSpotType parkingSpotType){
        return parkingLot.getEmptyParkingSpotWithElectricChargerOfType(parkingSpotType);
    }
    private Optional<ParkingSpot> getParkingSpotWithoutElectricCharger(ParkingLotRepository repository, ParkingSpotType parkingSpotType){
        return repository.getEmptyParkingSpotWithoutElectricChargerOfType(parkingSpotType);
    }
}

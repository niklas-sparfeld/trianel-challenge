package eu.sparfeld.trianel.spot.requirements;

public class Mapper {
    private Mapper() {
    }

    public static CustomerSpotRequirementsResponse toDto(CustomerSpotRequirements obj) {
        return new CustomerSpotRequirementsResponse(obj.getDay(), obj.getAmount(), obj.getCustomer(), obj.getVolumes().stream().map(Mapper::toDto).toList());
    }

    public static SpotVolumeResponse toDto(SpotVolume obj) {
        return new SpotVolumeResponse(obj.getVolume(), obj.getPeriodStart());
    }

    public static SpotVolume toEmbeddable(SpotVolumeResponse spotVolumeDto) {
        SpotVolume volume = new SpotVolume();
        volume.setVolume(spotVolumeDto.amount());
        volume.setPeriodStart(spotVolumeDto.periodStart());
        return volume;
    }
}

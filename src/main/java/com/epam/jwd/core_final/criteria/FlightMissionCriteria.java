package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionStatus;

import java.time.LocalDateTime;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long distance;
    private final MissionStatus missionStatus;

    public FlightMissionCriteria(String name, Long id, LocalDateTime startDate, LocalDateTime endDate, Long distance, MissionStatus missionStatus) {
        super(id, name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.missionStatus = missionStatus;
    }


    public static Builder builder() {
        return new Builder();
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }


    public LocalDateTime getEndDate() {
        return endDate;
    }


    public Long getDistance() {
        return distance;
    }

    public MissionStatus getMissionStatus() {
        return missionStatus;
    }

    @Override
    public String toString() {
        return "FlightMissionCriteria{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance +
                '}';
    }

    public static class Builder extends Criteria.Builder<FlightMissionCriteria.Builder> {
        public LocalDateTime startDate;
        public LocalDateTime endDate;
        public Long distance;
        public MissionStatus missionStatus;


        public Builder startDate(LocalDateTime value) {
            this.startDate = value;
            return this;
        }

        public Builder endDate(LocalDateTime value) {
            this.endDate = value;
            return this;
        }

        public Builder distance(Long value) {
            this.distance = value;
            return this;
        }

        public Builder missionStatus(MissionStatus value){
            this.missionStatus = missionStatus;
            return this;
        }

        public FlightMissionCriteria build() {
            return new FlightMissionCriteria(name, id, startDate, endDate, distance, missionStatus);
        }
    }
}

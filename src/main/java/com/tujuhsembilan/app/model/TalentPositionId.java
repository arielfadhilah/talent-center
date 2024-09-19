package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.UUID;
import java.util.Objects;

public class TalentPositionId implements Serializable {

    private UUID talent;
    private UUID position;

    public TalentPositionId() {}

    public TalentPositionId(UUID talent, UUID position) {
        this.talent = talent;
        this.position = position;
    }

    public UUID getTalent() {
        return talent;
    }

    public void setTalent(UUID talent) {
        this.talent = talent;
    }

    public UUID getPosition() {
        return position;
    }

    public void setPosition(UUID position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalentPositionId that = (TalentPositionId) o;
        return Objects.equals(talent, that.talent) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(talent, position);
    }
}

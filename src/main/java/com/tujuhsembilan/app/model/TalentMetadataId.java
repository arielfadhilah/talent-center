package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class TalentMetadataId implements Serializable {

    private UUID talent;

    public TalentMetadataId() {}

    public TalentMetadataId(UUID talent) {
        this.talent = talent;
    }

    public UUID getTalent() {
        return talent;
    }

    public void setTalent(UUID talent) {
        this.talent = talent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalentMetadataId that = (TalentMetadataId) o;
        return Objects.equals(talent, that.talent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(talent);
    }

}

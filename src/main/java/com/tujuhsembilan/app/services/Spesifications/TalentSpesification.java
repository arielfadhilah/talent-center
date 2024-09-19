package com.tujuhsembilan.app.services.Spesifications;

import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentRequest;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

public class TalentSpesification {
    public static Specification<Talent> talentFilter(List<UUID> tags, List<UUID> filterPosition, String filterLevel,
                                                     String filterExperience, String filterStatus, String filterEmployee, String sortBy, String nameSearch) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (tags != null && !tags.isEmpty()) {
                predicates.add(root.join("skillset").get("skillsetId").in(tags));
            }

            if (filterPosition != null && !filterPosition.isEmpty()) {
                predicates.add(root.join("positions").get("positionId").in(filterPosition));
            }

            if (filterLevel != null && !filterLevel.isEmpty()) {
                predicates.add(cb.equal(root.get("talentLevel").get("talentLevelName"), filterLevel));
            }

            if (filterExperience != null && !filterExperience.isEmpty()) {
                if (filterExperience.contains("-")) {
                    String[] experienceRange = filterExperience.split("-");
                    int minExperience = Integer.parseInt(experienceRange[0]);
                    int maxExperience = Integer.parseInt(experienceRange[1]);

                    predicates.add(cb.between(root.get("experience"), minExperience, maxExperience));
                } else {
                    int experience = Integer.parseInt(filterExperience);

                    if (experience >= 5) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("experience"), experience));
                    } else {
                        predicates.add(cb.equal(root.get("experience"), experience));
                    }
                }
            }

            if (filterStatus != null && !filterStatus.isEmpty()) {
                predicates.add(cb.equal(root.get("talentStatus").get("talentStatusName"), filterStatus));
            }

            if (filterEmployee != null && !filterEmployee.isEmpty()) {
                predicates.add(cb.equal(root.get("employeeStatus").get("employeeStatusName"), filterEmployee));
            }

            if (nameSearch != null && !nameSearch.isEmpty()) {
                String searchValue = nameSearch.toLowerCase();
                predicates.add(cb.like(cb.lower(root.get("talentName")), "%" + searchValue + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<TalentRequest> talentApprovalFilter(String agencyName, String talentName,
                                                                    String statusName) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (agencyName != null && !agencyName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("talentWishlist").get("client").get("agencyName")),
                        "%" + agencyName.toLowerCase() + "%"));
            }
            if (talentName != null && !talentName.isEmpty()) {
                predicates.add(cb.equal(root.get("talentWishlist").get("talent").get("talentName"), talentName));
            }

            if (statusName != null && !statusName.isEmpty()) {
                predicates.add(cb.equal(root.get("talentRequestStatus").get("talentRequestStatusName"), statusName));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}

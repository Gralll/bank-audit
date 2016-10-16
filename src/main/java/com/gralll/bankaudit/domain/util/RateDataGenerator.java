package com.gralll.bankaudit.domain.util;

import java.util.HashSet;
import java.util.Set;

import com.gralll.bankaudit.domain.GroupRate;
import com.gralll.bankaudit.domain.LocalRate;
import com.gralll.bankaudit.domain.RateData;
import com.gralll.bankaudit.domain.enumeration.CheckCategory;

public class RateDataGenerator {

    public  RateData generateRateData() {
        RateData rateData = new RateData();
        return rateData.groupRates(generateGroupRateSet(rateData));
    }

    private  Set<GroupRate> generateGroupRateSet(RateData rateData) {
        Set<GroupRate> groupRateSet = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            GroupRate groupRate = new GroupRate();
            groupRate.indexRate("" + i).category("TU").name("Name " + i).localRates(generateLocalRate(groupRate)).rateData(rateData);
            groupRateSet.add(groupRate);
        }
        return groupRateSet;
    }

    private  Set<LocalRate> generateLocalRate(GroupRate groupRate) {
        Set<LocalRate> localRateSet = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            localRateSet.add(new LocalRate()
                .indexRate("M1." + (i+1))
                .question("Qwesdksdhbsh cksdlkfbsd?" + i)
                .necessary(Boolean.TRUE)
                .category(i % 2 == 0 ? CheckCategory.FIRST : CheckCategory.FOURTH)
                .groupRate(groupRate));
        }
        return localRateSet;
    }
}

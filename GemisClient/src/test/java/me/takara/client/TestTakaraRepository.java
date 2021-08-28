package me.takara.client;

import me.takara.shared.Instrument;
import me.takara.shared.TakaraContext;
import me.takara.shared.entities.Bond;
import me.takara.shared.entities.Equity;
import me.takara.shared.entities.fields.BondFields;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestTakaraRepository {

    static boolean isGemisReady(TakaraRepository repository) {
        try {
            return repository.heartbeat();
        } catch (Exception ex) {
            System.out.println("No Gemis service!");
            return false;
        }
    }

    @Test
    public void testGet() {

        try (TakaraRepository.BondRepository bondRepository = (TakaraRepository.BondRepository)TakaraBuilder.create(TakaraContext.BOND_PRIMARY_LOCAL)) {

            if (!isGemisReady(bondRepository)) return;

            Bond item = bondRepository.get(12);
            Assert.assertEquals(12, item.getId());
        }

        try (TakaraRepository.EquityRepository equityRepository = (TakaraRepository.EquityRepository)TakaraBuilder.create(TakaraContext.EQUITY_PRIMARY_LOCAL)) {

            if (!isGemisReady(equityRepository)) return;

            Equity equity = equityRepository.get(12);
            Assert.assertEquals(12, equity.getId());
        }
    }

    @Test
    public void testWhere() {

        try (TakaraRepository.BondRepository bondRepository = (TakaraRepository.BondRepository)TakaraBuilder.create(TakaraContext.BOND_PRIMARY_LOCAL)) {
            if (!isGemisReady(bondRepository)) return;

            List<Instrument> results = bondRepository.where().equal(BondFields.ID, 22).fetchFirstOnly();
            Assert.assertEquals(1, results.size());
            Assert.assertEquals(22, results.get(0).getId());

            results = bondRepository.where().lessThan(BondFields.ID, 22).fetchAll();
            Assert.assertEquals(22, results.size());

            results = bondRepository.where().lessThan(BondFields.ID, 22).fetchFirstOnly();
            Assert.assertEquals(1, results.size());
        }
    }

    @Test
    public void testAdd() {
        try (TakaraRepository.BondRepository bondRepository = (TakaraRepository.BondRepository)TakaraBuilder.create(TakaraContext.BOND_PRIMARY_LOCAL)) {

            if (!isGemisReady(bondRepository)) return;

            Bond bond = new Bond() {{
               setName("Canon");
               setId(999);
               setStatus("ACTIVE");
            }};

            bondRepository.add(bond);

        }
    }

}
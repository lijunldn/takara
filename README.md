# Project Takara

Project Takara is a data cache application built on JDK 10.

## Gemis

To start a Takara Cache, execute Gemis (me.takara.gemis) with one of the enum types defined in 'me.takara.shared.TakaraContext'.
- start primary
  ```
  Bond: BOND_MASTER_LOCAL
  Equity: EQUITY_MASTER_LOCAL
  ```
- start secondary
  ```
  Bond: BOND_SLAVE_LOCAL
  Equity: EQUITY_SLAVE_LOCAL
  ```
  
### Data Replication

master-slave relationship as per 'me.takara.shared.TakaraContext'

    
### REST Interface

Jersey resource model: RestfulController (me.takara.gemis)
- heartbeat 
  ```
  http://localhost:8090/gemis/
  ```
  
- getById
  ```
  http://localhost:8090/gemis/12
  ```

## GemisClient

Client package for applications who want to query data from Takara data cache.  


### User Case
- Start a tracker to pull data from a remote Gemis
```java
var tracker = TakaraBuilder.createTrackerSinceTimeZero(TakaraContext.BOND_MASTER_LOCAL);
while (tracker.hasNext()) {
    var items = tracker.next(10);
}
```

- Data Query: By ID
```java
try (TakaraRepository.BondRepository bondRepository = (TakaraRepository.BondRepository)TakaraBuilder.create(TakaraContext.BOND_MASTER_LOCAL)) {
    
    Bond item = bondRepository.get(12);
    Assert.assertEquals(12, item.getId());
}
```  

- Data Query: WHERE clause
```java
try (TakaraRepository.BondRepository bondRepository = (TakaraRepository.BondRepository)TakaraBuilder.create(TakaraContext.BOND_MASTER_LOCAL)) {

    List<Instrument> results = bondRepository.where().equal(BondFields.ID, 22).fetchFirstOnly();
    Assert.assertEquals(1, results.size());
    Assert.assertEquals(22, results.get(0).getId());

    results = bondRepository.where().lessThan(BondFields.ID, 22).fetchAll();
    Assert.assertEquals(22, results.size());

}
```

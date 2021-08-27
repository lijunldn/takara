# Project Takara

Project Takara is a data cache application built on JDK 10.

```buildoutcfg
This project is inspired by a real project I worked in the past. 
I'm now re-creating it for fun. 
```

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

master-slave relationship as per TakaraContext (me.takara.shared)
- When data is added/changed in the primary, data replication will make sure same changes happen at the secondary (slave). 
- When remove data, change its STATUS from ACTIVE to INACTIVE.
- Gemis internal housekeeping will eventually remove INACTIVE data per scheduled. 
- Gemis data maintains a SyncStamp HashMap on the JVM heap, while each item in the map points to an off-heap memory blob (where the actual data is stored). 

    
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

#### Data Tracking
A tracker works like a data listener.  When there is a data change in the remote Gemis, it pulls the changes. 
- Start a tracker to pull data from a remote Gemis
```java
var tracker = TakaraBuilder.createTrackerSinceTimeZero(TakaraContext.BOND_MASTER_LOCAL);
while (tracker.hasNext()) {
    var items = tracker.next(10);
}
```

#### Data Query
- By ID
```java
try (TakaraRepository.BondRepository bondRepository = (TakaraRepository.BondRepository)TakaraBuilder.create(TakaraContext.BOND_MASTER_LOCAL)) {
    
    Bond item = bondRepository.get(12);
    Assert.assertEquals(12, item.getId());
}
```  

- WHERE clause
```java
try (TakaraRepository.BondRepository bondRepository = (TakaraRepository.BondRepository)TakaraBuilder.create(TakaraContext.BOND_MASTER_LOCAL)) {

    List<Instrument> results = bondRepository.where().equal(BondFields.ID, 22).fetchFirstOnly();
    Assert.assertEquals(1, results.size());
    Assert.assertEquals(22, results.get(0).getId());

    results = bondRepository.where().lessThan(BondFields.ID, 22).fetchAll();
    Assert.assertEquals(22, results.size());

}
```

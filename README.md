# Project Takara
- - - - 

Project Takara is a data cache application built on JDK 10.

## Gemis ##

In order to start a Takara Cache, pass in one of the enum types defined in 'me.takara.shared.TakaraContext'.

###Data Replication 
master-slave relationship as per 'me.takara.shared.TakaraContext'
    
###REST Interface
me.takara.gemis.RestfulController

## Shared ## 
Core libary shared by both Gemis and GemisClient. 

## GemisClient ##

Client package for applications who want to query data from Takara data cache.  

```java
    @Test
    public void testGetDataByItemID() {
        TakaraRepository repository = TakaraBuilder.create(TakaraContext.BOND_MASTER_LOCAL);
        Instrument data = repository.get(1);
        ...
    

